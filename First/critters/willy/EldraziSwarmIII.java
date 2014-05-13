package willy;
//William Berman
//willieberman@gmail.com

import java.awt.Color;
import java.util.ArrayList;

import critters.Critter;
import critters.CritterInfo;

/* TO DO:
 * modify battle algorithm? (more elegant OR different behavior)
 * make the new brood-direction part more efficient?
 * CCW cycling when not in battle
 * use brood for something - maybe split it into multiple groups?
 * inputs - fix it
 * (Debug: print out each element of enemiesTotal, and probably inputs) */

public class EldraziSwarmIII extends Critter {
	
	//Initialize
	public static ArrayList<EldraziSwarmIII> brood = new ArrayList<EldraziSwarmIII> (); //All of the ESCs
	public static Direction broodDirection = Direction.SOUTH; //Direction that the brood moves in, starts at north
	public static int inputs = 0; //How many ESCs have inputted data on enemy locations yet
	public static int[] enemiesTotal = new int[4]; //How many enemies are to the [north, east, south, west] of the brood
	public boolean[] enemiesThis = new boolean[4]; //Whether there's an enemy to the [north, east, south, west] of this ESC

	//Constructor
	public EldraziSwarmIII () {
		brood.add (this); //Add this ESC to the brood
	}
	
	//Returns what color this ESC is (red)
    @Override
	public Color getColor () {
    	return Color.RED;
    }

    //Returns the String that this ESC is (omega)
    @Override
	public String toString () {
    	return "�";
    }
	
