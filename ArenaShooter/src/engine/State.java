package engine;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collection;

import objects.BulletDataHolder;
import objects.Cursor;
import objects.ObjectDataHolder;
import objects.entities.EnemyDataHolder;
import objects.entities.EntityDataHolder;
import objects.events.EventDataHolder;
import player.PlayerDataHolder;
import utils.Cloner;
import bounds.Bounds;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;

/**
 * Class representing an internal engine view.
 * Immutable.
 * Passed from the engine to the renderer for displaying to the screen
 * also passed to all engine objects in update.
 * @author Rajan Troll
 *
 */
public class State implements Externalizable
{
	public final ImmutableCollection<? extends EntityDataHolder> entities;
	public final ImmutableCollection<? extends BulletDataHolder> bullets;
	public final ImmutableCollection<? extends EventDataHolder> events;
	public final ImmutableCollection<? extends ObjectDataHolder> objects;
	public final Bounds gameBounds;
	public final PlayerDataHolder player;
	public final Cursor mouse;
	public final double width, height;
	public final long time;
	
	
	public State(PlayerDataHolder player, Collection<? extends EntityDataHolder> entities,
			Collection<? extends BulletDataHolder> bullets, Collection<? extends EventDataHolder> events, Collection<? extends ObjectDataHolder> objects,
			Cursor cursor, double width, double height, Bounds bounds, long time)
	{
		this.entities = ImmutableList.copyOf(entities.stream().map(e -> (EntityDataHolder) e.clone()).iterator());
		this.bullets = ImmutableList.copyOf(bullets.stream().map(b -> (BulletDataHolder) b.clone()).iterator());
		this.events = ImmutableList.copyOf(events.stream().map(e -> (EventDataHolder) e.clone()).iterator());
		this.objects = ImmutableList.copyOf(objects.stream().map(e -> (ObjectDataHolder) e.clone()).iterator());
		this.gameBounds = bounds;
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
		public Bounds bounds;
		
		public State build()
		{
			return new State(player, enemies, bullets, events, objects, mouse, width, height, bounds, time);
		}
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException
	{
		throw new UnsupportedOperationException("Finish this later");
		
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
	{
		throw new UnsupportedOperationException("Finish this later");
		
	}
}
