package objects.events;

import java.util.Collection;

import objects.Bullet;
import objects.Enemy;
import objects.Faction;
import objects.ObjectDataHolder;
import objects.Player;

import com.google.common.collect.Multimap;

import fxcore.MainGame;

public abstract class GameEvent implements ObjectDataHolder, EventDataHolder
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
	
	public void effects(Player p, Multimap<Faction, Bullet> bullets, Collection<Enemy> enemies, Collection<GameEvent> events)
	{
		playerEffects(p);
		bulletEffects(bullets);
		enemyEffects(enemies);
		eventEffects(events);
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
