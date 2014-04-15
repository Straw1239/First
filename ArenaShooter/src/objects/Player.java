package objects;

import java.awt.Color;
import java.util.Arrays;




import utils.Utils;

public class Player extends Entity implements PlayerDataHolder
{
	public static double radius = 30;
	public static Color color = new Color(170,0,170);
	
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
		return entity.collidesWithPlayer(this);
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

	@Override
	public boolean collidesWithPlayer(Player p) 
	{
		//Only relevant if multiplayer is added, not for a long time
		return false;
	}

	@Override
	public boolean collidesWithBullet(Bullet b) 
	{
		if(b.faction == Faction.Player) return false;
		return Utils.circleCollide(this, b, radius + b.getRadius());
	}

	@Override
	public boolean collidesWithEnemy(Enemy e) 
	{
		return e.collidesWithPlayer(this);
	}

	@Override
	public void hitByBullet(Bullet b) 
	{
		damage(b.damage);
	}
	
}
