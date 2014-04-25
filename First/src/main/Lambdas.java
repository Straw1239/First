package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Lambdas {

	public static void main(String[] args) 
	{
		List<String> data = new ArrayList<>(Arrays.asList("Harry", "Bob", "George", ""));
		Collection<String> stuff = data.parallelStream().unordered().filter(e -> !e.isEmpty()).map(String::toUpperCase).collect(Collectors.toSet());
		System.out.println(stuff);

	}

}
