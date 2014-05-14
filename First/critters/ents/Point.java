package ents;

public class Point
{
	public static final Point ORIGIN = new Point(0, 0);
	
	public final int x, y;
	
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}
