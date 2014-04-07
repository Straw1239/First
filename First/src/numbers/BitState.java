package numbers;

import java.util.Random;

public class BitState 
{
	private static Random rand = new Random();
	private static long[][] tables;
	
	private long state;
	
	public BitState(long state)
	{
		this();
		this.state = state;
	}
	
	public BitState()
	{
		if(tables == null) initializeTables();
	}
	
	public byte getSquare(byte row, byte column)
	{
		return (byte) (state >> getIndex(row,column));
	}
	
	public BitState move(Direction d)
	{
		BitState moved = new BitState();
		long xor;
		for(byte i = 0; i < 4;i++)
		{
			
		}
		return moved; //Make it actually do something
	}
	
	private static void initializeTables()
	{
		
	}
	
	private int getIndex(byte row, byte column)
	{
		return (row << 2) ^ column;
	}
	
	private int getIndex(boolean row,byte number)
	{
		int index = 0;
		if(row)
		{
			
		}
		else
		{
			
		}
		return index; // Make it return correct indices
	}
	
	
}
