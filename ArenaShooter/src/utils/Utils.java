package utils;

import objects.GameObject;

public class Utils 
{
	public static double distance(double x1, double y1, double x2, double y2)
	{
		return Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2, 2));
	}
	
	public static double distance(GameObject g1, GameObject g2)
	{
		return distance(g1.getX(),g1.getY(),g2.getX(),g2.getY());
	}
}
