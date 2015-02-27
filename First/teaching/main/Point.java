package main;

public class Point
{
	private double x, y;
	
	public Point(double xc, double yc)
	{
		x = xc;
		y = yc;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public double theta()
	{
		return Math.atan2(y, x);
	}
	
	public String toString()
	{
		return String.format("(%f, %f)", x, y);
	}
	
	public static double distance(Point a ,Point b)
	{
		double xDistance = a.x - b.x;
		double yDistance = a.y - b.y;
		return Math.sqrt(xDistance*xDistance + yDistance*yDistance);
	}
}
