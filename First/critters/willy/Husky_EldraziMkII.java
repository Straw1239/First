package willy;
//William Berman
//willieberman@gmail.com

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import critters.Critter;
import critters.CritterInfo;

public class Husky_EldraziMkII extends Critter {
	
	//Initialize
	private static ArrayList<Husky_EldraziMkII> brood = new ArrayList<Husky_EldraziMkII> (); //All of the Eldrazi
	public static Direction broodDirection = Direction.SOUTH; //Direction that the brood moves in, starts at south
	public static int moves = 0; //How many moves since the last shift
	
	private static Random rand = new Random();
	private static final Color[] colors = {	new Color (4, 129, 115),
								new Color (184, 42, 41),
								new Color (159, 111, 171),
								new Color (156, 36, 81)}; //The colors that an Eldrazi can be
	private int color; //The color that this Eldrazi is

	//Constructor
	public Husky_EldraziMkII () {
		//brood.add (this); //Add this Eldrazi to the brood
		this.color = rand.nextInt (4);
		
	}
	
	//Returns what colors this Eldrazi is
    public Color getColor () {
        return colors[color];
    }

    //Returns the String that this Eldrazi is
    public String toString () {
    	return "·";
    }
	
    private static volatile boolean isFirst = false;
	//Returns how this Eldrazi will act
    public Action getMove (CritterInfo info) 
    {
    	isFirst = false;
    	moves++; /* Next move (total moves for all Eldrazi)
    	 		  * (It reaches the shift point faster if there are more Eldrazi,
    	 		  * hence acting more stationary when the situation is more dangerous due to a smaller number of Eldrazi) */
    	if (moves >= 4000) { //If it reached the shift point...
    		moves = 0; //reset the move count
    		if(!isFirst) broodDirection = Direction.values () [rand.nextInt (4)]; //shift the brood direction to a new, random direction
    		isFirst = true;
    	}
        if (getEnemiesAround (info) > 0) return getBattleMove (info); //Go into battle mode if there's enemy presence nearby
        else return getSwarmMove (info); //Otherwise, act as a swarm, following the progenitor
    }
    
    /* Returns how this Eldrazi will act in a swarm, when enemies are not present
     * Response is not immediate when in this mode: it waits until the end to make the move */
    private static Action getSwarmMove (CritterInfo info) {
    	Action out = Action.INFECT; //Default action
		if (broodDirection == Direction.NORTH) { //if the brood is going north...
			if (info.getDirection () == Direction.NORTH) { //if this Eldrazi is going north...
				if (info.getFront () == Neighbor.SAME || info.getFront () == Neighbor.OTHER) out = Action.INFECT; //infect if it can't hop or isn't at a wall,
				if (info.getFront () == Neighbor.WALL) out = Action.LEFT; //turn left at a wall,
				else out = Action.HOP; //and hop otherwise (if there's an empty space)
			} else if (info.getDirection () == Direction.EAST || info.getDirection () == Direction.SOUTH) { //if this Eldrazi is going east or south...
				out = Action.LEFT; //turn left, going towards north
			} else if (info.getDirection () == Direction.WEST) { //if this Eldrazi is going west...
				out = Action.RIGHT; //turn right, going towards north
			}
		} else if (broodDirection == Direction.EAST) { //if the brood is going east...
			if (info.getDirection () == Direction.EAST) { //if this Eldrazi is going east...
				if (info.getFront () == Neighbor.SAME || info.getFront () == Neighbor.OTHER) out = Action.INFECT; //infect if it can't hop or isn't at a wall,
				if (info.getFront () == Neighbor.WALL) out = Action.LEFT; //turn left at a wall,
				else out = Action.HOP; //and hop otherwise (if there's an empty space)
			} else if (info.getDirection () == Direction.SOUTH || info.getDirection () == Direction.WEST) { //if this Eldrazi is going south or west...
				out = Action.LEFT; //turn left, going towards east
			} else if (info.getDirection () == Direction.NORTH) { //if this Eldrazi is going north...
				out = Action.RIGHT; //turn right, going towards east
			}
		} else if (broodDirection == Direction.SOUTH) { //if the brood is going south...
			if (info.getDirection () == Direction.SOUTH) { //if this Eldrazi is going south...
				if (info.getFront () == Neighbor.SAME || info.getFront () == Neighbor.OTHER) out = Action.INFECT; //infect if it can't hop or isn't at a wall,
				if (info.getFront () == Neighbor.WALL) out = Action.LEFT; //turn left at a wall,
				else out = Action.HOP; //and hop otherwise (if there's an empty space)
			} else if (info.getDirection () == Direction.WEST || info.getDirection () == Direction.NORTH) { //if this Eldrazi is going west or north...
				out = Action.LEFT; //turn left, going towards south
			} else if (info.getDirection () == Direction.EAST) { //if this Eldrazi is going east...
				out = Action.RIGHT; //turn right, going towards south
			}
		} else if (broodDirection == Direction.WEST) { //if the brood is going west...
			if (info.getDirection () == Direction.WEST) { //if this Eldrazi is going west...
				if (info.getFront () == Neighbor.SAME || info.getFront () == Neighbor.OTHER) out = Action.INFECT; //infect if it can't hop or isn't at a wall,
				if (info.getFront () == Neighbor.WALL) out = Action.LEFT; //turn left at a wall,
				else out = Action.HOP; //and hop otherwise (if there's an empty space)
			} else if (info.getDirection () == Direction.NORTH || info.getDirection () == Direction.EAST) { //if this Eldrazi is going north or east...
				out = Action.LEFT; //turn left, going towards east
			} else if (info.getDirection () == Direction.SOUTH) { //if this Eldrazi is going south...
				out = Action.RIGHT; //turn right, going towards east
			}
		}
    	return out;
    }
    
