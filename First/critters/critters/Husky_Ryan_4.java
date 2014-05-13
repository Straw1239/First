package critters;


//Ryan Townsend
//eldar825@gmail.com
import java.awt.Color;
public class Husky_Ryan_4 extends Critter
{
	private boolean buddy;
	private boolean enemy;
	public Husky_Ryan_4()
	{
		buddy = false;
	}
	@Override
	public Color getColor()
	{
		return new Color(255, 0, 255);
	}
	@Override
	public String toString()
	{
		if(buddy == true) return ":D";
		else return "D:";
	}
	@Override
	public Action getMove(CritterInfo info)
	{
		martialLaw(info);
		if(enemy == true)
		{
			if(info.getFront() == Critter.Neighbor.OTHER) return Action.INFECT;
			else if(info.getLeft() == Critter.Neighbor.OTHER) return Action.LEFT;
			else if(info.getRight() == Critter.Neighbor.OTHER) return Action.RIGHT;
			else return Action.HOP;
		}
		else
		{
			if(info.getFront() == Critter.Neighbor.OTHER) return Action.INFECT;
			else if(info.getFront() == Critter.Neighbor.WALL) return Action.RIGHT;
			else if(info.getFront() == Critter.Neighbor.SAME) return Action.RIGHT;
			else return Action.HOP;
		}
	}
	private void buddySystem(CritterInfo info)
	{
		if(info.getFront() == Critter.Neighbor.SAME 
		|| info.getRight() == Critter.Neighbor.SAME 
		|| info.getLeft() == Critter.Neighbor.SAME 
		|| info.getBack() == Critter.Neighbor.SAME)
			buddy = true;
		else buddy = false;
	}
	private void martialLaw(CritterInfo info)
	{
		if(info.getFront() == Critter.Neighbor.OTHER 
		|| info.getRight() == Critter.Neighbor.OTHER 
		|| info.getLeft() == Critter.Neighbor.OTHER 
		|| info.getBack() == Critter.Neighbor.OTHER)
			enemy = true;
		else enemy = false;
	}
}
