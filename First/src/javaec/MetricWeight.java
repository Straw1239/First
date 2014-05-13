package javaec;

public class MetricWeight implements Comparable<MetricWeight>
{
	private static final long RATIO = 1000;
	
	private long grams;
	
	public MetricWeight()
	{
		this(0);
	}
	
	public MetricWeight(int grams)
	{
		this((long)grams);
	}
	
	private MetricWeight(long grams)
	{
		this.grams = grams;
	}
	
	public MetricWeight(int tons, int kg, int grams)
	{
		this(tons * RATIO * RATIO + kg * RATIO + grams);
	}
	
	public MetricWeight(double kg)
	{
		this((long) (kg * RATIO));
	}
	
	@Override
	public String toString()
	{
		return getTons() + "t " + getKG() + "kg " + getGrams() + "g";
	}
	
	public int getTons()
	{
		return (int) (grams / (RATIO * RATIO));
	}
	
	public int getKG()
	{
		return (int) (grams / RATIO) - getTons() * 1000;
	}
	
	public int getGrams()
	{
		return (int) (grams - RATIO * getKG() - RATIO * RATIO * getTons());
	}
	
	public void subtract(MetricWeight w)
	{
		grams -= w.grams;
	}
	
	public void add(MetricWeight w)
	{
		grams += w.grams;
	}
	
	public void multiply(int factor)
	{
		grams *= factor;
	}
	
	public void divide(int factor)
	{
		if(factor == 0) throw new IllegalArgumentException();
		grams /= factor;
	}
	
	public double toKG()
	{
		return grams / (double)RATIO;
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (grams ^ (grams >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof MetricWeight))
		{
			return false;
		}
		MetricWeight other = (MetricWeight) obj;
		if (grams != other.grams)
		{
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(MetricWeight o)
	{
		return Long.compare(grams, o.grams);
	}
	
	
}
