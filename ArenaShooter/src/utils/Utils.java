package utils;

import objects.ObjectDataHolder;

/**
 * Provides various utility functions used in multiple places
 * @author Rajan Troll
 *
 */
public class Utils 
{
	public static double distance(double x1, double y1, double x2, double y2)
	{
		return Math.sqrt(distanceSquared(x1,y1,x2,y2));
	}
	
	public static double distance(ObjectDataHolder g1, ObjectDataHolder g2)
	{
		return distance(g1.getX(), g1.getY(), g2.getX(), g2.getY());
	}
	
	public static double distanceSquared(double x1, double y1, double x2, double y2)
	{
		return Math.pow(x1-x2,2) + Math.pow(y1-y2, 2);
	}
	
	public static double distanceSquared(ObjectDataHolder g1, ObjectDataHolder g2)
	{
		return distanceSquared(g1.getX(),g1.getY(),g2.getX(),g2.getY());
	}
	
	/**
	 * Sleeps for the specified time with Thread.sleep.
	 * If interrupted, returns true, else false.
	 * @param millis
	 * @return
	 */
	public static boolean sleep(long millis)
	{
		try
		{
			Thread.sleep(millis);
		}
		catch(InterruptedException e)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if two circular objects collide at the given radius
	 * @param g1
	 * @param g2
	 * @param radius
	 * @return
	 */
	public static boolean circleCollide(ObjectDataHolder g1, ObjectDataHolder g2, double radius)
	{
		return distanceSquared(g1,g2) < radius * radius;
	}
	
	public static double angle(ObjectDataHolder g1, ObjectDataHolder g2)
	{
		double x = g2.getX() - g1.getX();
		double y = g2.getY() - g1.getY();
		return Math.atan2(y, x);
	}
	
	public static boolean isInRange(double value, double min, double max)
	{
		return (value >= min) && (value <= max);
	}
	
	public static double clamp(double value, double min, double max)
	{
		assert min <= max;
		if(value <= min) return min;
		if(value >= max) return max;
		return value;
	}
	
	
}
