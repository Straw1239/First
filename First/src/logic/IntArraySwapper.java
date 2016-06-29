package logic;

import static logic.Utils.swap;

public final class IntArraySwapper
{
	private int[] array;
	private int index1 = 0;
	private int index2 = 0;

	public IntArraySwapper(int[] source)
	{
		array = source;
	}
	
	public int firstSwapIndex()
	{
		return index1;
	}
	
	public int secondSwapIndex()
	{
		return index2;
	}
	
	public boolean hasNext()
	{
		return !(index1 == array.length - 2 && index2 == array.length - 1);
	}
	
	public void advance()
	{
		swap(array, index1, index2); // Swap back from old configuration
		index2++;
		if(index2 == array.length)
		{
			index1++;
			index2 = index1 + 1;
		}
		swap(array, index1, index2);
	}
	
	public int[] get()
	{
		return array;
	}
	
	public void revert()
	{
		if(hasNext()) throw new IllegalStateException();
		swap(array, index1, index2);
	}
}
