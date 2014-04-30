package chapter9.interfaces;


public abstract class Polygon implements Shape
{
	protected double sideLength;
	protected int numSides;
	
	protected Polygon(int sides, double sideLength)
	{
		if(sideLength < 0) throw new IllegalArgumentException("Negative side length: " + sideLength);
		this.numSides = sides;
		this.sideLength = sideLength;
	}
	
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + numSides;
		long temp;
		temp = Double.doubleToLongBits(sideLength);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Polygon))
			return false;
		Polygon other = (Polygon) obj;
		if (numSides != other.numSides)
			return false;
		if (Double.doubleToLongBits(sideLength) != Double
				.doubleToLongBits(other.sideLength))
			return false;
		return true;
	}

	public double perimeter()
	{
		return numSides * sideLength;
	}
	
	public int numSides()
	{
		return numSides;
	}
	
	public double sideLength()
	{
		return sideLength;
	}
	
}
