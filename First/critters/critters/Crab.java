package critters;


// A simple critter that infects when possible and otherwise randomly picks
// among the other three moves.

import java.awt.Color;
import java.util.Random;

public class Crab extends Critter {
    private Random r;

    public Crab() {
        r = new Random();
    }
        
    @Override
	public Action getMove(CritterInfo info) {
        if (info.getFront() == Neighbor.OTHER) {
            return Action.INFECT;
        } else { // randomly pick one of the other three
            int which = r.nextInt(3);
            if (which == 0) {
                return Action.HOP;
            } else if (which == 1) {
                return Action.LEFT;
            } else {  // which == 2
                return Action.RIGHT;
            }
        }
    }

    @Override
	public Color getColor() {
        return Color.MAGENTA;
    }

    @Override
	public String toString() {
        return "<X>";
    }
}
