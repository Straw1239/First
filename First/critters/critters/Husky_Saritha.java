package critters;

//Saritha Beauchamp
//advocate4tigers@gmail.com
import java.awt.*;
public class Husky_Saritha extends Critter{
	public Husky_Saritha(){
		
	}
	public Color getColor(){
		return Color.YELLOW;
	}
	public String toString(){
		return "H";
	}
	public Action getMove(CritterInfo info){
		if(info.getFront()==(Neighbor.OTHER)){
			return Action.INFECT;
		} if(info.getFront()==(Neighbor.EMPTY)&&info.getLeft().equals(Neighbor.EMPTY)&&info.getRight().equals(Neighbor.EMPTY)){
			return Action.HOP;
		}
		if(info.getFront()==(Neighbor.SAME)||info.getRight()==(Neighbor.SAME)||info.getLeft().equals(Neighbor.OTHER)){
			return Action.LEFT;
		} if(info.getFront()==(Neighbor.WALL)||info.getLeft().equals(Neighbor.SAME)){//||info.getRight().equals(Neighbor.OTHER)){
			return Action.RIGHT;
		}
		return Action.RIGHT;
	}

}
