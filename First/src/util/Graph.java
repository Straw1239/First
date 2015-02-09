package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class Graph<E> implements Set<E> 
{
	private final HashMultimap<E, E> nodes = HashMultimap.create();
	private final Class<E> clazz;
	
	public Graph(Class<E> clazz)
	{
		this.clazz = clazz;
	}
	/**
	 * Creates a new graph where each node is unconnected
	 * @param nodes
	 */
	@SuppressWarnings("unchecked")
	public Graph(Iterable<E> nodes)
	{
		Iterator<E> itr = nodes.iterator();
		E f = itr.next();
		if(f == null) throw new NullPointerException();
		clazz = (Class<E>) f.getClass();
		for(E n : nodes)
		{
			add(n);
		}
	}


	@Override
	public int size()
	{
		return nodes.keySet().size();
	}

	@Override
	public boolean isEmpty()
	{
		return size() == 0;
	}

	@Override
	public boolean contains(Object o)
	{
		return nodes.containsKey(o);
	}

	@Override
	public Iterator<E> iterator()
	{
		return nodes.keySet().iterator();
	}

	@Override
	public Object[] toArray()
	{
		return nodes.keySet().toArray();
	}

	@Override
	public <T> T[] toArray(T[] a)
	{
		return nodes.keySet().toArray(a);
	}

	
	@Override
	public boolean remove(Object o)
	{
		if(nodes.containsKey(o))
		{
			if(clazz.isAssignableFrom(o.getClass()))
			{
				E e = clazz.cast(o);
				Collection<E> links = nodes.get(e);
				for(E linked : links)
				{
					nodes.get(linked).remove(e);
				}
				return nodes.keySet().remove(e);
			}
			else return false;
		}
		else return false;
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		for(Object o : c)
		{
			if(!nodes.containsKey(o)) return false;
		}
		return true;
	}

	
	@Override
	public boolean removeAll(Collection<?> c)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear()
	{
		nodes.clear();
	}
	@Override
	public boolean add(E e)
	{
		throw new UnsupportedOperationException();
		//return true;
	}
	
	public void addLink(E a, E b)
	{
		nodes.put(a, b);
		nodes.put(b, a);
	}
	@Override
	public boolean addAll(Collection<? extends E> c)
	{
		for(E e : c)
		{
			add(e);
		}
		return true;
	}
	
	public boolean areLinked(E a, E b)
	{
		return nodes.get(a).contains(b);
	}
	
	public Set<E> linkedNodes(E node)
	{
		return nodes.get(node);
	}
	
	public int shortestDistance(E a, E b)
	{
		if(a.equals(b)) return 0;
		if(areLinked(a, b)) return 1;
		Set<E> visited = new HashSet<>();
		visited.add(a);
		Collection<E> toVisit = nodes.get(a);
		int distance = 1;
		while(!toVisit.isEmpty())
		{
			distance++;
			List<E> branches = new ArrayList<>();
			for(E branch : toVisit)
			{
				if(!visited.contains(branch))
				{
					if(nodes.get(branch).contains(b)) return distance;
					visited.add(branch);
					branches.addAll(nodes.get(branch));
				}
			}
			toVisit = branches;
		}
		return -1;
	}
	
	public Iterator<E> shortestPathBetween(E a, E b)
	{
		throw new UnsupportedOperationException();
	}
	
	public int numLinks()
	{
		return nodes.values().size() / 2;
	}
	
	public static void main(String[] args)
	{
		Graph<String> test = new Graph<>(String.class);
		//test.add("Hello");
		//test.add("World");
		//test.add("Test");
		test.addLink("Hello", "World");
		test.addLink("World", "Test");
		test.addLink("Hello", "Test");
		test.addLink("LAST", "Hello");
		System.out.println(test.shortestDistance("Test", "LAST"));
		
	}
}
