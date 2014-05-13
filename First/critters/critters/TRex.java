package critters;


// A simple critter that infects when possible and otherwise randomly picks
// among the other three moves.

import java.awt.*;
import java.util.*;

public class TRex extends Critter {
    private Random r;
	
    public TRex() {
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
        return Color.GREEN;
    }

    @Override
	public String toString() {
        return "~R~";
    }
}
