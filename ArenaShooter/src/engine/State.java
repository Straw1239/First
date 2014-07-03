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

import objects.ObjectDataHolder;
import objects.events.EventDataHolder;
import player.PlayerDataHolder;
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
public class State implements Externalizable
{
	public final ImmutableCollection<? extends EventDataHolder> events;
	public final ImmutableCollection<? extends ObjectDataHolder> objects;
	public final PlayerDataHolder player;
	public final Bounds gameBounds;
	public final double width, height;
	public final long time;
	
	
	public State(PlayerDataHolder player, Iterable<? extends EventDataHolder> events, Iterable<? extends ObjectDataHolder> objects, double width, double height, Bounds bounds, long time)
	{
		this.events = ImmutableList.copyOf(Utils.stream(events).map(e -> (EventDataHolder) e.clone()).iterator());
		this.objects = ImmutableList.copyOf(Utils.stream(objects).map(e -> (ObjectDataHolder) e.clone()).iterator());
		this.player = Cloner.clone(player);
		this.gameBounds = bounds;
		this.width = width;
		this.height = height;
		this.time = time;
	}
	
	private State(SerializationProxy proxy)
	{
		this.events = proxy.events;
		this.objects = proxy.objects;
		this.width = proxy.width;
		this.height = proxy.width;
		this.gameBounds = proxy.bounds;
		this.player = proxy.player;
		this.time = proxy.time;
	}
	
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
	{
		throw new IOException("Use Proxy");
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException
	{
		throw new IOException("Use Proxy");
	}
	
	private Object writeReplace() throws ObjectStreamException
	{
		return new SerializationProxy(this);
	}
	
	
	public String toString()
	{
		return String.format("Objects: %s, Events: %s, Time: %d", objects.toString(), events.toString(), time);
	}
	
	
	private static class SerializationProxy implements Externalizable
	{
		double width, height;
		long time;
		ImmutableCollection<? extends ObjectDataHolder> objects;
		ImmutableCollection<? extends EventDataHolder> events;
		Bounds bounds;
		PlayerDataHolder player;
		
		public SerializationProxy(State state)
		{
			this.width = state.width;
			this.height = state.height;
			this.time = state.time;
			this.bounds = state.gameBounds;
			this.objects = state.objects;
			this.player = state.player;
			this.events = state.events;
		}
		
		public SerializationProxy()
		{
			
		}

		@Override
		public void writeExternal(ObjectOutput out) throws IOException
		{
			out.writeDouble(width);
			out.writeDouble(height);
			out.writeLong(time);
			out.writeObject(player);
			out.writeObject(events);
			out.writeObject(objects);
			out.writeObject(bounds);	
			
		}

		@Override
		public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
		{
			width = in.readDouble();
			height = in.readDouble();
			time = in.readLong();
			player = (PlayerDataHolder) in.readObject();
			events = (ImmutableCollection<? extends EventDataHolder>) in.readObject();
			objects = (ImmutableCollection<? extends ObjectDataHolder>) in.readObject();
			bounds = (Bounds) in.readObject();
			
		}
		
		private Object readResolve() throws ObjectStreamException
		{
			 return new State(this);
		}
		
	}

	/**
	 * Convenient Class for building immutable displays, will be obtained from State.builder()
	 * Needs adder methods.
	 * @author Rajan Troll
	 *
	 */
	public static class Builder
	{
		public Collection<? extends EventDataHolder> events;
		public Collection<? extends ObjectDataHolder> objects;
		public double width, height;
		public long time;
		public Bounds bounds;
		public PlayerDataHolder player;
		
		public State build()
		{
			return new State(player, events, objects, width, height, bounds, time);
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		Engine e = new Engine(1, 1);
		State s = e.getState();
		ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(new File("serial.dat")));
		os.writeObject(s);
		os.close();
		ObjectInputStream is = new ObjectInputStream(new FileInputStream(new File("serial.dat")));
		State test = (State) is.readObject();
		System.out.println(test);
		is.close();
	}
}
