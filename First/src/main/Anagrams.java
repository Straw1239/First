package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Anagrams 
{
	static Map<String,List<String>> anagrams;
	static Map<Character, Integer> charVals = new TreeMap<>();
	final static String alphabet = "abcdefghijklmnopqrstuvwxyz";
	public static void main(String[] args) throws FileNotFoundException
	{	
		final Scanner console = new Scanner(System.in);
		for(int i = 0; i < 26;i++)
		{
			charVals.put(alphabet.charAt(i),i);
		}
		System.out.print("Dictionary name? ");
		String dictName = console.next();
		Scanner dictionary = new Scanner(new File(dictName));
		System.out.print("Input name? ");
		String inputName = console.next();
		Scanner input = new Scanner(new File(inputName));
		long time1 = System.nanoTime();
		anagrams = buildMap(dictionary);
		while(input.hasNext())
		{
			findAnagrams(input.next().toLowerCase());
		}
		while(console.hasNext())
		{
			findAnagrams(console.next().toLowerCase());
		}
		long time2 = System.nanoTime();
		System.out.println((time2-time1)/(double)1000000000);
		
		
		input.close();
		console.close();
	}
	public static Map<String,List<String>> buildMap(Scanner dictionary)
	{
		Map<String,List<String>> map = new TreeMap<>();
		String entry;
		String key;
		while (dictionary.hasNext())
		{
			entry = dictionary.next().toLowerCase();
			key = sortString(entry);
			if(map.containsKey(key))
			{
				map.get(key).add(entry);
			}
			else
			{
				List<String> temp = new ArrayList<>();
				temp.add(entry);
				map.put(key,temp);
			}		
		}
		return map;
	}
	public static String sortString(String s)
	{
		int[] chars = new int[26];
		for(int i = 0; i < 26; i++) chars[i] = 0;
		int l = s.length();
		char[] output = new char[l];	
		for(int i = 0; i < l;i++ ) chars[charVals.get(s.charAt(i))]++;	
		int position = 0;
		for(int i = 0; i < 26; i++)
		{
			for(int j = 0; j < chars[i]; j++)
			{
				output[position] = alphabet.charAt(i);
				position++;
			}
		}
		return new String(output);		
	}
	public static void findAnagrams(String arg)
	{
		//System.out.println("Anagrams of " + arg+":");
		String key = sortString(arg);
		List<String> results = anagrams.get(key);
		for(int i = 0; i < results.size();i++) 
		if(!results.get(i).equals(arg)) System.out.println(results.get(i));
		
	}
	
}
