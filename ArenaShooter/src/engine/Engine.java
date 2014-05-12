package engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import objects.BasicEnemy;
import objects.Bullet;
import objects.Cursor;
import objects.Enemy;
import objects.Entity;
import objects.Faction;
import objects.MovingEnemy;
import objects.Rectangle;
import objects.events.GameEvent;
import player.Player;
import utils.Utils;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

import fxcore.MainGame;


/**
 * Main game engine. Performs all non graphical internal calculation.
 * Keeps track of Player, all bullets, enemies, and events.
 * Provides an immutable Display object representing 
 * one snapshot of engine state.
 * 
 * @author Rajan Troll
 *
 */

public final class Engine 
{
	public static final long serialVersionUID = 0L;
	
	/**
	 * Dimensions of the game world
	 */
	
	public final double width, height;
	public final Rectangle bounds; 
	
	/**
	 * Number of ticks this Engine has executed
	 */
	
	private long updates = 0;
	private volatile Player player;
	private Cursor cursor = new Cursor(500,500);
	private volatile Player.Action playerAction;
	private Multimap<Faction, Entity> entities = LinkedListMultimap.create();
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
	private State view;
	
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
		bounds = Rectangle.of(0, 0, width, height);
		view = new State(player, entities.values(), bullets.values(), events, cursor, width, height, updates);
	}
	
	public Engine(State state)
	{
		width = state.width;
		height = state.height;
		throw new UnsupportedOperationException("Unfinished Contructor");
	}
	
	/**
	 * Returns a State object representing the internal state of all game elements.
	 * State is immutable, engine state cannot be changed. This method does not generate 
	 * a new State, but returns the one calculated after each engine update to all callers 
	 * until the next update.
	 * @return
	 */
	
	public State getState() 
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
	 * Also updates the Engine's State object to the latest state.
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
		view = new State(player, entities.values(), bullets.values(), events, cursor, width, height, updates);
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
		if(e.hasExpired()) throw new IllegalArgumentException("Event has already expired");
		events.add(e);
	}
	
	private void addNotNull(GameEvent e)
	{
		if(e != null)
		{
			addEvent(e);
		}
	}
	
	private void executeEvents()
	{
		Iterator<GameEvent> it = events.iterator();
		Collection<GameEvent> newEvents = new ArrayList<>();
		while(it.hasNext())
		{
			GameEvent e = it.next();
			if(e.hasExpired())
			{
				it.remove();
			}
			else
			{
				newEvents.addAll(e.effects(player, bullets, enemies, events));
			}
		}
		events.addAll(newEvents);
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
				if(!b.bounds().intersects(bounds))
				{
					it.remove();
					addNotNull(b.onDeath(view));
				}
				else
				{
					switch (f)
					{
					case Neutral:
					case Enemy:
						if(player.collidesWith(b))
						{
							b.collide(player);
							player.hitByBullet(b);
							if(b.isDead()) 
							{
								addNotNull(b.onDeath());
								it.remove();
							}	
						}
						if(f == Faction.Enemy)break;
					
					case Player:
						Iterator<Entity> et = entities.get(Faction.Enemy).iterator();
						while(et.hasNext())
						{
							Entity e = et.next();
							if(e.collidesWith(b))
							{
								b.collide(e);
								e.hitByBullet(b);
								if(b.isDead()) 
								{
									it.remove();
									addNotNull(e.onDeath(view));
								}
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
		Iterator<Entity> it = entities.get(Faction.Enemy).iterator();
		while(it.hasNext())
		{
			Entity e = it.next();
			e.update(view);
			if(e.isDead()) 
			{
				it.remove();
				addNotNull(e.onDeath(view));
			}
			else
			{
				 if(e.collidesWith(player))
				 {
					 e.collideWith(player);
				 }
				 GameEvent ev = e.event(view);
				 addNotNull(ev);
			}
		}
	}
	
	private void addRandomBasicEnemy()
	{
		double x = MainGame.rand.nextDouble() * width;
		double y = MainGame.rand.nextDouble() * height;
		entities.put(Faction.Enemy, new BasicEnemy(x,y));
	}
	
	private void addRandomMovingEnemy()
	{
		double x = MainGame.rand.nextDouble() * width;
		double y = MainGame.rand.nextDouble() * height;
		entities.put(Faction.Enemy,(new MovingEnemy(x, y));
	}
	
	/**
	 * Needs improvement or replacement.
	 * Executes the player's action for the tick,
	 * including movement and shooting
	 */
	
	private void executePlayerAction()
	{
		if(playerAction == null) return;
		double moveSpeed = 5;
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
			
			bullets.put(Faction.Player, new Bullet(player, cursor,10, 5,Player.color)
			{
				/*
				public GameEvent onDeath()
				{
					return new Explosion(this, getRadius() * 4, damage);
				}
				*/
			});
			/*
			double angle = Utils.angle(player, cursor);
			double spread = Math.PI * 50 / distance;
			double density = 30;
			double offset = spread / density;
			for(int i = 0; i < density; i++)
			{
				bullets.put(Faction.Player, new Bullet(player, angle + (i - density / 2) * offset, speed, 5, Player.color));
					
				
			}
			*/
			
		}
		
	}
	
	
	
	
	
	
	
	
}
