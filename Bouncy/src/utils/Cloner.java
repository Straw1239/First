package utils;

public interface Cloner extends Cloneable
{
	public Object clone();
	
	public static <T extends Cloner> T clone(Cloner c)
	{
		return (T) c.clone();
	}
}
