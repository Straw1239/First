package objects;

import utils.Utils;

public abstract class CircleCollider extends GameObject 
{

	protected double radius;
	
	protected CircleCollider(double x, double y,  double radius) 
	{
		this(x,y);
		this.radius = radius;
	}
	
	protected CircleCollider(double x, double y)
	{
		super(x,y);
	}

	@Override
	public void update() 
	{
		
	}
	
	public double getRadius()
	{
		return radius;
	}

	@Override
	public boolean collidesWith(GameObject entity) 
	{
		if(entity instanceof CircleCollider)
		{
			return Utils.distance(this, entity) < (radius + ((CircleCollider)entity).getRadius());
		}
		else return entity.collidesWith(this);
	}

}
