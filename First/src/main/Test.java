package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Test
{

	public static class Cell
	{
		
	}

	public static void main(String[] args)
	{
		char[] source = "hello whats up lets get cationing".toCharArray();
		Map<String, Cell> lookups = new HashMap<>(); // Fill this with all mappings of groups of characters to Cells, including single characters
		NavigableMap<Integer, Cell> results = new TreeMap<>();
		int maxLength = 4;
		for(int i =  maxLength; i > 0; i--)
		{
			for(int j = 0; j <= source.length - i; j++)
			{
				String sub = String.copyValueOf(source, j, i);
				Cell replacement = lookups.get(sub);
				if(replacement != null)
				{
					Arrays.fill(source, j, j + i, '\0');
					results.put(j, replacement);
				}
			}
		}

	}

}
