package objects;

import java.util.Arrays;

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
		private boolean up = false, down = false, left = false, right = false;
		private boolean shoot = false;
		private double targetX, targetY;
		
		public Action(double targetX, double targetY)
		{
			this.targetX = targetX;
			this.targetY = targetY;
			shoot = true;
		}
		
		public Action()
		{
			
		}
		
		public boolean isShooting()
		{
			return shoot;
		}
		
		public double targetX()
		{
			if(!shoot) throw new IllegalStateException("Action does not call for shooting: no target");
			return targetX;
		}
		
		public double targetY()
		{
			if(!shoot) throw new IllegalStateException("Action does not call for shooting: no target");
			return targetY;
		}
		
		
	}
	
}
