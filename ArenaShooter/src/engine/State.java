package engine;

import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.util.Collection;

import objects.ReadableObject;
import objects.events.ReadableEvent;
import player.ReadablePlayer;
import utils.Cloner;
import utils.Utils;
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
public class State 
{
	public final ImmutableCollection<ReadableEvent> events;
	public final ImmutableCollection<ReadableObject> objects;
	public final Bounds gameBounds;
	public final double width, height;
	public final long time;
	
	
	public State( Iterable<? extends ReadableEvent> events, Iterable<? extends ReadableObject> objects, double width, double height, Bounds bounds, long time)
	{
		this.events = ImmutableList.copyOf(Utils.stream(events).map(e -> e.copy()).iterator());
		this.objects = ImmutableList.copyOf(Utils.stream(objects).map(e -> e.copy()).iterator());
		this.gameBounds = bounds;
		this.width = width;
		this.height = height;
		this.time = time;
	}
	
	public String toString()
	{
		return String.format("Objects: %s, Events: %s, Time: %d", objects.toString(), events.toString(), time);
	}

	/**
	 * Convenient Class for building immutable displays, will be obtained from State.builder()
	 * Needs adder methods.
	 * @author Rajan Troll
	 *
	 */
	public static class Builder
	{
		public Collection<? extends ReadableEvent> events;
		public Collection<? extends ReadableObject> objects;
		public double width, height;
		public long time;
		public Bounds bounds;
		
		public State build()
		{
			return new State(events, objects, width, height, bounds, time);
		}
	}
}
