package critters;

import java.awt.Color;


public class Husky_Socha extends Critter {

	public Husky_Socha()
	{
		
	}
	
	@Override
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
	
	@Override
	public Color getColor()
	{
		return Color.PINK;
	}
	
	@Override
	public String toString()
	{
		return "+";
	}
}
