package objects.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.Bullet;
import objects.Faction;
import objects.events.GameEvent;
import utils.Utils;
import utils.Vector;
import bounds.Bounds;
import bounds.Circle;
import engine.EventHandler;
import engine.State;
import static fxcore.MainGame.*;

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
	}

	@Override
	public void draw(GraphicsContext g)
	{
		g.setFill(color);
		g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
	}

	@Override
	public void hitByBullet(Bullet b)
	{
		
	}

	@Override
	public void collideWith(Entity e)
	{
		if(e.getFaction() == Faction.Player)
		{
			e.damage(.3);
		}
	}
	
	public Collection<? extends GameEvent> onEntry(State s)
	{
		return Collections.singleton(new RepulsionField());
	}
	
	private class RepulsionField extends GameEvent
	{
		public RepulsionField()
		{
			super(FirstBoss.this);
		}

		@Override
		public void effects(EventHandler handler)
		{
			Collection<? extends Entity> enemies = handler.entitiesOfFaction(faction);
			for(Entity e : enemies)
			{
				//Push them away
			}
		}

		@Override
		public void draw(GraphicsContext g)
		{
			//No visual
		}

		@Override
		public boolean hasExpired()
		{
			return isDead();
		}
	}

	
	
	@Override
	public void update(State d)
	{
		nextEvents.clear();
		
		Vector accel = new Vector(x - d.player.getX(), y - d.player.getY()).normalized(.1).inverse();
		dx += accel.x;
		dy += accel.y;
		double hypot = Math.hypot(dx, dy);
		double maxV = 7;
		if(hypot >= maxV)
		{
			dx = dx * maxV / hypot;
			dy = dy * maxV / hypot;
		}
		
		
		super.update(d);
	}
	
	private List<GameEvent> nextEvents = new ArrayList<>();
	long lastMinionSpawn = 0;
	
	public Collection<? extends GameEvent> events(State s)
	{
		if(s.time - lastMinionSpawn >= 30)
		{
			nextEvents.add(GameEvent.spawner(new Minion()));
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
			Vector direction = new Vector(x - FirstBoss.this.x, y - FirstBoss.this.y).perpindicular().normalized().scale(8);
			if(clockwise) direction = direction.inverse();
			dx = direction.x + FirstBoss.this.dx;
			dy = direction.y + FirstBoss.this.dy;
			
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
			g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
		}

		@Override
		public void hitByBullet(Bullet b)
		{
			
		}

		@Override
		public void collideWith(Entity e)
		{
			if(e.getFaction() == Faction.Player)
			{
				e.damage(.1);
			}
		}

		@Override
		public void update(State d)
		{
			super.update(d);
			double distance = Utils.distance(this, FirstBoss.this);
			distance = Utils.clamp(distance, 50, Double.POSITIVE_INFINITY);
			double distanceSqrd = distance * distance;
			double force = 10000 / distanceSqrd;
			dx -= force * (x - FirstBoss.this.x) / distance;
			dy -= force * (y - FirstBoss.this.y) / distance;
			
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
		
		public Collection<GameEvent> onDeath()
		{
			numMinions--;
			return Collections.emptyList();
		}
		
	}

}
