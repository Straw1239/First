package objects;

import utils.Vector;

public interface Bounds
{
	public boolean contains(double x, double y);
	
	public default boolean contains(Vector v)
	{
		return contains(v.x, v.y);
	}
	
	public default Vector center()
	{
		return new Vector(centerX(), centerY());
	}
	
	public double centerX();
	
	public double centerY();
	
	public double distanceAtAngle(double angle);
	
	public boolean intersects(Bounds b);
	
	public boolean intersectsCircle(Circle circle);
	
	public boolean intersectsRectangle(Rectangle r);
	
	public static final Bounds NONE = new Bounds()
	{
		@Override
		public boolean contains(double x, double y)
		{
			return false;
		}

		@Override
		public double centerX()
		{
			return Double.NaN;
		}

		@Override
		public double centerY()
		{
			return Double.NaN;
		}

		@Override
		public double distanceAtAngle(double angle)
		{
			return 0;
		}

		@Override
		public boolean intersects(Bounds b)
		{
			return false;
		}

		@Override
		public boolean intersectsCircle(Circle circle)
		{
			return false;
		}

		@Override
		public boolean intersectsRectangle(Rectangle r)
		{
			return false;
		}
	};
	
	
	
	
}
