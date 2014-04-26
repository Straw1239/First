package fxcore;

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


/**
 * Main game engine. Performs all non graphical internal calculation.
 * Keeps track of Player, all bullets, enemies, and events.
 * Provides an immutable Display object representing 
 * one snapshot of engine state.
 * 
 * @author Rajan Troll
 *
 */

public final class Engine implements Serializable
{
	public static final long serialVersionUID = 0L;
	
	/**
	 * Dimensions of the game world
	 */
	
	public final double width, height;
	
	/**
	 * Number of ticks this Engine has executed
	 */
	
	private long updates = 0;
	private volatile Player player;
	private Cursor cursor = new Cursor(500,500);
	private volatile Player.Action playerAction;
	private List<Enemy> enemies = new LinkedList<>();
	private List<GameEvent> events = new LinkedList<>();
	
	/**
	 * Map from Faction to all bullets of that Faction, for optimization.
	 * To access all bullets independent of faction, use .values() (Cannot add to .values())
	 * Uses LinkedLists, so use iterators provided. 
	 */
	private Multimap<Faction, Bullet> bullets = LinkedListMultimap.create();
	
	/**
	 * Representation of engine state. Replaced each update, 
	 * is immutable and can be passed around safely.
	 * Passed to the Renderer each frame. 
	 */
	private Display view;
	
	/**
	 * Creates a new engine with the specified dimensions.
	 * These dimensions cannot be changed after creation.
	 * Initializes the player to the center of the game.
	 * @param width
	 * @param height
	 */
	
	public Engine(double width, double height)
	{
		this.width = width;
		this.height = height;
		player = new Player(width / 2, height / 2);
		view = new Display(player, enemies, bullets.values(), events, cursor, width, height);
	}
	
	/**
	 * Returns a Display object representing the internal state of all game elements.
	 * Display is immutable, engine state cannot be changed. This method does not generate 
	 * a new Display, but returns the one calculated after each engine update to all callers 
	 * until the next update.
	 * @return
	 */
	
	public Display getDisplay() 
	{
		return view;
	}
	
	/**
	 * Sets the player's action for the next engine update
	 * @param action
	 */
	public void setPlayerAction(Player.Action action)
	{
		playerAction = action;
	}
	
	
	public void setCursorLocation(double x, double y)
	{
		cursor = new Cursor(x, y);
	}
	
	/**
	 * Advances the game represented by this engine one tick.
	 * Updates all game entities, processes all events, and currently can add new enemies.
	 * Also updates the Engine's Display object to the latest state.
	 */
	public void update()
	{
		if(updates % 60 == 0) 
		{
			if(MainGame.rand.nextBoolean())
				addRandomBasicEnemy();
			else
				addRandomMovingEnemy();
		}
		updatePlayer();
		if(!player.isDead())
		{
			executePlayerAction();
		}
		updateEnemies();
		updateBullets();
		executeEvents();
		view = new Display(player, enemies, bullets.values(), events, cursor, width, height);
		updates++;
	}
	
	/**
	 * Returns number of engine updates that have occured
	 * @return Time in ticks
	 */
	
	public long getTime()
	{
		return updates;
	}
	
	/**
	 * Adds a new event to the engine event list
	 * throws IllegalArgumentException if the event is expired,
	 * NullPointerException if it is null
	 * @param New Event
	 */
	
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
				 GameEvent ev = e.event(view);
				 if(ev != null)
				 {
					 events.add(ev);
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
		enemies.add(new MovingEnemy(x, y));
	}
	
	/**
	 * Needs improvement or replacement.
	 * Executes the player's action for the tick,
	 * including movement and shooting
	 */
	
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