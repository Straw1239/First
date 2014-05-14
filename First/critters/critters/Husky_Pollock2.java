package critters;
// The Rosebush has two phases. During the first, the "buds" move toward the top-left, stopping if
//they become adjacent to another friendly critter. This starts phase two, in which the critters
//stay in the same spot, but orient themselves outward away from the cluster so they are ready for a fight.

import java.awt.Color;
import java.util.Random;


public class Husky_Pollock2 extends Critter {
	private String phase;
	private String character = "@";
	private Color hue = Color.red;
	private boolean hopNext = false;
	public static Direction groupDirection = Direction.NORTH;
	final static Random rand = new Random();
	final static int maxInfectCount = 5;
	public static int groupTimer = 0;
	
	public static int critterCount = 0;
	
	public Husky_Pollock2(){
		this.phase = "findFriends";
		critterCount++;
	}
    @Override
	public Action getMove(CritterInfo info) {
    	groupTimer++;
    	getChar(info);
    	getColor(info);
        
    	//THIS CODE OVERRIDES EVERYTHING ELSE; THE CRITTER WILL ALWAYS INFECT IF IT CAN, AND hopNext TOWARDS ENEMY CRITTERS
    	if (info.getFront() == Neighbor.OTHER){
    		groupDirection = info.getDirection();
            return Action.INFECT;
    	}
        else if (info.getLeft() == Neighbor.OTHER)
        	return Action.LEFT;
        else if (info.getRight() == Neighbor.OTHER)
        	return Action.RIGHT;
        else if (info.getBack() == Neighbor.OTHER)
        	return Action.RIGHT;
    	
    	
    	//MAKE A BIGASS CLUMP ON THE SIDE
    	if (groupTimer > 10000){
    		groupTimer = 0;
    		Direction[] d = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
    		groupDirection = d[rand.nextInt(d.length)];
    	}
    		if(groupDirection == Direction.NORTH)
    			return goNorth(info, true);
    		if(groupDirection == Direction.SOUTH)
    			return goSouth(info, true);
    		if(groupDirection == Direction.EAST)
    			return goEast(info, true);
    		if(groupDirection == Direction.WEST)
    			return goWest(info, true);
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
    	else
    		this.character = "@";
    }
    
    private void getColor(CritterInfo info){
    	if (character.equals("#"))
        	hue = new Color(55, 200, 0);
        else{
        	hue = Color.red;
        }
    }
    
    private Action goNorth(CritterInfo info, boolean go){
    	if(info.getDirection() == Direction.EAST)
    		return Action.LEFT;
    	else if(info.getDirection() == Direction.WEST || info.getDirection() == Direction.SOUTH)
    		return Action.RIGHT;
    	if (go == true)
    		return Action.HOP;
    	return Action.INFECT;
    }
    
    private Action goSouth(CritterInfo info, boolean go){
    	if(info.getDirection() == Direction.EAST)
    		return Action.RIGHT;
    	else if(info.getDirection() == Direction.WEST || info.getDirection() == Direction.NORTH)
    		return Action.LEFT;
    	if (go == true)
    		return Action.HOP;
    	return Action.INFECT;
    }
    
    private Action goWest(CritterInfo info, boolean go){
    	if(info.getDirection() == Direction.NORTH)
    		return Action.LEFT;
    	else if(info.getDirection() == Direction.EAST || info.getDirection() == Direction.SOUTH)
    		return Action.RIGHT;
    	if (go == true)
    		return Action.HOP;
    	return Action.INFECT;
    }
    
    private Action goEast(CritterInfo info, boolean go){
    	if(info.getDirection() == Direction.NORTH)
    		return Action.RIGHT;
    	else if(info.getDirection() == Direction.WEST || info.getDirection() == Direction.SOUTH)
    		return Action.LEFT;
    	if (go == true)
    		return Action.HOP;
    	return Action.INFECT;
    }
    
    private int countNeighbors(CritterInfo info, Neighbor type){
    	int num = 0;
    	if (info.getFront() == type) num++;
    	if (info.getBack() == type) num++;
    	if (info.getLeft() == type) num++;
    	if (info.getRight() == type) num++;
    	return num;
    }
    
    private Neighbor getWest(CritterInfo info){
    	if(info.getDirection() == Direction.NORTH)
    		return info.getLeft();
    	if(info.getDirection() == Direction.SOUTH)
    		return info.getRight();
    	if(info.getDirection() == Direction.EAST)
    		return info.getBack();
    	if(info.getDirection() == Direction.WEST)
    		return info.getFront();
    	return Neighbor.EMPTY;
    }
    
    private Neighbor getSouth(CritterInfo info){
    	if(info.getDirection() == Direction.NORTH)
    		return info.getBack();
    	if(info.getDirection() == Direction.SOUTH)
    		return info.getFront();
    	if(info.getDirection() == Direction.EAST)
    		return info.getRight();
    	if(info.getDirection() == Direction.WEST)
    		return info.getLeft();
    	return Neighbor.EMPTY;
    }
    
    private Action leftOrRight(CritterInfo info){
    	if (rand.nextInt(2) == 0)
    		return Action.RIGHT;
    	else
    		return Action.LEFT;
    }
}