package engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import objects.Bullet;
import objects.Cursor;
import objects.Faction;
import objects.GameObject;
import objects.entities.Entity;
import objects.events.GameEvent;
import player.Player;
import utils.Concatenator;
import utils.Utils;
import bounds.Bounds;
import bounds.Rectangle;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;


/**
 * Main game engine. Performs all non graphical internal calculation.
 * Keeps track of Player, all bullets, enemies, and events.
 * Provides an immutable State object representing 
 * one snapshot of engine state.
 * 
 * @author Rajan Troll
 *
 */

public final class Engine 
{
	/**
	 * 0 For all dev builds
	 */
	public static final long serialVersionUID = 0L;
	
	/**
	 * Dimensions of the game world
	 */
	
	public final double width, height;
	
	/**
	 * Bounds of the game world
	 */
	public Bounds bounds; 
	
	/**
	 * Number of ticks this Engine has executed
	 */
	
	private long updates = 0;
	
	/**
	 * Represents the player of the game
	 */
	private volatile Player player;
	/**
	 * The mouse cursor for the game
	 */
	private Cursor cursor = new Cursor(500,500);
	/**
	 * Map from faction to entities of that faction
	 */
	private Multimap<Faction, Entity> entities = LinkedListMultimap.create();
	/**
	 * List of all events currently happening
	 */
	private List<GameEvent> events = new LinkedList<>();
	/**
	 * Event handler for this engine
	 */
	private Handler handler = new Handler();
	/**
	 * Map from faction to objects in that faction not in entities or bullets (should change to all objects?)
	 */
	private Multimap<Faction, GameObject> objects = LinkedListMultimap.create();
	
	/**
	 * Map from Faction to all bullets of that Faction, for optimization.
	 * To access all bullets independent of faction, use .values() (Cannot add to .values())
	 * Uses LinkedLists, so use iterators provided. 
	 */
	private Multimap<Faction, Bullet> bullets = LinkedListMultimap.create();
	
	/**
	 * Spawner for this engine, creates enemies and probably other stuff.
	 */
	private Spawner spawner;
	
