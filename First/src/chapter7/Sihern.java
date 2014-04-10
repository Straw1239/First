package chapter7;
import java.util.*;
import java.io.*;
public class Sihern 
{

	public static void main(String[] args) throws FileNotFoundException 
	{
		Scanner input = getScanner("WordLengths");
		int[] lengthCounts = new int[80];
		while(input.hasNext())
		{
			String word = input.next();
			int length = word.length();
			lengthCounts[length-1]++;
		}
		for(int i = 0; i < lengthCounts.length; i++)
		{
			if( lengthCounts[i] > 0)
			{
				System.out.println("There were " + lengthCounts[i] + " words with length " + (i+1) + ".");
			}
		}
	}
	
	public static Scanner getScanner(String filename) throws FileNotFoundException
	{
		return new Scanner(new File(filename));
	}
}
