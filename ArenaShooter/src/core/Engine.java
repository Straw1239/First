package core;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import objects.BasicEnemy;
import objects.Bullet;
import objects.Cursor;
import objects.Enemy;
import objects.Faction;
import objects.MovingEnemy;
import objects.Player;
import objects.events.GameEvent;
import utils.Utils;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;


public class Engine implements Serializable
{
	public static final long serialVersionUID = 0L;
	
	public final double width, height;
	
	private long updates = 0;
	private volatile Player player;
	private Cursor cursor = new Cursor(500,500);
	private volatile Player.Action playerAction;
	private List<Enemy> enemies = new LinkedList<>();
	private List<GameEvent> events = new LinkedList<>();
	private Multimap<Faction, Bullet> bullets = LinkedListMultimap.create();
	private Display view;
	
	public Engine(double width, double height)
	{
		this.width = width;
		this.height = height;
		player = new Player(width / 2, height / 2);
		view = new Display(player, enemies, bullets.values(), events, cursor, width, height);
	}
	
	public Display getDisplay() 
	{
		return view;
	}
	
	public void setPlayerAction(Player.Action action)
	{
		playerAction = action;
	}
	
	public void setCursorLocation(double x, double y)
	{
		cursor = new Cursor(x, y);
	}
	
	public void update()
	{
		if(updates % 60 == 0) 
		{
			if(MainGame.rand.nextBoolean())
				addRandomBasicEnemy();
			else
				addRandomMovingEnemy();
		}
	
		executeEvents();
		updatePlayer();
		if(!player.isDead())
		{
			executePlayerAction();
		}
		updateEnemies();
		updateBullets();
		view = new Display(player, enemies, bullets.values(), events, cursor, width, height);
		updates++;
	}
	
	public long getTime()
	{
		return updates;
	}
	
	public void addEvent(GameEvent e)
	{
		if(e.hasExpired()) throw new IllegalArgumentException();
		events.add(e);
	}
	
	private void executeEvents()
	{
		Iterator<GameEvent> it = events.iterator();
		while(it.hasNext())
		{
			GameEvent e = it.next();
			if(e.hasExpired())
			{
				it.remove();
			}
			else
			{
				e.effects(player, bullets, enemies, events);
			}
		}
	}
	
	private void updatePlayer()
	{
		player.update(view);
	}
	
	private void updateBullets()
	{
		for(Faction f : Faction.values())
		{
			Collection<Bullet> fBullets = bullets.get(f);
			Iterator<Bullet> it = fBullets.iterator();
			while(it.hasNext())
			{
				Bullet b = it.next();
				b.update(view);
				if(b.hasHitWall(width, height))
				{
					it.remove();
				}
				else
				{
					switch (f)
					{
					case Neutral:
					case Enemy:
						if(player.collidesWithBullet(b))
						{
							b.collide(player);
							player.hitByBullet(b);
							if(b.isDead()) it.remove();
						}
						if(f == Faction.Enemy)break;
					
					case Player:
						Iterator<Enemy> et = enemies.iterator();
						while(et.hasNext())
						{
							Enemy e = et.next();
							if(e.collidesWithBullet(b))
							{
								b.collide(e);
								e.hitByBullet(b);
								if(b.isDead()) it.remove();
								break;
							}
						}
					}
				}
			}
		}
		
	}
	
	private void updateEnemies()
	{
		Iterator<Enemy> it = enemies.iterator();
		while(it.hasNext())
		{
			Enemy e = it.next();
			e.update(view);
			if(e.isDead()) 
			{
				it.remove();
			}
			else
			{
				 if(e.collidesWithPlayer(player))
				 {
					 e.collideWithPlayer(player);
				 }
				 Bullet b = e.shot(view);
				 if(b != null)
				 {
					 bullets.put(Faction.Enemy, b);
				 }
			}
		}
	}
	
	private void addRandomBasicEnemy()
	{
		double x = MainGame.rand.nextDouble() * width;
		double y = MainGame.rand.nextDouble() * height;
		enemies.add(new BasicEnemy(x,y));
	}
	
	private void addRandomMovingEnemy()
	{
		double x = MainGame.rand.nextDouble() * width;
		double y = MainGame.rand.nextDouble() * height;
		System.out.println("Attempting to add a new enemy");
		enemies.add(new MovingEnemy(x, y));
	}
	
	private void executePlayerAction()
	{
		if(playerAction == null) return;
		int moveSpeed = 5;
		double dx = 0, dy = 0;
		
		if(playerAction.isDown()) dy++;
		if(playerAction.isUp()) dy--;
		if(playerAction.isRight()) dx++;
		if(playerAction.isLeft()) dx--;
		
		if(dx != 0 && dy != 0)
		{
			double sqrt2 = Math.sqrt(2);
			dx /= sqrt2;
			dy /= sqrt2;
		}
		player.move(dx * moveSpeed, dy * moveSpeed);
		if(playerAction.isShooting())
		{
			double x = playerAction.targetX(), y = playerAction.targetY();
			double distance = Utils.distance(player.getX(), player.getY(), x, y);
			double speed = 10;
			double ratio = speed / distance;
			bullets.put(Faction.Player, new Bullet(player, (x - player.getX()) * ratio, (y - player.getY())  * ratio,5,Player.color));
		}
		
	}
	
	
	
	
	
	
	
	
}
