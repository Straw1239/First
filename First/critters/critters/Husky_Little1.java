package critters;



import java.awt.Color;

public class Husky_Little1 extends Critter
{
	public Husky_Little1()
	{
	}
	
	public Action getMove(CritterInfo info)
	{
		if (info.getFront() == Critter.Neighbor.OTHER)
			return Action.INFECT;
		
		if (info.getFront() == Critter.Neighbor.SAME)
		{
			if (info.getDirection() == Critter.Direction.WEST || info.getDirection() == Critter.Direction.NORTH)
				return Action.LEFT;
			else
				return Action.RIGHT;
		}
		else if (info.getLeft() == Critter.Neighbor.OTHER)
			return Action.LEFT;
		else if (info.getRight() == Critter.Neighbor.OTHER)
			return Action.RIGHT;
		else if (info.getFront() == Critter.Neighbor.WALL)
			return Action.LEFT;
		else
			return Action.HOP;
	}
	
	public Color getColor()
	{
		return Color.ORANGE;
	}
	
	public String toString()
	{
		return "#";
	}
}
