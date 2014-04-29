package chapter9.interfaces;

public class Hexagon extends Polygon
{
	public static final int NUM_SIDES = 6;
	
	private static final double RATIO = 1.5 * Math.sqrt(3);
	
	public Hexagon(double sideLength) 
	{
		super(NUM_SIDES, sideLength);
	}

	@Override
	public double area() 
	{
		return RATIO * sideLength * sideLength;
	}
}
