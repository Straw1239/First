package tristan;

public class Complex
{
	private final double r , i;
	public Complex (double real , double imaginary)
	{
		r = real;
		i = imaginary;
	}
	
	public double realPart ()
	{
		return r;
	}
	
	public double imaginaryPart ()
	{
		return i;
	}
	
	public Complex add (Complex toad)
	{
		Complex result = new Complex (r + toad.r, i + toad.i);
		return result;
	}
	
	public Complex sub (Complex toast)
	{
		return new Complex (r - toast.r, i - toast.i);
	}
	
	public String toString ()
	{
		return r + " + " + i + "i";
	}
	
	public Complex conjugate ()
	{
		return new Complex (r , - i);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(i);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(r);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Complex)) return false;
		Complex other = (Complex) obj;
		if (Double.doubleToLongBits(i) != Double.doubleToLongBits(other.i))
			return false;
		if (Double.doubleToLongBits(r) != Double.doubleToLongBits(other.r))
			return false;
		return true;
	}
	
	
}
