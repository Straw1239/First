package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Lambdas 
{

	public static void main(String[] args) 
	{
		List<String> list = new ArrayList<>(Arrays.asList("Hello", "Goodbye", "Good Morning", "Evening", "Konichiwa", "Bonjour"));
		Collection<String> goods = list.stream().filter(s -> { return s.startsWith("Good");}).collect(Collectors.toSet());
		System.out.println(goods);
		Collections.sort(list, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
		System.out.println(list);
		
		
		
	}

}
