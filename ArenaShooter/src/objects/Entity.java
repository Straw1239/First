package objects;

public abstract class Entity extends GameObject implements EntityDataHolder
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
	
	private void correctHealth()
	{
		health = Math.max(0, Math.min(health, maxHealth));
	}
	
	
	
	
}
