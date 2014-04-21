package javaec;

public class MetricLength implements Comparable<MetricLength>
{
	private static final long MMCM = 10;
	private static final long CMM = 100;
	
	private long mm;
	
	public static long areaInMM2(MetricLength m1, MetricLength m2)
	{
		return m1.mm * m2.mm;
	}
	
	public static double areaInM2(MetricLength m1, MetricLength m2)
	{
		return areaInMM2(m1, m2) / (double)(CMM * CMM * MMCM * MMCM);
	}
	
	public MetricLength()
	{
		this(0);
	}
	
	public MetricLength(int meters, int centimeters, int millimeters)
	{
		this((meters * CMM * MMCM) + centimeters * MMCM + millimeters);
		if(meters < 0 || centimeters < 0 || millimeters < 0) throw new IllegalArgumentException();
	}
	
	public MetricLength(int millis)
	{
		this((long)millis);
	}
	
	public MetricLength(double centimeters)
	{
		this((long)(centimeters * MMCM));
	}
	
	private MetricLength(long millis)
	{
		if(millis < 0) throw new IllegalArgumentException();
		this.mm = millis;
	}
	
	public void add(MetricLength m)
	{
		mm += m.mm;
	}
	
	public void subtract(MetricLength m)
	{
		mm -= m.mm;
	}
	
	public void divide(int factor)
	{
		if(factor == 0) throw new IllegalArgumentException();
		mm /= factor;
	}
	
	public void multiply(int factor)
	{
		mm *= factor;
	}

	@Override
	public int compareTo(MetricLength o)
	{
		return Long.compare(mm, o.mm);
	}
	
	public int toMillimeters()
	{
		return (int) mm;
	}
	
	public double toCentimeters()
	{
		return mm / MMCM;
	}
	
	public double toMeters()
	{
		return mm / (MMCM * CMM);
	}
	
	public String toString()
	{
		return getMeters() + "m " + getCentimeters() + "cm " + getMillimeters() + "mm";
	}
	
	public int getMeters()
	{
		return (int)(mm / (MMCM * CMM));
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (mm ^ (mm >>> 32));
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
		if (!(obj instanceof MetricLength))
		{
			return false;
		}
		MetricLength other = (MetricLength) obj;
		if (mm != other.mm)
		{
			return false;
		}
		return true;
	}

	public int getCentimeters()
	{
		return (int) (((mm / MMCM)) - getMeters() * CMM);
	}
	
	public int getMillimeters()
	{
		return (int) (mm - MMCM * CMM * getMeters() - MMCM * getCentimeters());
	}
	
	
	
	
	
}
