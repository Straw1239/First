package chapter9;

public class AdvancedTicket extends Ticket
{
	int days;
	public AdvancedTicket(int number, int daysBefore)
	{
		super(number);
		days = daysBefore;
	}

	@Override
	public double getPrice()
	{
		return (days < 10) ? 40 : 30;
	}

}
