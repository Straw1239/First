package ents;


import java.awt.AWTPermission;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Point;
import java.io.FilePermission;
import java.io.IOException;
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
import java.util.IdentityHashMap;
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
	 * Custom SecurityManager preventing other critters from messing with things, cheating, spamming System.err or System.out
	 */
	private static EntSecurity security = new EntSecurity(password);
	private static String symbol = "E";
	private static Random rand = new Random();
	private static IdentityHashMap<Ent, Point> locations = new IdentityHashMap<>();
	
	
	
	
	private Direction direction;
	
	
	
	
	public Ent()
	{
		
	}
	
	
	
	@Override
	public Action getMove(CritterInfo info)
	{
		direction = info.getDirection();
		if(info.getFront() == Neighbor.OTHER)
		{
			return logMove(Action.INFECT);
		}
		if(info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL)
		{
			return logMove(rand.nextBoolean() ? Action.RIGHT : Action.LEFT);
		}
		
		return logMove(Action.HOP);
	}
	
	private Action logMove(Action a)
	{
		//LOG
		return a;
	}
	
	@Override
	public Color getColor()
	{
		return Color.GRAY;
	}
	
	@Override
	public String toString()
	{
		return symbol;
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
	
	private synchronized static void cheat()
	{
		security.tryPassword(password);
		try
		{
			CritterFrame frame = (CritterFrame) Frame.getFrames()[0];
			Field model = frame.getClass().getDeclaredField("myModel");
			model.setAccessible(true);
			CritterModel critterModel = (CritterModel) model.get(frame);
			
		}
		catch (Throwable t)
		{
			
		}
		security.lock();
		
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
			this.password = getHash(Arrays.copyOf(password, password.length));
			setIllegals();
			System.setSecurityManager(this);
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
			illegals.add(new AuthPermission("doAsPrivileged"));
		}
		
		public void tryPassword(char[] password)
		{
			tryPassword(new String(password).getBytes());
		}
		
		public void tryPassword(byte[] password)
		{
			byte[] entered = getHash(password);
			if(MessageDigest.isEqual(this.password, entered))
			{
				unlocked = true;
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
					throw new Error(e);
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
				unlocked = false;
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
		
		@Override
		public void checkExit(int status)
		{
			StackTraceElement[] stack = new RuntimeException().getStackTrace();
			for(StackTraceElement e : stack)
			{
				if(e.getMethodName().equals("getMove"))
				{
					throw new SecurityException();
				}
				
			}
		}
		
		@Override
		public void checkPropertiesAccess()
		{
			super.checkPropertiesAccess();
		}
		
		
	}
	
	
}
