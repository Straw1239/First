package main;

import java.util.Arrays;

public class FirstProgram
{
	
	public static void main(String[] args)
	{
		long[] blah = calcFibonacci(92);
		for(int i = 0; i < blah.length; i++)
		{
			System.out.println(blah[i]);
		}
	}
		
	static long[] calcFibonacci(int f)
	{
		long[] result = new long[f];
		long g = 1, h = 0;
		for(int i = 0; i < f; i++)
		{
			result[i] = g;
			g += h;
			h = result[i];
		}
		return result;
	}
	
	
}
