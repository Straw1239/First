package utils;

import objects.GameObject;
import objects.ObjectDataHolder;

public class Utils 
{
	public static double distance(double x1, double y1, double x2, double y2)
	{
		return Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2, 2));
	}
	
	public static double distance(ObjectDataHolder g1, ObjectDataHolder g2)
	{
		return distance(g1.getX(),g1.getY(),g2.getX(),g2.getY());
	}
	
	public static boolean circleCollide(ObjectDataHolder g1, ObjectDataHolder g2, double radius)
	{
		return distance(g1,g2) < radius;
	}
}
