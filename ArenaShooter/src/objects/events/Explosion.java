package objects.events;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.Bullet;
import objects.Enemy;
import objects.Faction;
import objects.ObjectDataHolder;
import player.Player;
import utils.Utils;

import com.google.common.collect.Multimap;

import fxcore.MainGame;

/**
 * Basic Event representing an explosion. Damages all entities of appropriate factions within it's radius, 
 * displaying a red expanding circle graphic.
 * @author Rajan Troll
 *
 */
public class Explosion extends GameEvent implements ObjectDataHolder
{
	public static final long DURATION = 10;
	
	private double radius, damage;
	private Color color = Color.RED;
	
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
	public void enemyEffects(Collection<Enemy> enemies)
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
	public Collection<GameEvent> eventEffects(Collection<GameEvent> events)
	{
		return new ArrayList<>(0);

	}

	@Override
	public void draw(GraphicsContext g)
	{
		g.setFill(color);
		long time = MainGame.getTime() - startTime();
		double radius = this.radius * (time / (double) DURATION);
		double temp = 2 * radius;
		g.fillOval(x - radius, y - radius, temp, temp);
	}

	@Override
	public boolean hasExpired()
	{
		return (MainGame.getTime() - startTime()) > DURATION;
	}

}
