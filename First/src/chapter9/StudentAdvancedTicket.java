package chapter9;

public class StudentAdvancedTicket extends AdvancedTicket
{
	
	public StudentAdvancedTicket(int number, int daysBefore)
	{
		super(number, daysBefore);
	}
	
	public double getPrice()
	{
		return super.getPrice() / 2;
	}
	
	public String toString()
	{
		return super.toString() + " (ID required)";
	}

}
