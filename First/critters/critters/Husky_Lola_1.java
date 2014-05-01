package critters;

import java.awt.Color;
//Lola Bradford
//libbyib@gmail.com
/*
 * This husky walks around in straight lines, turning away from walls and towards empty spaces, and towards enemies.
 * It sticks to walls. I wasn't aware if that constituted "clumping" or not;
 * if it does, switch WALL_STICKY to false and it will still win. It will just be less interesting.
 * If a husky has gone for a long time without infecting anything, all of the huskies will jump off of the walls.
 */
public class Husky_Lola_1 extends Critter {
	private int moveCount;
	private int lastInfect;//Which move the husky last infected on
	private static boolean WALL_STICKY=true;//Turn off to no longer stick to walls
	private boolean wall;//Whether it was recently on a wall, used for the picture only.
	private static boolean swarm;//If this is true, huskies no longer stop moving on walls
	private static final int SWARM_DURATION=50;
	private static final int SWARM_PATIENCE=500;//How long the husky will wait between infecting and calling for swarm.
	private int swarmTime;//Counts down how long the swarm has lasted
	public Husky_Lola_1() {
		swarmTime=SWARM_DURATION;
	}
	@Override
	public Action getMove(CritterInfo info) {
		if(moveCount-lastInfect>SWARM_PATIENCE) swarm=true;
		wall=isOnWall(info);
		moveCount++;
		//Infect when enemy is in front
		if(info.getFront()==Neighbor.OTHER){
			lastInfect=moveCount;
			return Action.INFECT;
		}
		if(info.getRight()==Neighbor.OTHER){
			return Action.RIGHT;
		}
		if(info.getLeft()==Neighbor.OTHER){
			return Action.LEFT;
		}
		if(info.getBack()==Neighbor.OTHER){
			return leftOrRight();
		}
		//Go in a direction if possible and if swarm is active or it's not on a wall
		if (info.getFront()==Neighbor.EMPTY&&(!WALL_STICKY||!isOnWall(info)||swarm)){
			if(swarm){
				swarmTime--;
				if(swarmTime==0){
					swarmTime=SWARM_DURATION;
					swarm=false;
				}
			}
			return Action.HOP;
		}
		return faceOut(info);
	}
	/*
	 * faceOut returns an action that will turn the critter towards an empty space,
	 * or if not, away from a wall,
	 * or if not that, then it doesn't turn at all.
	 */
	private Action faceOut(CritterInfo info){
		if(info.getRight()==Neighbor.EMPTY){
			if(info.getLeft()==Neighbor.EMPTY){
				return leftOrRight();
			}
			return Action.RIGHT;
		}
		if(info.getLeft()==Neighbor.EMPTY){
			return Action.LEFT;
		}
		if(info.getRight()==Neighbor.WALL){
			return Action.LEFT;
		}
		if(info.getLeft()==Neighbor.WALL){
			return Action.RIGHT;
		}
		if(info.getBack()==Neighbor.EMPTY){
			return leftOrRight();
		}
		return null;
	}
	/*
	 * returns whether the critter is touching a wall
	 */
	private boolean isOnWall(CritterInfo info){
		return info.getFront()==Neighbor.WALL||info.getRight()==Neighbor.WALL||info.getLeft()==Neighbor.WALL||info.getBack()==Neighbor.WALL;
	}
	/*
	 * picks a random one of left and right, and returns it
	 */
	private static Action leftOrRight(){
		return Math.random()<0.5?Action.LEFT:Action.RIGHT;
	}
	@Override
	public Color getColor() {//Green-grey
		return new Color(180,220,120);
	}
	@Override
	public String toString() {//It flaps.
		return wall?(moveCount%2==0?(moveCount%4==0?"|.|":"_._"):"\\./"):(moveCount%2==0?(moveCount%4==0?"\\./":"/'\\"):"-.-");
	}

}
