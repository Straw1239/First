package main;

import java.util.Map;
import java.util.TreeMap;

public class SuperMap<K,V> extends TreeMap<K,V> implements Map<K,V>
{
	SuperMap(K k, V v)
	{
		put(k,v);
	}
	
}
