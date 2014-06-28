package objects;

import engine.State;

public abstract class MovingObject extends GameObject implements Velocity
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
	
		
	
	public double getDX()
	{
		return dx;
	}
	
	public double getDY()
	{
		return dy;
	}
}
