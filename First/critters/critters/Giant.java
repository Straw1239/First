package critters;
import java.awt.Color;

public class Giant extends Critter {
	private static int numberOfMoves;

	public Giant() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		numberOfMoves = (numberOfMoves == 23)? 0 : numberOfMoves++;
		if(numberOfMoves < 6)
			return "fee";
		else if(numberOfMoves < 12)
			return "fie";
		else if(numberOfMoves < 18)
			return "foe";
		else 
			return "fum";
	}
	
	@Override
	public Color getColor() {
		return Color.GRAY;
	}
	
	@Override
	public Action getMove(CritterInfo info) {
		if(info.getFront() == Neighbor.OTHER ){
			return Action.INFECT;
		}else if(info.getFront() == Neighbor.EMPTY){
			return Action.HOP;
		}else
			return Action.LEFT;
	}

}
