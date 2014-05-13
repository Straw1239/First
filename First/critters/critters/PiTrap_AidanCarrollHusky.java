package critters;


// The PiTrap class


import java.awt.*;
import java.util.Random;

public class PiTrap_AidanCarrollHusky extends Critter {
	int count;
	boolean circle;
	int dir;
	Random rand = new Random();

	public PiTrap_AidanCarrollHusky() {
		count = 0;
		circle = false;
		dir = 0;
	}

	@Override
	public Action getMove(CritterInfo info) {
		count++;
		if (count > 1000) {
			dir = (count / 5000 - 1) % 4;
		}


		/**/
		/**/

		/*if (info.getFront() != Neighbor.OTHER && info.getLeft() != Neighbor.OTHER && info.getRight() != Neighbor.OTHER && info.getBack() != Neighbor.OTHER) {
			if (info.getFront() == Neighbor.WALL) {
				if (Coin()) {
					return Action.LEFT;
				} else {
					return Action.RIGHT;
				}
				//if (info.getFront() != Neighbor.OTHER && info.getLeft() != Neighbor.OTHER && info.getRight() != Neighbor.OTHER && info.getBack() != Neighbor.OTHER && info.getFront() != Neighbor.WALL && info.getLeft() != Neighbor.WALL && info.getRight() != Neighbor.WALL && info.getBack() != Neighbor.WALL) {
			} else {
				return Action.HOP;
			}
		} else*/
		/*if (count > 1000 /*&& info.getFront() == Neighbor.EMPTY && info.getLeft() == Neighbor.EMPTY && info.getRight() == Neighbor.EMPTY && info.getBack() == Neighbor.EMPTY/*) {

			}*/
		if (info.getFront() == Neighbor.OTHER) {
			return Action.INFECT;
		} else if (info.getLeft() == Neighbor.OTHER) {
			return Action.LEFT;
		} else if (info.getRight() == Neighbor.OTHER) {
			return Action.RIGHT;
		} else if (info.getBack() == Neighbor.OTHER) {
			return Action.LEFT;
		} if (count > 1000) { //go North
			if (dir == 0) { //go North
				if (info.getDirection() == Direction.EAST || info.getDirection() == Direction.SOUTH) {
					return Action.LEFT;
				} else if (info.getDirection() == Direction.WEST) {
					return Action.RIGHT;
				} else {
					return Action.HOP;
				}
			} else if (dir == 1) { //go West
				if (info.getDirection() == Direction.NORTH || info.getDirection() == Direction.EAST) {
					return Action.LEFT;
				} else if (info.getDirection() == Direction.SOUTH) {
					return Action.RIGHT;
				} else {
					return Action.HOP;
				}
			}
			else if (dir == 2) { //go South
				if (info.getDirection() == Direction.WEST || info.getDirection() == Direction.NORTH) {
					return Action.LEFT;
				} else if (info.getDirection() == Direction.EAST) {
					return Action.RIGHT;
				} else {
					return Action.HOP;
				}
			}
			else { //go East
				if (info.getDirection() == Direction.SOUTH || info.getDirection() == Direction.WEST) {
					return Action.LEFT;
				} else if (info.getDirection() == Direction.NORTH) {
					return Action.RIGHT;
				} else {
					return Action.HOP;
				}
			}

		} else if (info.getFront() == Neighbor.SAME /**/&& info.getLeft() != Neighbor.SAME && info.getRight() != Neighbor.SAME && info.getBack() != Neighbor.SAME && info.getLeft() != Neighbor.WALL && info.getRight() != Neighbor.WALL && info.getBack() != Neighbor.WALL/**/) {
			if (info.getDirection() == Direction.NORTH || info.getDirection() == Direction.EAST) {
				return Action.LEFT;
			} else {
				return Action.RIGHT;
			}
		} else if (count > 1000 && (info.getLeft() == Neighbor.WALL || info.getRight() == Neighbor.WALL || info.getBack() == Neighbor.WALL)) {
			if (info.getBack() == Neighbor.WALL) {
				if (info.getDirection() == Direction.NORTH) {
					return Action.HOP;
				} else if (info.getDirection() == Direction.WEST) {
					return Action.RIGHT;
				} else if (info.getDirection() == Direction.EAST) {
					return Action.LEFT;
				} else {
					return Action.LEFT;
				}
			} else {
				return Action.HOP;
			}

			/**/
			//more Tur(n)ing Tests...
		} else if (info.getFront() == Neighbor.SAME
				&& info.getLeft() == Neighbor.EMPTY
				&& info.getRight() == Neighbor.SAME
				&& info.getBack() == Neighbor.SAME) {
			return Action.LEFT;

		} else if (info.getFront() == Neighbor.SAME
				&& info.getLeft() == Neighbor.SAME
				&& info.getRight() == Neighbor.SAME
				&& info.getBack() == Neighbor.EMPTY) {
			return Action.LEFT;

		} else if (info.getFront() == Neighbor.SAME
				&& info.getLeft() == Neighbor.SAME
				&& info.getRight() == Neighbor.EMPTY
				&& info.getBack() == Neighbor.SAME) {
			return Action.RIGHT;

		} else if (info.getFront() == Neighbor.SAME
				&& info.getLeft() == Neighbor.EMPTY
				&& info.getRight() == Neighbor.SAME
				&& info.getBack() == Neighbor.EMPTY) {
			return Action.LEFT;

		} else if (info.getFront() == Neighbor.SAME
				&& info.getLeft() == Neighbor.SAME
				&& info.getRight() == Neighbor.EMPTY
				&& info.getBack() == Neighbor.EMPTY) {
			return Action.RIGHT;

		} else if (info.getFront() == Neighbor.EMPTY
				&& info.getLeft() == Neighbor.SAME
				&& info.getRight() == Neighbor.EMPTY
				&& info.getBack() == Neighbor.SAME) {
			if (count < 1000) {
				return Action.INFECT;
			} else {
				return Action.HOP;
			}
		} else if (info.getFront() == Neighbor.EMPTY
				&& info.getLeft() == Neighbor.EMPTY
				&& info.getRight() == Neighbor.SAME
				&& info.getBack() == Neighbor.SAME) {
			if (count < 1000) {
				return Action.INFECT;
			} else {
				return Action.HOP;
			}
		} else if (info.getFront() == Neighbor.SAME
				&& info.getLeft() == Neighbor.EMPTY
				&& info.getRight() == Neighbor.EMPTY
				&& info.getBack() == Neighbor.EMPTY) {
			return Action.LEFT;

		} else if (info.getFront() == Neighbor.EMPTY
				&& info.getLeft() == Neighbor.EMPTY
				&& info.getRight() == Neighbor.SAME
				&& info.getBack() == Neighbor.EMPTY) {
			return Action.LEFT;

		} else if (info.getFront() == Neighbor.EMPTY
				&& info.getLeft() == Neighbor.SAME
				&& info.getRight() == Neighbor.EMPTY
				&& info.getBack() == Neighbor.EMPTY) {
			return Action.RIGHT;

		} else if (info.getFront() == Neighbor.EMPTY
				&& info.getLeft() == Neighbor.EMPTY
				&& info.getRight() == Neighbor.EMPTY
				&& info.getBack() == Neighbor.SAME) {
			if (count < 1000) {
				return Action.INFECT;
			} else {
				return Action.HOP;
			}			
			/**/
		} else if (info.getFront() == Neighbor.WALL || info.getRight() == Neighbor.WALL) {
			return Action.LEFT;
		} else if (info.getLeft() == Neighbor.WALL) {
			return Action.RIGHT;
			/**/

		} else {
			return Action.INFECT;
		}
	}


	public boolean Coin() {
		if (rand.nextInt(2) == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Color getColor() {
		return Color.BLUE;
	}

	@Override
	public String toString() {
		return "π";
	}
}
/*
if (count < 1000) {
	return Action.INFECT;
} else {
	return Action.HOP;
}
 */