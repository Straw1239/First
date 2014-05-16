package ents;


import java.awt.AWTPermission;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Point;
import java.io.FilePermission;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.SerializablePermission;
import java.lang.reflect.Field;
import java.lang.reflect.ReflectPermission;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Permission;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.security.auth.AuthPermission;

import critters.Critter;
import critters.CritterFrame;
import critters.CritterInfo;
import critters.CritterModel;

public final class Ent extends Critter
{
	/**
	 * Store default System.out printstream so we can change the System.out version
	 */
	private static final PrintStream out = System.out;
	/**
	 * Store default System.err printstream so we can change the System.err version
	 */
	private static final PrintStream err = System.err;
	/**
	 * Secure random for secure number generation. Used for password generation, not general random numbers
	 */
	private static SecureRandom secureRand = new SecureRandom();
	
	/**
	 * Password for our custom security manager. Randomly generated 64 bytes (by our secure random number generator)
	 *  should be practically impossible to break; the security manager stops reflection preventing outside access.
	 * 
	 */
	private static byte[] password = secureRand.generateSeed(64);
	/**
	 * Custom SecurityManager preventing critters from messing with things, cheating, spamming System.err or System.out
	 */
	private static EntSecurity security = new EntSecurity(password);
	private static String symbol = "E";
	private static Random rand = new Random();
	/**
	 * Internal model used as a verification that critters are not cheating.
	 */
	private static CritterModel model;
	
	/**
	 * List of all of our critters currently alive. Updated regularly
	 */
	private static List<Ent> ents = new LinkedList<>();
	
	private Mover run;
	
	
	
	
	public Ent()
	{
		if(model == null) secureModel();
		ents.add(this);
	}
		
	
	private Map<Direction, Neighbor> getAllAdjacent(CritterInfo info)
	{
		Map<Direction, Neighbor> data = new EnumMap<>(Direction.class);
		Neighbor a = info.getFront();
		Direction d = info.getDirection();
		if (a == Neighbor.OTHER || a == Neighbor.SAME)
		{
			data.put(d, a);
		}
		
		a = info.getBack();
		d = (d == Direction.NORTH) ? Direction.WEST : (d == Direction.WEST) ? Direction.SOUTH : (d == Direction.SOUTH) ? Direction.EAST : Direction.NORTH;
		d = (d == Direction.NORTH) ? Direction.WEST : (d == Direction.WEST) ? Direction.SOUTH : (d == Direction.SOUTH) ? Direction.EAST : Direction.NORTH;
		if (a == Neighbor.OTHER || a == Neighbor.SAME)
		{
			data.put(d, a);
		}
		
		a = info.getLeft();
		d = info.getDirection();
		d = (d == Direction.NORTH) ? Direction.WEST : (d == Direction.WEST) ? Direction.SOUTH : (d == Direction.SOUTH) ? Direction.EAST : Direction.NORTH;
		if (a == Neighbor.OTHER || a == Neighbor.SAME)
		{
			data.put(d, a);
		}
		a = info.getRight();
		d = info.getDirection();
		d = (d == Direction.NORTH) ? Direction.EAST : (d == Direction.WEST) ? Direction.NORTH : (d == Direction.SOUTH) ? Direction.WEST : Direction.SOUTH;
		if (a == Neighbor.OTHER || a == Neighbor.SAME)
		{
			data.put(d, a);
		}
		return data;
	}
	
	@Override
	public Color getColor()
	{
		return Color.GRAY;
	}
	
	
	@Override
	public Action getMove(CritterInfo info)
	{
		removeDead();
		if(run == null) first();
		Action move = run.getMove(info);
		run = null;
		return move;
		
	}
	
	@Override
	public String toString()
	{
		return symbol;
	}
	
	private static Point direction(Direction d)
	{
		switch (d)
		{
		case EAST:
			return new Point(1, 0);
		case NORTH:
			return new Point(0, 1);
		case SOUTH:
			return new Point(0, -1);
		case WEST:
			return new Point(-1, 0);
		default:
			throw new Error("Missing enum label");
		}
	}
	
	private static void first()
	{
		for(Ent e : ents)
		{
			e.run = new Mover()
			{
				@Override
				public Action getMove(CritterInfo info)
				{
					return Action.HOP;
				}
			};
		}
	}
	private static void removeDead()
	{
		Iterator<Ent> it = ents.iterator();
		while (it.hasNext())
		{
			Ent e = it.next();
			Point p = null;
			try
			{
				 p = model.getPoint(e);
				 if(p == null) it.remove();
			}
			catch (NullPointerException npr)
			{
				it.remove();
			}
			
		}
	}
	/**
	 * Obtain and secure the crittermodel as part of our anti-cheat
	 */
	private synchronized static void secureModel()
	{
		security.tryPassword(password);
		Field model = null;
		try
		{
			CritterFrame frame = (CritterFrame) Frame.getFrames()[0];
			model = frame.getClass().getDeclaredField("myModel");
			model.setAccessible(true);
			Ent.model = (CritterModel) model.get(frame);
		}
		catch (Throwable t)
		{
			t.printStackTrace(err);
		}
		finally
		{
			model.setAccessible(false);
			security.lock();
		}
	}
	
