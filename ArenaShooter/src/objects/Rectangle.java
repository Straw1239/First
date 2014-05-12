package objects;

import javafx.scene.canvas.GraphicsContext;
import utils.Utils;
import utils.Vector;

public abstract class Rectangle implements Bounds
{
	protected double width, height;
	
	protected Rectangle(double width, double height)
	{
		this.width = width;
		this.height = height;
	}
	@Override
	public boolean contains(double x, double y)
	{
		return Utils.isInRange(x, getX(), getX() + width) && Utils.isInRange(y, getY(), getY() + height);
	}

	@Override
	public Vector center()
	{
		return new Vector(centerX(), centerY());
	}

	@Override
	public double distanceAtAngle(double angle)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean intersects(Bounds b)
	{
		return b.intersectsRectangle(this);
	}

	@Override
	public boolean intersectsCircle(Circle circle)
	{
		double x = Math.abs(centerX() - circle.centerX());
		double y = Math.abs(centerY() - circle.centerY());
		double xapo = width / 2;
		double yapo = height / 2;
		
		if(x > (xapo + circle.radius())) return false;
		if(y > (yapo + circle.radius())) return false;
		
		if(x <= xapo || y <= yapo) return true;
		
		double cornerDistanceSquared = Math.pow(x - xapo, 2) + Math.pow(y - yapo, 2);
		return cornerDistanceSquared <= Math.pow(circle.radius(), 2);
	}

	@Override
	public boolean intersectsRectangle(Rectangle r)
	{
		return contains(r.getX(), r.getY()) || r.contains(getX(), getY());
	}
	
	public abstract double getX(); 
	public abstract double getY();
	

	@Override
	public double centerX()
	{
		return getX() + width / 2;
	}

	@Override
	public double centerY()
	{
		return getY() + height / 2;
	}
	
	public static Rectangle of(double x, double y, double width, double height)
	{
		return new Rectangle(width, height)
		{
			@Override
			public double getX()
			{
				return x;
			}

			@Override
			public double getY()
			{
				return y;
			}
			
		};
	}
	
	public void stroke(GraphicsContext g)
	{
		g.strokeRect(getX(), getY(), width, height);
	}
	
	public void fill(GraphicsContext g)
	{
		g.fillRect(getX(), getX(), width, height);
	}

}
