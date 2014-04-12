package objects;

public class Player extends Entity
{
	
	public Player(double x, double y) 
	{
		super(x, y);
		faction = Faction.Player;
		maxHealth = 10;
		health = maxHealth;
	}

	@Override
	public void update() 
	{
			
	}

	@Override
	public boolean collidesWith(GameObject entity) 
	{
		return false;
	}
	
	public static class Action
	{
		
	}
	
}
