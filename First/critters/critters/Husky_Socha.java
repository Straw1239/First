package critters;

import java.awt.Color;


public class Husky_Socha extends Critter {

	public Husky_Socha()
	{
		
	}
	
	public Action getMove(CritterInfo info)
	{
		if (info.getFront() == Neighbor.OTHER)
			return Action.INFECT;
		else if (info.getLeft() == Neighbor.OTHER)
			return Action.LEFT;
		else if (info.getRight() == Neighbor.OTHER)
			return Action.RIGHT;
		else if (info.getFront() == Neighbor.EMPTY)
			return Action.HOP;
		else
			return Action.RIGHT;
	}
	
	public Color getColor()
	{
		return Color.PINK;
	}
	
	public String toString()
	{
		return "+";
	}
}
