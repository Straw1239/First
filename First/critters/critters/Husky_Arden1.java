package critters;

import java.awt.Color;
import java.util.Random;


public class Husky_Arden1 extends Critter{
	public Husky_Arden1(){
		
	}

	public String toString(){
		return "Ӝ";
	}

	public Action getMove(CritterInfo info){
		if(info.getFront() == Neighbor.OTHER)
			return Action.INFECT;
		else if(info.getLeft() == Neighbor.OTHER)
			return Action.LEFT;
		else if(info.getBack() == Neighbor.OTHER)
			return Action.LEFT;
		else if(info.getRight() == Neighbor.OTHER)
			return Action.RIGHT;
		else if(info.getFront() == Neighbor.WALL)
			return Action.LEFT;
		else if(info.getFront() == Neighbor.EMPTY)
			return Action.HOP;
		else if(info.getLeft() == Neighbor.WALL)
			return Action.RIGHT;
		else if(info.getRight() == Neighbor.WALL)
			return Action.LEFT;
		else if(info.getRight() == Neighbor.WALL)
			return Action.LEFT;
		else return Action.LEFT;
	}

	

	public Color getColor(){
		return new Color(new Random().nextInt(256),new Random().nextInt(256),new Random().nextInt(256));
	}
}