	//Returns how this ESC will act
    @Override
	public Action getMove (CritterInfo info) {
    	//Determine which direction to move in (not acted on in battle)
    	if (inputs == 0) enemiesTotal = new int[4]; //Reset the total enemy counts if it's the first ESC to input data
    	//Update its knowledge of where nearby enemies are, updating its enemiesThis boolean[4]
		if (info.getDirection () == Direction.NORTH) { //if this ESC is pointing north...
    		if (info.getFront () == Neighbor.OTHER) this.enemiesThis[0] = true; //if there's an enemy in front of it, there's a northern enemy
    		else this.enemiesThis[0] = false; //otherwise, there's no northern enemy
    		if (info.getRight () == Neighbor.OTHER) this.enemiesThis[1] = true; //if there's an enemy on its right, there's an eastern enemy
    		else this.enemiesThis[1] = false; //otherwise, there's no eastern enemy
    		if (info.getBack () == Neighbor.OTHER) this.enemiesThis[2] = true; //if there's an enemy behind it, there's a southern enemy
    		else this.enemiesThis[2] = false; //otherwise, there's no southern enemy
    		if (info.getLeft () == Neighbor.OTHER) this.enemiesThis[3] = true; //if there's an enemy on its left, there's a western enemy
    		else this.enemiesThis[3] = false; //otherwise, there's no western enemy
    	} else if (info.getDirection () == Direction.EAST) { //if this ESC is pointing east...
    		if (info.getFront () == Neighbor.OTHER) this.enemiesThis[1] = true; //if there's an enemy in front of it, there's an eastern enemy
    		else this.enemiesThis[1] = false; //otherwise, there's no eastern enemy
    		if (info.getRight () == Neighbor.OTHER) this.enemiesThis[2] = true; //if there's an enemy on its right, there's a southern enemy
    		else this.enemiesThis[2] = false; //otherwise, there's no southern enemy
    		if (info.getBack () == Neighbor.OTHER) this.enemiesThis[3] = true; //if there's an enemy behind it, there's a western enemy
    		else this.enemiesThis[3] = false; //otherwise, there's no western enemy
    		if (info.getLeft () == Neighbor.OTHER) this.enemiesThis[0] = true; //if there's an enemy on its left, there's a northern enemy
    		else this.enemiesThis[0] = false; //otherwise, there's no northern enemy
    	} else if (info.getDirection () == Direction.SOUTH) { //if this ESC is pointing south...
    		if (info.getFront () == Neighbor.OTHER) this.enemiesThis[2] = true; //if there's an enemy in front of it, there's a southern enemy
    		else this.enemiesThis[2] = false; //otherwise, there's no southern enemy
    		if (info.getRight () == Neighbor.OTHER) this.enemiesThis[3] = true; //if there's an enemy on its right, there's a western enemy
    		else this.enemiesThis[3] = false; //otherwise, there's no western enemy
    		if (info.getBack () == Neighbor.OTHER) this.enemiesThis[0] = true; //if there's an enemy behind it, there's a northern enemy
    		else this.enemiesThis[0] = false; //otherwise, there's no northern enemy
    		if (info.getLeft () == Neighbor.OTHER) this.enemiesThis[1] = true; //if there's an enemy on its left, there's an eastern enemy
    		else this.enemiesThis[1] = false; //otherwise, there's no eastern enemy
    	} else if (info.getDirection () == Direction.WEST) { //if this ESC is pointing west...
    		if (info.getFront () == Neighbor.OTHER) this.enemiesThis[3] = true; //if there's an enemy in front of it, there's a western enemy
    		else this.enemiesThis[3] = false; //otherwise, there's no western enemy
    		if (info.getRight () == Neighbor.OTHER) this.enemiesThis[0] = true; //if there's an enemy on its right, there's a northern enemy
    		else this.enemiesThis[0] = false; //otherwise, there's no northern enemy
    		if (info.getBack () == Neighbor.OTHER) this.enemiesThis[1] = true; //if there's an enemy behind it, there's an eastern enemy
    		else this.enemiesThis[1] = false; //otherwise, there's no eastern enemy
    		if (info.getLeft () == Neighbor.OTHER) this.enemiesThis[2] = true; //if there's an enemy on its left, there's a southern enemy
    		else this.enemiesThis[2] = false; //otherwise, there's no southern enemy
    	}
		//Tally the enemies
		for (int direction = 0; direction < 4; direction++) { //For each direction (represented by ints, the indices of the enemies arrays)...
			if (this.enemiesThis[direction]) enemiesTotal[direction]++; //if this ESC sees an enemy in that direction, increment that direction's count
		}
		inputs++; //Note that this ESC inputted its data
    	if (inputs == brood.size ()) { //If it's the last ESC to have inputted data...
    		//send the brood in the direction that the most enemies are in
        	if (enemiesTotal[0] >= enemiesTotal[1] && enemiesTotal[0] >= enemiesTotal[2] && enemiesTotal[0] >= enemiesTotal[3]) broodDirection = Direction.NORTH; //if northern enemies are the most common, go north
        	else if (enemiesTotal[1] >= enemiesTotal[0] && enemiesTotal[1] >= enemiesTotal[2] && enemiesTotal[1] >= enemiesTotal[3]) broodDirection = Direction.EAST; //if eastern enemies are the most common, go east
        	else if (enemiesTotal[2] >= enemiesTotal[0] && enemiesTotal[2] >= enemiesTotal[1] && enemiesTotal[2] >= enemiesTotal[3]) broodDirection = Direction.SOUTH; //if southern enemies are the most common, go south
        	else if (enemiesTotal[3] >= enemiesTotal[0] && enemiesTotal[3] >= enemiesTotal[1] && enemiesTotal[3] >= enemiesTotal[2]) broodDirection = Direction.WEST; //if western enemies are the most common, go west
        	inputs = 0; //reset the count of ESCs that inputted data, for the next step
    	}
    	//Move
        if (this.getEnemiesAround (info) > 0) return this.getBattleAction (info); //Go into battle mode if there's enemy presence nearby
        else return this.getSwarmAction (info); //Otherwise, act as a swarm
    }
    
