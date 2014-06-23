package bounds;

import utils.Utils;

public interface Circle extends Bounds
{
	public static Circle of(double x, double y, double radius)
	{
		return new Circle()
		{
			@Override
			public double centerX()
			{
				return x;
			}

			@Override
			public double centerY()
			{
				return y;
			}
			
			@Override
			public double radius()
			{
				return radius;
			}
		};
	}
	
	public default boolean isContainedByCircle(Circle c)
	{
		return c.containsCircle(this);
	}
	
	public default boolean isContainedBy(Bounds b)
	{
		return b.containsCircle(this);
	}
	
	public default boolean isContainedByRectangle(Rectangle r)
	{
		return r.containsCircle(this);
	}
	
	public default boolean contains(Bounds b)
	{
		return b.isContainedByCircle(this);
	}
	
	public default boolean containsCircle(Circle c)
	{
		return Utils.distanceSquared(centerX(), centerY(), c.centerX(), c.centerY()) <= Math.pow(radius() - c.radius(), 2);
	}
	
	public default boolean containsRectangle(Rectangle r)
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public default boolean contains(double x, double y)
	{
		return Utils.distance(x, y, centerX(), centerY()) <= radius();
	}

	@Override
	public default double distanceAtAngle(double radians)
	{
		return radius();
	}

	@Override
	public default boolean intersects(Bounds b)
	{
		return b.intersectsCircle(this);
	}

	@Override
	public default boolean intersectsCircle(Circle other)
	{
		return Utils.distance(centerX(), centerY(), other.centerX(), other.centerY()) < (radius() + other.radius());
	}
	
	@Override
	public default boolean intersectsRectangle(Rectangle other)
	{
		return other.intersectsCircle(this);
	}
	
	public double radius();

}
