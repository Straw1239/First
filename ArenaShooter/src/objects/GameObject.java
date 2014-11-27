package objects;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Effect;
import objects.events.GameEvent;
import utils.Utils;
import utils.Vector;
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
	
	public Collection<GameEvent> events(State d)
	{
		return Collections.emptyList();
	}
	
	public Collection<GameEvent> onDeath(State d)
	{
		return onDeath();
	}
	
	public Collection<GameEvent> onEntry(State s)
	{
		return Collections.emptyList();
	}
	
	public Collection<GameEvent> onDeath()
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
	
	public Set<Faction> collidableFactions()
	{
		EnumSet<Faction> set = EnumSet.allOf(Faction.class);
		set.remove(faction);
		return set;
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
	
	public Collection<Effect> specialEffects()
	{
		return Collections.emptyList();
	}
	
	public void renderHUD(GraphicsContext g)
	{
		
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
	
	public String toString()
	{
		return String.format("GameObject of faction %s at:(%f, %f)", faction.toString(), x, y);
	}
	
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException
	{
		out.writeDouble(x);
		out.writeDouble(y);
		out.writeObject(faction);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
	{
		x = in.readDouble();
		y = in.readDouble();
		faction = (Faction) in.readObject();
		
	}
	
	public void hitBy(Impact impact)
	{
		for(Change c : impact.changes)
		{
			handleChange(c, impact.source);
		}
	}
	
	
	public static final int SETPOSITION = 0;
	public static final int SETFACTION = 1;
	public static final int MOVE = 7;
	
	public boolean supportsOperation(int code)
	{
		switch(code)
		{
		case SETPOSITION:
		case SETFACTION:
		case MOVE:
			return true;
		}
		return false;
	}
	
	protected void handleChange(Change change, GameObject source)
	{
		switch(change.code)
		{
		case SETPOSITION:
			Locatable location = (Locatable) change.data;
			x = location.getX();
			y = location.getY();
			break;
		case SETFACTION:
			faction = (Faction) change.data;
			break;
		case MOVE:
			Vector v = (Vector) change.data;
			x += v.x;
			y += v.y;
		default: //throw new IllegalArgumentException();
				
		}
	}
	
	public Impact collideWith(GameObject other)
	{
		return Impact.NONE;
	}
	
	public static final class Change
	{
		public final int code;
		public final Object data;
		
		public Change(int code, Object obj)
		{
			this.code = code;
			data = obj;
		}
	}
	
	public static final class Impact
	{
		
		public static final Impact NONE = new Impact(null, Collections.emptyList());
		public final GameObject source;
		public final Collection<Change> changes;
		
		
		
		public Impact(GameObject origin, Collection<Change> effects)
		{
			source = origin;
			changes = effects;
		}
		
		public Impact(GameObject origin)
		{
			this(origin, new ArrayList<>());
		}
		
		public Impact(GameObject origin, Change... changes)
		{
			this(origin, new ArrayList<>(Arrays.asList(changes)));
		}
	}
	
	
	
	
	/**
	 * Collides the two specified game objects. 
	 * @param obj
	 * @param other
	 */
	public static void collide(GameObject obj, GameObject other)
	{
		Impact forOther = obj.collideWith(other);
		Impact forObj = other.collideWith(obj);
		other.hitBy(forOther);
		obj.hitBy(forObj);
	}
	
	
	
	
	
	
	
	
	
}
