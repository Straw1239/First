package critters;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Husky_Osborn2 extends Critter {

	public static ArrayList<Husky_Osborn2> me = new ArrayList<Husky_Osborn2>();
	public static final int wait = 2000;
	public static Random r = new Random();
	public static Husky_Osborn2 lead;
	
	private int step;
	// private Action next;
	private boolean updated;
	private int corner;
	
	public Husky_Osborn2() {
		me.add(this);
		this.updated = false;
		this.corner = 0;
		this.step = 0;
		this.corner = me.get(0).getCorner();
		this.step = 0;
		lead = me.get(0);
	}
	
	public Action getMove(CritterInfo info) {
		if (this.step == 0) {
			this.step = this.overallStep();
			this.corner = lead.getCorner();
		}
		this.step++;
		if (this.overallStep() % wait == 0) {
			this.updated = false;
			this.corner++;
		} this.corner = lead.getCorner();
		
		if (info.getFront() == Neighbor.OTHER) {
			return Action.INFECT;
	    } 
		
		//	if (this.next == Action.HOP) {
		//		this.next = Action.LEFT;
		//		return Action.HOP;
		//	}
		
		else if (this.overallStep() < wait / 4) {
			return this.West(info);
		}
		
		else if (this.corner % 4 == 3) {
	    	return this.NorthEast(info);
		}
		
		else if (this.corner % 4 == 2) {
			return this.SouthEast(info);
		}
		
		else if (this.corner % 4 == 1) {
	    	return this.SouthWest(info);
		} 
        
		// else if (this.corner % 4 == 0) {
		else {
		    return this.NorthWest(info);
		} 
    }
	
	public int getCorner() {
		return this.corner;
	}
	
	public int getStep() {
		return this.step;
	}
	
	private int overallStep() {
		int s = me.get(0).getStep();
		for (int i = 1; i <= me.size() - 1; i++) {
			if (me.get(i).getStep() > s) {
				s = me.get(i).getStep();
				lead = me.get(i);
			}
		} return s;
	}
	
/*	private Action trap(CritterInfo info) {
		if (info.getRight() == Neighbor.EMPTY || info.getRight() == Neighbor.OTHER) {
            return Action.RIGHT;
        } else if (info.getLeft() == Neighbor.EMPTY || info.getLeft() == Neighbor.OTHER) {
            return Action.LEFT;
        } else if (info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL) {
        	return Action.RIGHT;
        } return Action.INFECT;
	} */
	
	private Action West(CritterInfo info) {
		if (info.getDirection() == Direction.NORTH && (info.getLeft() == Neighbor.EMPTY || info.getLeft() == Neighbor.OTHER)) {
			return Action.LEFT;
		}
		else if (info.getDirection() == Direction.EAST && (info.getLeft() == Neighbor.EMPTY || info.getLeft() == Neighbor.OTHER)) {
			return Action.LEFT;
		}
		else if (info.getDirection() == Direction.SOUTH && (info.getRight() == Neighbor.EMPTY || info.getRight() == Neighbor.OTHER)) {
			return Action.RIGHT;
		}
		else if (info.getDirection() == Direction.WEST && info.getFront() == Neighbor.EMPTY) {
			return Action.HOP;
		}
		else if (info.getLeft() != Neighbor.SAME && info.getLeft() != Neighbor.WALL) {
	        return Action.LEFT;
	    }
		else if (info.getRight() != Neighbor.SAME && info.getRight() != Neighbor.WALL) {
	       	return Action.RIGHT;
	    }
		else if ((info.getFront() == Neighbor.WALL || info.getFront() == Neighbor.SAME) && (info.getBack() == Neighbor.EMPTY || info.getBack() == Neighbor.OTHER)) {
	       	return Action.RIGHT;
	    } 
		else if (info.getFront() == Neighbor.SAME && (info.getDirection() == Direction.SOUTH || info.getDirection() == Direction.WEST)) {
			return Action.LEFT;
		}
		else if (info.getFront() == Neighbor.SAME) {
			return Action.RIGHT;
		} return Action.INFECT;
	}
	
	private Action NorthWest(CritterInfo info) {
		if ((info.getDirection() == Direction.NORTH || info.getDirection() == Direction.WEST) && info.getFront() == Neighbor.EMPTY) {
	    	return Action.HOP;
	    } 
		else if (info.getDirection() == Direction.NORTH && (info.getLeft() == Neighbor.EMPTY || info.getLeft() == Neighbor.OTHER)) {
	    	return Action.LEFT;
	    }
		else if (info.getDirection() == Direction.WEST && (info.getRight() == Neighbor.EMPTY || info.getRight() == Neighbor.OTHER)) {
	    	return Action.RIGHT;
	    }
		else if (info.getDirection() == Direction.SOUTH) {
	    	if (info.getRight() != Neighbor.SAME && info.getRight() != Neighbor.WALL) {
	    		return Action.RIGHT;
	    	} else if (info.getBack() != Neighbor.SAME && info.getBack() != Neighbor.WALL) {
	    		return Action.LEFT;
	    	}
	    }
		else if (info.getDirection() == Direction.EAST) {
	    	if (info.getLeft() != Neighbor.SAME && info.getLeft() != Neighbor.WALL) {
	    		return Action.LEFT;
	    	} else if (info.getBack() != Neighbor.SAME && info.getBack() != Neighbor.WALL) {
	    		return Action.RIGHT;
	    	}
	    }
		if (!this.updated && info.getDirection() == Direction.EAST && info.getBack() == Neighbor.WALL && info.getFront() == Neighbor.EMPTY) {
		   	this.updated = true;
		   	// this.next = Action.HOP;
		   	return Action.HOP;
		}
		else if (!this.updated && info.getDirection() == Direction.SOUTH && info.getBack() == Neighbor.WALL && info.getFront() == Neighbor.EMPTY) {
		   	this.updated = true;
		   	// this.next = Action.HOP;
		   	return Action.HOP;
		}
		else if (info.getLeft() != Neighbor.SAME && info.getLeft() != Neighbor.WALL) {
	        return Action.LEFT;
	    }
		else if (info.getRight() != Neighbor.SAME && info.getRight() != Neighbor.WALL) {
	       	return Action.RIGHT;
	    }
		else if ((info.getFront() == Neighbor.WALL || info.getFront() == Neighbor.SAME) && (info.getBack() == Neighbor.EMPTY || info.getBack() == Neighbor.OTHER)) {
	       	return Action.RIGHT;
	    } 
		else if (info.getFront() == Neighbor.SAME && (info.getDirection() == Direction.SOUTH || info.getDirection() == Direction.WEST)) {
			return Action.LEFT;
		}
		else if (info.getFront() == Neighbor.SAME) {
			return Action.RIGHT;
		} return Action.INFECT;
	}
	
	private Action NorthEast(CritterInfo info) {
		if ((info.getDirection() == Direction.EAST || info.getDirection() == Direction.NORTH) && info.getFront() == Neighbor.EMPTY) {
	    	return Action.HOP;
	    } 
		else if (info.getDirection() == Direction.EAST && (info.getLeft() == Neighbor.EMPTY || info.getLeft() == Neighbor.OTHER)) {
	    	return Action.LEFT;
	    }
		else if (info.getDirection() == Direction.NORTH && (info.getRight() == Neighbor.EMPTY || info.getRight() == Neighbor.OTHER)) {
	    	return Action.RIGHT;
	    }
		else if (info.getDirection() == Direction.WEST) {
	    	if (info.getRight() != Neighbor.SAME && info.getRight() != Neighbor.WALL) {
	    		return Action.RIGHT;
	    	} else if (info.getBack() != Neighbor.SAME && info.getBack() != Neighbor.WALL) {
	    		return Action.LEFT;
	    	}
	    }
		else if (info.getDirection() == Direction.SOUTH) {
	    	if (info.getLeft() != Neighbor.SAME && info.getLeft() != Neighbor.WALL) {
	    		return Action.LEFT;
	    	} else if (info.getBack() != Neighbor.SAME && info.getBack() != Neighbor.WALL) {
	    		return Action.RIGHT;
	    	}
	    }
		if (!this.updated && info.getDirection() == Direction.SOUTH && info.getBack() == Neighbor.WALL && info.getFront() == Neighbor.EMPTY) {
		   	this.updated = true;
		   	// this.next = Action.HOP;
		   	return Action.HOP;
		}
		else if (!this.updated && info.getDirection() == Direction.WEST && info.getBack() == Neighbor.WALL && info.getFront() == Neighbor.EMPTY) {
		   	this.updated = true;
		   	// this.next = Action.HOP;
		   	return Action.HOP;
		}
		else if (info.getLeft() != Neighbor.SAME && info.getLeft() != Neighbor.WALL) {
	        return Action.LEFT;
	    }
		else if (info.getRight() != Neighbor.SAME && info.getRight() != Neighbor.WALL) {
	       	return Action.RIGHT;
	    }
		else if ((info.getFront() == Neighbor.WALL || info.getFront() == Neighbor.SAME) && (info.getBack() == Neighbor.EMPTY || info.getBack() == Neighbor.OTHER)) {
	       	return Action.RIGHT;
	    } 
		else if (info.getFront() == Neighbor.SAME && (info.getDirection() == Direction.WEST || info.getDirection() == Direction.NORTH)) {
			return Action.LEFT;
		}
		else if (info.getFront() == Neighbor.SAME) {
			return Action.RIGHT;
		} return Action.INFECT;
	}
	
	private Action SouthEast(CritterInfo info) {
		if ((info.getDirection() == Direction.SOUTH || info.getDirection() == Direction.EAST) && info.getFront() == Neighbor.EMPTY) {
	    	return Action.HOP;
	    } 
		else if (info.getDirection() == Direction.SOUTH && (info.getLeft() == Neighbor.EMPTY || info.getLeft() == Neighbor.OTHER)) {
	    	return Action.LEFT;
	    }
		else if (info.getDirection() == Direction.EAST && (info.getRight() == Neighbor.EMPTY || info.getRight() == Neighbor.OTHER)) {
	    	return Action.RIGHT;
	    }
		else if (info.getDirection() == Direction.NORTH) {
	    	if (info.getRight() != Neighbor.SAME && info.getRight() != Neighbor.WALL) {
	    		return Action.RIGHT;
	    	} else if (info.getBack() != Neighbor.SAME && info.getBack() != Neighbor.WALL) {
	    		return Action.LEFT;
	    	}
	    }
		else if (info.getDirection() == Direction.WEST) {
	    	if (info.getLeft() != Neighbor.SAME && info.getLeft() != Neighbor.WALL) {
	    		return Action.LEFT;
	    	} else if (info.getBack() != Neighbor.SAME && info.getBack() != Neighbor.WALL) {
	    		return Action.RIGHT;
	    	}
	    }
		if (!this.updated && info.getDirection() == Direction.WEST && info.getBack() == Neighbor.WALL && info.getFront() == Neighbor.EMPTY) {
		   	this.updated = true;
		   	// this.next = Action.HOP;
		   	return Action.HOP;
		}
		else if (!this.updated && info.getDirection() == Direction.NORTH && info.getBack() == Neighbor.WALL && info.getFront() == Neighbor.EMPTY) {
		   	this.updated = true;
		   	// this.next = Action.HOP;
		   	return Action.HOP;
		}
		else if (info.getLeft() != Neighbor.SAME && info.getLeft() != Neighbor.WALL) {
	        return Action.LEFT;
	    }
		else if (info.getRight() != Neighbor.SAME && info.getRight() != Neighbor.WALL) {
	       	return Action.RIGHT;
	    }
		else if ((info.getFront() == Neighbor.WALL || info.getFront() == Neighbor.SAME) && (info.getBack() == Neighbor.EMPTY || info.getBack() == Neighbor.OTHER)) {
	       	return Action.RIGHT;
	    } 
		else if (info.getFront() == Neighbor.SAME && (info.getDirection() == Direction.NORTH || info.getDirection() == Direction.EAST)) {
			return Action.LEFT;
		}
		else if (info.getFront() == Neighbor.SAME) {
			return Action.RIGHT;
		} return Action.INFECT;
	}
	
	private Action SouthWest(CritterInfo info) {
		if ((info.getDirection() == Direction.WEST || info.getDirection() == Direction.SOUTH) && info.getFront() == Neighbor.EMPTY) {
	    	return Action.HOP;
	    } 
		else if (info.getDirection() == Direction.WEST && (info.getLeft() == Neighbor.EMPTY || info.getLeft() == Neighbor.OTHER)) {
	    	return Action.LEFT;
	    }
		else if (info.getDirection() == Direction.SOUTH && (info.getRight() == Neighbor.EMPTY || info.getRight() == Neighbor.OTHER)) {
	    	return Action.RIGHT;
	    }
		else if (info.getDirection() == Direction.EAST) {
	    	if (info.getRight() != Neighbor.SAME && info.getRight() != Neighbor.WALL) {
	    		return Action.RIGHT;
	    	} else if (info.getBack() != Neighbor.SAME && info.getBack() != Neighbor.WALL) {
	    		return Action.LEFT;
	    	}
	    }
		else if (info.getDirection() == Direction.NORTH) {
	    	if (info.getLeft() != Neighbor.SAME && info.getLeft() != Neighbor.WALL) {
	    		return Action.LEFT;
	    	} else if (info.getBack() != Neighbor.SAME && info.getBack() != Neighbor.WALL) {
	    		return Action.RIGHT;
	    	}
	    }
		if (!this.updated && info.getDirection() == Direction.NORTH && info.getBack() == Neighbor.WALL && info.getFront() == Neighbor.EMPTY) {
		   	this.updated = true;
		   	// this.next = Action.HOP;
		   	return Action.HOP;
		}
		else if (!this.updated && info.getDirection() == Direction.EAST && info.getBack() == Neighbor.WALL && info.getFront() == Neighbor.EMPTY) {
		   	this.updated = true;
		   	// this.next = Action.HOP;
		   	return Action.HOP;
		}
		else if (info.getLeft() != Neighbor.SAME && info.getLeft() != Neighbor.WALL) {
	        return Action.LEFT;
	    }
		else if (info.getRight() != Neighbor.SAME && info.getRight() != Neighbor.WALL) {
	       	return Action.RIGHT;
	    }
		else if ((info.getFront() == Neighbor.WALL || info.getFront() == Neighbor.SAME) && (info.getBack() == Neighbor.EMPTY || info.getBack() == Neighbor.OTHER)) {
	       	return Action.RIGHT;
	    } 
		else if (info.getFront() == Neighbor.SAME && (info.getDirection() == Direction.EAST || info.getDirection() == Direction.SOUTH)) {
			return Action.LEFT;
		}
		else if (info.getFront() == Neighbor.SAME) {
			return Action.RIGHT;
		} return Action.INFECT;
	}
	
	public Color getColor() {
		if (this.step % 2 == 0) {
			return new Color (154, 11, 240);
		} else {
			return new Color (92, 134, 31);
		}
	}
	
	public String toString() {
		/* String spaces = "";
		for (int i = 1; i <= this.step % 60; i++) {
			spaces += "o";
		} return spaces + "o"; */
		return "N";
	}
	
}
