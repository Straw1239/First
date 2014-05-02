package gandalf;

public class Point extends java.awt.Point
{
	public Point(int i, int j) 
	{
		super(i, j);
	}

	public Point shift(int dx, int dy)
	{
		return new Point(x + dx, y + dy);
	}
	
	public Point shift(Point vector)
	{
		return shift(vector.x, vector.y);
	}
	
	public void move(Point vector)
	{
		x += vector.x;
		y += vector.y;
	}
	
	public Point reflect()
	{
		return new Point(-x, -y);
	}
}
