package chapter10;

public interface NamedRunnable extends Runnable
{
	public String getName();
	
	public static NamedRunnable[] combine(String[] names, Runnable[] runnables)
	{
		if(names.length != runnables.length) throw new IllegalArgumentException();
		NamedRunnable[] results = new NamedRunnable[names.length];
		for(int i = 0; i < results.length; i++)
		{
			final int j = i;
			results[i] = new NamedRunnable()
			{
				public void run() { runnables[j].run(); }
				
				public String getName() { return names[j]; }
			};
		}
		return results;
	}
}
