package chapter10;


class ReferencedDouble implements Comparable<ReferencedDouble>
{
	public double value;
	
	public ReferencedDouble(double value)
	{
		this.value = value;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof ReferencedDouble)) return false;
		ReferencedDouble other = (ReferencedDouble) obj;
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value)) return false;
		return true;
	}

	@Override
	public int compareTo(ReferencedDouble d) 
	{
		return Double.compare(value, d.value);
	}
}