package chapter9.interfaces;

public class Octagon extends Polygon
{
	public static final int NUM_SIDES = 8;
	
	private static final double RATIO =  2 * (1 + Math.sqrt(2));
	
	public Octagon(double sideLength)
	{
		super(NUM_SIDES, sideLength);
	}

	@Override
	public double area() 
	{
		return RATIO * sideLength * sideLength;
	}
}
