package objects.entities;

import static fxcore.MainGame.UPS;
import static fxcore.MainGame.getTime;
import static fxcore.MainGame.rand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.GameObject;
import objects.Locatable;
import objects.events.GameEvent;
import utils.Utils;
import utils.Vector;
import bounds.Bounds;
import bounds.Circle;
import engine.State;

public class MannyEnemy extends Enemy
{
	
	private static double radius = 100;
	private double orbitRadius = rand.nextDouble() * 600 + 100;
	public MannyEnemy(double x, double y)
	{
		super(x, y);
		maxHealth = 100;
		health = maxHealth;
	}

	@Override
	public void draw(GraphicsContext g)
	{
		g.setFill(Color.MAROON);
		bounds().fill(g);
	}

	@Override
	public Bounds bounds()
	{
		return Circle.of(x, y, radius);
	}
	
	public Impact collideWith(GameObject other)
	{
		return new Impact(this, new Change(Entity.DAMAGE, .1));
	}
	
	public void update(State s)
	{
		
		Locatable player = s.player;
		Vector distance = new Vector(x - player.getX(), y - player.getY());
		Vector normal = distance.perpindicular().normalized(2);
		double d2 = Utils.distanceSquared(this, player);
		double o2 = orbitRadius * orbitRadius;
		Vector correction = Vector.ZERO;
		double correctionFactor = 1;
		if(d2 < o2)
		{
			correction = distance.normalized(correctionFactor);
		}
		else if(d2 > o2)
		{
			correction = distance.normalized(correctionFactor).inverse();
		}
		normal = normal.add(correction).scale(3);
		dx = normal.x;
		dy = normal.y;
		super.update(s);
		
	}
	
	
	long nextSpawn = getTime() + 7 * UPS;
	public Collection<GameEvent> events(State s)
	{
		Collection<GameEvent> spawns = Collections.emptyList();
		if(s.time >= nextSpawn)
		{
			spawns = new ArrayList<>(1);
			spawns.add(GameEvent.spawnerOf(new MannyEnemy(x, y)));
			nextSpawn = s.time + 7 * UPS;
		}
		return spawns;
	}
	//TODO add a coin drop based on number alive, if there are lots they will be harder to kill, so more should drop
//	public Collection<? extends GameEvent> onDeath(){
//		ArrayList<GameEvent> coins = new ArrayList<GameEvent>();
//		
//	}
	
	

}
