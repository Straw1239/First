package logic;




import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Scanner;
import java.util.Spliterators;
import java.util.concurrent.Executors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class Utils
{
	
	/**
	 * Indicates the number of threads or level of parallelism which should be used in calculation.
	 */
	public static final int THREADS = Runtime.getRuntime().availableProcessors();
	
	public static final ListeningScheduledExecutorService compute = MoreExecutors.listeningDecorator(Executors.newScheduledThreadPool(THREADS));
	
	public static final ListeningExecutorService exec = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
	
	
	
	
	
	public static void swap(int[] array, int index1, int index2)
	{
		int temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}
	
	public static <T, V> void swap(Map<T, V> map, T a, T b)
	{
		V temp = map.get(a);
		map.put(a, map.get(b));
		map.put(b, temp);
	}
	
	public static <K, V> Entry<K, V> entryFrom(K k, V v)
	{
		return new Entry<K, V>()
		{
			public String toString()
			{
				return "(" + k + ", " + v + ")";
			}
			@Override
			public K getKey()
			{
				return k;
			}

			@Override
			public V getValue()
			{
				return v;
			}

			@Override
			public V setValue(V value)
			{
				throw new UnsupportedOperationException();
			}
			
		};
	}

	public static <T> Stream<T> stream(Iterator<T> itr)
	{
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(itr, 0), false);
	}
	
}
