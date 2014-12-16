package objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Effect;
import objects.events.GameEvent;
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
public abstract class GameObject implements ReadableObject
{
	/**
	 * Represents the position of this object
	 */
	protected double x, y;
	/**
	 * The faction of this object
	 */
	protected Faction faction;
	
	/**
	 * Initializes the position of this to (x, y)
	 * @param x
	 * @param y
	 */
	protected GameObject(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Initializes the position and faction of this to (x, y) and faction
	 * @param x
	 * @param y
	 * @param faction
	 */
	protected GameObject(double x, double y, Faction faction)
	{
		this(x, y);
		this.faction = faction;
	}
	
	/**
	 * Advances this object one tick, with regard to the provided game state.
	 * 
	 * Subclasses should override this method to implement their behavior;
	 * because it will be called each engine tick, it is the primary place to perform 
	 * basic operations such as movement.
	 * @param state
	 */
	public abstract void update(State d);
	
	/**
	 * Checks if this object collides with the provided object.
	 * @param entity
	 * @return true if we collide, otherwise false
	 */
	public boolean collidesWith(GameObject entity)
	{
		return bounds().intersects(entity.bounds());
	}
	
	/**
	 * The physical bounds of this object in the game world.
	 * Subclasses should implement this to change their collision size and shape. 
	 */
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
	/**
	 * Applies the effects of the impact to this object. Although it is likely that a change will occur if the impact specifies it,
	 * this object is free to ignore or change any of the impact's effects.
	 * @param impact to apply to this object
	 */
	public void hitBy(Impact impact)
	{
		for(Change c : impact.changes)
		{
			handleChange(c, impact.source);
		}
	}
	
	/**
	 * Represents a change in which the position is set.
	 * The data for this change must be a Locatable which specifies the new location of the object.
	 */
	public static final int SETPOSITION = 0;
	
	/**
	 * Change which sets the faction of the object.
	 * The data for this change must be a Faction.
	 */
	public static final int SETFACTION = 1;
	/**
	 * Change which moves an object a specified offset.
	 * Data for this change must be a Vector representing the move.
	 */
	public static final int MOVE = 7;
	
	public boolean supportsOperation(int code)
	{
		switch(code)
		{
		case SETPOSITION:
		case SETFACTION:
		case MOVE:
			return true;
		default: return false;
		}
		
	}
	
	/**
	 * Applies a change to this object, the type of which is specified in the change.
	 * This object is free to interpret given changes however it wishes and may even ignore requested changes.
	 * This is called by default on each change in an Impact to apply that impact to this object.
	 * 
	 * Subclasses can override this method to modify the way in which change requests are processed, or to add/remove types of changes.
	 * A typical implementation might handle the changes it knows, then call super.handleChange(), so any change handling implementation can be inherited by subclasses.
	 * @param Change to apply
	 * @param Source which is requesting the change, or null if a change has no source.
	 */
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
		/**
		 * Represents an Impact which has no effect on the target object.
		 */
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
	 * Collides the two given game objects. 
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
