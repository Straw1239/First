package critters;

import java.awt.Color;

public class Husky_Karcher1 extends Critter{

	@Override
	public Color getColor() {
		return Color.YELLOW;
	}

	@Override
	public String toString() {
		return "[]";
	}

	@Override
	public Action getMove(CritterInfo info) {
		if (info.getFront()==Neighbor.OTHER)
			return Action.INFECT;
		else 
			return Action.LEFT;
	}

}
