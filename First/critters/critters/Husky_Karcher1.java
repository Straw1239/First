package critters;

import java.awt.Color;

public class Husky_Karcher1 extends Critter{

	public Color getColor() {
		return Color.YELLOW;
	}

	public String toString() {
		return "[]";
	}

	public Action getMove(CritterInfo info) {
		if (info.getFront()==Neighbor.OTHER)
			return Action.INFECT;
		else 
			return Action.LEFT;
	}

}
