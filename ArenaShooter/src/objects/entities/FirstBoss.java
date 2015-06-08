package objects.entities;

import static fxcore.MainGame.rand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.GameObject;
import objects.ReadableObject;
import objects.events.GameEvent;
import utils.Utils;
import utils.Vector;
import bounds.Bounds;
import bounds.Circle;
import engine.State;

public class FirstBoss extends Enemy
{
	private double radius = 100;
	private int numMinions = 0;
	private static Color color = Color.BLUE.interpolate(Color.GREEN, .7);
	
	public FirstBoss(double x, double y)
	{
		super(x, y);
		maxHealth = 2000;
		health = maxHealth;
		mass = 5;
	}

	@Override
	public void draw(GraphicsContext g)
	{
		g.setFill(color);
		g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
	}
	
	public Impact collideWith(GameObject other)
	{
		return new Impact(this, new Change(DAMAGE, .3));
	}

	
	
	
	@Override
	public void update(State d)
	{
		nextEvents.clear();
		ReadableObject target = findTarget(d);
		Vector accel = new Vector(x - target.getX(), y - target.getY()).normalized(.1).inverse();
		dx += accel.x;
		dy += accel.y;
		double hypot = Math.hypot(dx, dy);
		double maxV = 7;
		if(hypot >= maxV)
		{
			dx *= maxV / hypot;
			dy *= maxV / hypot;
		}
		super.update(d);
	}
	
	private List<GameEvent> nextEvents = new ArrayList<>();
	long lastMinionSpawn = 0;
	
	public Collection<GameEvent> events(State s)
	{
		if(s.time - lastMinionSpawn >= 30)
		{
			nextEvents.add(GameEvent.spawnerOf(new Minion()));
			lastMinionSpawn = s.time;
		}
		return nextEvents;
	}

	

	@Override
	public Bounds bounds()
	{
		return new Circle()
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

			@Override
			public double radius()
			{
				return radius;
			}
			
		};
	}
	
	private class Minion extends Enemy
	{
		private double radius = 15;
		public Minion(double x, double y)
		{
			super(x, y);
			maxHealth = 15;
			health = maxHealth;
			numMinions++;
			boolean clockwise = rand.nextBoolean();
			Vector direction = new Vector(x - FirstBoss.this.x, y - FirstBoss.this.y).perpendicular().normalized(8);
			if(clockwise) direction = direction.inverse();
			dx = direction.x + FirstBoss.this.dx;
			dy = direction.y + FirstBoss.this.dy;
			
		}
		
		public Impact collideWith(GameObject other)
		{
			return new Impact(this, new Change(DAMAGE, .1));
		}
		
		public boolean isDead()
		{
			return super.isDead() || FirstBoss.this.isDead();
		}
		
		public Minion(Vector position)
		{
			this(position.x, position.y);
		}
		
		public Minion()
		{
			this(Utils.circleRandom(150).add(new Vector(FirstBoss.this.x, FirstBoss.this.y)));
		}

		@Override
		public void draw(GraphicsContext g)
		{
			g.setFill(color);
			bounds().fill(g);
		}


		@Override
		public void update(State d)
		{
			super.update(d);
			double distance = Utils.distance(this, FirstBoss.this);
			//distance = Utils.clamp(distance, 50, Double.POSITIVE_INFINITY);
			//double distanceSqrd = distance * distance;
			double force = distance / 1000;
			dx -= force * (x - FirstBoss.this.x) / distance;
			dy -= force * (y - FirstBoss.this.y) / distance;
			
		}
		
		private Bounds bounds = new Circle()
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

				@Override
				public double radius()
				{
					return radius;
				}
				
		};

		@Override
		public Bounds bounds()
		{
			return bounds;
		}
		
		public Collection<GameEvent> onDeath(State d)
		{
			numMinions--;
			return super.onDeath(d);
		}
		
	}

}
