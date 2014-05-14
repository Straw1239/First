package objects.events;

import objects.Faction;
import objects.ObjectDataHolder;
import fxcore.MainGame;
/**
 * Basic abstract base class for all events. See EventDataHolder. 
 * Stores x, y, faction, and starting time of events. Provides
 * useful methods such as clone, and effects() combining all of the effects into one method. 
 * @author Rajan
 *
 */
public abstract class GameEvent implements EventDataHolder
{
	protected double x, y;
	protected Faction faction;
	private long startTime;
	
	protected GameEvent(double x, double y, Faction faction)
	{
		this.x = x;
		this.y = y;
		this.faction = faction;
		startTime = MainGame.getTime();
	}
	
	protected GameEvent(ObjectDataHolder source)
	{
		this(source.getX(), source.getY(), source.getFaction());
	}
	
	@Override
	public double getX()
	{
		return x;
	}
	
	@Override
	public double getY()
	{
		return y;
	}
	
	@Override
	public Faction getFaction()
	{
		return faction;
	}
	
	@Override
	public long startTime()
	{
		return startTime;
	}
	
	@Override
	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new InternalError(e);
		}
	}
}
