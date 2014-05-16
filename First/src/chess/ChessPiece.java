package chess;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;

public enum ChessPiece 
{
	 King,Queen,Rook,Bishop,Knight,Pawn;
	 public static void drawPiece(Graphics g, int rank, int file, ChessPiece p, boolean isWhite, int size)
	 {
		 int x = file * size + size/3, y = (8-rank) * size - size/2;
		 g.setFont(new Font("TimesRoman",Font.PLAIN,size/5));
		 g.setColor((isWhite) ? Color.white : Color.black);
		 g.drawString(p.toString(), x, y);
	 }
	 public static Collection<State> possibleMoves(State board,int rank, int file)
	 {
		 if(board.getSquare(rank,file).isEmpty()) throw new IllegalArgumentException();
		 Piece p = board.getSquare(rank,file).occupant;
		 ChessPiece piece = p.type;
		 boolean isWhite = p.isWhite;
		 switch (piece)
		 {
		 case King: return kingMoves(board,rank,file,isWhite);
		 case Queen: return queenMoves(board,rank,file, isWhite);
		 case Rook: return rookMoves(board,rank,file,isWhite);
		 case Bishop: return bishopMoves(board,rank,file,isWhite);
		 case Knight: return knightMoves(board,rank,file,isWhite);
		 case Pawn: return pawnMoves(board,rank,file,isWhite); 
		 default: return null;
		 }
		 
		 
	 }
	
	 public static Collection<State> kingMoves(State board,int rank, int file,boolean isWhite)
	 {
		 Collection<State> results = board.castling();
		 for(int i = -1; i < 2; i++)
		 {
			 for(int j = -1; j < 2; j++)
			 {
				 if(i != 0 || j != 0)
				 {
					 int r = rank + i, f = file + j;
					 if(board.isOnBoard(r,f))
					 {
						 Square s = board.getSquare(r, f);
						 if(s.isEmpty() || s.occupant.isWhite != isWhite)
						 {
							 State temp = board.move(rank, file, r, f);
							 if(!(isWhite ? temp.isWhiteinCheck() : temp.isBlackinCheck()))
							 {
								 results.add(temp);
							 }
						 }
					 }
				 }
			 }
		 }
		 return results;
	 }
	 public static Collection<State> queenMoves(State board, int rank, int file, boolean isWhite)
	 {
		 Collection<State> moves = rookMoves(board,rank,file,isWhite);
		 moves.addAll(bishopMoves(board,rank,file,isWhite));
		 return moves;
	 }
	 public static Collection<State> rookMoves(State board, int rank, int file, boolean isWhite)
	 {
		 Collection<State> results = new ArrayList<>();
		 int[] rf = new int[2];
		 for(int i = 0; i < 2; i++)
		 {
			 for(int j = -1; j < 2 ; j += 2)
			 {
				 rf[0] = rank;
				 rf[1] = file;
				 rf[i] += j;
				 while(board.isOnBoard(rf[0], rf[1]) && board.getSquare(rf[0], rf[1]).isEmpty())
				 {
					State temp = board.move(rank, file, rf[0], rf[1]);
					if(!(isWhite? temp.isWhiteinCheck() : temp.isBlackinCheck()))
					{
						results.add(temp);
					}
					rf[i] += j;
				 }
				 if(board.isOnBoard(rf[0],rf[1]))
				 {
					if(board.getSquare(rf[0], rf[1]).occupant.isWhite != isWhite)
					{
						State temp = board.move(rank, file, rf[0], rf[1]);
						if(!(isWhite? temp.isWhiteinCheck() : temp.isBlackinCheck()))
						{
							 results.add(temp);
						}
					}
						 
					
				 }
			 }
		 }
		 return results;
	 }
	 public static Collection<State> bishopMoves(State board, int rank, int file, boolean isWhite)
	 {
		 ArrayList<State> results = new ArrayList<>();
		 for(int i = -1; i < 2; i += 2)
		 {
			 for(int j = -1; j < 2; j += 2)
			 {
				 int r = rank + i, f = file + j;
				 while(board.isOnBoard(r, f) && board.getSquare(r, f).isEmpty())
				 {
					 State temp = board.move(rank, file, r, f);
					 if(!(isWhite? temp.isWhiteinCheck() : temp.isBlackinCheck()))
					 {
						 results.add(temp);
					 }
					 r += i;
					 f += j;
				 }
				 if(board.isOnBoard(r,f))
				 {
					 Piece p = board.getSquare(r, f).occupant;
					 if(isWhite != p.isWhite)
					 {
						 State temp = board.move(rank, file, r, f);
						 if(!(isWhite ? temp.isWhiteinCheck() : temp.isBlackinCheck()))
						 {
							 results.add(temp);
						 }
					 }
				 }
				 
			 }
		 }
		 return results;
	 }
	 public static Collection<State> knightMoves(State board, int rank, int file, boolean isWhite)
	 {
		 ArrayList<State> results = new ArrayList<>(8);
		 for(int i = -1; i < 2; i += 2)
		 {
			 for(int j = -2; j < 3; j += 4)
			 {
				 int r = rank + i, f = file + j;
				 if(board.isOnBoard(r, f))
				 {
					 Square s = board.getSquare(r, f);
					 if(!s.isEmpty())
					 {
						 if(s.occupant.isWhite != isWhite)
						 {
							 State temp = board.move(rank, file, r, f);
							 if(!(isWhite ? temp.isWhiteinCheck() : temp.isBlackinCheck()))
							 {
								 results.add(temp);
							 }
						 }
					 }
					 else
					 {
						 State temp = board.move(rank, file, r, f);
						 if(!(isWhite ? temp.isWhiteinCheck() : temp.isBlackinCheck()))
						 {
							 results.add(temp);
						 }
					 }
						 
				 }
				 r = rank + j;
				 f = file + i;
				 if(board.isOnBoard(r, f))
				 {
					 Square s = board.getSquare(r, f);
					 if(!s.isEmpty())
					 {
						 if(s.occupant.isWhite != isWhite)
						 {
							 State temp = board.move(rank, file, r, f);
							 if(!(isWhite ? temp.isWhiteinCheck() : temp.isBlackinCheck()))
							 {
								 results.add(temp);
							 }
						 }
					 }
					 else 
					 {
						 State temp = board.move(rank, file, r, f);
						 if(!(isWhite ? temp.isWhiteinCheck() : temp.isBlackinCheck()))
						 {
							 results.add(temp);
						 }
					 }
				 }
			 }
		 }
		 return results;
	 }
	 public static Collection<State> pawnMoves(State board, int rank, int file, boolean isWhite)
	 {
		 ArrayList<State> results = new ArrayList<>(4);
		 int direction = (isWhite) ? 1 : -1;
		 int r,f;
		 for(int i = -1; i < 2; i += 2)
		 {
			 r = rank + direction;
			 f = file + i;
			 if(board.isOnBoard(r, f))
			 {
				 Square s = board.getSquare(r, f);
				 if(s.isEmpty() || s.occupant.isWhite != isWhite)
				 {
					 State temp = board.move(rank, file, r, f);
					 if(!(isWhite ? temp.isWhiteinCheck() : temp.isBlackinCheck()))
					 {
						 results.add(temp);
					 }
				 }
			 }
		 }
		 // Forward motion, en passant, 2 move from starting rank, and promotion
		 return results;
	 }
	 
	
}
