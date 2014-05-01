package critters;

import java.awt.*;
import java.util.*;
//UNABLE TO BEAT:
//Dabo
//Dickson
//Juvet
//Karcher (bring in the old code)
//Pollock
//Wei (bring in the old code)
//PiTrap (set hibernationEnd to 1000000 to beat them)
public class Husky_WilliamBerman_Species8472_2 extends Critter {
	private Color[] colors = {	new Color (130, 66, 82),
								new Color (155, 125, 151),
								new Color (132, 102, 136),
								new Color (122, 98, 124),
								new Color (182, 152, 202)};
	private Color thisColor;
	private int count = 0;
	private int hibernationEnd = 2000;
	private Random rand = new Random ();
	private double chanceOfHoldfast = 1;
	private static enum status { HOLDFAST, UPWELLING, DORMANT };
	private status what = status.UPWELLING;

	public Husky_WilliamBerman_Species8472_2 () {
		this.thisColor = colors [rand.nextInt(5)];
		if (Math.random() < this.chanceOfHoldfast)
			this.what = status.HOLDFAST;
	}

	public Color getColor () {
		return this.thisColor;
	}

	public String toString () {
		return "§";
		
		//if (this.what == status.DORMANT)
			//return "D";
		//else if (this.what == status.HOLDFAST)
			//return "H";
		//else
			//return "U";
		
	}

