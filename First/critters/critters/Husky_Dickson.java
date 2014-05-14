package critters;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
 
public class Husky_Dickson extends Critter {
	
	public static ArrayList<Husky_Dickson> army = new ArrayList<Husky_Dickson>();

	private Neighbor[] side = new Neighbor[4];

	private int one;
	private int two;
	private int closed;
	private int countMoves;
	private int swarmMovement;
	private int movesSinceCreature;

	private boolean swarm;
	private boolean correct;

	private CritterInfo info;

	public Husky_Dickson() {
		Random random = new Random();
		one = random.nextInt(90);
		two = random.nextInt(70);
		
		correct = true;
		army.add(this);
		swarmMovement = army.get(0).getSwarmMovement();
		swarm = army.get(0).getSwarm();
		closed = 0;
		countMoves = army.get(0).getMoves();
	}

	private static final double GET_THEM = 0.5;

	@Override
	public Action getMove(CritterInfo info) {
		update(info);
		if (swarm) {
			swarmMovement++;
			return hyperMode();
		} 
		else {
			return calmMode();
		}		
	}
	
	public Action calmMode(){
		if (side[0] == Neighbor.OTHER) {
			return Action.INFECT;
		}
		else if (side[1] == Neighbor.OTHER) {
			return Action.LEFT;
		}
		else if (side[3] == Neighbor.OTHER) {
			return Action.RIGHT;
		}
		else if (side[0] == Neighbor.EMPTY && side[3] != Neighbor.SAME && side[1] != Neighbor.SAME && side[2] != Neighbor.SAME) {
			return Action.HOP;
		} else {
			return Action.LEFT;
		}				
	}

	public Action hyperMode() {
		Direction direction = info.getDirection();
		if (side[0] == Neighbor.OTHER) {
			return Action.INFECT;
		} 
		else if (side[1] == Neighbor.OTHER) {
			return Action.LEFT;
		} 
		else if (side[3] == Neighbor.OTHER) {
			return Action.RIGHT;
		} 
		else if (swarmMovement < 100 && !(direction == Direction.NORTH)) {
			return Action.LEFT;
		} 
		else if (swarmMovement > 100 && swarmMovement < 200 && !(direction == Direction.WEST)) {
			return Action.RIGHT;
		} 
		else if (swarmMovement > 200 && swarmMovement < one + 200 &&
				!(direction == Direction.SOUTH)) {
			return Action.LEFT;
		} 
		else if (swarmMovement > one + 200 && swarmMovement < 350 &&
				!(direction == Direction.EAST)) {
			return Action.RIGHT; 
		}
		else if (swarmMovement > 350 && swarmMovement < 450 &&
				!(direction == Direction.NORTH)) {
			return Action.RIGHT;
		} 
		else if (swarmMovement > 450 && swarmMovement < two + 450 &&
				!(direction == Direction.SOUTH)) {
			return Action.LEFT;
		}
		else if (swarmMovement > 350 && swarmMovement < 450 &&
				!(direction == Direction.SOUTH)) {
			return Action.RIGHT;
		} 
		else if (swarmMovement > 100 && swarmMovement < 200 && !(direction == Direction.WEST)) {
			return Action.RIGHT;
		} 
		else if (swarmMovement > 200 && swarmMovement < one + 200 &&
				!(direction == Direction.SOUTH)) {
			return Action.LEFT;
		} 
		else if (swarmMovement > two + 450 && swarmMovement < 510) {
			return calmMode();
		} 
		else if (swarmMovement < 510) {
			return Action.HOP;
		}
		else {
			swarmMovement = 0;
			return Action.HOP;
		}
	}
	
	public boolean offGuard(Neighbor side) {
		return !(side == Neighbor.SAME || side == Neighbor.WALL);
	}
	
	public int getMovesSinceCreature() {
		return movesSinceCreature;
	}
	
	public void update(CritterInfo info) {
		huskyRemoval();
		countMoves++;

		side[0] = info.getFront();
		side[1] = info.getLeft();
		side[2] = info.getBack();
		side[3] = info.getRight();
		
		int count = 0;
		closed = 0;
		
		for (int i = 0; i < side.length; i++) {
			if (side[i] == Neighbor.OTHER) {
				count++;
			}
			if (side[i] == Neighbor.SAME || side[i] == Neighbor.WALL) {
				closed++;
			}
		}
		
		if (count == 0) {
			movesSinceCreature++;
		}
		else {
			movesSinceCreature = 0;
		}
		
		int noTouch = 0;
		
		for (int i = 0; i < army.size(); i++) {
			if (army.get(i).getMovesSinceCreature() > 150) {
				noTouch++;
			}
		}
		if (noTouch > army.size() * GET_THEM) {
			swarm = true;
		}
		if (swarmMovement > 0) {
			swarm = true;
		}	
		this.info = info;
	}

	public int getMoves() {
		return countMoves;
	}

	public int getSwarmMovement() {
		return swarmMovement;
	}
 
	public boolean getSwarm() {
		return swarm;
	}
	
	@Override
	public Color getColor() {
		return Color.WHITE;
	}

	@Override
	public String toString() {
		return "     DC     ";
	}
	
	public void huskyRemoval() {
		for (int i = 0; i < army.size(); i++) {
			if (army.get(i).getMoves() + 5 < countMoves) {
				army.remove(i);
			}
		}
	}
}