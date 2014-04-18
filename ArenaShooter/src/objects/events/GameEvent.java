package objects.events;

import java.awt.Graphics;
import java.util.Collection;

import com.google.common.collect.Multimap;

import objects.Bullet;
import objects.Enemy;
import objects.Faction;
import objects.ObjectDataHolder;
import objects.Player;
import ui.Transformer;
import core.MainGame;

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
	
	public abstract void playerEffects(Player p);
	
	public abstract void bulletEffects(Multimap<Faction, Bullet> bullets);
	
	public abstract void enemyEffects(Collection<? extends Enemy> enemies);
	
	public abstract void eventEffects(Collection<? extends GameEvent> events);
	
	public abstract void draw(Graphics g, Transformer t);
	
	public abstract boolean hasExpired();
	
	public void effects(Player p, Multimap<Faction, Bullet> bullets, Collection<? extends Enemy> enemies, Collection<? extends GameEvent> events)
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
}
