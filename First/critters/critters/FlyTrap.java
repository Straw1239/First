package critters;


// This defines a simple class of critters that infect whenever they can and
// otherwise just spin around, looking for critters to infect.  This simple
// strategy turns out to be surpisingly successful.

import java.awt.Color;

public class FlyTrap extends Critter {
    @Override
	public Action getMove(CritterInfo info) {
        if (info.getFront() == Neighbor.OTHER) {
            return Action.INFECT;
        } else {
            return Action.LEFT;
        }
    }

    @Override
	public Color getColor() {
        return Color.RED;
    }

    @Override
	public String toString() {
        return "T";
    }
}