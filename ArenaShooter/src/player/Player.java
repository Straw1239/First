package player;



import java.util.ArrayList;
import java.util.Collection;

import bounds.Bounds;
import bounds.Circle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.Bullet;
import objects.Entity;
import objects.Faction;
import objects.GameObject;
import objects.events.GameEvent;
import static utils.Utils.*;
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
	private Action action;
	private Collection<GameEvent> nextEvents = new ArrayList<>();
	private Circle bounds = new Circle()
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
		
		public double radius()
		{
			return radius;
		}
	};
	
	public Player(double x, double y) 
	{
		super(x, y);
		faction = Faction.Player;
		maxHealth = 10;
		health = maxHealth;
	}
	
	public void setAction(Action a)
	{
		action = a;
	}
	
	private void move(double dx, double dy, State state)
	{
		x += dx;
		x = clamp(x, radius, state.width - radius);
		y += dy;
		y = clamp(y, radius, state.height - radius);
	}

	@Override
	public void update(State d) 
	{
		nextEvents.clear();
		if(!isDead()) 
		{
			heal(.03);
			executeAction(d);
		}
		
	}
	
	private void executeAction(State state)
	{
		double moveSpeed = 5;
		double dx = 0, dy = 0;
		
		if(action.isDown()) dy++;
		if(action.isUp()) dy--;
		if(action.isRight()) dx++;
		if(action.isLeft()) dx--;
		move(dx * moveSpeed, dy * moveSpeed, state);
		if(dx != 0 && dy != 0)
		{
			double sqrt2 = Math.sqrt(2);
			dx /= sqrt2;
			dy /= sqrt2;
		}
		if(action.isShooting())
		{
			double x = action.targetX(), y = action.targetY();
			double distance = distance(this.x, this.y, x, y);
			double speed = 10;
			double ratio = speed / distance;
			Bullet bullet = new Bullet(this, GameObject.dataOf(x, y, faction), 10, 5, color);
			bullet.spread(Math.toRadians(5.0));
			nextEvents.add(GameEvent.spawner(bullet));
		}
	}
	
	@Override
	public Collection<GameEvent> events(State d)
	{
		return nextEvents;
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

		public Action(){}
		
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
	}

	@Override
	public Bounds bounds()
	{
		return bounds;
	}

	@Override
	public void collideWith(Entity e) 
	{
		//Do nothing for now, damage will be handled by enemy's collide function
	}	
}
