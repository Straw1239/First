package objects.events;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collection;
import java.util.Collections;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Effect;
import objects.Faction;
import objects.GameObject;
import objects.ReadableObject;
import engine.EventHandler;
import fxcore.MainGame;
/**
 * Basic abstract base class for all events. See EventDataHolder. 
 * Stores x, y, faction, and starting time of events. Provides
 * useful methods such as clone.
 * @author Rajan
 *
 */
public abstract class GameEvent implements ReadableEvent
{
	protected double x, y;
	protected Faction faction;
	
	protected GameEvent(double x, double y, Faction faction)
	{
		this.x = x;
		this.y = y;
		this.faction = faction;
	}
	
	protected GameEvent(ReadableObject source)
	{
		this(source.getX(), source.getY(), source.getFaction());
	}
	
	protected GameEvent(ReadableEvent source)
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
	
	public void renderHUD(GraphicsContext g)
	{
		//No effects on HUD by default
	}
	
	public static final GameEvent spawnerOf(GameObject obj)
	{
		return new GameEvent(obj)
		{
			@Override
			public void effects(EventHandler handler)
			{
				handler.add(obj);
			}

			public void draw(GraphicsContext g)
			{
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	public static final GameEvent merge(GameEvent e, GameEvent other)
	{
		if(e.getX() != other.getX() || e.getY() != other.getY() || e.getFaction() != other.getFaction()) throw new IllegalArgumentException();
		return new GameEvent(e)
		{
			@Override
			public void effects(EventHandler handler)
			{
				e.effects(handler);
				other.effects(handler);
			}

			@Override
			public void draw(GraphicsContext g)
			{
				e.draw(g);
				other.draw(g);
			}
		};
	}
	
	
}
