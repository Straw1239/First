package objects;

import java.awt.Color;
import java.awt.Graphics;

import ui.Transformer;
import utils.Utils;
import core.Display;

public class MovingEnemy extends Enemy 
{
	public static final double radius = 20;
	public static final double maxHealth = 30;
	public static final double contactDamage = .1;
	public static final Color color = Color.red;
	
	public MovingEnemy(double x, double y) 
	{
		super(x, y);
		health = maxHealth;
		super.maxHealth = maxHealth;
	}

	@Override
	public void collideWithPlayer(Player p) 
	{
		p.damage(contactDamage);
	}

	@Override
	public void draw(Graphics g, Transformer t) 
	{
		g.setColor(color);
		g.fillOval(t.screenX(x - radius), t.screenY(y - radius), t.pixels(2 * radius), t.pixels(2 * radius));
	}

	@Override
	public Bullet shot(Display d) 
	{
		return null;
	}

	@Override
	public void hitByBullet(Bullet b) 
	{
		double mass = 2;
		x += b.getDX() / mass;
		y += b.getDY() / mass;
	}

	@Override
	public void update(Display d) 
	{
		double speed = 3;
		PlayerDataHolder p = d.player;
		double distance = Utils.distance(this, p);
		x += speed * (p.getX() - x) / distance;
		y += speed * (p.getY() - y) / distance;
	}

	public boolean collidesWith(GameObject entity) 
	{
		return entity.collidesWithEnemy(this);
	}

	@Override
	public boolean collidesWithPlayer(Player p) 
	{
		return Utils.circleCollide(this, p, radius + Player.radius);
	}

	@Override
	public boolean collidesWithBullet(Bullet b) 
	{
		if(faction == b.faction) return false;
		return Utils.circleCollide(this, b, radius + b.getRadius());
	}

	@Override
	public boolean collidesWithEnemy(Enemy e) 
	{
		//Should enemies collide? for now, no.
		return false;
	}

}
