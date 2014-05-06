package ents;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.lang.reflect.Field;
import java.lang.reflect.ReflectPermission;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Permission;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

import critters.Critter;
import critters.CritterFrame;
import critters.CritterInfo;
import critters.CritterModel;

public class Ent extends Critter
{
	
	
	private static String symbol = "G";
	private static Random rand = new XRandom();
	private static SecureRandom secureRand = new SecureRandom();
	private static byte[] password = secureRand.generateSeed(64);
	private static Robot robot;
	private static EntSecurity security = new EntSecurity(password);
	
	private static class EntSecurity extends SecurityManager
	{
		private MessageDigest hasher;
		private boolean unlocked = false;
		private byte[] password;
		
		public EntSecurity(byte[] password)
		{
			this.password = getHash(Arrays.copyOf(password, password.length));
			System.setSecurityManager(this);
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
		public void checkPermission(Permission p)
		{
			if(unlocked) return;
			
			if(p.getName().equals("setSecurityManager"))
			{
				throw new SecurityException();
			}
			StackTraceElement[] stack = new RuntimeException().getStackTrace();
			for(StackTraceElement e : stack)
			{
				if(e.getMethodName().equals("getMove"))
				{
					Permission illegal = new ReflectPermission("suppressAccessChecks");
					if(p.implies(illegal)) throw new SecurityException();
				}
			}
			
			
		}
	};
	private Point location = new Point(0, 0);
	private Direction direction;
	
	
	
	
	public Ent()
	{
		if(robot == null)
		{
			try
			{
				robot = new Robot();
			}
			catch (AWTException e)
			{
				throw new RuntimeException(e);
			}
		}
	}
	
	
	
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
		if(a == Action.HOP)
		{
			location.move(direction(direction));
		}
		return a;
	}
	
	public Color getColor()
	{
		return Color.GRAY;
	}
	
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
			CritterFrame frame = (CritterFrame) CritterFrame.getFrames()[0];
			Field model = frame.getClass().getDeclaredField("myModel");
			model.setAccessible(true);
			CritterModel critterModel = (CritterModel) model.get(frame);
			
		}
		catch (Throwable t)
		{
			
		}
		security.lock();
		
	}
	
	
}
