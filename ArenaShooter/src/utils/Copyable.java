package utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Map;

public interface Copyable<T> extends Cloneable
{
	@SuppressWarnings("unchecked")
	public default T copy()
	{
		Method m = null;
		Boolean access = null;
		try
		{
			Class<Object> object = Object.class;
			m = object.getDeclaredMethod("clone");
			access = m.isAccessible();
			m.setAccessible(true);
			return (T)m.invoke(this);
		}
		catch (IllegalAccessException | IllegalArgumentException| InvocationTargetException | NoSuchMethodException| SecurityException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			if(m != null && access != null) m.setAccessible(access);
		}
	}
	
	
	public default T deepCopy()
	{
		Method m = null;
		Boolean access = null;
		T copy = null;
		try
		{
			Class<Object> object = Object.class;
			m = object.getDeclaredMethod("clone");
			access = m.isAccessible();
			m.setAccessible(true);
			copy = (T) m.invoke(this);
		}
		catch (IllegalAccessException | IllegalArgumentException| InvocationTargetException | NoSuchMethodException| SecurityException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			if(m != null && access != null) m.setAccessible(access);
		}
		copy = Utils.deepCopy(copy);
		return copy;
	}
	
}
