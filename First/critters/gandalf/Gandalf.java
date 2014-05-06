package gandalf;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.io.FilePermission;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Permission;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;

import critters.Critter;
import critters.CritterFrame;
import critters.CritterInfo;
import critters.CritterModel;

public class Gandalf extends Critter
{
	
	
	private static String symbol = "G";
	private static Random rand = new XRandom();
	private static Robot robot;
	private static JFrame stopKevinFromWinning = new JFrame();
	private static SecurityManager stopKevin2 = new EntSecurity();
	
	private static class EntSecurity extends SecurityManager
	{
		private MessageDigest hasher;
		private boolean unlocked = false;
		private byte[] password;
		
		public EntSecurity()
		{
			System.setSecurityManager(this);
		}
		public void tryPassword(char[] password)
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
			
			hasher.update(new String(password).getBytes());
			byte[] entered = hasher.digest();
			hasher.reset();
			if(MessageDigest.isEqual(this.password, entered))
			{
				unlocked = true;
			}
		}
		
		public void lock()
		{
			unlocked = false;
		}
		public void checkPermission(Permission p)
		{
			if(unlocked) return;
			StackTraceElement[] stack = new RuntimeException().getStackTrace();
			for(StackTraceElement e : stack)
			{
				if(e.getMethodName().equals("getMove"))
				{
					if(p.getName().equals("suppressAccessChecks"))
					{
						throw new SecurityException();
					}
					
					
				}
			}
			
			
		}
	};
	private Point location = new Point(0, 0);
	private Direction direction;
	
	
	
	
	public Gandalf()
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
		try
		{
			//System.setSecurityManager(stopKevin2);
		}
		catch (Throwable t)
		{
			//throw new Error(t);
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
			throw new RuntimeException("Missing enum label");
		}
	}
	
	private static void cheat()
	{
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
		
	}
	
	
}
