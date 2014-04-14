package objects;

import java.awt.Color;

import core.MainGame;
import utils.Utils;

public class Bullet extends CircleCollider implements BulletDataHolder
{
	public Color color;
	public double damage = 1;
	
	private double dx,dy;
	private long startTime;
	
	
	private Bullet(double x, double y)
	{
		super(x,y);
		startTime = MainGame.getTime();
	}
	
	private Bullet(GameObject entity)
	{
		this(entity.x, entity.y);
		faction = entity.faction;
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
	
	public boolean hasHitWall(double width, double height)
	{
		return (x + radius >= width || x - radius <= 0 || y + radius >= height || y - radius  <= 0);
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
		return super.collidesWith(entity);
	}

	@Override
	public double getRadius() 
	{
		return radius;
	}

	@Override
	public Color getColor() 
	{
		return color;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		long temp;
		temp = Double.doubleToLongBits(damage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(dx);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(dy);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(radius);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (startTime ^ (startTime >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Bullet)) {
			return false;
		}
		Bullet other = (Bullet) obj;
		if (color == null) {
			if (other.color != null) {
				return false;
			}
		} else if (!color.equals(other.color)) {
			return false;
		}
		if (Double.doubleToLongBits(damage) != Double
				.doubleToLongBits(other.damage)) {
			return false;
		}
		if (Double.doubleToLongBits(dx) != Double.doubleToLongBits(other.dx)) {
			return false;
		}
		if (Double.doubleToLongBits(dy) != Double.doubleToLongBits(other.dy)) {
			return false;
		}
		if (Double.doubleToLongBits(radius) != Double
				.doubleToLongBits(other.radius)) {
			return false;
		}
		if (startTime != other.startTime) {
			return false;
		}
		return true;
	}
	

}
