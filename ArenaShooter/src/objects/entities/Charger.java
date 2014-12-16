package objects.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.Bullet;
import objects.Coin;
import objects.GameObject;
import objects.ReadableObject;
import objects.events.GameEvent;
import utils.Utils;
import bounds.Bounds;
import bounds.Circle;
import engine.State;
import fxcore.MainGame;

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

	public Impact collideWith(GameObject other)
	{
		if(other.getFaction() != faction)
		{
			return new Impact(this, new Change(DAMAGE, .1));
		}
		return Impact.NONE;
	}
	
	public void handleChange(Change c, GameObject source)
	{
		super.handleChange(c, source);
		if(c.code == DAMAGE)
		{
			hits += (Double) c.data;
		}
	}

	
	private void lunge(State s)
	{
		double speed = 20;
		ReadableObject target = findTarget(s);
		double distance = Utils.distance(this, target);
		double x = target.getX() - this.x;
		double y = target.getY() - this.y;
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
			Bullet b = new Bullet(this, findTarget(d), bulletSpeed, 5, Color.BLUE);
			b.damage = .5;
			b.spread(Math.toRadians(20));
			nextEvents.add(GameEvent.spawnerOf(b));
		}
		if(!isDead()) heal(.1);
	}
	
	public Collection<GameEvent> events(State s)
	{
		return nextEvents;
	}

	@Override
	public Bounds bounds()
	{
		 return bounds;
	}
	
	public Collection<GameEvent> onDeath(){
		ArrayList<GameEvent> coins = new ArrayList<GameEvent>();
		for(int i = 0; i < 5; i++){
			coins.add(GameEvent.spawnerOf((new Coin(x + MainGame.rand.nextInt((int)(2 * radius)) - radius, y + MainGame.rand.nextInt((int)(2 * radius)) - radius))));
		}
		return coins;
	}

}
