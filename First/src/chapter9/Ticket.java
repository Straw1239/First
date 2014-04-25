package chapter9;

public abstract class Ticket
{
	private int number;
	
	protected Ticket(int number)
	{
		this.number = number;
	}
	
	public abstract double getPrice();
	
	public int getNumber()
	{
		return number;
	}
	
	public String toString()
	{
		return String.format("Number: %d, Price: %.1f", number, getPrice());
	}
}
