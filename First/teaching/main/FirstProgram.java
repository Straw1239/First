package main;

import java.util.Arrays;

public class FirstProgram
{
	
	public static void main(String[] args)
	{
		int[] blah = calcFibonacci(10);
		for(int i = 0; i < blah.length; i++)
		{
			System.out.println(blah[i]);
		}
	}
		
	static int[] calcFibonacci(int f)
	{
		int[] result = new int[f];
		int g = 1;
		int h = 0;
		for(int i = 0; i < f; i++)
		{
			result[i] = g;
			g += h;
			h = result[i];
		}
		return result;
	}
	
	
}
