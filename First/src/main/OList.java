package main;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class OList <T>
{
	private T[] array;
	private int size;
	
	public OList()
	{
		this(8);
	}
	
	@SuppressWarnings("unchecked")
	public OList(int capacity)
	{
		array = (T[]) new Object[capacity];
		size = 0;
	}
	
	public OList(T[] array)
	{
		this.array = Arrays.copyOf(array,array.length);
		size = array.length;
	}
	
	public OList(OList<T> list)
	{
		this(list.array,true);
		size = list.size;
	}
	
	public OList(Collection<T> collection)
	{
		this(collection.size());
		Iterator<T> it = collection.iterator();
		while(it.hasNext())
		{
			add(it.next());
		}
	}
	
	private OList(T[] array, boolean copy)
	{
		this.array = copy ? Arrays.copyOf(array,array.length) : array;
		size = array.length;
	}
	
	
	public void set(int position,T value)
	{
		if (position >= size) throw new IllegalArgumentException();
		array[position] = value;
	}
	
	public T get(int position)
	{
		if (position >= size) throw new IllegalArgumentException();
		return array[position];
	}
	
	public void add(T newE)
	{
		if (size >= array.length)
		{
			array = Arrays.copyOf(array, array.length*2);
		}
		array[size] = newE;
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
	
	public T[] getArray()
	{
		return Arrays.copyOf(array, size);
	}
	
	@Override
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

