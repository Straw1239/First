package logic;

import net.openhft.koloboke.collect.set.hash.HashIntSet;
import net.openhft.koloboke.collect.set.hash.HashIntSets;

public class Test
{
	public static void main(String[] args)
	{
		HashIntSet reached = HashIntSets.newUpdatableSet();
		int[][] start = {{1, 2, 3, 4}, {2, 3, 4, 1}, {3, 4, 1, 2}, {4, 1, 2, 3}};
		
		
		for(int[] s : start)
		{
			reached.add(convert(s));
			print(convert(s));
			IntArraySwapper gen = new IntArraySwapper(s);
			while(gen.hasNext())
			{
				gen.advance();
				reached.add(convert(gen.get()));
			}
			gen.revert();
		}
		for(int i : reached)
		{
			print(i);
		}
		System.out.println(reached.size());
	}
	
	static int convert(int[] combo)
	{
		return combo[0] | (combo[1] << 8) | (combo[2] << 16) | (combo[3] << 24);
	}
	
	static void print(int combo)
	{
		System.out.printf("[%d, %d, %d, %d]\n", combo & 0xFF, (combo >>> 8) & 0xFF, (combo >>> 16) & 0xFF, (combo >>>24) & 0xFF);
	}
}
