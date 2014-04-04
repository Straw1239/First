package chess;

import java.util.ArrayList;

public class Chessboard 
{
	ArrayList<BoardState> history = new ArrayList<>();
	public Chessboard()
	{
		history.add(BoardState.getStartingPosition());
	}
}
