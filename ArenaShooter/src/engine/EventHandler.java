package engine;

import java.util.Collection;

import objects.Faction;
import objects.GameObject;
import objects.events.GameEvent;
import player.Player;

public interface EventHandler 
{
	/**
	 * Returns a collection of ALL game objects, including elements such as the mouse.
	 * 
	 * @return all GameObjects in the engine
	 */
	public Collection<? extends GameObject> getAll();
	
	public Player getPlayer();
	
	
	public Collection<? extends GameEvent> getAllEvents();
	
	public void add(GameObject obj);
	
	public default void addAll(Iterable<? extends GameObject> gameObjects)
	{
		for(GameObject obj : gameObjects)
		{
			add(obj);
		}
	}
	
	public void addEvent(GameEvent e);
	
	public default void addAllEvents(Iterable<? extends GameEvent> events)
	{
		for(GameEvent e : events)
		{
			addEvent(e);
		}
	}
	
	public Collection<? extends GameObject> objectsOfFaction(Faction f);
	
	public Collection<? extends GameEvent> eventsOfFaction(Faction f);
	
}
