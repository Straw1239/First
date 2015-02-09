package main;

import java.util.Random;
import java.util.Scanner;


public class YouAreAmazing
{

	public static void main(String[] args)
	{
		System.out.println("Guess my number...");
		int maxBound = 10;
		int num = new Random().nextInt(maxBound) + 1;
		Scanner console = new Scanner(System.in);
		while (true)
		{
		    int guess = console.nextInt();
		    if (guess == num)
		    {
		    	System.out.println("Well done! You are very clever.");
		    	break;
		    }
		    else if (Math.abs(guess - num) <= 2) System.out.println("So close!");
		    else System.out.println("What a shame...");
		}
	}
}
