package chapter7;
import java.util.*;
public class PigLatin 
{
	public static void main(String[] args) 
	{
		Scanner console = new Scanner(System.in);
		System.out.println("This is a program which converts phrases to pig latin.");
		System.out.print("phrase? (enter to quit)");
		String line = console.nextLine();
		while(line.length() > 0)
		{
			Scanner splitter = new Scanner(line);
			while(splitter.hasNext())
			{
				System.out.print(convertWord(splitter.next()) + " ");
			}
			System.out.println();
			System.out.print("phrase? (enter to quit)");
			line = console.nextLine();
		}
	}
	public static String convertWord(String word)
	{
		int firstVowel = 0;
		while(firstVowel < word.length())
		{
			if(isVowel(word.charAt(firstVowel))) break;
			firstVowel++;
		}
		String endString = "-" + word.substring(0,firstVowel) + "ay";
		String startString = word.substring(firstVowel);
		return startString + endString;
	}
	public static boolean isVowel(char character)
	{
		String vowels = "AEIUOaeiuo";
		for(int i = 0; i < vowels.length();i++)
		{
			if (character == vowels.charAt(i)) return true;
		}
		return false;
	}

}
