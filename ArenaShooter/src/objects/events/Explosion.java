package objects.events;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Collection;

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
		if(MainGame.getTime() - startTime() <= 1)
		if(Utils.circleCollide(this, p, radius + Player.radius))
		{
			
		}
	}

	@Override
	public void bulletEffects(Multimap<Faction, Bullet> bullets)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void enemyEffects(Collection<? extends Enemy> enemies)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void eventEffects(Collection<? extends GameEvent> events)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics g, Transformer t)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hasExpired()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
