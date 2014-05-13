package critters;


// This defines a simple class of critters that sit around waiting to be
// taken over by other critters.

import java.awt.*;

public class Food extends Critter {
    @Override
	public Action getMove(CritterInfo info) {
        return Action.INFECT;
    }

    @Override
	public Color getColor() {
        return Color.GREEN;
    }

    @Override
	public String toString() {
        return "F";
    }
}
