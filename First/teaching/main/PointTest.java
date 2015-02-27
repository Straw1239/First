package main;

public class PointTest
{

	public static void main(String[] args)
	{
		Point a = new Point(535.34354, 42.4234);
		System.out.println(a);
		Point b = new Point(33333,2);
		System.out.println(Point.distance(a,b));
	}

}
