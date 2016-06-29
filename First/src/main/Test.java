package main;

import java.util.stream.IntStream;

public class Test
{

	public static class Cell
	{
		
	}
	
	static long root(long val)
	{
		int s = 32 - Long.numberOfLeadingZeros(val - 1) / 2;
		long g0 = 1L << s;
		long g1 = (g0 + (val >>> s)) >>> 1;
		while(g1 < g0)
		{
			g0 = g1;
			g1 = ( g0 + (val / g0)) >>> 1;
		}
		return g0;
	}

	static boolean isSquare(long v)
	{
		long k = root(v);
		return k * k == v;
	}	
	
	public static void main(String[] args)
	{
		double p = 1000;
		int counter = 0;
		while(p < 10000)
		{
			p += 10000 / p;
			counter++;
		}
		System.out.println(counter);
	}

}
