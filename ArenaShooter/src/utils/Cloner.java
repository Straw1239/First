package utils;

public interface Cloner extends Cloneable
{
	public Object clone();
	
	public static <T extends Cloner> T clone(T object)
	{
		try
		{
			return (T)object.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new InternalError(e);
		}
	}
}
