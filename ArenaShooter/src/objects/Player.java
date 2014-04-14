package objects;

import java.awt.Color;
import java.util.Arrays;




import utils.Utils;

public class Player extends Entity implements PlayerDataHolder
{
	public static double radius = 30;
	public static Color color = Color.green;
	
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
	
	public void setPosition(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void move(double dx, double dy)
	{
		setPosition(x + dx, y + dy);
	}

	@Override
	public boolean collidesWith(GameObject entity) 
	{
		if(entity instanceof CircleCollider)
		{
			return Utils.distance(this, entity) < (radius + ((CircleCollider)entity).getRadius());
		}
		return new CircleCollider(x, y, radius){}.collidesWith(entity);
	}
	
	public static class Action
	{
		private boolean up = false, down = false, left = false, right = false;
		private boolean shoot = false;
		private double targetX, targetY;
		
		public Action(boolean up, boolean down, boolean left, boolean right, double targetX, double targetY)
		{
			this(up ,down, left, right);
			this.targetX = targetX;
			this.targetY = targetY;
			shoot = true;
		}
		
		public Action(boolean up, boolean down, boolean left, boolean right) 
		{
			this.up = up;
			this.down = down;
			this.left = left;
			this.right = right;
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

		public boolean isUp() 
		{
			return up;
		}

		public boolean isDown() 
		{
			return down;
		}

		public boolean isLeft() 
		{
			return left;
		}

		public boolean isRight() 
		{
			return right;
		}
		
		
		
		
	}
	
}
