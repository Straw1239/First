package chapter10;

public class Sale implements Comparable<Sale>
{
	public String name;
	public double value;
	
	public Sale(String name, double value)
	{
		this.name = name;
		this.value = value;
	}
	
	@Override
	public int compareTo(Sale s) 
	{
		return Double.compare(s.value, value);
	}
}
