package numbers;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Random;

public class BitState 
{
	private static Random rand = new Random();
	private static long[][] tables;
	private static byte mask = 0B00001111;
	
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
		return (byte) ((byte) (state >>> (getIndex(row,column)) * 16) & mask);
	}
	
	public BitState move(Direction d)
	{
		BitState moved = new BitState(state);
		if(d == Direction.Left)
		{
			for(byte i = 0; i < 4; i++)
			{
				moved.state ^= tables[i][getIndex(true,i)];
			}
		}
		else if (d == Direction.Right)
		{
			for(byte i = 0; i < 4; i++)
			{
				moved.state ^= tables[i + 8][getIndex(true,i)];
			}
		}
		else if(d == Direction.Down)
		{
			for(byte i = 0; i < 4; i++)
			{
				moved.state ^= tables[i + 4][getIndex(false,i)];
			}
		}
		else if(d == Direction.Up)
		{
			for(byte i = 0; i < 4; i++)
			{
				moved.state ^= tables[i + 12][getIndex(false,i)];
			}
		}
		return moved; //Make it actually do something
	}
	
	private static void initializeTables()
	{
		//ADD XOR
		tables = new long[16][65536];
		for(byte i = 0; i < 16; i++)
		{
			for(byte j = 0; j < 16;j++)
			{
				for(byte k = 0; k < 16; k++)
				{
					for(byte m = 0; m < 16; m++)
					{
						int index = getIndex(i,j,k,m);
						byte[] one = calculate(i,j,k,m);
						byte[] reverse = calculate(m,k,j,i);
						reverse(reverse);
						long value = ((long)i) +((long)j << 4) + ((long)k << 8) + ((long)m << 12);
						for(int h = 0; h < 4; h++)
						{
							long temp = ((long)one[0]) +((long)one[1] << 4) + ((long)one[2] << 8) + ((long)one[3] << 12) ^ value;
							temp <<= h * 16;
							tables[h][index] = temp;
							temp = ((long)reverse[0]) +((long)reverse[1] << 4) + ((long)reverse[2] << 8) + ((long)reverse[3] << 12) ^ value;
							temp <<= h * 16;
							tables[h + 8][index] = temp; 
						}
						//Reset value to correct column value
						//value = ;
						for(int h = 4; h < 7;h++)
						{
							long temp = 0L;
							for(int g = 0; g < 4; g++) temp += ((long)one[g]) << ((g << 4) + (h << 2));
							tables[h][index] = temp ^ value;
							temp = 0L;
							for(int g = 0; g < 4; g++) temp += ((long)reverse[g]) << ((g << 4) + (h << 2));
							tables[h + 8][index] = temp ^ value;
						}
							
					}
				}
			}
		}
	}
	
	private static void reverse(byte[] array)
	{
		for(int i = 0; i < array.length / 2;i++)
		{
			byte temp = array[i];
		    array[i] = array[array.length - i - 1];
		    array[array.length - i - 1] = temp;
		}
	}
	
	private static byte[] calculate(byte... column)
	{
		column = Arrays.copyOf(column, column.length);
		for(int i = 1; i < column.length;i++)
		{
			if(column[i] != 0)
			{
				int j = i;
				while(j >= 1 && column[j-1] == 0)
				{
					j--;
				}
				if(j > 0 && column[j-1] == column[i])
				{
					column[j-1]++;
					column[j-1] *= -1;
					column[i] = 0;
				}
				else if(j != i)
				{
					column[j] = column[i];
					column[i] = 0;
				}
			}
		}
		for(int i = 0; i < column.length;i++)
		{
			if(column[i] < 0) column[i] *= -1;
		}
		return column;
	}
	
	private int getIndex(byte row, byte column)
	{
		assert (row < 4 && row >= 0) : "Illegal row: " + row ;
		assert (column < 4 && column >= 0) : "Illegal column: " + column;
		return (row << 2) ^ column;
	}
	
	private static int getIndex(byte a, byte b, byte c, byte d)
	{
		return (((int)a) << 12) ^ (((int)b) << 8) ^ (((int)c) << 4) ^ ((int)d);
	}
	
	private int getIndex(boolean row,byte number)
	{
		int index = 0;
		if(row)
		{
			for(byte i = 0; i < 4; i++)
			{
				index += (getSquare(i,number)) << (i << 2);
			}
		}
		else
		{
			for(byte i = 0;i < 4;i++)
			{
				index += (getSquare(number,i)) << (i << 2);
			}
		}
		return index; 
	}
	
	public void print(PrintStream p)
	{
		for(byte i = 3; i >= 0;i--)
		{
			for(byte j = 0; j < 4;j++)
			{
				p.print(getSquare(j,i) + " ");
			}
			p.println();
		}
		p.flush();
	}
	
	public static void main(String[] args)
	{
		long test = 0B11110000_00001111_00000000_00000000_00000000_00000000_00000000_00001111L;
		BitState b = new BitState(test);
		System.out.println(b.getSquare((byte)0,(byte)0));
		b.print(System.out);
	}
	
	
	
}
