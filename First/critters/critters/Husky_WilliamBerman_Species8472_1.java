package critters;

import java.awt.Color;
import java.util.Random;
public class Husky_WilliamBerman_Species8472_1 extends Critter {
	
	private Color[] colors = {	new Color (130, 66, 82),
								new Color (155, 125, 151),
								new Color (132, 102, 136),
								new Color (122, 98, 124),
								new Color (182, 152, 202)};
	private Color thisColor;
	private int count = 0;
	private int hibernationEnd = 1000;
	private Random rand = new Random ();
	private double chanceOfHoldfast = 0.8;
	private static enum Status { HOLDFAST, UPWELLING };
	private Status what = Status.UPWELLING;

	public Husky_WilliamBerman_Species8472_1 () {
		this.thisColor = colors [rand.nextInt(5)];
		if (Math.random() < this.chanceOfHoldfast)
			this.what = Status.HOLDFAST;
	}

	@Override
	public Color getColor () {
		return this.thisColor;
	}

	@Override
	public String toString () {
		/* This is for testing purposes; it's usually
		return "S"; */
		if (this.what == Status.HOLDFAST)
			return "H";
		else
			return "U";
	}

	@Override
	public Action getMove (CritterInfo info) {
		this.count++;
		
		if (this.count >= this.hibernationEnd && this.what == Status.HOLDFAST)
			this.what = Status.UPWELLING;
		
		//Test for sneak-ups (for holdfast-8472s)
		if (this.what == Status.HOLDFAST && info.getBack() == Neighbor.OTHER) {
			this.what = Status.UPWELLING;
			return Action.LEFT;
		}
		
		//Prepare the interior holdfast
		if (	(info.getFront() == Neighbor.SAME
				|| info.getFront() == Neighbor.WALL)
				&& (info.getBack() == Neighbor.SAME
				|| info.getBack() == Neighbor.WALL)
				&& (info.getLeft() == Neighbor.SAME
				|| info.getLeft() == Neighbor.WALL)
				&& (info.getRight() == Neighbor.SAME
				|| info.getRight() == Neighbor.WALL)) {
			
			if (Math.random() < this.chanceOfHoldfast)
				this.what = Status.HOLDFAST;
			else
				this.what = Status.UPWELLING;
			
			if (info.getDirection() == Direction.SOUTH || info.getDirection() == Direction.WEST)
				return Action.RIGHT;
			if (info.getDirection() == Direction.EAST)
				return Action.LEFT;
			if (info.getDirection() == Direction.NORTH)
				return Action.INFECT;
		}
		
		//Default: move
		Action out = Action.HOP;

		/* Random-wall-bounce implementation:
		These if/else statements have "out = " at the end as opposed to "return " 
		because moving takes less priority than killing other things and not getting killed. 
		This is overridden by swarm implementation in holdfast-8472s. */
		if (info.getFront() == Neighbor.WALL) {
			if (rand.nextInt(2) == 1)
				out = Action.LEFT;
			else
				out = Action.RIGHT;
		}
		
		/* Swarm implementation: (holdfast-8472)
		the 8472 will flock towards the south when they hit each other from any direction.
		The group will fall down, smashing through most critters in their path
		(and maybe adding the newly infected critters to their group.) */
		if (this.what == Status.HOLDFAST) {
			if (	info.getFront() == Neighbor.SAME
					|| info.getBack() == Neighbor.SAME
					|| info.getLeft() == Neighbor.SAME
					|| info.getRight() == Neighbor.SAME) {
				if (info.getDirection() == Direction.NORTH || info.getDirection() == Direction.WEST)
					out = Action.LEFT;
				else if (info.getDirection() == Direction.EAST)
					out = Action.RIGHT;
				else if (info.getDirection() == Direction.SOUTH)
					out = Action.HOP;
			}
		}
		
		/* Random-same-bounce implementation: (upwelling-8472)
		These if/else statements have "out = " at the end as opposed to "return " 
		because moving takes less priority than killing other things and not getting killed. 
		This causes normal-8472s to be more spread-out and chaotic than other critters,
		allowing normal-8472s to "track down" other critters. */
		if (info.getFront() == Neighbor.SAME && this.what == Status.UPWELLING) {
			if (rand.nextInt(2) == 1)
				out = Action.LEFT;
			else
				out = Action.RIGHT;
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
				if (rand.nextInt(2) == 1) { //Go for either one
					return Action.LEFT;
				} else {
					return Action.RIGHT;
				}
			}
	
			else if (info.getBack() == Neighbor.OTHER && info.getFront() != Neighbor.OTHER) {
				if (info.getFront() == Neighbor.EMPTY) {
					return Action.HOP; //Escape from the Neighbor.OTHER behind this 8472
				} else {
					if (info.getLeft() == Neighbor.OTHER) { //If enemies B, L
						return Action.LEFT;
					}
					else if (info.getRight() == Neighbor.OTHER) { //If enemies B, R
						return Action.RIGHT;
					}
					else { //If enemy B
						return Action.LEFT;
					}
				}
			}
			
			if (info.getFront() == Neighbor.OTHER) //If enemy F
				return Action.INFECT;

		return out; //Just in case there's no Neighbor.OTHER to interact with
	}
}