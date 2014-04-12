package objects;

import java.awt.Color;

import core.MainGame;
import utils.Utils;

public class Bullet extends GameObject 
{
	public Color color;
	
	private double dx,dy;
	private double radius;
	private long startTime;
	
	private Bullet()
	{
		startTime = MainGame.getTime();
	}
	
	private Bullet(double x, double y)
	{
		this();
		super.x = x;
		super.y = y;
	}
	
	private Bullet(GameObject entity)
	{
		this(entity.x, entity.y);
	}
	
	public Bullet(double x, double y, double dx, double dy, double radius, Color color)
	{
		this(x,y);
		this.dx = dx;
		this.dy = dy;
		this.radius = radius;
		this.color = color;
	}
	
	public Bullet(double x, double y, double dx, double dy)
	{
		this(x,y,dx,dy,Color.red);
	}
	
	public Bullet(double x, double y, double dx, double dy, Color color)
	{
		this(x,y,dx,dy,1,color);
	}
	
	public Bullet(double x, double y, double dx, double dy, double radius)
	{
		this(x,y,dx,dy,radius,Color.red);
	}
	
	public Bullet(GameObject source, double dx, double dy, double radius,Color color)
	{
		this(source.x,source.y,dx,dy,radius,color);
	}
	
	public Bullet(GameObject source, GameObject target, double speed, double radius, Color color)
	{
		this(source);
		double distance = Utils.distance(source,target);
		dx = speed * (target.x - x) / distance;
		dy = speed * (target.y - y) / distance;
		this.radius = radius;
		this.color = color;
	}
	
	
	@Override
	public void update() 
	{
		x += dx;
		y += dy;
	}

	@Override
	public boolean collidesWith(GameObject entity) 
	{
		return false;
	}

}
