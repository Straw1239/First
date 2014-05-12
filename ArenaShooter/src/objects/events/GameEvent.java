package objects.events;

import java.util.Collection;

import objects.Bullet;
import objects.Enemy;
import objects.Faction;
import objects.ObjectDataHolder;
import player.Player;

import com.google.common.collect.Multimap;

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
	/**
	 * Applies all effects this GameEvent has on internal engine objects.
	 * @param p
	 * @param bullets
	 * @param enemies
	 * @param events
	 */
	public Collection<GameEvent> effects(Player p, Multimap<Faction, Bullet> bullets, Collection<Enemy> enemies, Collection<GameEvent> events)
	{
		playerEffects(p);
		bulletEffects(bullets);
		enemyEffects(enemies);
		return eventEffects(events);
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public Faction getFaction()
	{
		return faction;
	}
	
	public long startTime()
	{
		return startTime;
	}
	
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