	/**
	 * Representation of engine state. Replaced each update, 
	 * is immutable and can be passed around safely.
	 * Passed to the Renderer each frame. 
	 */
	private State state;
	
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
		bounds = Rectangle.of(0, 0, this.width, this.height);
		player = new Player(bounds.centerX(), bounds.centerY());//new Player(width / 2, height / 2);
		handler.addAllEvents(player.onEntry(state));
		handler.addAllEvents(cursor.onEntry(state));
		entities.put(Faction.Player, player);
		state = generateState();
		spawner = new DefaultSpawner(width, height);
	}
	
	private State generateState()
	{
		return new State(player, entities.values(), bullets.values(), events, objects.values(), cursor, width, height, bounds, updates);
	}
	/**
	 * Creates engine based on data contained in state. State is immutable; all data is copied into the new engine.
	 * Exactly recreates the state represented by the State object in the engine.
	 * @param state
	 */
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
		return state;
	}
	
	/**
	 * Sets the player's action for the next engine update
	 * @param action
	 */
	public void setPlayerAction(Player.Action action)
	{
		player.setAction(action);
	}
	
	
	
	
	/**
	 * Advances the game represented by this engine one tick.
	 * Updates all game entities, processes all events, and currently can add new enemies.
	 * Also updates the Engine's State object to the latest state.
	 */
	public void update()
	{
		/*
		if(updates % 60 == 0) 
		{
			if(MainGame.rand.nextBoolean())
				addRandomBasicEnemy();
			else
				addRandomMovingEnemy();
		}
		*/
		handler.addAll(spawner.spawn(state));
		cursor.update(state);
		updatePlayer();
		updateObjects();
		updateEnemies();
		updateBullets();
		executeEvents();
		state = generateState();
		updates++;
		if(sleepTime > 0) 
		{
			Utils.sleep(sleepTime);
			sleepTime = 0;
		}
	}
	
	private void updateObjects()
	{
		for(Faction f : Faction.values())
		{
			Collection<GameObject> objectsOfFaction = objects.get(f);
			for(GameObject obj : objectsOfFaction)
			{
				obj.update(state);//Add way to remove GameObjects which aren't any other class?
				
			}
		}
		
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
				e.effects(handler);
			}
		}
		events.addAll(handler.newEvents);
		handler.newEvents.clear();
	}
	private boolean gameOver = false;
	private void updatePlayer()
	{
		
		player.update(state);
		events.addAll(player.events(state));
		if(player.isDead())
		{
			if(!gameOver)
			events.addAll(player.onDeath(state));
			gameOver = true;
		}

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
				b.update(state);
				if(b.isDead())
				{
					it.remove();
					events.addAll(b.onDeath(state));
				}
				else
				{
					events.addAll(b.events(state));
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
								events.addAll(b.onDeath(state));
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
									events.addAll(b.onDeath(state));
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
			e.update(state);
			if(e.isDead()) 
			{
				it.remove();
				events.addAll(e.onDeath(state));
			}
			else
			{
				 if(e.collidesWith(player))
				 {
					 e.collideWith(player);
					 player.collideWith(e);
				 }
				 events.addAll(e.events(state));
			}
		}
	}
	
	
	
	
	
	
	
	private class Handler implements EventHandler
	{
		public Collection<GameEvent> newEvents = new ArrayList<>();
		@Override
		public Collection<? extends GameObject> getAll() 
		{
			return new Concatenator<GameObject>(Collections.singleton(player), Collections.singleton(cursor), entities.values(), bullets.values())
			{

				@Override
				public boolean add(GameObject arg0) 
				{
					Handler.this.add(arg0);
					return true;
				}

				@Override
				public boolean addAll(Collection<? extends GameObject> objs) 
				{
					for(GameObject obj : objs)
					{
						add(obj);
					}
					return true;
				}
			};
		}

		@Override
		public Collection<? extends GameEvent> getAllEvents() 
		{
			return events;
		}

		@Override
		public void add(GameObject obj) 
		{
			for(GameEvent e : obj.onEntry(getState()))
			{
				addEvent(e);
			}
			if(obj instanceof Bullet) bullets.put(obj.getFaction(), (Bullet)obj);
			else if(obj instanceof Player) throw new IllegalArgumentException("cannot add multiple players");
			else if(obj instanceof Entity) entities.put(obj.getFaction(), (Entity) obj);
			else objects.put(obj.getFaction(), obj);
		}

		@Override
		public void addEvent(GameEvent e) 
		{
			newEvents.add(e);
		}

		@Override
		public Collection<? extends GameObject> objectsOfFaction(Faction f) 
		{
			return objects.get(f);
		}

		@Override
		public Collection<? extends GameEvent> eventsOfFaction(Faction f) 
		{
			return Collections.unmodifiableCollection(events.stream().filter(e -> e.getFaction() == f).collect(Collectors.toList()));
		}

		@Override
		public Player getPlayer() 
		{
			return player;
		}

		@Override
		public Collection<? extends Entity> entitiesOfFaction(Faction f) 
		{
			return entities.get(f);
		}

		@Override
		public Collection<? extends Entity> entities() 
		{
			return entities.values();
		}

		@Override
		public Collection<? extends Bullet> bullets()
		{
			return bullets.values();
		}

		@Override
		public Collection<? extends Bullet> bulletsOfFaction(Faction f) 
		{	
			return bullets.get(f);
		}
	}





	/**
	 * Pauses the engine for the specified time, first finishing execution of the current tick
	 * @param milliseconds to sleep for
	 */
	private volatile long sleepTime = 0;
	
	public synchronized void sleep(long time)
	{
		sleepTime += time;	
	}	
}
