package objects.entities;


import java.util.Collection;
import java.util.Collections;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.Bullet;
import objects.Coin;
import objects.events.GameEvent;
import bounds.Bounds;
import bounds.Circle;
import engine.State;
import fxcore.MainGame;


public class BasicEnemy extends Enemy 
{
	public static final double radius = 20;
	public static final double contactDamage = 2;
	public static final double startHealth = 10;
	public static final long fireTime = 45;
	
	private long shotTime = MainGame.getTime();
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
	
	public BasicEnemy(double x, double y) 
	{
		super(x, y);
		health = startHealth;
		maxHealth = startHealth;
	}

	@Override
	public void update(State d) 
	{	
		
	}

	
	
	@Override
	public void draw(GraphicsContext g) 
	{
		g.setFill(Color.rgb(50, 200, 80));
		g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
	}

	
	
	@Override
	public Collection<GameEvent> events(State d)
	{
		if(MainGame.getTime() < shotTime + fireTime) return Collections.emptyList();
		if(d.player.isDead()) return Collections.emptyList();
		shotTime = MainGame.getTime();
		return Collections.singleton(GameEvent.spawnerOf(new Bullet(this, d.player, 5, 10, Color.GREEN)));
		
	}

	@Override
	public Bounds bounds()
	{
		return bounds;
	}

	public Collection<? extends GameEvent> onDeath(){
		Coin coin = new Coin(x + MainGame.rand.nextInt((int)radius), y + MainGame.rand.nextInt((int)radius));
		return Collections.singleton(GameEvent.spawnerOf(coin));
	}
	

}