	public Action getMove (CritterInfo info) {
		this.count++; //Next move
		
		//Default: move
			Action out = Action.HOP;
		
		//Default dormant-8472 action
			if (this.what == status.DORMANT)
				out = Action.INFECT;
		
		//End the hibernation in the 8472, every time hibernationEnd is reached
			if (this.count >= this.hibernationEnd && (this.what == status.HOLDFAST || this.what == status.DORMANT)) {
				this.what = status.UPWELLING;
				this.count = 0;
			}
		
		//Determine if this 8472 should go from holdfast to dormant
			if (this.what == status.HOLDFAST //If it's holdfast,
				&& info.getDirection() == Direction.SOUTH //If it's in the direction that being a holdfast-8472 put it in...
				&& ((info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL) //and it's blocked by an ally or wall,
				&& info.getFront() != Neighbor.OTHER //and there...
				&& info.getBack() != Neighbor.OTHER //is no...
				&& info.getLeft() != Neighbor.OTHER //enemy near...
				&& info.getRight() != Neighbor.OTHER)) { //this holdfast-8472,
				this.what = status.DORMANT; //transform
			}
		
		//Determine if this 8472 should go from dormant to holdfast
			if (this.what == status.DORMANT //If it's dormant,
				&& ((info.getDirection() == Direction.NORTH && info.getBack() == Neighbor.EMPTY) //and there...
				|| (info.getDirection() == Direction.EAST && info.getRight() == Neighbor.EMPTY) //is no...
				|| (info.getDirection() == Direction.SOUTH && info.getFront() == Neighbor.EMPTY) //ally below...
				|| (info.getDirection() == Direction.WEST && info.getLeft() == Neighbor.EMPTY))) //this dormant-8472,
					this.what = status.HOLDFAST; //transform
		
		//Point and infect outwards (dormant-8472)
			if (this.what == status.DORMANT) {
				if (info.getDirection() == Direction.SOUTH) { //(just became a dormant-8472)
					if ((info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL)
						&& (info.getLeft() == Neighbor.SAME || info.getLeft() == Neighbor.WALL)
						&& (info.getRight() == Neighbor.SAME || info.getRight() == Neighbor.WALL))
						out = Action.LEFT;
					else if (	(info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL)
								&& (info.getBack() == Neighbor.SAME || info.getBack() == Neighbor.WALL)
								&& (info.getLeft() == Neighbor.SAME || info.getLeft() == Neighbor.WALL))
						out = Action.RIGHT;
					else if (	(info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL)
								&& (info.getBack() == Neighbor.SAME || info.getBack() == Neighbor.WALL)
								&& (info.getRight() == Neighbor.SAME || info.getRight() == Neighbor.WALL))
						out = Action.LEFT;
					else if (	(info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL)
								&& (info.getLeft() == Neighbor.SAME || info.getLeft() == Neighbor.WALL))
						out = Action.RIGHT;
					else if (	(info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL)
								&& (info.getRight() == Neighbor.SAME || info.getRight() == Neighbor.WALL))
						out = Action.LEFT;
					else if (	(info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL)
								&& (info.getBack() == Neighbor.SAME || info.getBack() == Neighbor.WALL)) {
						if (rand.nextInt(2) == 1)
							out = Action.LEFT;
						else
							out = Action.RIGHT;
					} else if ((info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL)) {
						if (rand.nextInt(2) == 1)
							out = Action.LEFT;
						else
							out = Action.RIGHT;
					} else
						out = Action.LEFT;
				
				} else if (info.getDirection() == Direction.EAST) {
					if ((info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL)
						&& (info.getBack() == Neighbor.SAME || info.getBack() == Neighbor.WALL)
						&& (info.getRight() == Neighbor.SAME || info.getRight() == Neighbor.WALL))
						out = Action.LEFT;
					else if (	(info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL)
								&& (info.getLeft() == Neighbor.SAME || info.getLeft() == Neighbor.WALL)
								&& (info.getRight() == Neighbor.SAME || info.getRight() == Neighbor.WALL))
						out = Action.LEFT;
					else if (	(info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL)
								&& (info.getRight() == Neighbor.SAME || info.getRight() == Neighbor.WALL))
						out = Action.LEFT;
					else if (	(info.getRight() == Neighbor.SAME || info.getRight() == Neighbor.WALL)
								&& (info.getLeft() != Neighbor.SAME || info.getLeft() != Neighbor.WALL))
						out = Action.LEFT;
				
				} else if (info.getDirection() == Direction.WEST) {
					if ((info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL)
						&& (info.getBack() == Neighbor.SAME || info.getBack() == Neighbor.WALL)
						&& (info.getLeft() == Neighbor.SAME || info.getLeft() == Neighbor.WALL))
						out = Action.RIGHT;
					else if (	(info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL)
								&& (info.getLeft() == Neighbor.SAME || info.getLeft() == Neighbor.WALL)
								&& (info.getRight() == Neighbor.SAME || info.getRight() == Neighbor.WALL))
						out = Action.RIGHT;
					else if (	(info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL)
								&& (info.getLeft() == Neighbor.SAME || info.getLeft() == Neighbor.WALL))
						out = Action.RIGHT;
					else if (	(info.getLeft() == Neighbor.SAME || info.getLeft() == Neighbor.WALL)
								&& (info.getRight() != Neighbor.SAME || info.getRight() != Neighbor.WALL))
						out = Action.RIGHT;
					
				} else if (info.getDirection() == Direction.NORTH) {
					if ((info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL)
						&& (info.getBack() == Neighbor.SAME || info.getBack() == Neighbor.WALL)
						&& (info.getRight() == Neighbor.SAME || info.getRight() == Neighbor.WALL))
						out = Action.LEFT;
					else if (	(info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL)
								&& (info.getBack() == Neighbor.SAME || info.getBack() == Neighbor.WALL)
								&& (info.getLeft() == Neighbor.SAME || info.getLeft() == Neighbor.WALL))
						out = Action.RIGHT;
					else if (	(info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL)
								&& (info.getBack() == Neighbor.SAME || info.getBack() == Neighbor.WALL)) {
						if (rand.nextInt(2) == 1)
							out = Action.LEFT;
						else
							out = Action.RIGHT;
					}
				}
			}
		
		//Test for sneak-ups (holdfast-8472)
			if (this.what == status.HOLDFAST) {
				if (info.getLeft() == Neighbor.OTHER) {
					this.what = status.UPWELLING; //Go to battle mode
					return Action.LEFT; //and turn towards enemy
					
				} else if (info.getRight() == Neighbor.OTHER) {
					this.what = status.UPWELLING; //Go to battle mode
					return Action.RIGHT; //and turn towards enemy
					
				} else if (info.getBack() == Neighbor.OTHER) {
					if (info.getFront() == Neighbor.EMPTY)
						return Action.HOP; //Escape if possible
					else {
						this.what = status.UPWELLING; //Go to battle mode
						return Action.LEFT; //and start turning towards enemy
					}
				}
			}
		
		//Make the interior of the colonies fluctuant
			if (	(info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL) //If this...
					&& (info.getBack() == Neighbor.SAME || info.getBack() == Neighbor.WALL) //8472 is...
					&& (info.getLeft() == Neighbor.SAME || info.getLeft() == Neighbor.WALL) //fully surrounded...
					&& (info.getRight() == Neighbor.SAME || info.getRight() == Neighbor.WALL)) { //by allies,
				
				if (Math.random() < this.chanceOfHoldfast) //Transform (not always into upwelling-8472 because that way, they'd all leak out of a breach)
					this.what = status.HOLDFAST;
				else
					this.what = status.UPWELLING;
				
				//Align north
				if (info.getDirection() == Direction.SOUTH || info.getDirection() == Direction.WEST)
					out = Action.RIGHT;
				if (info.getDirection() == Direction.EAST)
					out = Action.LEFT;
				if (info.getDirection() == Direction.NORTH)
					out = Action.INFECT;
			}
		
		/* Random-wall-and-same-bounce implementation: (upwelling-8472)
		These if/else statements have "out = " at the end as opposed to "return " 
		because moving takes less priority than killing other things and not getting killed.
		This statement causes upwelling-8472s to randomly swarm the screen,
		more so if there are more of them. */
			if (this.what == status.UPWELLING && (info.getFront() == Neighbor.WALL || info.getFront() == Neighbor.SAME)) {
				if (rand.nextInt(2) == 1)
					out = Action.LEFT;
				else
					out = Action.RIGHT;
				
			}
		
		/* Swarm implementation: (holdfast-8472)
		the 8472 will flock towards the south when they hit each other from any direction.
		The group will fall down, smashing through most critters in their path
		(and maybe adding the newly infected critters to their group.) */
			if (this.what == status.HOLDFAST
				&& (info.getFront() == Neighbor.SAME //If it...
				|| info.getBack() == Neighbor.SAME //is next...
				|| info.getLeft() == Neighbor.SAME //to another...
				|| info.getRight() == Neighbor.SAME)) { //ally,
					//Align south
					if (info.getDirection() == Direction.NORTH || info.getDirection() == Direction.WEST)
						out = Action.LEFT;
					else if (info.getDirection() == Direction.EAST)
						out = Action.RIGHT;
					else if (info.getDirection() == Direction.SOUTH && info.getFront() == Neighbor.EMPTY)
						out = Action.HOP;
					
					//Just in case
					if (info.getFront() == Neighbor.OTHER)
						return Action.INFECT;
			}
		
		/* Suicidal-charge test: if the 8472 is surrounded,
		(if there are more enemies around the 8472 than just the one the 8472 can infect)
		then the 8472 automatically infects the one within reach, no matter what.
		Then the new 8472, deeper into enemy ranks, will do the same.
		This is (usually) devastatingly effective against dense critter colonies. */
			if (	(info.getLeft() == Neighbor.OTHER
					|| info.getRight() == Neighbor.OTHER
					|| info.getBack() == Neighbor.OTHER)
					&& info.getFront() == Neighbor.OTHER) {
				return Action.INFECT;
			}

		/* Homing tests: these 8472 turn towards adjacent enemies, trying to infect them.
		 These are used in "one-on-one" or "dogfight" combat,
		 as the "else" ensures that only one of these options happens. */
			if (	info.getLeft() == Neighbor.OTHER
					&& info.getRight() != Neighbor.OTHER
					&& info.getFront() != Neighbor.OTHER
					&& info.getBack() != Neighbor.OTHER) { //If enemy L
				return Action.LEFT;
			}
	
			else if (info.getRight() == Neighbor.OTHER
					&& info.getLeft() != Neighbor.OTHER
					&& info.getFront() != Neighbor.OTHER
					&& info.getBack() != Neighbor.OTHER) { //If enemy R
				return Action.RIGHT;
			}
			
			else if (info.getRight() == Neighbor.OTHER
					&& info.getLeft() == Neighbor.OTHER
					&& info.getFront() != Neighbor.OTHER
					&& info.getBack() != Neighbor.OTHER) { //If enemies L, R
				if (rand.nextInt(2) == 1) { //Otherwise, go for either one
					return Action.LEFT;
				} else {
					return Action.RIGHT;
				}
			}
	
			else if (info.getBack() == Neighbor.OTHER && info.getFront() == Neighbor.EMPTY)
				return Action.HOP; //Escape from the Neighbor.OTHER behind this 8472
			
			else if (info.getFront() == Neighbor.OTHER) //If enemy F
				return Action.INFECT;

		return out; //Just in case there's no Neighbor.OTHER to interact with
	}
}