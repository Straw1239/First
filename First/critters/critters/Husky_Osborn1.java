package critters;



import java.awt.Color;


public class Husky_Osborn1 extends Critter {

	private int step;
	private Action next;
	private boolean updated;
	
	public Husky_Osborn1() {
		this.updated = false;
	}
	
	@Override
	public Action getMove(CritterInfo info) {
		this.step++;
		if (info.getFront() == Neighbor.OTHER) {
			return Action.INFECT;
	    } if (this.step >= 100) {
	    	if (info.getDirection() == Direction.NORTH || info.getDirection() == Direction.WEST) {
	    		return Action.LEFT;
	    	} if (info.getDirection() == Direction.EAST) {
	    		return Action.RIGHT;
	    	} if (info.getFront() == Neighbor.EMPTY) {
	    		return Action.HOP;
	    	}
	    }
		    if (this.next == Action.HOP) {
		    	this.next = Action.INFECT;
		    	return Action.HOP;
		    } 
		    if (this.step < 100) {
			    if ((info.getDirection() == Direction.NORTH || info.getDirection() == Direction.WEST) && 
			    		(info.getFront() != Neighbor.WALL && info.getFront() != Neighbor.SAME)) {
			    	return Action.HOP;
			    } if (info.getDirection() == Direction.NORTH) {
			    	if (!(info.getLeft() == Neighbor.WALL || info.getLeft() == Neighbor.SAME)) {
			    		return Action.LEFT;
			    	}
			    } if (info.getDirection() == Direction.WEST) {
			    	if (!(info.getRight() == Neighbor.WALL || info.getRight() == Neighbor.SAME)) {
			    		return Action.RIGHT;
			    	}
			    } if (info.getDirection() == Direction.SOUTH) {
			    	if (info.getRight() != Neighbor.SAME && info.getRight() != Neighbor.WALL) {
			    		return Action.RIGHT;
			    	} else if (info.getBack() != Neighbor.SAME && info.getBack() != Neighbor.WALL) {
			    		return Action.LEFT;
			    	}
			    } if (info.getDirection() == Direction.EAST) {
			    	if (info.getLeft() != Neighbor.SAME && info.getLeft() != Neighbor.WALL) {
			    		return Action.LEFT;
			    	} else if (info.getBack() != Neighbor.SAME && info.getBack() != Neighbor.WALL) {
			    		return Action.RIGHT;
			    	}
			    } 
		    } if (!this.updated && info.getDirection() == Direction.SOUTH && info.getRight() == Neighbor.WALL && info.getLeft() == Neighbor.EMPTY) {
		    	this.updated = true;
		    	this.next = Action.HOP;
		    	return Action.LEFT;
		    } if (!this.updated && info.getDirection() == Direction.EAST && info.getLeft() == Neighbor.WALL && info.getRight() == Neighbor.EMPTY) {
		    	this.updated = true;
		    	this.next = Action.HOP;
		    	return Action.RIGHT;
		    } if (info.getLeft() != Neighbor.SAME && info.getLeft() != Neighbor.WALL) {
	            return Action.LEFT;
	        } if (info.getRight() != Neighbor.SAME && info.getRight() != Neighbor.WALL) {
	        	return Action.RIGHT;
	        } if ((info.getFront() == Neighbor.WALL || info.getFront() == Neighbor.SAME) && (info.getBack() == Neighbor.EMPTY || info.getBack() == Neighbor.OTHER)) {
	        	return Action.RIGHT;
	        } return Action.INFECT;
    }
	
	@Override
	public Color getColor() {
		return Color.ORANGE;
	}
	
	@Override
	public String toString() {
		String spaces = "";
		for (int i = 1; i <= this.step; i++) {
			spaces += " ";
		} return spaces + "o";
	}
	
}