	private static final class EntSecurity extends SecurityManager
	{
		private MessageDigest hasher;
		private boolean unlocked = false;
		private byte[] password;
		private Collection<Permission> illegals = new ArrayList<>();
		
		public EntSecurity(byte[] password)
		{
			Thread.setDefaultUncaughtExceptionHandler((e, t) -> 
			{
				err.println("ERROR: " + t + " THROWN BY CRITTER; SHOULD BE DISQUALIFIED");
				t.printStackTrace(err);
			}); 
			PrintStream nothing = new PrintStream(new OutputStream()
			{

				@Override
				public void write(int b) throws IOException
				{
					
				}
			});
			System.setErr(nothing);
			System.setOut(nothing);
			System.setIn(new InputStream()
			{
				@Override
				public int read() throws IOException
				{
					return 0;
				}
				
			});
			this.password = getHash(Arrays.copyOf(password, password.length));
			setIllegals();
			try
			{
				System.setSecurityManager(this);
			}
			catch(SecurityException e)
			{
				err.println("ERROR: SECURITY MANAGER CANNOT BE INSTALLED, ANTI CHEAT UNENFORCABLE");
				e.printStackTrace(err);
			}
			
		}
		
		@Override
		public synchronized void checkPermission(Permission p)
		{
			if(unlocked) return;
			
			if(p.getName().equals("setSecurityManager"))
			{
				throw new SecurityException();
			}
			StackTraceElement[] stack = new RuntimeException().getStackTrace();
			
			for(StackTraceElement e : stack)
			{
				unlocked = true;
				Class<?> next;
				try
				{
					next = Class.forName(e.getClassName());
				}
				catch (ClassNotFoundException e1)
				{
					throw new InternalError(e1);
				}
				finally
				{
					unlocked = false;
				}
				if(e.getMethodName().equals("getMove") || (e.getMethodName().equals("<init>") && Critter.class.isAssignableFrom(next)))
				{
					for(Permission i : illegals)
					{
						if(p.implies(i)) throw new SecurityException();
						if(i.implies(p)) throw new SecurityException();
					}
				}		
			}	
		}
		
		private byte[] getHash(byte[] data)
		{
			if(hasher == null) 
				try
				{
					hasher = MessageDigest.getInstance("SHA-256");
				}
				catch (NoSuchAlgorithmException e)
				{
					throw new Error("NO SHA-256 ALGORITHM AVALIABLE", e);
				}
			hasher.update(data);
			byte[] hash = hasher.digest();
			hasher.reset();
			return hash;
		}
		
		public void lock()
		{
			unlocked = false;
		}
		
		private void setIllegals()
		{
			illegals.add(new ReflectPermission("suppressAccessChecks"));
			illegals.add(new ReflectPermission("newProxyInPackage.*"));
			illegals.add(new AWTPermission("*"));
			//illegals.add(new FilePermission("<<ALL FILES>>", "read"));
			illegals.add(new FilePermission("<<ALL FILES>>", "write"));
			illegals.add(new FilePermission("<<ALL FILES>>", "execute"));
			illegals.add(new FilePermission("<<ALL FILES>>", "delete"));
			illegals.add(new FilePermission("<<ALL FILES>>", "readlink"));
			illegals.add(new SerializablePermission("enableSubstitution"));
			illegals.add(new SerializablePermission("enableSubclassImplementation"));
			illegals.add(new RuntimePermission("setIO"));
			illegals.add(new RuntimePermission("accessClassInPackage.*"));
			illegals.add(new RuntimePermission("accessDeclaredMembers"));
			illegals.add(new RuntimePermission("createClassLoader"));
			illegals.add(new RuntimePermission("getClassLoader"));
			illegals.add(new RuntimePermission("setContextClassLoader"));
			illegals.add(new RuntimePermission("exitVM.*"));
			illegals.add(new AuthPermission("doAsPrivileged"));
		}
		
		public void tryPassword(byte[] password)
		{
			byte[] entered = getHash(password);
			if(MessageDigest.isEqual(this.password, entered))
			{
				unlocked = true;
			}
		}
		
		
		public void tryPassword(char[] password)
		{
			tryPassword(new String(password).getBytes());
		}	
	}	
}
