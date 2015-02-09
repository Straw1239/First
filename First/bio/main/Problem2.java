package main;

import java.util.Scanner;

import util.Utils;

public class Problem2
{
	static byte[][] board = new byte[10][10];
	public static void main(String[] args)
	{
		Utils.init();
		Scanner console = new Scanner(System.in);
		int a = console.nextInt();
		int c = console.nextInt();
		int m = console.nextInt();
		console.close();
		int r = 0;
		for(int i = 4; i > 0; i--)
		{
			for(int j = 0; j < 5 - i; j++) r = placeShip(a, c, m, r, i, board);
		}
		
		
	}
	
	private static int placeShip(int a, int c, int m, int r, int length, byte[][] board)
	{
		while(true)
		{
			r = (a * r + c) % m;
			int x = r % 10;
			int y = (r % 100) / 10;
			r = (a * r + c) % m;
			int rMod2 = r % 2;
			Axis d = (rMod2 == 0) ? Axis.HORIZONTAL : Axis.VERTICAL;
			if(isValidPlacement(board, x, y, d, length))
			{
				System.out.println(x + " " + y + " " + (rMod2 == 0 ? "H" : "V"));
				for(int i = 0; i < length; i++)
				{
					board[x][y] = (byte)(rMod2 + 1);
					switch(d)
					{
					case HORIZONTAL: x++;
						break;
					case VERTICAL: y++;
						break;
					default:
						throw new AssertionError();
					
					}
				}
				return r;
			}
		}
	}
	
	private static boolean isValidPlacement(byte[][] board, int x, int y, Axis d, int length)
	{
		for(int i = 0; i < length; i++)
		{
			if(x < 0 || x >= board.length) return false;
			if(y < 0 || y >= board[x].length) return false;
			if(!isValidSquare(board, x, y)) return false;
			switch(d)
			{
			case HORIZONTAL: x++;
				break;
			case VERTICAL: y++;
				break;
			default:
				throw new AssertionError();
			
			}
		}
		return true;
	}
	
	private static boolean isValidSquare(byte[][] board, int x, int y)
	{
		for(int i = -1; i < 2; i++)
		{
			int cX = x + i;
			if(cX < board.length && cX >= 0)
			for(int j = -1; j < 2; j++)
			{
				int cY = y + j;
				if(cY >= 0 && cY < board[cX].length)
				{
					if(board[cX][cY] != 0) return false;
				}
			}
		}
		return true;
	}
}
