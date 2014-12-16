package player;

import objects.Locatable;
import objects.ReadableObject;
import objects.events.GameEvent;

public interface Gun<T>
{
	public void setTarget(ReadableObject target);
	
	public void setLocation(double x, double y);
	
	public default void setLocation(Locatable location)
	{
		setLocation(location.getX(), location.getY());
	}
	
	public default void setParameters(T params)
	{
		//No parameters, do nothing.
	}
	
	public default T parameters()
	{
		return null; // No parameters by default, return null
	}
	
	public GameEvent fire();
}
