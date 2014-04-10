package chess;

public class BoardBuilder 
{
	public Square[][] board;
	int turns = 0;
	boolean[] hasInvalidatedCastling = new boolean[4];
	char enPassant = 'x';
	boolean turn;
	public BoardBuilder()
	{
		
	}
	public BoardBuilder(BoardState b)
	{
		board = new Square[8][8];
		for(int i = 0; i < 8;i++)
		{
			for(int j = 0; j < 8;j++)
			{
				board[i][j] = b.getSquare(i, j);
			}
		}
		turns = b.movesTillStalemate();
		hasInvalidatedCastling = b.castlingRights();
	}
	public void addPiece(Piece p, int rank, int file)
	{
		board[rank][file].place(p);
	}
	public void removePiece(int rank, int file)
	{
		board[rank][file].remove();
	}
	public void setTurns(int turns)
	{
		this.turns = turns;
	}
	public void setEnPassantFile(char file)
	{
		enPassant = file;
	}
	public void setCastlingRights(boolean[] castlingRights)
	{
		hasInvalidatedCastling = castlingRights;
	}
	public void setTurn(boolean turn)
	{
		this.turn = turn;
	}
	public BoardState getBoardState()
	{
		return new BoardState(board,turn,turns,enPassant,hasInvalidatedCastling);
	}
}
