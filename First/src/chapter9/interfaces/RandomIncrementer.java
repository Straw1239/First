package chapter9.interfaces;

import java.util.Random;

public class RandomIncrementer implements Incrementable {

	private static Random rand = new Random();
	private int value = rand.nextInt();
	@Override
	public void increment() 
	{
		value = rand.nextInt();
	}

	@Override
	public int getValue() {
		return value;
	}

}
