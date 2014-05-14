package critters;
// The Rosebush has two phases. During the first, the "buds" move toward the top-left, stopping if
//they become adjacent to another friendly critter. This starts phase two, in which the critters
//stay in the same spot, but orient themselves outward away from the cluster so they are ready for a fight.

import java.awt.Color;
import java.util.Random;

public class Husky_Pollock_Rosebush3 extends Critter {
	private String character = "@";
	private Color hue = Color.red;
	public static Direction groupDirection = Direction.NORTH;
	final static Random rand = new Random();
	final static int maxInfectCount = 5;
	public static int groupTimer = 0;
	public static int maxTimer = 1000;
	public static int timesTurned = 0;
	
	public Husky_Pollock_Rosebush3(){
	}
	
    @Override
	public Action getMove(CritterInfo info) {
    	groupTimer++;
    	getChar(info);
    	getColor(info);
    	Direction[] d = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
        
    	//THIS CODE OVERRIDES EVERYTHING ELSE; THE CRITTER WILL ALWAYS INFECT IF IT CAN, AND hopNext TOWARDS ENEMY CRITTERS
    	if (info.getFront() == Neighbor.OTHER){
    		groupDirection = info.getDirection();
        	groupTimer = 0;
            return Action.INFECT;
    	}
    	
    	
    	//TURN TOWARD ENEMIES AND KICK THEIR STUPID ASSES
        else if (info.getLeft() == Neighbor.OTHER){
        	groupTimer = 0;
        	return Action.LEFT;}
        else if (info.getRight() == Neighbor.OTHER){
        	groupTimer = 0;
        	return Action.RIGHT;}
        else if (info.getBack() == Neighbor.OTHER){
        	groupTimer = 0;
        	return Action.RIGHT;}
    	
    	
    	//MOVE THE CLUMP ONCE IN A WHILE TO SPICE THINGS UP
    	if (groupTimer > maxTimer && info.getFront() == Neighbor.WALL){
    		maxTimer += 100;
    		groupTimer = 0;
    		timesTurned++;
    		if (timesTurned == 1)
    			groupDirection = Direction.SOUTH;
    		else if (timesTurned == 2)
    			groupDirection = Direction.EAST;
    		else
    			groupDirection = d[rand.nextInt(d.length)];
    	}
    	
    	//GO IN THE GROUP DIRECTION BECAUSE YOU'RE SUCH A PEER PRESSURE SHEEP
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

}