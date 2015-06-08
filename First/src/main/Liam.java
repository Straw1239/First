package main;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Liam
{
	public static void main(String[] args)
	{
		//String sam = "3^aaU2ae2feg\\2e[2_Se2W_aeWiS"; // 18
		//String birth = "2jrUyeczs1jaary"; // 17
		"2jrUyeczs1jaary".chars().map(d -> d - 17).map(Character::toUpperCase).mapToObj(d->(char)d).collect(Collector.of(ArrayDeque::new, (r, a) -> {r.push(a);}, (r, r2) -> {r.addAll(r2); return r;})).stream().peek(System.out::print).toArray();
	}
}
