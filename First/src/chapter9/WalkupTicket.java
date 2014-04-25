package chapter9;

public class WalkupTicket extends Ticket
{

	public WalkupTicket(int number)
	{
		super(number);
	}

	@Override
	public double getPrice()
	{
		return 50;
	}

}
