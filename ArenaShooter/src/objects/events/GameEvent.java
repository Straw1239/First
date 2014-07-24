package objects.events;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javafx.scene.canvas.GraphicsContext;
import objects.Faction;
import objects.GameObject;
import objects.ObjectDataHolder;
import engine.EventHandler;
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
	
	public boolean supportsOperation(int code)
	{
		return false;
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
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException
	{
		out.writeDouble(x);
		out.writeDouble(y);
		out.writeInt(faction.ordinal());
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
	{
		x = in.readDouble();
		y = in.readDouble();
		faction = Faction.values()[in.readInt()];
		
	}
	
	public static final GameEvent spawnerOf(GameObject obj)
	{
		return new GameEvent(obj)
		{
			boolean hasExpired = false;
			@Override
			public void effects(EventHandler handler)
			{
				if(!hasExpired) handler.add(obj);
				hasExpired = true;
			}

			@Override
			public void draw(GraphicsContext g)
			{
				
			}

			@Override
			public boolean hasExpired()
			{
				return hasExpired;
			}

			@Override
			public boolean isDead()
			{
				return hasExpired();
			}
			
		};
	}
}