    //Returns how this ESC will act when in a swarm, when enemies are not present
    private Action getSwarmAction (CritterInfo info) {
		if (broodDirection == Direction.NORTH) { //if the brood is going north...
			if (info.getDirection () == Direction.NORTH) return this.getAlignedBroodAction (info); //if this ESC is going north too, give it the right action
			else if (info.getDirection () == Direction.EAST || info.getDirection () == Direction.SOUTH) return Action.LEFT; //if this ESC is going east or south, turn left, going towards north
			else if (info.getDirection () == Direction.WEST) return Action.RIGHT; //if this ESC is going west, turn right, going towards north
			
		} else if (broodDirection == Direction.EAST) { //if the brood is going east...
			if (info.getDirection () == Direction.EAST) return this.getAlignedBroodAction (info); //if this ESC is going east too, give it the right action
			else if (info.getDirection () == Direction.SOUTH || info.getDirection () == Direction.WEST) return Action.LEFT; //if this ESC is going south or west, turn left, going towards east
			else if (info.getDirection () == Direction.NORTH) return Action.RIGHT; //if this ESC is going north, turn right, going towards east
			
		} else if (broodDirection == Direction.SOUTH) { //if the brood is going south...
			if (info.getDirection () == Direction.SOUTH) return this.getAlignedBroodAction (info); //if this ESC is going south too, give it the right action
			else if (info.getDirection () == Direction.WEST || info.getDirection () == Direction.NORTH) return Action.LEFT; //if this ESC is going west or north, turn left, going towards south
			else if (info.getDirection () == Direction.EAST) return Action.RIGHT; //if this ESC is going east, turn right, going towards south
			
		} else if (broodDirection == Direction.WEST) { //if the brood is going west...
			if (info.getDirection () == Direction.WEST) return this.getAlignedBroodAction (info); //if this ESC is going west too, give it the right action
			else if (info.getDirection () == Direction.NORTH || info.getDirection () == Direction.EAST) return Action.LEFT; //if this ESC is going north or east, turn left, going towards west
			else if (info.getDirection () == Direction.SOUTH) return Action.RIGHT; //if this ESC is going south, turn right, going towards west
		}
		return Action.INFECT; /* The code won't reach here,
			 				   * but it's here both as a default action and to make the function work */
    }
    
    //Returns how the ESC will act when it's in the swarm, pointing in the brood Direction
    private Action getAlignedBroodAction (CritterInfo info) {
    	if (info.getFront () == Neighbor.SAME || info.getFront () == Neighbor.OTHER) return Action.INFECT; //infect if it might hit something
		if (info.getFront () == Neighbor.WALL) return Action.LEFT; //turn left at a wall
		else return Action.HOP; //otherwise, hop (if there's an empty space)
    }
    
    //Returns how this ESC will act in battle
    private Action getBattleAction (CritterInfo info) {
    	if (this.getEnemiesAround (info) == 1) { //If there's 1 enemy nearby...
    		if (info.getFront () == Neighbor.OTHER) return Action.INFECT; //infect it if it's in front
    		else if (info.getRight () == Neighbor.OTHER) return Action.RIGHT; //turn towards if it's to the right
    		else if (info.getBack () == Neighbor.OTHER) { //if it's behind...
    			if (info.getFront () == Neighbor.EMPTY) return Action.HOP; //escape if it can
    			else return Action.LEFT; //and start turning to face it otherwise
    		}
    		else if (info.getLeft () == Neighbor.OTHER) return Action.LEFT; //turn towards if it's to the left
    		
    	} else if (this.getEnemiesAround (info) == 2) { //If there are 2 enemies nearby...
    		if (info.getFront () == Neighbor.OTHER) return Action.INFECT; //infect one if it's in front
    		if (info.getLeft () == Neighbor.OTHER && info.getRight () != Neighbor.OTHER) return Action.LEFT; //face one on the left if it isn't turning away from one on the right
    		if (info.getRight () == Neighbor.OTHER && info.getLeft () != Neighbor.OTHER) return Action.RIGHT; //face one on the right if it isn't turning away from one on the left
    		else { //otherwise...
    			if (info.getFront () == Neighbor.EMPTY) return Action.HOP; //escape if it can
    			else return Action.INFECT; //and infect otherwise (the only position not dealt with by the code by this point is left-right)
    		}
    		
    	} else if (this.getEnemiesAround (info) == 3) { //If there are 3 enemies nearby...
    		if (info.getFront () == Neighbor.OTHER) return Action.INFECT; //infect one if it's in front
    		if (info.getFront () == Neighbor.EMPTY) return Action.HOP; //escape if it can
    		else return Action.INFECT; //infect otherwise
    		
    	} else if (this.getEnemiesAround (info) == 4) return Action.INFECT; //If there are 4 enemies nearby, infect
    		
    	return Action.INFECT; /* The code won't reach here,
    	 			 		   * but it's here both as a default action and to make the function work */
    }
    
    //Counts the number of enemies around this ESC
    private int getEnemiesAround (CritterInfo info) {
    	int out = 0; //Initialize
    	//Increment the count for each enemy around this ESC
    	if (info.getFront () == Neighbor.OTHER) out++;
    	if (info.getRight () == Neighbor.OTHER) out++;
    	if (info.getBack () == Neighbor.OTHER) out++;
    	if (info.getLeft () == Neighbor.OTHER) out++;
    	return out;
    }
}