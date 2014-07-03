package objects.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.Bullet;
import objects.Faction;
import objects.events.GameEvent;
import utils.Utils;
import bounds.Bounds;
import bounds.Circle;
import engine.State;

public class Charger extends Enemy
{
	private List<GameEvent> nextEvents = new ArrayList<>();
	private volatile double hits = 0;
	public static final Color COLOR = Color.BLUE;
	public static final double radius = 50;
	
	
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

		@Override
		public double radius()
		{
			return radius;
		}
		
	};
	
	public Charger(double x, double y)
	{
		super(x, y);
		maxHealth = 300;
		health = maxHealth;
	}

	@Override
	public void draw(GraphicsContext g)
	{
		g.setFill(COLOR);
		g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
	}

	@Override
	public void hitByBullet(Bullet b)
	{
		hits += b.damage;
	}

	@Override
	public void collideWith(Entity e)
	{
		if(e.getFaction() == Faction.Player)
		{
			e.damage(.1);
		}
	}

	private void lunge(State s)
	{
		double speed = 20;
		double distance = Utils.distance(this, s.player);
		double x = s.player.getX() - this.x;
		double y = s.player.getY() - this.y;
		x = speed * x / distance;
		y = speed * y / distance;
		dx = x;
		dy = y;
		
		
	}
	
	@Override
	public void update(State d)
	{
		super.update(d);
		dx *= .97;
		dy *= .97;
		double lungeThreshold = Math.pow((4/ (1 + (health / maxHealth))), 2);
		if(Utils.hypotSquared(dx, dy) < lungeThreshold) lunge(d);
		nextEvents.clear();
		for(; hits > 0; hits--)
		{
			double bulletSpeed = 5;
			//if(health / maxHealth < .5) bulletSpeed = 10;
			Bullet b = new Bullet(this, d.player, bulletSpeed, 5, Color.BLUE);
			b.damage = .5;
			b.spread(Math.toRadians(20));
			nextEvents.add(GameEvent.spawner(b));
		}
		if(!isDead()) heal(.1);
	}
	
	public Collection<? extends GameEvent> events(State s)
	{
		return nextEvents;
	}

	@Override
	public Bounds bounds()
	{
		 return bounds;
	}

}
