package objects;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collection;
import java.util.Collections;

import javafx.scene.canvas.GraphicsContext;
import objects.events.GameEvent;
import utils.Utils;
import bounds.Bounds;
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
	
	public boolean collidesWith(GameObject entity)
	{
		return bounds().intersects(entity.bounds());
	}
	
	public abstract Bounds bounds();
	
	public Collection<? extends GameEvent> events(State d)
	{
		return Collections.emptyList();
	}
	
	public Collection<? extends GameEvent> onDeath(State d)
	{
		return onDeath();
	}
	
	public Collection<? extends GameEvent> onEntry(State s)
	{
		return Collections.emptyList();
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

			@Override
			public boolean isDead()
			{
				return false;
			}
			
		};
	}
	
	public static long collisions = 0; // TESTING ONLY
	/**
	 * Collides the two specified game objects. 
	 * @param obj
	 * @param other
	 */
	public static void collide(GameObject obj, GameObject other)
	{
		//DO THE COLLISION
		//Determine run-time type of objs? obtain actions from objects? How give both a say in what happens to each of them?
	}
	
	public String toString()
	{
		return String.format("GameObject of faction %s at:(%f, %f)", faction.toString(), x, y);
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
	
	
	
}
