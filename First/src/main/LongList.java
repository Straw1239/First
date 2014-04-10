package main;

import java.util.Arrays;

public class LongList 
{
	private long[] array;
	private int size;
	public LongList()
	{
		this(8);
		size = 0;
	}
	public LongList(int capacity)
	{
		array = new long[capacity];
		size = 0;
	}
	public LongList(long[] array)
	{
		this.array = Arrays.copyOf(array,array.length);
		size = array.length;
	}
	public void set(int position,long value)
	{
		if (position >= size) throw new IllegalArgumentException();
		array[position] = value;
	}
	public long get(int position)
	{
		if (position >= size) throw new IllegalArgumentException();
		return array[position];
	}
	public void add(long newLong)
	{
		if (size >= array.length)
		{
			array = Arrays.copyOf(array, array.length*2);
		}
		array[size] = newLong;
		size++;
	}
	public void ensureCapacity(int capacity)
	{
		if(capacity > array.length)
		{
			array = Arrays.copyOf(array, capacity);
		}
	}
	public int capacity()
	{
		return array.length;
	}
	public int size()
	{
		return size;
	}
	public long[] getArray()
	{
		return Arrays.copyOf(array, size);
	}
	public String toString()
	{
		StringBuilder s = new StringBuilder();
		s.append("[");
		for(int i = 0; i < size-1;i++)
		{
			s.append(array[i]);
			s.append(", ");
		}
		s.append(array[size-1]);
		s.append("]");
		return s.toString();
	}
}
