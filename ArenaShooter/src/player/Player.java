package player;



import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import objects.Bounds;
import objects.Bullet;
import objects.Circle;
import objects.Entity;
import objects.Faction;
import objects.GameObject;
import engine.State;

/**
 * Represents the player of the game. 
 * @author Rajan
 *
 */
public class Player extends Entity implements PlayerDataHolder
{
	public static double radius = 30;
	public static Color color = Color.rgb(170, 0, 170);
	private long fireTime;
	private Circle bounds = new Circle(radius)
	{
		@Override
		public double centerX()
		{
			return x;
		}

		@Override
		public double centerY()
		{
			return y;
		}
	};
	
	public Player(double x, double y) 
	{
		super(x, y);
		faction = Faction.Player;
		maxHealth = 10;
		health = maxHealth;
	}

	@Override
	public void update(State d) 
	{
		if(!isDead()) heal(.03);
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
		return bounds.intersects(entity.bounds());
	}
	
	@Override
	public void hitByBullet(Bullet b) 
	{
		//damage(b.damage);
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
	public long fireTime()
	{
		return fireTime;
	}

	@Override
	public void draw(GraphicsContext g)
	{
		g.setFill(Player.color);
		g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
		g.setStroke(Color.WHITE);
		double healthBar = 150;
		g.fillRect(0, 0, healthBar * health / maxHealth, 30);
		g.strokeRect(0, 0, healthBar, 30);
		
		
	}

	@Override
	public Bounds bounds()
	{
		return bounds;
	}	
}
