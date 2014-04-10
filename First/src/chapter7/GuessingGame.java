package chapter7;

import java.util.*;

public class GuessingGame 
{

	public static void main(String[] args) 
	{
		Scanner console = new Scanner(System.in);
		Random rand = new Random();
		System.out.println("This is a guessing game where you try and guess a number between 1 and 100");
		int total = 0, games = 0;
		while(true)
		{
			int guess = getGuess(console), number = getNextInt(rand),guesses = 1;
			while(guess != number)
			{
				System.out.println( (guess < number) ? "higher" : "lower");
				guess = getGuess(console);
				guesses++;
			}
			System.out.println("You got it in " + guesses + " guesses");
			total += guesses;
			games++;
			System.out.print("Another game? (y/n) ");
			if(!console.next().equals("y")) break;
		}
		System.out.println("Total guesses: " + total);
		System.out.println("Average number of guesses per game: " + (total/(double)games));
	}
	public static int getGuess(Scanner console)
	{
		System.out.print("Your guess? ");
		return console.nextInt();
	}
	public static int getNextInt(Random r)
	{
		return r.nextInt(100)+1;
	}
}
