package objects;

import java.awt.Color;
import java.awt.Graphics;

import ui.Transformer;
import core.Display;

public class Cursor extends GameObject
{
	public static final int radius = 10;
	public static final Color color = new Color(170,0,170);
	
	public Cursor(double x, double y)
	{
		super(x, y, Faction.Neutral);
	}

	@Override
	public void update(Display d)
	{
		
	}

	@Override
	public boolean collidesWith(GameObject entity)
	{
		return false;
	}

	@Override
	public boolean collidesWithPlayer(Player p)
	{
		return false;
	}

	@Override
	public boolean collidesWithBullet(Bullet b)
	{
		return false;
	}

	@Override
	public boolean collidesWithEnemy(Enemy e)
	{
		return false;
	}
	
	public void draw(Graphics g, Transformer t)
	{
		g.setColor(color);
		int pixels = t.pixels(radius* 2); 
		g.drawOval(t.screenX(x - radius), t.screenY(y - radius), pixels, pixels);
	}
	

}
