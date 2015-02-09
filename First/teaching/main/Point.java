package main;

public class Point
{
	public static void main(String[] args)
	{
		Point p1 = new Point(1,1);
		System.out.println(p1);
		Point p2 = new Point(2,2);
		System.out.println(p1);
		System.out.println("You are so clever!");
	}

	
	public Point(double xCoordinate, double yCoordinate)
	{
		x = xCoordinate;
		y = yCoordinate;
	}
	
	private double x, y;
	
	public String toString()
	{
		return String.format("(%f, %f)", x, y);
	}
}
