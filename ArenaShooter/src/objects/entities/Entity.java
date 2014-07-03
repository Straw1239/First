package objects.entities;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import objects.Bullet;
import objects.Faction;
import objects.MovingObject;

public abstract class Entity extends MovingObject implements EntityDataHolder
{
	protected Entity(double x, double y) 
	{
		super(x, y);
	}
	
	protected Entity(double x, double y, Faction faction)
	{
		super(x,y,faction);
	}
	
	public abstract void hitByBullet(Bullet b);
	
	public abstract void collideWith(Entity e);
	
	protected double health;
	protected double maxHealth;
	
	@Override
	public boolean isDead()
	{
		return health == 0;
	}
	
	@Override
	public double health()
	{
		return health;
	}
	
	@Override
	public double maxHealth()
	{
		return maxHealth;
	}
	
	public void damage(double damage)
	{
		health -= damage;
		correctHealth();
	}
	
	public void heal(double amount)
	{
		health += amount;
		correctHealth();
	}
	
	protected void correctHealth()
	{
		health = Math.max(0, Math.min(health, maxHealth));
	}
	
	public void writeExternal(ObjectOutput out) throws IOException
	{
		super.writeExternal(out);
		out.writeDouble(health);
		out.writeDouble(maxHealth);
	}
	
	public void readExternal(ObjectInput in) throws ClassNotFoundException, IOException
	{
		super.readExternal(in);
		health = in.readDouble();
		maxHealth = in.readDouble();
	}
	
	
	
	
}
