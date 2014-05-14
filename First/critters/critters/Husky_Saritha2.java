package critters;

//Saritha Beauchamp
//advocate4tigers@gmail.com
import java.awt.Color;
public class Husky_Saritha2 extends Critter{
	private boolean swarm;
	public Husky_Saritha2(){
		swarm=false;
	}
	@Override
	public Color getColor(){
		return Color.YELLOW;
	}
	@Override
	public String toString(){
		return "H";
	}
	@Override
	public Action getMove(CritterInfo info){
		if(info.getFront()==(Neighbor.OTHER)){
			return Action.INFECT;
		}
		if(swarm==false){
		if(info.getLeft()==Neighbor.SAME && info.getRight()==Neighbor.SAME&&info.getBack()==Neighbor.SAME){
			return Action.INFECT;
		} if(info.getFront()==(Neighbor.EMPTY)&&info.getLeft().equals(Neighbor.EMPTY)&&info.getRight().equals(Neighbor.EMPTY)){
			swarm=false;
			return Action.HOP;
		}
		if(info.getFront()==(Neighbor.SAME)||info.getRight()==(Neighbor.SAME)||info.getLeft().equals(Neighbor.OTHER)){
			return Action.LEFT;
		} if(info.getFront()==(Neighbor.WALL)||info.getLeft().equals(Neighbor.SAME)||info.getRight().equals(Neighbor.OTHER)){
			return Action.RIGHT;
		} if(info.getLeft()==Neighbor.SAME && info.getRight()==Neighbor.SAME&&info.getBack()==Neighbor.SAME){
			swarm=true;
			return Action.INFECT;
		} if(swarm==true){
			int x =0;
			if(info.getFront()==(Neighbor.SAME)){
				x++;
			} if(info.getLeft()==Neighbor.SAME){
				x++;
			}if(info.getRight()==Neighbor.SAME){
				x++;
			}if(info.getBack()==Neighbor.SAME){
				x++;
			}
			 if(info.getLeft()==Neighbor.SAME && info.getRight()==Neighbor.SAME&&info.getBack()==Neighbor.SAME){
					swarm=true;
					return Action.INFECT;
			 } if(info.getFront()==Neighbor.SAME&&x<1){
				 swarm=false;
				 return Action.LEFT;
			 } if(x<=1){
				 swarm=false;
				 if(info.getFront()==Neighbor.SAME){
					 return Action.RIGHT;
				 }
				 if((info.getFront()==Neighbor.EMPTY&&info.getRight()==Neighbor.SAME)||(info.getFront()==Neighbor.EMPTY&&info.getLeft()==Neighbor.SAME)){
					 return Action.HOP;
				 } if(info.getBack()==Neighbor.SAME){
					 return Action.LEFT;
				 }
			 }
		}

			 }
		 return Action.LEFT;
	}

}
