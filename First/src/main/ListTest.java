package main;

import java.util.*;

public class ListTest 
{

	public static void main(String[] args) 
	{
		IntList list = new IntList();
		for(int i = 0; i <= 1000;i++)
		{
			list.add(i*i);
		}
		list.set(0,10000);
		System.out.println(list.remove(5));
		System.out.println(list.remove());	
		list.add(0,5);
		System.out.println(list);
		
		
		
	}

}
