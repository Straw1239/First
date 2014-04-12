package objects;

public abstract class Entity extends GameObject 
{
	protected Entity(double x, double y) 
	{
		super(x, y);
	}
	
	protected Entity(double x, double y, Faction faction)
	{
		super(x,y,faction);
	}

	protected double health;
	protected double maxHealth;
	
	public boolean isDead()
	{
		return health == 0;
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
