package objects;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import utils.Vector;
import engine.State;

public abstract class MovingObject extends GameObject implements MoverDataHolder
{
	protected double dx, dy;
	
	protected MovingObject(double x, double y, double dx, double dy, Faction faction)
	{
		super(x, y, faction);
		this.dx = dx;
		this.dy = dy;
	}
	
	protected MovingObject(double x, double y)
	{
		super(x, y);
		dx = 0;
		dy = 0;
	}

	protected MovingObject(double x, double y, Faction faction)
	{
		this(x, y);
		this.faction = faction;
	}

	@Override
	public void update(State d)
	{
		x += getDX();
		y += getDY();
		if(!d.gameBounds.contains(bounds()))
		{
			onWallHit(d);
		}
	}
	
	protected void onWallHit(State s)
	{
		x -= getDX();
		y -= getDY();
		dx = 0;
		dy = 0;
	}
	
	public void writeExternal(ObjectOutput out) throws IOException
	{
		super.writeExternal(out);
		out.writeDouble(dx);
		out.writeDouble(dy);
	}
	
	public void readExternal(ObjectInput in) throws ClassNotFoundException, IOException
	{
		super.readExternal(in);
		dx = in.readDouble();
		dy = in.readDouble();
	}
	
		
	
	public double getDX()
	{
		return dx;
	}
	
	public double getDY()
	{
		return dy;
	}
	
	public static final int ACCELERATE = 3;
	public static final int SETVELOCITY = 4;
	
	public boolean supportsOperation(int code)
	{
		if(super.supportsOperation(code)) return true;
		switch(code)
		{
		case ACCELERATE:
		case SETVELOCITY:
			return true;
		}
		return false;
	}
	
	protected void handleChange(Change change, GameObject source)
	{
		switch(change.code)
		{
		case ACCELERATE:
			Vector v = (Vector) change.data;
			dx += v.x;
			dy += v.y;
			break;
		case SETVELOCITY:
			Vector vec = (Vector) change.data;
			dx = vec.x;
			dy = vec.y;
			break;
		default:
			super.handleChange(change, source);
		}
	}
}