package chapter10;

import java.util.Comparator;

/**
 * Represents a sale, with a name of the person who bought, and the value in $ of the sale.
 * @author Rajan Troll 
 */
public class Sale implements Comparable<Sale>
{
	/**
	 * Name of customer who bought something
	 */
	public final String name;
	/**
	 * Value of sale
	 */
	public final double value;
	
	/**
	 * New sale with specified name and value.
	 * @param name
	 * @param value
	 */
	public Sale(String name, double value)
	{
		this.name = name;
		this.value = value;
	}
	
	/**
	 * New sale with specified name and value. Same as Sale(name, value)
	 * @param name
	 * @param value
	 */
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
	
	/**
	 * Orders sales in alphabetical order of name, ignoring value.
	 */
	public static final Comparator<Sale> ALPHABETICAL_ORDER = new Comparator<Sale>()
	{
		@Override
		public int compare(Sale s1, Sale s2)
		{
			int value = s1.name.compareTo(s2.name);
			return value;
		}
	};
	
	/**
	 * Orders sales by ascending value, breaking ties by alphabetical order.
	 */
	public static final Comparator<Sale> ASCENDING_ORDER = new Comparator<Sale>()
	{
		@Override
		public int compare(Sale o1, Sale o2)
		{
			int value = Double.compare(o1.value, o2.value);
			return value == 0 ? o1.name.compareTo(o2.name) : value;
		}
	};
	
	/**
	 * Orders sales by descending value, breaking ties by alpabetical order.
	 */
	public static final Comparator<Sale> DESCENDING_ORDER = new Comparator<Sale>()
	{
		@Override
		public int compare(Sale o1, Sale o2)
		{
			return o1.compareTo(o2);
		}
	};
}
