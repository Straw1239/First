package critters;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
//Lola Bradford
//libbyib@gmail.com
public class Husky_Lola_2 extends Critter {
	private int moveCount;//of this critter
	private Direction facingDir;//which direction a "stepping" critter ought to face
	private boolean willMove;//whether it's appropriate to step away from a wall next turn
	private CritterInfo info;//latest CritterInfo
	private int color;//which color the critter is
	private Color[] colors=new Color[] 
			{Color.RED,Color.ORANGE,Color.YELLOW,new Color(0,160,110),Color.BLUE,new Color(180,0,255)};
	private static boolean stepMode;//whether critters step off the walls
	private boolean thisStep;//whether this critter will stand still/step from walls, or run around
	private static final int STEP_TIME=200;//how long until the critters step off the walls
	private static ArrayList<Direction> dirs=new ArrayList<Direction> (Arrays.asList(new Direction[]
			{Direction.NORTH,Direction.EAST,Direction.SOUTH,Direction.WEST}));
	public Husky_Lola_2(){
	}
	@Override
	public Action getMove(CritterInfo inf) {
		info=inf;
		moveCount++;
		if(moveCount>STEP_TIME){
			stepMode=true;
		}
		if(stepMode&&countNeighbors(info,Neighbor.WALL)>0){
			thisStep=true;
		}
		if(thisStep&&countNeighbors(info,Neighbor.SAME)>2){
			thisStep=false;
		}
		//Infect when enemy is in front
		if(info.getFront()==Neighbor.OTHER){
			return Action.INFECT;
		}
		//Don't face a wall
		if(info.getFront()==Neighbor.WALL){
			return faceOut(info);
		}
		//if in running mode, run around.
		if(!stepMode){
			//turn towards enemies
			if(info.getRight()==Neighbor.OTHER){
				return Action.RIGHT;
			}
			if(info.getLeft()==Neighbor.OTHER){
				return Action.LEFT;
			}
			if(info.getBack()==Neighbor.OTHER){
				return leftOrRight();
			}
			//Go in a direction if possible
			if (info.getFront()==Neighbor.EMPTY&&!(countNeighbors(info,Neighbor.WALL)>0)){
				return Action.HOP;
			}
			return faceOut(info);
		}
		if(stepMode){
			//Turn towards enemies on the sides if on a wall
			if(info.getBack()==Neighbor.WALL&&info.getRight()==Neighbor.OTHER){
				return Action.RIGHT;
			}
			if(info.getBack()==Neighbor.WALL&&info.getLeft()==Neighbor.OTHER){
				return Action.LEFT;
			}
			//if you have a lot of neighbors, stop standing still
			if(countNeighbors(info,Neighbor.OTHER)>0){
				thisStep=false;
			}
			//If you're on a wall face away from it
			if(countNeighbors(info,Neighbor.WALL)>0&&info.getBack()!=Neighbor.WALL){
				return faceOut(info);
			}
			if(!thisStep){//if it's not being a wall-thang
				if(!hasL(info)){//if it shouldn't stand by friends
					//face an enemy
					if(info.getRight()==Neighbor.OTHER){
						return Action.RIGHT;
					}
					if(info.getLeft()==Neighbor.OTHER){
						return Action.LEFT;
					}
					if(info.getBack()==Neighbor.OTHER){
						return leftOrRight();
					}
					//Go in a direction if possible
					if (info.getFront()==Neighbor.EMPTY&&countNeighbors(info,Neighbor.WALL)==0){
						return Action.HOP;
					}
				}
				return faceOut(info);
			}
			//step, if appropriate
			if(info.getLeft()==Neighbor.SAME&&info.getRight()==Neighbor.SAME){//if conditions are right, wait a turn, then move
				if(!willMove){
					willMove=true;
					return null;
				}
			}
			if(willMove){
				willMove=false;
				color++;
				color%=6;
				facingDir=info.getDirection();
				return Action.HOP;
			}
			//if you're not facing the right way, do so
			if(info.getDirection()!=facingDir&&facingDir!=null){
				return turnTowards(facingDir,info);
			}
			//if there's an enemy in striking distance, turn towards it
			if(info.getLeft()==Neighbor.OTHER){
				return Action.LEFT;
			}
			if(info.getRight()==Neighbor.OTHER){
				return Action.RIGHT;
			}
		}
		return null;
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
	//returns the one direction, rotated by the other direction
	public static Direction addDirs(Direction d1, Direction d2){
		return dirs.get((dirs.indexOf(d1)+dirs.indexOf(d2))%dirs.size());
	}
	/*
	 * return how many of this neighbor the critter has
	 */
	private int countNeighbors(CritterInfo info, Neighbor type){
		int count=0;
		if(info.getFront()==type){
			count++;
		}
		if(info.getBack()==type){
			count++;
		}
		if(info.getLeft()==type){
			count++;
		}
		if(info.getRight()==type){
			count++;
		}
		return count;
	}
	//returns whether this husky has exactly two friends, in directions perpendicular to each other
	private boolean hasL(CritterInfo info){
		if (info.getFront()==Neighbor.SAME){
			return info.getBack()!=Neighbor.SAME
					&&!(info.getLeft()==Neighbor.SAME&&info.getRight()==Neighbor.SAME)
					&&(info.getLeft()==Neighbor.SAME||info.getRight()==Neighbor.SAME);
		}
		if (info.getBack()==Neighbor.SAME){
			return !(info.getLeft()==Neighbor.SAME&&info.getRight()==Neighbor.SAME)
					&&(info.getLeft()==Neighbor.SAME||info.getRight()==Neighbor.SAME);
		}
		return false;
	}
	//returns an action that will turn the critter towards whatever direction
	private Action turnTowards(Direction dir, CritterInfo info){
		Direction facing=info.getDirection();
		if(dir==facing){
			return null;
		}
		if(addDirs(dir, Direction.EAST)==facing){
			return Action.LEFT;
		}
		if(addDirs(dir,Direction.WEST)==facing){
			return Action.RIGHT;
		}
		if(info.getLeft()==Neighbor.EMPTY){
			if(info.getRight()==Neighbor.EMPTY){
				return leftOrRight();	
			}
			return Action.LEFT;
		}
		if(info.getRight()==Neighbor.EMPTY){
			return Action.RIGHT;
		}
		return leftOrRight();
	}
	/*
	 * picks a random one of left and right, and returns it
	 */
	private static Action leftOrRight(){
		return Math.random()<0.5?Action.LEFT:Action.RIGHT;
	}
	@Override
	public Color getColor() {
		if(!thisStep){
			color=0;
			return Color.BLACK;
		}
		return colors[color];
	}
	@Override
	public String toString() {//It flaps.
		//return countNeighbors(info,Neighbor.WALL)>0?(moveCount%2==0?(moveCount%4==0?"|.|":"_._"):"\\./"):(moveCount%2==0?(moveCount%4==0?"\\./":"/'\\"):"-.-");
		return "X";
		//return ""+info.getInfectCount();
	}

}
