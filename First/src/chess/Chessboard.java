package chess;

import java.util.ArrayList;

public class Chessboard 
{
	ArrayList<State> history = new ArrayList<>();
	public Chessboard()
	{
		history.add(State.getStartingPosition());
	}
}
