package critters;

//Cameron Wallace
import java.awt.Color;

public class Husky_wallace extends Critter {

	private int count = 0;
	private int wallcount = 0;
	
	public Husky_wallace () {
	
	}

	@Override
	public Action getMove(CritterInfo info) {
		count++;
		/****************INFECT****************/
		if (info.getFront() == Neighbor.OTHER) {
			return Action.INFECT;
		}
		/****************INFECT****************/
		/**************OFFTHEWALL**************/
		if (wallcount > 100 && info.getFront() == Neighbor.WALL) {
			return Action.RIGHT;
		}
		if (wallcount > 100 && info.getDirection() == Direction.NORTH) {
			return Action.HOP;
		}
		if (wallcount > 100 && info.getDirection() == Direction.EAST) {
			return Action.HOP;
		}
		if (wallcount > 100 && info.getDirection() == Direction.WEST) {
			return Action.HOP;
		}
		if (wallcount > 100 && info.getDirection() == Direction.SOUTH) {
			return Action.HOP;
		}
		
		/**************OFFTHEWALL**************/
		/*****************TURN*****************/
		if (info.getRight() == Neighbor.OTHER) {
			return Action.RIGHT;
		}
		if (info.getLeft() == Neighbor.OTHER) {
			return Action.LEFT;
		}
		/*****************TURN*****************/
		/***************OBSTACLES**************/
		if (info.getFront() == Neighbor.SAME) {
			return Action.RIGHT;
		}
		if (info.getFront() == Neighbor.WALL) {
			return Action.RIGHT;
		}
		if (info.getLeft() == Neighbor.WALL) {
			wallcount++;
			return Action.HOP;
		}
		if (info.getFront() == Neighbor.SAME && info.getRight() == Neighbor.SAME) {
			return Action.LEFT;
		}
		if (info.getFront() == Neighbor.SAME && info.getLeft() == Neighbor.SAME) {
			return Action.RIGHT;
		}
		/***************OBSTACLES**************/
		else {
			return Action.HOP;
		}
	}
	
	@Override
	public String toString() {
		return("@");
	}
	
	@Override
		public Color getColor() {
			return new Color(210,	105	,30	);
		}
}