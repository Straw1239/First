package chapter9.interfaces;


public abstract class Polygon implements Shape
{
	protected double sideLength;
	protected int numSides;
	
	protected Polygon(int sides, double sideLength)
	{
		this.numSides = sides;
		this.sideLength = sideLength;
	}
	
	public double perimeter()
	{
		return numSides * sideLength;
	}
	
}
