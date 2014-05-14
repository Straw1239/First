package critters;
// The Rosebush has two phases. During the first, the "buds" move toward the top-left, stopping if
//they become adjacent to another friendly critter. This starts phase two, in which the critters
//stay in the same spot, but orient themselves outward away from the cluster so they are ready for a fight.

import java.awt.Color;
import java.util.Random;


public class Husky_Pollock1 extends Critter {
	private int phase;
	private String character = "FACE YOUR ROSY DOOM";
	private Color hue = Color.red;
	final static int maxTime = 3;
	final static Random rand = new Random();
	final static int maxInfectCount = 1;
	
	public Husky_Pollock1(){
		this.phase = 1;
	}
    @Override
	public Action getMove(CritterInfo info) {
    	getChar(info);
    	getColor(info);
        
    	//THIS CODE OVERRIDES EVERYTHING ELSE; THE CRITTER WILL ALWAYS INFECT IF IT CAN, AND TURN TOWARDS ENEMY CRITTERS
    	if (info.getFront() == Neighbor.OTHER)
            return Action.INFECT;
        else if (info.getLeft() == Neighbor.OTHER)
        	return Action.LEFT;
        else if (info.getRight() == Neighbor.OTHER)
        	return Action.RIGHT;
        else if (info.getBack() == Neighbor.OTHER)
        	return Action.RIGHT;
       
        else if (info.getRight() == Neighbor.SAME && info.getLeft() == Neighbor.SAME && info.getBack() == Neighbor.SAME && info.getFront() == Neighbor.SAME)
        	return Action.INFECT;
        
    	//--------------------PHASE 1-------------------------//
        else if (phase == 1){
        	if (countNeighbors(info, Neighbor.SAME) >= 1) //&& info.getInfectCount() >= maxInfectCount)
        		this.phase = 2;
        	else if (info.getDirection() == Direction.NORTH || info.getDirection() == Direction.WEST)
        		return goWest(info);
        	else if (info.getDirection() == Direction.SOUTH || info.getDirection() == Direction.EAST)
        		return goEast(info);
        }
    	//--------------------PHASE 2-------------------------//
        else if (phase == 2){ //STAND STILL AND FACE OUTWARD
        	if (info.getFront() != Neighbor.EMPTY)
        		if (info.getLeft() == Neighbor.EMPTY)
        			return Action.LEFT;
        		else
        			return Action.RIGHT;
        	
        	
        	if (countNeighbors(info, Neighbor.EMPTY) >= 3 || (countNeighbors(info, Neighbor.EMPTY) == 2 && countNeighbors(info, Neighbor.WALL) == 1)){
        		if (rand.nextInt(5) == 0) //&& info.getInfectCount() <= maxInfectCount){
        			this.phase = 3; //LAUNCH OFF //UNCOMMENT THESE TO ENABLE SEED PROBES
        			return Action.HOP;
        		
        	}
        	
        	
        	if (info.getLeft() == Neighbor.EMPTY && info.getRight() != Neighbor.EMPTY)
        		return Action.LEFT;
        	else if (info.getRight() == Neighbor.EMPTY && info.getLeft() != Neighbor.EMPTY)
        		return Action.RIGHT;
        	
        	//IF IT'S A STRAGGLER (STICKING OUT)
        	
        }
    	
    	//--------------------PHASE 3-------------------------//
        else if (phase == 3){ //GO AROUND RANDOMLY
        	if (countNeighbors(info, Neighbor.SAME) >= 1)
        		this.phase = 2;
        	else if (info.getFront() != Neighbor.EMPTY)
        		return leftOrRight(info);
        	else if (rand.nextInt(10) == 0)
        		return leftOrRight(info);
        	else
        		return Action.HOP;
        }
        
        
        return Action.INFECT;

    }

    @Override
	public Color getColor() {
        return hue;
    }

    @Override
	public String toString() {
    	return character;
    }
    
    private void getChar(CritterInfo info){
    	if (countNeighbors(info, Neighbor.SAME) >= 3)
    		this.character = "#";
    	else if (countNeighbors(info, Neighbor.SAME) == 0)
    		this.character = "*";
    	else
    		this.character = "@";
    }
    
    private void getColor(CritterInfo info){
    	if (character.equals("#"))
        	hue = new Color(55, 200, 0);
    	else if (character.equals("*"))
    		hue = new Color(128,128,0);
        else{
        	hue = Color.red;
        }
    }
    
    private Action goNorth(CritterInfo info){
    	if(info.getDirection() == Direction.EAST)
    		return Action.LEFT;
    	else if(info.getDirection() == Direction.WEST || info.getDirection() == Direction.SOUTH)
    		return Action.RIGHT;
    	return Action.HOP;
    }
    
    private Action goWest(CritterInfo info){
    	if(info.getDirection() == Direction.NORTH)
    		return Action.LEFT;
    	else if(info.getDirection() == Direction.EAST || info.getDirection() == Direction.SOUTH)
    		return Action.RIGHT;
    	return Action.HOP;
    }
    
    private Action goEast(CritterInfo info){
    	if(info.getDirection() == Direction.NORTH)
    		return Action.RIGHT;
    	else if(info.getDirection() == Direction.WEST || info.getDirection() == Direction.SOUTH)
    		return Action.LEFT;
    	return Action.HOP;
    }
    
    private int countNeighbors(CritterInfo info, Neighbor type){
    	int num = 0;
    	if (info.getFront() == type) num++;
    	if (info.getBack() == type) num++;
    	if (info.getLeft() == type) num++;
    	if (info.getRight() == type) num++;
    	return num;
    }
    
    private Action leftOrRight(CritterInfo info){
    	if (rand.nextInt(2) == 0)
    		return Action.RIGHT;
    	else
    		return Action.LEFT;
    }
}