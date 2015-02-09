package main;

import java.util.Scanner;

import util.Utils;

public class Problem1
{

	public static void main(String[] args)
	{
		Utils.init();
		Scanner console = new Scanner(System.in);
		String input = console.next();
		console.close();
		System.out.println(numBlockPalindromes(input));
		printBPalindromes(input, "", "");
	}
	
	private static int numBlockPalindromes(String input)
	{
		int total = 0;
		for(int i = 1; i < input.length(); i++)
		{
			int j = input.length() - i;
			if(j < i) break;
			String left = input.substring(0, i);
			String right = input.substring(j);
			if(left.equals(right))
			{
				String middle = input.substring(i, j);
				//System.out.println(middle);
				if(middle.length() > 1) total += numBlockPalindromes(middle) + 1;
				else total += 1;
			}
		}
		return total;
	}
	
	private static void printBPalindromes(String input, String oleft, String oright)
	{
		if(input.length() == 0) return;
		for(int i = 1; i < input.length(); i++)
		{
			int j = input.length() - i;
			if(j < i) break;
			String left = input.substring(0, i);
			String right = input.substring(j);
			if(left.equals(right))
			{
				String middle = input.substring(i, j);
				if(middle.length() > 1)
				{
					printBPalindromes(middle, oleft + "(" + left + ")", "(" + right + ")" + oright);
				}
				else
				{
					if(middle.length() == 1)
					System.out.printf("%s(%s)(%s)(%s)%s\n", oleft, left, middle, right, oright);
					else System.out.printf("%s(%s)(%s)%s\n", oleft, left, right, oright);
				}
			}
		}
		if(oleft.length() != 0)
		{
			System.out.printf("%s(%s)%s\n", oleft, input, oright);
		}
	}

}