    /* Returns how this Eldrazi will act in battle
     * Response is immediate when in this mode: it immediately makes a move in the right circumstances */
    private static Action getBattleMove (CritterInfo info) {
    	Action out = Action.INFECT;
    	if (getEnemiesAround (info) == 1) { //If there's 1 enemy nearby...
    		if (info.getFront () == Neighbor.OTHER) return Action.INFECT; //infect it if it's in front
    		else if (info.getRight () == Neighbor.OTHER) return Action.RIGHT; //turn towards if it's to the right
    		else if (info.getBack () == Neighbor.OTHER) { //if it's behind...
    			if (info.getFront () == Neighbor.EMPTY) return Action.HOP; //escape if it can
    			else return Action.LEFT; //and start turning to face it otherwise
    		}
    		else if (info.getLeft () == Neighbor.OTHER) return Action.LEFT; //turn towards if it's to the left
    		
    	} else if (getEnemiesAround (info) == 2) { //If there are 2 enemies nearby...
    		if (info.getFront () == Neighbor.OTHER) return Action.INFECT; //infect one if it's in front
    		if (info.getLeft () == Neighbor.OTHER && info.getRight () != Neighbor.OTHER) return Action.LEFT; //face one on the left if it isn't turning away from one on the right
    		if (info.getRight () == Neighbor.OTHER && info.getLeft () != Neighbor.OTHER) return Action.RIGHT; //face one on the right if it isn't turning away from one on the left
    		else { //otherwise...
    			if (info.getFront () == Neighbor.EMPTY) return Action.HOP; //escape if it can
    			else return Action.INFECT; //and infect otherwise (the only position not dealt with by the code by this point is left-right)
    		}
    		
    	} else if (getEnemiesAround (info) == 3) { //If there are 3 enemies nearby...
    		if (info.getFront () == Neighbor.OTHER) return Action.INFECT; //infect one if it's in front
    		if (info.getFront () == Neighbor.EMPTY) return Action.HOP; //escape if it can
    		else return Action.INFECT; //infect otherwise
    		
    	} else if (getEnemiesAround (info) == 4) return Action.INFECT; //If there are 4 enemies nearby, infect
    		
    	return out; /* The code probably won't reach here,
    	 			 * but it's here both as a default action and to make the non-void getBattleMove work, despite how it's coded */
    }
    
    //Count the number of enemies around this Eldrazi
    private static int getEnemiesAround (CritterInfo info) {
    	int out = 0; //Initialize
    	//Increment the count for each enemy around this Eldrazi
    	if (info.getFront () == Neighbor.OTHER) out++;
    	if (info.getRight () == Neighbor.OTHER) out++;
    	if (info.getBack () == Neighbor.OTHER) out++;
    	if (info.getLeft () == Neighbor.OTHER) out++;
    	return out;
    }
}