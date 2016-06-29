package main;

import java.util.Arrays;

public class Yay
{

	public static void main(String[] args)
	{
		String plaintext = "Hello how are you whats up this is a list of semi meaningless greetings which are repeated endlessly for no discernable purpose";
		String key = "ajkshqlw";
		char[] result = encipher(plaintext, key);
		System.out.println(result);
		
	}
	
	static char[] encipher(String plaintext, String key)
	{
		plaintext = plaintext.toUpperCase();
		key = key.toUpperCase();
		if(key.length() > 26) throw new IllegalArgumentException("Key is too long");  
		//Counts of each character
		int[] counts = new int[26];
		
		/*
		 * Count the characters
		 */
		for(int i = 0; i < key.length(); i++)
		{
			char a = key.charAt(i);
			int index = a - 65;
			if(index < 0 || index > 26) throw new IllegalArgumentException("Invalid character");
			counts[index]++;
			if(counts[index] > 1) throw new IllegalArgumentException("Repeated character");
			
		}
		//Fill in the key
		
		for(int i=0; i < counts.length; i++)
		{
			if(counts[i] == 0) 
			{
				key += (char)(i + 65);
			}
		}
		
		//Actual enciphering
		
		char[] cipher = new char[plaintext.length()];
		for(int i = 0; i < cipher.length; i++)
		{
			int index = plaintext.charAt(i) - 65;
			if(index >= 0 && index < 26) 
			{
				cipher[i] = key.charAt(index);
			}
			else
			{
				cipher[i] = plaintext.charAt(i);
			}
		}
		return cipher;
	}

}
