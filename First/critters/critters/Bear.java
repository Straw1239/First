package critters;


import java.awt.Color;

public class Bear extends Critter {
	
	private boolean polar;
	private int numberOfMoves;
	
	public Bear(boolean polar){
		this.polar = polar;
	}
		
	@Override
	public Color getColor() {
		if(this.polar)
			return Color.WHITE;
		return Color.BLACK;
	}
	
	@Override
	public String toString() {
		if(numberOfMoves % 2 == 0)
			return "\\";
		return "/";
	}
	
	@Override
	public Action getMove(CritterInfo info) {
		numberOfMoves++;
		
		if(info.getFront() == Neighbor.OTHER ){
			return Action.INFECT;
		}else if(info.getFront() == Neighbor.EMPTY){
			return Action.HOP;
		}else
			return Action.LEFT;
	}
}
