package engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import objects.Cursor;
import objects.Faction;
import objects.GameObject;
import objects.events.GameEvent;
import player.Player;
import utils.Concatenator;
import utils.Utils;
import bounds.Bounds;
import bounds.Rectangle;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;

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
	 * List of all events currently happening
	 */
	private List<GameEvent> events = new LinkedList<>();
	/**
	 * Event handler for this engine
	 */
	
	private ListMultimap<Faction, GameObject> objects = Multimaps.newListMultimap(new EnumMap<>(Faction.class), LinkedList::new);
	//private ListMultimap<Faction, ListMultimap<Set<Faction> ,GameObject>> objects = Multimaps.newListMultimap(new EnumMap<>(Faction.class), LinkedList::new);
	
	
	private Handler handler = new Handler();
	
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
	
	private Player player;
	
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
		player = new Player(width / 2, height / 2);
		objects.put(Faction.Player, player);
		objects.put(Faction.Player, new Cursor(0, 0));
		generateState();
		spawner = new DefaultSpawner(width, height);
		//spawner = new HankSpawner();
	}
	
	private void generateState()
	{
		state = new State(events, objects.values(), width, height, bounds, updates);
		//System.out.println(state);
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
	 * Advances the game represented by this engine one tick.
	 * Updates all game entities, processes all events, and currently can add new enemies.
	 * Also updates the Engine's State object to the latest state.
	 */
	public void update()
	{
		handler.addAll(spawner.spawn(state));
		updateObjects();
		executeEvents();
		updates++;
		generateState();
		
		if(sleepTime > 0) 
		{
			Utils.sleep(sleepTime);
			sleepTime = 0;
		}
	}
	
	private void updateObjects()
	{
		//Update all objects
		Iterator<GameObject> all = objects.values().iterator();
		while(all.hasNext())
		{
			GameObject next = all.next();
			next.update(state);
			if(next.isDead())
			{
				all.remove();
				handler.addAllEvents(next.onDeath(state));
			}
		}
	
		//Currently we only check for collision between objects of different factions
		Faction[] factions = Faction.values();
		for(int i = 0; i < factions.length; i++)
		{
			List<GameObject> objs = objects.get(factions[i]);
			for(GameObject obj : objs)
			{
				for(int j = i + 1; j < factions.length; j++)
				{
					for(GameObject other : objects.get(factions[j]))
					{
						if(obj.collidesWith(other))
						{
							GameObject.collide(obj, other);
						}
					}
				}
			}
		}
		//System.out.println("Collision Checks: " + GameObject.collisions);
		//System.out.println(objects.size());
		 
		 
		for(GameObject obj : objects.values()) handler.addAllEvents((obj.events(state)));
		
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
		//if(e.hasExpired()) throw new IllegalArgumentException("Event has already expired");
		handler.addEvent(e);
	}
	
	private void executeEvents()
	{
		Iterator<GameEvent> it = events.iterator();
		while(it.hasNext())
		{
			GameEvent e = it.next();
			e.effects(handler);
			it.remove();
		}
		events.addAll(handler.newEvents);
		handler.newEvents.clear();
	}
	
	private class Handler implements EventHandler
	{
		public Collection<GameEvent> newEvents = new ArrayList<>();
		@Override
		public Collection<GameObject> getAll() 
		{
			return new Concatenator<GameObject>(Collections.singleton(player), objects.values())
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
		public Collection<GameEvent> getAllEvents() 
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
			objects.put(obj.getFaction(), obj);
		}

		@Override
		public void addEvent(GameEvent e) 
		{
			newEvents.add(e);
		}

		@Override
		public Collection<GameObject> objectsOfFaction(Faction f) 
		{
			return objects.get(f);
		}

		@Override
		public Collection<GameEvent> eventsOfFaction(Faction f) 
		{
			return Collections.unmodifiableCollection(events.stream().filter(e -> e.getFaction() == f).collect(Collectors.toList()));
		}

		@Override
		public Player getPlayer() 
		{
			return player;
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

	public synchronized void reset()
	{
		synchronized(objects) // is this really neccesary... 
		{
			synchronized(events)
			{
				updates = 0;
				sleepTime = 0;
				events.clear();
				objects.clear();
				handler = new Handler();
				spawner = new DefaultSpawner(width, height);
				player = new Player(width / 2, height / 2);
				objects.put(Faction.Player, player);
				objects.put(Faction.Player, new Cursor(0, 0));
				generateState();
			}
		}
	}	
}
