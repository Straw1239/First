package gandalf;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import critters.Critter;
import critters.CritterInfo;

public class Gandalf extends Critter
{
	private static String symbol = "G";
	
	private Point location = new Point(0, 0);
	
	
	
	public Gandalf()
	{
		
	}
	
	public Critter.Action getMove(CritterInfo info)
	{
		throw new InternalError(new UnsupportedOperationException());
	}
	
	public Color getColor()
	{
		return Color.GRAY;
	}
	
	public String toString()
	{
		return symbol;
	}
	
	private Map<Point, Neighbor> getAllAdjacent(CritterInfo info)
	{
		Map<Point, Neighbor> data = new HashMap<>();
		Neighbor a = info.getFront();
		if (a == Neighbor.OTHER || a == Neighbor.SAME)
		{
			data.put(location.shift(direction(info.getDirection())), a);
		}
		return data;
	}
	
	private Point direction(Direction d)
	{
		switch (d)
		{
		case EAST:
			return new Point(1, 0);
		case NORTH:
			return new Point(0, 1);
		case SOUTH:
			return new Point(0, -1);
		case WEST:
			return new Point(-1, 0);
		default:
			throw new RuntimeException("Missing enum label");
		}
	}
}
