package numbers;

import java.util.Scanner;

import util.Random;

public class LongStates
{
	/**
	 * long bit mask for the final nibble (4 bits) 
	 */
	private static final long L0NIBB = 0B1111L;
	private static final long L1NIBB = L0NIBB << 4;
	private static final long L2NIBB = L1NIBB << 4;
	private static final long L3NIBB = L2NIBB << 4;
	
	public static long fromFastState(FastState s)
	{
		long result = 0;
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++)
			{
				result = setSquare(result, 3 - i, j, s.getSquare(i, j));
			}
		return result;
	}
	public static void main(String[] args)
	{
		long board = of(new int[][]{{1, 0, 2, 2}, {3, 5, 4, 1}, {0, 0, 1, 2}, {1, 6, 4, 3}});
		System.out.printf("%x\n", board);
		System.out.println(toString(board));
		System.out.println(toString(board = moveLeft(board)));
		System.out.println(toString(board = moveUp(board)));
		System.out.println(toString(board = moveDown(board)));
		System.out.println(toString(board = moveRight(board)));
		System.out.println(numTiles(board));
	}
	public static long of(int[][] board)
	{
		long result = 0;
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++)
			{
				result = setSquare(result, i, j, board[i][j]);
			}
		return result;
	}
	
	public static String toString(long board)
	{
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				result.append(squareAt(board, i, j));
				result.append(" ");
			}
			result.append("\n");
		}
		return result.toString();
	}
	
	public static long loadBoard(Scanner s)
	{
		long result = 0;
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				result = setSquare(result, i, j, s.nextInt());
			}
		}
		return result;
	}
	
	public static int squareAt(long board, int row, int column)
	{
		return ((int)(board >>> (((row << 2) + column) << 2))) & 0xF;
	}
	
	public static long setSquare(long board, int row, int column, int value)
	{
		int index = 4 * (4 * row + column);
		long data = (value & 0B00001111L) << index;
		long mask = ~(L0NIBB << index);
		return (board & mask) | data;
	}
	private static short calculate(short column)
	{
		boolean[] combinable = {true, true, true, true};
		for(int i = 1; i < 4; i++)
		{
			int j = i;
			while(j > 0 && ((column >>> (4 * (j - 1))) & (0xF)) == 0) j--;
			int square = ((column >>> (4 * i)) & (0xF));
			if(j > 0 && combinable[j - 1] && square == ((column >>> (4 * (j - 1))) & (0xF)))
			{
				combinable[j - 1] = false;
				column = setSquare(column, j - 1, square + 1);
				column = setSquare(column, i, 0);
			}
			else if(j != i)
			{
				column = setSquare(column, j, square);
				column = setSquare(column, i, 0);
			}
		}
		return column;
		
	}
	public static int score(long board)
	{
		int score = 0;
		for(int i = 0; i < 4;i++)
			for(int j = 0; j < 4;j++)
			{
				int s = squareAt(board, i, j);
				if(s != 0)
				{
					score +=  (1 << s) * (s - 1);
				}
			}
		return score;
	}
	
	private static int squareAt(short row, int index)
	{
		return ((row >>> (4 * index)) & (0xF));
	}
	
	private static short setSquare(short row, int index, int value)
	{
		index *= 4;
		int data = (value & 0B00001111) << index;
		int mask = (~(0B1111 << index));
		return (short)((row & mask) | data);
	}
	
	private static short[] table = new short[1 << 16];
	static
	{
		for(int i = 0; i < table.length; i++)
		{
			table[i] = calculate((short)i);
		}
	}
	
	public static short slideRight(short row)
	{
		return table[Short.toUnsignedInt(row)];
	}
	
	public static short slideLeft(short row)
	{
		return Utils.reverseBits(table[Short.toUnsignedInt(Utils.reverseBits(row))]);
	}
	
	public static short rowAt(long board, int index)
	{
		return (short)(board >>> index * 16);
	}
	
	public static long fromRows(short r1, short r2, short r3, short r4)
	{
		return (Short.toUnsignedLong(r1) | Short.toUnsignedLong(r2) << 16) | (Short.toUnsignedLong(r3) << 32 | Short.toUnsignedLong(r4) << 48);
	}
	
	private static long moveLeft(long board)
	{
		return fromRows(table[Short.toUnsignedInt((short)(board))], table[Short.toUnsignedInt((short)(board >>> 16))], table[Short.toUnsignedInt((short)(board >>> 32))], table[Short.toUnsignedInt((short)(board >>> 48))]);
	}
	
	public static short columnAt(long board, int index)
	{
		index *= 4;
		return (short) (((board >>> index) & 0B1111) | ((board >>> (16 + index)) & 0B1111) << 4 | ((board >>> (32 + index)) & 0B1111) << 8 | ((board >>> (48 + index)) & 0B1111) << 12);
	}

	public static long fromColumns(short c0, short c1, short c2, short c3)
	{
		return
		(c0 & L0NIBB) 		|
		(c0 & L1NIBB) << 12 |
		(c0 & L2NIBB) << 24 |
		(c0 & L3NIBB) << 36 |
		
		(c1 & L0NIBB) << 4  |
		(c1 & L1NIBB) << 16 |
		(c1 & L2NIBB) << 28 |
		(c1 & L3NIBB) << 40 |
		
		(c2 & L0NIBB) << 8  | 
		(c2 & L1NIBB) << 20 |
		(c2 & L2NIBB) << 32 |
		(c2 & L3NIBB) << 44 |
	
		(c3 & L0NIBB) << 12 |
		(c3 & L1NIBB) << 24 |
		(c3 & L2NIBB) << 36 |
		(c3 & L3NIBB) << 48;
	}
	
	public static long moveUp(long board)
	{
		return fromColumns(table[Short.toUnsignedInt(columnAt(board, 0))], table[Short.toUnsignedInt(columnAt(board, 1))], table[Short.toUnsignedInt(columnAt(board, 2))], table[Short.toUnsignedInt(columnAt(board, 3))]);
	}
	private static final long EONIBBLE = 0xF0F0F0F0F0F0F0F0L;
	private static final long EOBYTE = 0xFF00FF00FF00FF00L;
	public static long reverseRows(long board)
	{
		
		board = (board & EONIBBLE) >>> 4 | ((board << 4) & EONIBBLE);
		board = (board & EOBYTE) >>> 8 | ((board << 8) & EOBYTE);
		return board;
	}
	private static final long EOSHORT = 0xFFFF0000FFFF0000L;
	public static long reverseColumns(long board)
	{
		board = (board & EOSHORT) >>> 16 | ((board << 16) & EOSHORT);
		board = board >>> 32 | board << 32;
		return board;
	}
	
	public static long moveRight(long board)
	{
		board = reverseRows(board);
		return reverseRows(fromRows(table[Short.toUnsignedInt((short)(board))], table[Short.toUnsignedInt((short)(board >>> 16))], table[Short.toUnsignedInt((short)(board >>> 32))], table[Short.toUnsignedInt((short)(board >>> 48))]));
	}
	
	public static long moveDown(long board)
	{
		board = reverseColumns(board);
		return reverseColumns(fromColumns(table[Short.toUnsignedInt(columnAt(board, 0))], table[Short.toUnsignedInt(columnAt(board, 1))], table[Short.toUnsignedInt(columnAt(board, 2))], table[Short.toUnsignedInt(columnAt(board, 3))]));
	}
	
	public static long move(long board, int direction)
	{
		if(direction > 1)
		{
			if(direction == 3) return moveRight(board);
			return moveLeft(board);
		}
		else
		{
			if(direction == 0) return moveUp(board);
			return moveDown(board);
		}
	}
	
	public static int numTiles(long board)
	{
		final long tempMask = 0B0100010001000100010001000100010001000100010001000100010001000100L;
		board = ((board & 0B1010101010101010101010101010101010101010101010101010101010101010L) >>> 1) | board;
		board = ((board & tempMask) >>> 2) | board;
		board &= (tempMask >>> 2);
		board = (board + (board >>> 4)) & 0x0F0F0F0F0F0F0F0FL;
	    board = board + (board >>> 8);
	    board = board + (board >>> 16);
	    board = board + (board >>> 32);
	    return (int)board & 0x7f;
	}
	
	private static int getSquare(long board, int index)
	{
		return ((int)(board >>> (index * 4)) & 0xF);
	}
	
	private static long setSquare(long board, int index, int value)
	{
		index *= 4;
		long data = (value & 0B00001111L) << index;
		long mask = ~(L0NIBB << index);
		return (board & mask) | data;
	}
	
	public static long[] possibleRandomAdditions(long board)
	{
		int emptyTiles = 16 - numTiles(board);
		if(emptyTiles == 0) throw new RuntimeException();
		long[] result = new long[2 * emptyTiles];
		for(int i = 0, index = 0; i < 16; i++)
		{
			if(((int)(board >>> (i * 4)) & 0xF) == 0)
			{
				result[index] = setSquare(board, i, 1);
				index++;
				result[index] = setSquare(board, i, 2);
				index++;
			}
		}
		return result;
	}
	private static Random rand = Random.create();
	public static long addRandomTile(long board)
	{
		long[] t = possibleRandomAdditions(board);
		return t[rand.nextInt(t.length)];
	}
	//public static long move(long board, Direction d)
	{
		
	}
}
