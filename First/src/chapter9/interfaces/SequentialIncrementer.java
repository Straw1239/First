package chapter9.interfaces;

public class SequentialIncrementer implements Incrementable 
{
	int value = 0;
	@Override
	public void increment() 
	{
		value++;

	}

	@Override
	public int getValue() 
	{
		return value;
	}

}
