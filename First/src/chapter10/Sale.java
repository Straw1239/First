package chapter10;

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
		return Double.compare(s.value, value);
	}
}
