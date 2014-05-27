package chapter10;

import java.util.Comparator;

public class Sale implements Comparable<Sale>
{
	public final String name;
	public final double value;
	
	public Sale(String name, double value)
	{
		this.name = name;
		this.value = value;
	}
	
	public Sale(double value, String name)
	{
		this(name, value);
	}
	
	@Override
	public int compareTo(Sale s) 
	{
		int value = Double.compare(s.value, this.value);
		return value == 0 ? name.compareToIgnoreCase(s.name) : value;
	}
	
	@Override
	public String toString()
	{
		return String.format("%.2f %s", value, name);
	}
	
	public static final Comparator<Sale> ALPHABETICAL_ORDER = new Comparator<Sale>()
	{
		@Override
		public int compare(Sale s1, Sale s2)
		{
			int value = s1.name.compareToIgnoreCase(s2.name);
			return value;
		}
	};
	
	public static final Comparator<Sale> ASCENDING_ORDER = new Comparator<Sale>()
	{
		@Override
		public int compare(Sale o1, Sale o2)
		{
			int value = Double.compare(o1.value, o2.value);
			return value == 0 ? o1.name.compareToIgnoreCase(o2.name) : value;
		}
	};
	
	public static final Comparator<Sale> DESCENDING_ORDER = new Comparator<Sale>()
	{
		@Override
		public int compare(Sale o1, Sale o2)
		{
			return o1.compareTo(o2);
		}
	};
}
