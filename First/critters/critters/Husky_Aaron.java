package critters;


//Aaron Vargo

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;

//A not so good husky
public class Husky_Aaron extends Critter {

	private static int turnCount;
	private static ArrayList<Husky_Aaron> huskies = new ArrayList<>();
	private static int huskyCount;
	private int moveCount;
	private int wait;
	private static final int WAIT_TIME = 2;
	private int initialTurn;
	private static int turnsSinceInfect;
	private static final int PASSIVE_TIME = 100;

	public Husky_Aaron() {
		initialTurn = turnCount;
		huskies.add(this);
	}

	@Override
	public Action getMove(CritterInfo info) {
		if(initialTurn + moveCount >= turnCount) {
			newTurn();
		}

		moveCount++;
		if (info.getFront() == Neighbor.OTHER) {
			return Action.INFECT;
		} else if(info.getLeft() == Neighbor.OTHER || info.getRight() == Neighbor.OTHER 
				|| info.getBack() == Neighbor.OTHER) {
			if(info.getFront() == Neighbor.EMPTY) {
				return Action.HOP;
			} else if(info.getLeft() == Neighbor.OTHER) {
				return Action.LEFT;
			} else if(info.getRight() == Neighbor.OTHER) {
				return Action.RIGHT;
			} else {
				if(info.getRight() != Neighbor.WALL) {
					return Action.RIGHT;
				} else return Action.LEFT;
			}
		} else if(turnsSinceInfect < PASSIVE_TIME) {
			if(info.getFront() != Neighbor.EMPTY) {
				if(info.getRight() == Neighbor.EMPTY) {
					return Action.RIGHT;
				} else if (info.getLeft() == Neighbor.EMPTY) {
					return Action.LEFT;
				} else {
					if(info.getRight() != Neighbor.WALL) {
						return Action.RIGHT;
					} else return Action.LEFT;
				}
			} else {
				return Action.INFECT;
			}
		} else {
			if(info.getBack() == Neighbor.SAME && info.getFront() == Neighbor.EMPTY) {
				if(++wait > WAIT_TIME ) {
					wait = 0;
					return Action.HOP;
				} else return Action.INFECT;
			} else if(info.getFront() == Neighbor.EMPTY) {
				return Action.HOP;
			} else if(info.getRight() == Neighbor.SAME) {
				return Action.RIGHT;
			} else if(info.getLeft() == Neighbor.SAME) {
				return Action.LEFT;
			} else {
				if(info.getRight() != Neighbor.WALL) {
					return Action.RIGHT;
				} else return Action.LEFT;
			}
		}
	}

	public static void newTurn() {
		boolean infect = huskyCount != huskies.size();
		LinkedList<Husky_Aaron> removeList = new LinkedList(); 
		for(Husky_Aaron husky: huskies) {
			if(husky.initialTurn + husky.moveCount < turnCount) {
				removeList.add(husky);
				infect = true;
			}
		}
		huskies.removeAll(removeList);
		if(infect && turnsSinceInfect < PASSIVE_TIME) {
			turnsSinceInfect = 0;
		}  else turnsSinceInfect++;
		huskyCount = huskies.size();
		turnCount++;
	}

	@Override
	public String toString() {
		return "A";
	}

	@Override
	public Color getColor() {
		return Color.CYAN;
	}

}