package utils;

import java.util.Collection;
import java.util.Iterator;

import com.google.common.collect.Iterables;

public abstract class Concatenator<E> implements Collection<E> 
{
	Collection<? extends E>[] collections;
	@SafeVarargs
	public Concatenator(Collection<? extends E>... collections )
	{
		this.collections = collections;
	}
	
	public Iterator<E> iterator()
	{
		return Iterables.concat(collections).iterator();
	}
	
	public boolean remove(Object obj)
	{
		Iterator<E> iterator = iterator();
		boolean found = false;
		while(iterator.hasNext())
		{
			E next = iterator.next();
			if(next.equals(obj)) 
			{
				iterator.remove();
				found = true;
			}
		}
		return found;
	}
	
	public boolean removeAll(Collection<?> objects)
	{
		Iterator<E> iterator = iterator();
		boolean found = false;
		while(iterator.hasNext())
		{
			E next = iterator.next();
			if(objects.contains(next)) 
			{
				iterator.remove();
				found = true;
			}
		}
		return found;
	}
	
	public boolean contains(Object obj)
	{
		for(Collection<? extends E> c : collections)
		{
			if(c.contains(obj)) return true;
		}
		return false;
	}
	
	public boolean containsAll(Collection<?> objects)
	{
		throw new UnsupportedOperationException();
	}
	
	public void clear()
	{
		for(Collection<? extends E> c : collections)
		{
			c.clear();
		}
	}
	
	public int size()
	{
		int size = 0;
		for(Collection<? extends E> c : collections)
		{
			size += c.size();
		}
		return size;
	}
	
	public boolean isEmpty()
	{
		return size() == 0;
	}
	
	public boolean retainAll(Collection<?> objects)
	{
		boolean changed = false;
		for(Collection<? extends E> c : collections)
		{
			if(c.retainAll(objects)) changed = true;
		}
		return changed;
	}
	
	public Object[] toArray()
	{
		throw new UnsupportedOperationException();
	}
	
	public <T> T[] toArray(T[] arg)
	{
		throw new UnsupportedOperationException();
	}
	
	
}
