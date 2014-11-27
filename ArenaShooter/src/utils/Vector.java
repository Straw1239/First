package utils;


import static java.lang.Math.cos;
import static java.lang.Math.sin;
import objects.Locatable;

public final class Vector implements Locatable
{
	public static final Vector ZERO = new Vector(0, 0);
	public final double x, y;
	
	public Vector(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector(Locatable l)
	{
		this(l.getX(), l.getY());
	}

	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public double getLength()
	{
		return Math.sqrt(x * x + y * y);
	}
	
	public double lengthSquared()
	{
		return x * x + y * y;
	}
	
	public Vector normalized()
	{
		return normalized(1);
	}
	
	public Vector add(Vector v)
	{
		return new Vector(x + v.x, y + v.y);
	}
	
	public Vector sub(Vector v)
	{
		return new Vector(x - v.x, y - v.y);
	}
	
	public Vector scale(double r)
	{
		return new Vector(x * r, y * r);
	}
	
	public Vector perpindicular()
	{
		return new Vector(-y, x);
	}
	
	public Vector scaleAdd(double scale, Vector other)
	{
		return new Vector(x * scale + other.x, y * scale + other.y);
	}
	
	public Vector addScaled(Vector other, double scale)
	{
		return new Vector(x + scale * other.x, y + scale * other.y);
	}
	
	public Vector reflect(Vector normal)
	{
		return normal.scaleAdd(-2 * dotProduct(this, normal) / normal.lengthSquared(), this);
	}
	
	public Vector inverse()
	{
		return new Vector(-x, -y);
	}
	
	@Override
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}
	
	public static double dotProduct(Vector vec1, Vector vec2)
	{
		return vec1.x * vec2.x + vec1.y * vec2.y;
	}
	
	public static Vector fromPolar(double radius, double radians)
	{
		return new Vector(cos(radians) * radius, sin(radians) * radius);
	}

	public Vector normalized(double d)
	{
		return scale(d / getLength());
	}

}
