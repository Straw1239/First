package main;

import java.util.Scanner;


import util.Utils;

public class Problem3
{
	public static void main(String[] args)
	{
		Utils.init();
		Scanner console = new Scanner(System.in);
		int a = console.nextInt();
		int b = console.nextInt();
		int c = console.nextInt();
		int d = console.nextInt();
		long n = console.nextLong();
		System.out.println(NthArrangement(a, b, c, d, n - 1));
		console.close();
	}

	private static String NthArrangement(int a, int b, int c, int d, long n)
	{
		//O(log(n)) Search?
		int numPaintings = a + b + c + d;
		int[] paintings = {a, b, c, d};
		char[] state = new char[numPaintings];
		for(int stateI = 0; stateI < state.length; stateI++)
		{
			int lastI = 0;
			for(int i = 0; i < paintings.length; i++)
			{
				if(paintings[i] == 0) 
				{
					if(i == paintings.length - 1)
					{
						paintings[lastI]--;
						break;
					}
					else continue;
				};
				state[stateI] = painters[i];
				paintings[i]--;
				int index = stateI + 1;
				for(int j = 0; j < paintings.length; j++)
				{
					for(int k = 0; k < paintings[j]; k++) state[index++] = painters[j];
				}
				paintings[i]++;
				long num = arrangementNum(state, a, b, c, d);
				if(num > n)
				{
					state[stateI] = painters[lastI];
					paintings[lastI]--;
					break;
				}
				lastI = i;
				if(i == paintings.length - 1) paintings[lastI]--;
			}
			
		}
		return new String(state);
	}
	
	private static long arrangementNum(char[] arr, int a, int b, int c, int d)
	{
		return arrangementNum(arr, 0, arr.length, a, b, c, d);
	}
	private static final char[] painters = {'A', 'B', 'C', 'D'};
	
	private static final int getPainterNum(char painter)
	{
		switch (painter)
		{
		case 'A': return 0;
		case 'B': return 1;
		case 'C': return 2;
		case 'D': return 3;
		}
		throw new IllegalArgumentException();
	}
	private static long arrangementNum(char[] arr, int start, int end, int a, int b, int c, int d)
	{
		long total = 0;
		int[] paintings = {a, b, c, d};
		for(int i = start; i < end; i++)
		{
			char painter = arr[i];
			for(int j = 0; j < painters.length; j++)
			{
				if(painter == painters[j]) break;
				int num = j;
				if(paintings[num] > 0)
				{
					paintings[num]--;
					total += countAll(paintings);
					paintings[num]++;
				}
			}
			paintings[getPainterNum(arr[i])]--;
		}
		return total;
	}
	private static long countAll(int[] counts)
	{
		return countAll(counts[0], counts[1], counts[2], counts[3]);
	}
	private static long countAll(int a, int b, int c, int d)
	{
		return factorial(a + b + c + d) / 
				(factorial(a) * factorial(b) * factorial(c) * factorial(d));
	}
	private static final long[] factorials = new long[21];
	static
	{
		factorials[0] = 1;
		factorials[1] = 1;
		for(int i = 2; i < factorials.length; i++)
		{
			factorials[i] = factorials[i - 1] * i;
		}
	}
	
	
	private static long factorial(int n)
	{
		if(n < 0) throw new IllegalArgumentException();
		return (n < factorials.length) ? factorials[n] : Long.MAX_VALUE;
	}
}
