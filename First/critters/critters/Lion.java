package critters;
import java.awt.Color;
import java.util.Random;

public class Lion extends Critter {
	private static Random rand;
	
	public Lion() {
		rand = new Random();
	}
	
	@Override
	public Action getMove(CritterInfo info) {
        if (info.getFront() == Neighbor.OTHER) {
            return Action.INFECT;
        } 
        else if ((info.getFront() == Neighbor.WALL)
        		|| (info.getRight() == Neighbor.WALL)){
            return Action.LEFT;
        } 
        else if (info.getFront() == Neighbor.SAME)
        	return Action.RIGHT;
        else
        	return Action.HOP;
    }

    @Override
	public Color getColor() {
        int which = rand.nextInt(3);
        if (which == 0) {
            return Color.RED;
        } else if (which == 1) {
            return Color.GREEN;
        } else {  // which == 2
            return Color.BLUE;
        }
    }

    @Override
	public String toString() {
        return "L";
    }
}
