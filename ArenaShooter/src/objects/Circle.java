package objects;

import utils.Utils;

public abstract class Circle implements Bounds
{
	protected double radius;
	
	protected Circle(double radius)
	{
		this.radius = radius;
	}
	
	public static Circle of(double x, double y, double radius)
	{
		return new Circle(radius)
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
		};
	}
	@Override
	public boolean contains(double x, double y)
	{
		return Utils.distance(x, y, centerX(), centerY()) <= radius;
	}

	@Override
	public double distanceAtAngle(double radians)
	{
		return radius;
	}

	@Override
	public boolean intersects(Bounds b)
	{
		return b.intersectsCircle(this);
	}

	@Override
	public boolean intersectsCircle(Circle other)
	{
		return Utils.distance(centerX(), centerY(), other.centerX(), other.centerY()) < (radius + other.radius);
	}
	
	public boolean intersectsRectangle(Rectangle other)
	{
		return other.intersectsCircle(this);
	}
	
	public double radius()
	{
		return radius;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(radius);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(centerX());
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(centerY());
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof Circle))
		{
			return false;
		}
		Circle other = (Circle) obj;
		if (Double.doubleToLongBits(radius) != Double
				.doubleToLongBits(other.radius))
		{
			return false;
		}
		if (Double.doubleToLongBits(centerX()) != Double
				.doubleToLongBits(other.centerX()))
		{
			return false;
		}
		if (Double.doubleToLongBits(centerY()) != Double
				.doubleToLongBits(centerY()))
		{
			return false;
		}
		
		return true;
	}

}
