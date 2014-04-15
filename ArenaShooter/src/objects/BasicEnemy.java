package objects;

import java.awt.Color;
import java.awt.Graphics;

import ui.Transformer;
import utils.Utils;

public class BasicEnemy extends Enemy 
{
	public static final double radius = 20;
	public static final double contactDamage = 2;
	public static final double startHealth = 10;
	
	
	public BasicEnemy(double x, double y) 
	{
		super(x, y);
		health = startHealth;
		maxHealth = startHealth;
	}

	@Override
	public void update() 
	{	
		
	}

	@Override
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

	@Override
	public void hitByBullet(Bullet b) 
	{
		damage(b.damage);
	}
	
	public void collideWithPlayer(Player p)
	{
		damage(contactDamage * 2);
		p.damage(contactDamage);
	}

	@Override
	public void draw(Graphics g, Transformer t) 
	{
		g.setColor(new Color(50, 200, 80));
		g.fillOval(t.screenX(x - radius), t.screenY(y - radius), t.pixels(2 * radius), t.pixels(2 * radius));
	}

}
