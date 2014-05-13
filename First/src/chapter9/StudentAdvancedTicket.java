package chapter9;

public class StudentAdvancedTicket extends AdvancedTicket
{
	
	public StudentAdvancedTicket(int number, int daysBefore)
	{
		super(number, daysBefore);
	}
	
	@Override
	public double getPrice()
	{
		return super.getPrice() / 2;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + " (ID required)";
	}

}
