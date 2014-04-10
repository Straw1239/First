//Rajan Troll
//Straw1239@gmail.com

package chapter7;

import java.util.*;
import java.io.*;

public class Mastermind 
{
	public static Random rand = new Random();
	public static Scanner console = new Scanner(System.in);
	public static void main(String[] args) 
	{
		System.out.println("This program lets you play mastermind.");
		System.out.println("First set the number of digits for my number, then start guessing.");
		System.out.println("After each guess, I will tell you how many digits were correct and correctly placed, ");
		System.out.println("and how many digits were correct but incorrectly placed.");
		System.out.print("Number of digits?");
		int digits = console.nextInt();
		char[] number = getNumber(digits);
		System.out.println( new String(number));
		char[] b = new char[5];
		char[] guess = getGuess(digits);
		int guesses = 1;
		while(!Arrays.equals(number,guess))
		{
			System.out.print("Number in correct spot: ");
			System.out.println(numInCorrectSpot(guess,number));
			System.out.print("Number correct in wrong spot: ");
			System.out.println(numCorrectInWrongSpot(guess,number));
			guess = getGuess(digits);
			guesses++;
		}
		System.out.println("You got my number of " + new String(number) + " in " + guesses + " guesses!");
		
		
	}
	
	/**
	 * Returns a random number of digits length in the form of a char array, 0 included.
	 * @param digits
	 * @return
	 */
	public static char[] getNumber(int digits)
	{
		String number = "";
		for(int i = 0; i < digits;i++) number += rand.nextInt(10);
		return number.toCharArray();
	}
	
	/**
	 * Returns the number of digits in the correct spot given two numbers.
	 * @param guess
	 * @param number
	 * @return
	 */
	public static int numInCorrectSpot(char[] guess, char[] number)
	{
		if(guess.length != number.length) throw new IllegalArgumentException();
		int numCorrect = 0;
		for(int i = 0; i < guess.length;i++)
		{
			if(guess[i] == number[i]) numCorrect++;
		}
		return numCorrect;
	}
	
	/**
	 * Returns the number of digits correct, but in the wrong spot, given two numbers.
	 * @param guess
	 * @param number
	 * @return
	 */
	public static int numCorrectInWrongSpot(char[] guess, char[] number)
	{
		if(guess.length != number.length) throw new IllegalArgumentException();
		int output = 0;
		char[] digits = Arrays.copyOf(number, number.length);
		//If something is in the right spot, we shouldn't count it as a digit that could be in the wrong spot
		int correct = 0;
		for(int i = 0; i < guess.length; i++)
		{
			if(digits[i] == guess[i]) digits[i] = 'C';
		}
		if(correct >= number.length-1) return 0;
		for(int i = 0; i < guess.length;i++)
		{
			for(int j = 0; j < digits.length;j++)
			{
				if(guess[i] == digits[j])
				{
					output++;
					digits[j] = 'c';
					break;
				}
			}
		}
		return output;
	}
	
	/**
	 * Gets a valid guess of digits digits.
	 * @return
	 */
	public static char[] getGuess(int digits)
	{
		System.out.print("Guess?");
		String input = console.next();
		while(!isValidInput(input,digits))
		{
			System.out.print("Guess?");
			input = console.nextLine();
		}
		return input.toCharArray();
	}
	
	/**
	 * Checks if a given input is a valid guess.
	 * @param input
	 * @return
	 */
	public static boolean isValidInput(String input, int digits)
	{
		if(input.length() != digits) return false;
		char[] chars = input.toCharArray();
		if(chars[0] == '+' || chars[0] == '-') return false;
		try
		{
			Integer.parseInt(input);
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
	}
}
