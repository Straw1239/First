package objects.events;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Collection;
import java.util.Iterator;

import objects.Bullet;
import objects.Enemy;
import objects.Faction;
import objects.ObjectDataHolder;
import objects.Player;
import ui.Transformer;
import utils.Utils;

import com.google.common.collect.Multimap;

import core.MainGame;

public class Explosion extends GameEvent implements ObjectDataHolder
{
	public static final long DURATION = 10;
	
	private double radius, damage;
	private Color color = Color.red;
	
	public Explosion(double x, double y, Faction faction, double radius, double damage)
	{
		super(x, y, faction);
		this.radius = radius;
		this.damage = damage;
	}

	public Explosion(ObjectDataHolder source, double radius, double damage)
	{
		super(source);
		this.radius = radius;
		this.damage = damage;
	}
	
	public void setColor(Color c)
	{
		color = c;
	}

	@Override
	public void playerEffects(Player p)
	{
		if(faction != Faction.Player)
		if(MainGame.getTime() - startTime() <= 0)
		if(Utils.circleCollide(this, p, radius + Player.radius))
		{
			p.damage(damage);
		}
	}

	@Override
	public void bulletEffects(Multimap<Faction, Bullet> bullets)
	{
		//Currently explosions do not affect bullets
	}

	@Override
	public void enemyEffects(Collection<? extends Enemy> enemies)
	{
		if(faction != Faction.Enemy)
		if(MainGame.getTime() - startTime() <= 0)
		{
			Iterator<? extends Enemy> it = enemies.iterator();
			while(it.hasNext())
			{
				Enemy e = it.next();
				if(Utils.circleCollide(this, e, radius + Player.radius))
				{
					e.damage(damage);
				}	
			}
		}
	}

	@Override
	public void eventEffects(Collection<? extends GameEvent> events)
	{
		// Currently explosions do not affect other events

	}

	@Override
	public void draw(Graphics g, Transformer t)
	{
		g.setColor(color);
		long time = MainGame.getTime() - startTime();
		double radius = this.radius * (time / (double) DURATION);
		int temp = t.pixels(2 * radius);
		g.fillOval(t.screenX(x - radius), t.screenY(y - radius), temp, temp);
	}

	@Override
	public boolean hasExpired()
	{
		return (MainGame.getTime() - startTime()) > DURATION;
	}

}
