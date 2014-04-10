package chess;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class BoardState 
{
	private Square[][] board = new Square[8][8];
	private ArrayList<PiecePosition>[] pieceList = new ArrayList[2];
	private boolean turn;
	private int moveCount;
	private char enPassant;
	private boolean[] hasInvalidatedCastling = new boolean[4];
	public BoardState(ArrayList<PiecePosition>[] pieces)
	{
		pieceList = pieces;
		updateBoard();
		turn = true;
		moveCount = 0;
		enPassant = 'x';
	}
	public Collection<BoardState> castling()
	{
		Collection<BoardState> results = new ArrayList<>(4);
		for(int i = 0; i < 8; i += 7)
		{
			for(int j = 0; j < 8; j += 7)
			{
				if(!hasInvalidatedCastling[((i == 0) ? 0 : 2) + ((j == 0)? 0 : 1)])
				{
					boolean castlingPossible = true;
					for(int k = j; k != 4; k = (j == 0)? k + 1 : k -1)
					{
						if(!board[i][k].isEmpty())
						{
							castlingPossible = false;
							break;
						}
					}
					for(int k = j; k != 4; k = (j == 0)? k + 1 : k -1)
					{
						
					}
					
				}
			}
		}
		return results;
	}
	public Collection<BoardState> getAllMoves()
	{
		ArrayList<BoardState> results = new ArrayList<>();
		int white = (turn) ? 0 : 1;
		ArrayList<PiecePosition> movables = pieceList[white];
		for(int i = 0; i < movables.size();i++)
		{
			PiecePosition p = movables.get(i);
			results.addAll(ChessPiece.possibleMoves(this, p.rank, p.file));
		}
		return results;
	}
	public void drawBoard(Graphics g, int size)
	{
		int width = size/8;
		Color lightColor = Color.lightGray, darkColor = Color.darkGray;
		for(int i = 0; i < 8;i++ )
		{
			for(int j = 0; j < 8;j++)
			{
				Color color = ((i + j) % 2 == 0) ? lightColor : darkColor;
				g.setColor(color);
				g.fillRect(i * width, j * width, width, width);
			}
		}
		for(int i = 0; i < 2; i++)
		{
			ArrayList<PiecePosition> pieces = pieceList[i];
			for(int j = 0; j < pieces.size();j++) 
			{
				PiecePosition piece = pieces.get(j);
				ChessPiece.drawPiece(g, piece.rank, piece.file, piece.piece.type, piece.piece.isWhite, width);
			}
		}
	}	
	
	public boolean isTranspositionOf(BoardState b)
	{
		for(int i = 0; i < 8;i++)
		{
			for(int j = 0; j < 8;j++)
			{
				if(!board[i][j].equals( b.getSquare(i, j))) 
				{
					return false; 
				}
			}
		}
		if(turn != b.isWhiteToPlay()) return false;
		return true;
	}
	public boolean isWhiteToPlay()
	{
		return turn;
	}
	public char enPassantColumn()
	{
		return enPassant;
	}
	public boolean[] castlingRights()
	{
		return Arrays.copyOf(hasInvalidatedCastling, hasInvalidatedCastling.length);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(board);
		result = prime * result + enPassant;
		result = prime * result + Arrays.hashCode(hasInvalidatedCastling);
		result = prime * result + moveCount;
		result = prime * result + Arrays.hashCode(pieceList);
		result = prime * result + (turn ? 1231 : 1237);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BoardState))
			return false;
		BoardState other = (BoardState) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		if (enPassant != other.enPassant)
			return false;
		if (!Arrays.equals(hasInvalidatedCastling, other.hasInvalidatedCastling))
			return false;
		if (moveCount != other.moveCount)
			return false;
		if (!Arrays.equals(pieceList, other.pieceList))
			return false;
		if (turn != other.turn)
			return false;
		return true;
	}
	public int movesTillStalemate()
	{
		return moveCount;
	}
	public BoardState(Square[][] board)
	{
		
	}
	public BoardState(Square[][] board, boolean turn, int moveCount, char enPassant, boolean[] hasInvalidatedCastling)
	{
		this.board  = board;
		this.turn = turn;
		this.moveCount = moveCount;
		this.enPassant = enPassant;
		this.hasInvalidatedCastling = Arrays.copyOf(hasInvalidatedCastling, 4);
		updatePieceList();
	}
	public BoardState(String state)
	{
		
	}
	public Square getSquare(int rank, int file)
	{
		return board[rank][file].copy();
	}
	private void updateBoard()
	{
		for(int i = 0; i < 8;i++)
		for(int j = 0; j < 8;j++) board[i][j] = new Square();
		
		for(int i = 0; i < pieceList.length;i++)
		{
			for(int j = 0; j < pieceList[i].size();j++)
			{
				PiecePosition p = pieceList[i].get(j);
				board[p.rank][p.file] = new Square(p.piece);
			}
		}
	}
	public boolean isWhiteinCheck()
	{
		PiecePosition king = null;
		for(int i = 0; i < pieceList[0].size();i++)
		{
			if(pieceList[0].get(i).piece.type == ChessPiece.King)
			{
				king = pieceList[0].get(i);
			}
		}
		if(king == null) throw new IllegalStateException();
		return isAttackedBy(!king.piece.isWhite,king.rank,king.file);
	}
	public boolean isBlackinCheck()
	{
		PiecePosition king = null;
		for(int i = 0; i < pieceList[1].size();i++)
		{
			if(pieceList[1].get(i).piece.type == ChessPiece.King)
			{
				king = pieceList[1].get(i);
			}
		}
		if(king == null) throw new IllegalStateException();
		return isAttackedBy(!king.piece.isWhite,king.rank,king.file);
	}
	private boolean isAttackedBy(boolean white, int rank, int file)
	{
		if(isAttackedByRook(white,rank,file)) return true;
		if(isAttackedByKnight(white,rank,file)) return true;
		if(isAttackedDiagonally(white,rank,file)) return true;
		if(isAttackedByPawn(white,rank,file)) return true;
		if(isAttackedByKing(white,rank,file)) return true;
		return false;
		
		
		
		
		
	}
	private boolean isAttackedByKing(boolean white, int rank, int file)
	{
		
		for(int i = -1; i < 2; i++)
		{
			for(int j = -1; j < 2; j++)
			{
				if(i != 0 || j != 0)
				{
					int r = rank + i, f = file + j;
					if(isOnBoard(r,f))
					{
						Piece p = board[r][f].occupant;
						if(p != null)
						if(p.isWhite == white)
						if(p.type == ChessPiece.King)
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	private boolean isAttackedByPawn(boolean white, int rank, int file)
	{
		int direction = white ? -1 : 1;
		int[][] squares = new int[2][2];
		squares[0] = new int[]{rank + direction, file + 1};
		squares[1] = new int[]{rank + direction, file - 1};
		for(int i = 0; i < squares.length;i++)
		{
			int[] c = squares[i];
			if(isOnBoard(c[0],c[1]))
			{
				Piece p = board[c[0]][c[1]].occupant;
				if(p != null)
				if(p.isWhite == white)
				if(p.type == ChessPiece.Pawn)
				{
					return true;
				}
			}
		}
		return false;
	}
	private boolean isAttackedByRook(boolean white, int rank, int file)
	{
		int[] c = new int[2];
		for(int k = 0; k < 2;k++)
		{
			c[0] = rank;
			c[1] = file;
			for(int i = -1; i < 2; i += 2)
			{
				while(isOnBoard(c[0],c[1]) && board[c[0]][c[1]].isEmpty())
				{
					 c[k] += i;
				}
				if(isOnBoard(c[0],c[1]))
				{
					Piece p = board[c[0]][c[1]].occupant;
					if(p.isWhite == white)
					if(p.type == ChessPiece.Rook || p.type == ChessPiece.Queen)
					{
						return true;
				 	}
				}
			}
		}
		return false;
	}			
	private boolean isAttackedDiagonally(boolean white, int rank, int file)
	{
		for(int i = -1; i < 2; i += 2)
		{
			for(int j = -1; j < 2; j += 2)
			{
				int r = rank + i, f = file + j;
				while(isOnBoard(r,f) && board[r][f].isEmpty())
				{
					 r += i;
					 f += j; 
				}
				if(isOnBoard(r,f))
				{
					
					 
					 Piece p = board[r][f].occupant;
					 if(p.isWhite == white)
					 if(p.type == ChessPiece.Bishop || p.type == ChessPiece.Queen)
					 {
						 return true;
					 } 
				}
			}
		}
		return false;
	}
	private boolean isAttackedByKnight(boolean white, int rank, int file)
	{
		for(int i = -1; i < 2;i += 2)
		{
			for(int j = -2; j < 3; j += 4)
			{
				if(isOnBoard(rank + i,file + j))
				{
					Piece occupant = board[rank+i][file+j].occupant;
					if(occupant != null)
					if(occupant.isWhite == white)
					{
						if(occupant.type == ChessPiece.Knight)
						{
							return true;
						}
					}
				}
				if(isOnBoard(rank + j,file + i))
				{
					Piece occupant = board[rank+j][file+i].occupant;
					if(occupant != null)
					if(occupant.isWhite == white)
					{
						if(occupant.type == ChessPiece.Knight)
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	public boolean isOnBoard(int rank, int file)
	{
		if(rank < 0 || rank > 7) return false;
		if(file < 0 || file > 7) return false;
		return true;
	}
	public BoardState move(int startRank,int startFile,int rank, int file)
	{
		if(board[startRank][startFile].isEmpty()) return this;
		BoardBuilder b = new BoardBuilder(this);
		b.setTurn(!turn);
		
		
		Piece p = board[startRank][startFile].occupant;
		b.removePiece(startRank, startFile);
		b.removePiece(rank, file);
		b.addPiece(p, rank, file);
		updatePieceList();
		return b.getBoardState();
	}
	private void updatePieceList()
	{
		for(int i = 0; i < pieceList.length;i++)
		pieceList[i] = new ArrayList<PiecePosition>();
		for(int i = 0; i < 8;i++)
			for(int j = 0; j < 8;j++)
			{
				Square s = board[i][j];
				if(!s.isEmpty())
				{
					Piece piece = s.occupant;
					if(piece.isWhite)
					{
						pieceList[0].add(new PiecePosition(piece,i,j));
					}
					else
					{
						pieceList[1].add(new PiecePosition(piece,i,j));
					}
				}
			}
	}
	public static BoardState getStartingPosition()
	{
		ArrayList<PiecePosition>[] pieces = new ArrayList[2];
		for(int i = 0; i < pieces.length;i++) pieces[i] = new ArrayList<>();
		for(int i = 0; i < pieces.length;i++)
		{
			int rank = 7 * i;
			boolean isWhite = (i == 0) ? true:false;
			pieces[i].add(new PiecePosition(new Piece(isWhite,ChessPiece.King),rank,4));
			pieces[i].add(new PiecePosition(new Piece(isWhite,ChessPiece.Rook),rank,0));
			pieces[i].add(new PiecePosition(new Piece(isWhite,ChessPiece.Knight),rank,1));
			pieces[i].add(new PiecePosition(new Piece(isWhite,ChessPiece.Bishop),rank,2));
			pieces[i].add(new PiecePosition(new Piece(isWhite,ChessPiece.Queen),rank,3));
			pieces[i].add(new PiecePosition(new Piece(isWhite,ChessPiece.Bishop),rank,5));
			pieces[i].add(new PiecePosition(new Piece(isWhite,ChessPiece.Knight),rank,6));
			pieces[i].add(new PiecePosition(new Piece(isWhite,ChessPiece.Rook),rank,7));	
		}
		for(int i = 0; i < 8;i++)
		{
			pieces[0].add(new PiecePosition(new Piece(true,ChessPiece.Pawn),1,i));
			pieces[1].add(new PiecePosition(new Piece(false,ChessPiece.Pawn),6,i));
		}
		return new BoardState(pieces);
	}
	private static class PiecePosition
	{
		public int rank,file;
		public Piece piece;
		public PiecePosition(Piece p, int rank, int file)
		{
			this.rank = rank;
			this.file = file;
			this.piece = p;
		}
		public int hashCode()
		{
			int hash = rank * 31 + file;
			int multiplier;
			switch (piece.type)
			{
			case Pawn: multiplier = 2; break;
			case Knight: multiplier = 3; break;
			case Bishop: multiplier = 5; break;
			case Rook: multiplier = 7; break;
			case Queen: multiplier = 11; break;
			case King: multiplier = 13; break;
			default: multiplier = 1;
			}
			hash = hash * multiplier + ((piece.isWhite)? 17:100000001);
			return hash;
			
			
		}
	}
}
