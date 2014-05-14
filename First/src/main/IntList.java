package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class IntList 
{
	private int[] array;
	private int size;
	public IntList() { this(8); }
	public IntList(int capacity)
	{
		array = new int[capacity];
		size = 0;
	}
	public IntList(int[] array)
	{
		this.array = Arrays.copyOf(array,array.length);
		size = capacity();
	}
	public IntList(Collection<Integer> base)
	{
		this(base.size()*2);
		Iterator<Integer> it = base.iterator();
		while(it.hasNext())
		{
			add(it.next().intValue());
		}
	}
	public void set(int position,int value)
	{
		if (position >= size) throw new ArrayIndexOutOfBoundsException(position);
		array[position] = value;
	}
	public int get(int position)
	{
		if (position >= size) throw new ArrayIndexOutOfBoundsException(position);
		return array[position];
	}
	public void add(int newInt)
	{
		if (size >= capacity())
		{
			array = Arrays.copyOf(array, Math.max(capacity()*2,1));
		}
		array[size] = newInt;
		size++;
	}
	public void add(int index, int value)
	{
		if (size >= capacity())
		{
			array = Arrays.copyOf(array, Math.max(capacity()*2,1));
		}
		for(int i = size; i > index; i--)
		{
			array[i]  = array[i-1];
		}
		array[index] = value;
		size++;
	}
	public void addAll(int[] newElements)
	{
		while(capacity() <= size + newElements.length) ensureCapacity(Math.max(capacity()*2,1));
		for(int i = 0; i < newElements.length;i++)
		{
			add(newElements[i]);
		}
	}
	public void addAll(Collection<Integer> newElements)
	{
		while(capacity() <= size + newElements.size()) ensureCapacity(Math.max(capacity()*2,1));
		Iterator<Integer> it = newElements.iterator();
		while(it.hasNext())
		{
			add(it.next().intValue());
		}
	}
	public void addAll(IntList list)
	{
		while(capacity() <= size + list.size()) ensureCapacity(Math.max(capacity()*2,1));
		for(int i = 0; i < list.size();i++)
		{
			add(list.get(i));
		}
	}
	public void ensureCapacity(int capacity)
	{
		if(capacity > capacity())
		{
			array = Arrays.copyOf(array, capacity);
		}
	}
	public void removeRange(int start, int end)
	{
		int shift = end - start;
		for(int i = start; i < size-shift;i++)
		{
			array[i] = array[i+shift];
		}
		size -= shift;
	}
	private int capacity()
	{
		return array.length;
	}
	public int size()
	{
		return size;
	}
	public void trimToSize()
	{
		array = Arrays.copyOf(array, size);
	}
	public int[] toArray()
	{
		return Arrays.copyOf(array, size);
	}
	public int[] copyOfRange(int start, int end)
	{
		return Arrays.copyOfRange(array, start, end);
	}
	public int remove()
	{
		return remove(0);
	}
	public int remove(int index)
	{
		int a = array[index];
		for(int i = index; i < size-1; i++)
		{
			array[i] = array[i+1];
		}
		size--;
		return a;
		
	}
	public void clear()
	{
		size = 0;
	}
	public void reset()
	{
		array = new int[8];
		size = 0;
	}
	public IntList copy()
	{
		IntList copy = new IntList(capacity());
		for(int i = 0; i < size();i++)
		{
			copy.add(get(i));
		}
		return copy;
	}
	public Iterator<Integer> iterator()
	{
		return iterator(0);
	}
	public Iterator<Integer> iterator(int position)
	{
		return new IntIterator(position,this);
	}
	@Override
	public String toString()
	{
		StringBuilder s = new StringBuilder(3*size);
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
	public List<Integer> asList()
	{
		ArrayList<Integer> list = new ArrayList<>(capacity());
		for(int i = 0; i < size();i++)
		{
			list.add(get(i));
		}
		return list;
	}
	private class IntIterator implements Iterator<Integer>
	{
		private IntList array;
		private int position;
		private boolean next = false;
		public IntIterator(int position, IntList a)
		{
			this.position = position;
			array = a;
		}
		@Override
		public boolean hasNext()
		{
			return position < array.size();
		}
		@Override
		public Integer next() throws NoSuchElementException
		{
			if(position >= array.size()) throw new NoSuchElementException();
			position++;
			next = true;
			return new Integer(array.get(position-1));
		}
		@Override
		public void remove() throws IllegalStateException
		{
			if (!next) throw new IllegalStateException();
			position--;
			array.remove(position);
			next = false;
		}
	}
}
