package utils;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

/**
 * Work in progress class for fast enum multimap.
 * Might be uneccesary: don't bother working on it till you actually need to.
 * @author ing_rrtroll
 *
 * @param <K>
 * @param <V>
 */
public class EnumMultimap<K extends Enum<K>, V> implements Multimap<K, V>
{
	private EnumMap<K, Collection<V>> map;
	private int size = 0;
	
	public EnumMultimap(Class<K> type)
	{
		map = new EnumMap<K, Collection<V>>(type);
	}

	@Override
	public Map<K, Collection<V>> asMap() 
	{
		return map;
	}

	@Override
	public void clear() 
	{
		map.clear();
	}

	@Override
	public boolean containsEntry(Object arg0, Object arg1) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsKey(Object arg0) 
	{
		return map.containsKey(arg0);
	}

	@Override
	public boolean containsValue(Object arg0) 
	{
		return false;
	}

	@Override
	public Collection<Entry<K, V>> entries() 
	{
		return null;
	}

	@Override
	public Collection<V> get(K arg0) 
	{
		return map.get(arg0);
	}

	@Override
	public boolean isEmpty() 
	{
		return size == 0;
	}

	@Override
	public Set<K> keySet() 
	{
		return map.keySet();
	}

	@Override
	public Multiset<K> keys() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean put(K arg0, V arg1)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean putAll(Multimap<? extends K, ? extends V> arg0) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean putAll(K arg0, Iterable<? extends V> arg1) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Object arg0, Object arg1) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<V> removeAll(Object arg0) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<V> replaceValues(K arg0, Iterable<? extends V> arg1) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<V> values() 
	{
		// TODO Auto-generated method stub
		return null;
	}
}
