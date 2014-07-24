package manny;

import java.util.Arrays;

public class Eric
{

	public static void main(String[] args)
	{
		printFactors(24);
		System.out.println();
		printFactors(2013);
	}
	
	public static void printFactors(int num)
	{
		int sqrt = (int) Math.round(Math.sqrt(num));
		for (int factor = 2; factor <= sqrt; factor++)
		{
			while(num % factor == 0)
			{
				System.out.println(factor);
				num /= factor;
			}
		}
		
		if(num != 1)
		{
			System.out.println(num);
		}
		


	
	}
}
