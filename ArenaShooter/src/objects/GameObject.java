package objects;

import java.util.Collection;
import java.util.Collections;

import bounds.Bounds;
import javafx.scene.canvas.GraphicsContext;
import objects.events.GameEvent;
import utils.Utils;
import engine.State;
/**
 * Base object of the object hierarchy.
 * Each game object has an x, y, and Faction.
 * 
 * @author Rajan Troll
 *
 */
public abstract class GameObject implements ObjectDataHolder
{
	protected double x, y;
	protected Faction faction;
	
	protected GameObject(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	protected GameObject(double x, double y, Faction faction)
	{
		this(x, y);
		this.faction = faction;
	}
	
	public abstract void update(State d);
	
	public abstract boolean collidesWith(GameObject entity);
	
	public abstract Bounds bounds();
	
	public Collection<? extends GameEvent> events(State d)
	{
		return Collections.emptyList();
	}
	
	public Collection<? extends GameEvent> onDeath(State d)
	{
		return onDeath();
	}
	
	public Collection<? extends GameEvent> onDeath()
	{
		return Collections.emptyList();
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
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((faction == null) ? 0 : faction.hashCode());
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj) 
		{
			return true;
		}
		if (obj == null) 
		{
			return false;
		}
		if (!(obj instanceof GameObject)) 
		{
			return false;
		}
		
		GameObject other = (GameObject) obj;
		if (faction != other.faction) 
		{
			return false;
		}
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) 
		{
			return false;
		}
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) 
		{
			return false;
		}
		return true;
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
	
	public double angleTo(ObjectDataHolder o)
	{
		return Utils.angle(this, o);
	}
	
	public static GameObject dataOf(double x, double y, Faction f)
	{
		return new GameObject(x, y, f)
		{
			@Override
			public void draw(GraphicsContext g){}

			@Override
			public void update(State d){}

			@Override
			public boolean collidesWith(GameObject entity)
			{
				return false;
			}

			@Override
			public Bounds bounds()
			{
				return Bounds.NONE;
			}
			
		};
	}
	
	
	
	
}
