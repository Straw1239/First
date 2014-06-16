package engine;

import java.util.Collection;

import objects.BulletDataHolder;
import objects.Cursor;
import objects.EnemyDataHolder;
import objects.EntityDataHolder;
import objects.ObjectDataHolder;
import objects.events.EventDataHolder;
import player.PlayerDataHolder;
import utils.Cloner;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;

/**
 * Class representing a display of an internal engine view.
 * Is Immutable
 * Passed from the engine to the renderer for displaying to the screen
 * also passed to all engine objects in update.
 * @author Rajan Troll
 *
 */
public class State 
{
	public final ImmutableCollection<? extends EntityDataHolder> entities;
	public final ImmutableCollection<? extends BulletDataHolder> bullets;
	public final ImmutableCollection<? extends EventDataHolder> events;
	public final ImmutableCollection<? extends ObjectDataHolder> objects; 
	public final PlayerDataHolder player;
	public final Cursor mouse;
	public final double width, height;
	public final long time;
	
	
	public State(PlayerDataHolder player, Collection<? extends EntityDataHolder> entities,
			Collection<? extends BulletDataHolder> bullets, Collection<? extends EventDataHolder> events, Collection<? extends ObjectDataHolder> objects,
			Cursor cursor, double width, double height, long time)
	{
		this.entities = ImmutableList.copyOf(entities.stream().map(e -> (EntityDataHolder) e.clone()).iterator());
		this.bullets = ImmutableList.copyOf(bullets.stream().map(b -> (BulletDataHolder) b.clone()).iterator());
		this.events = ImmutableList.copyOf(events.stream().map(e -> (EventDataHolder) e.clone()).iterator());
		this.objects = ImmutableList.copyOf(objects.stream().map(e -> (ObjectDataHolder) e.clone()).iterator());
		this.player = Cloner.clone(player);
		this.mouse = Cloner.clone(cursor);
		this.width = width;
		this.height = height;
		this.time = time;
	}
	
	/**
	 * Convenient Class for building immutable displays, will be obtained from State.builder()
	 * Needs adder methods.
	 * @author Rajan Troll
	 *
	 */
	public static class Builder
	{
		public Collection<? extends EnemyDataHolder> enemies;
		public Collection<? extends BulletDataHolder> bullets;
		public Collection<? extends EventDataHolder> events;
		public Collection<? extends ObjectDataHolder> objects;
		public PlayerDataHolder player;
		public Cursor mouse;
		public double width, height;
		public long time;
		
		public State build()
		{
			return new State(player, enemies, bullets, events, objects, mouse, width, height, time);
		}
	}
}
