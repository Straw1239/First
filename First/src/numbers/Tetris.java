package numbers;

public class Tetris
{
	static final int MASK = (1 << 20) - 1; 
	public static int column(long a, long b, long c, long d, int index)
	{
		assert (index < 10);
		if(index < 6)
		{
			if(index < 3)
			{
				return (int)((a >>> (index * 20)) & MASK);
			}
			else
			{
				index -= 3;
				return (int)((b >>> (index * 20)) & MASK);
			}
		}
		else
		{
			if(index < 9)
			{
				index -= 6;
				return (int)((c >>> (index * 20)) & MASK);
			}
			else
			{
				index -= 9;
				return (int)((d >>> (index * 20)) & MASK);
			}
		}
	}
	
	public static int column(long[] board, int index)
	{
		int k = index  / 3;
		int j = index % 3;

		return (int)((board[k] >>> (j * 20)) & MASK);
	}
	
	public static void shiftDown(long[] board, int amount)
	{
		long mask = 1L << amount - 1;
		mask <<= 20 - amount;
		mask = mask | (mask << 20) | (mask << 40);
		mask = ~mask;
		board[0] = (board[0] >>> amount) & mask;
		board[1] = (board[1] >>> amount) & mask;
		board[2] = (board[2] >>> amount) & mask;
		board[3] = (board[3] >>> amount) & mask;
	}
	
	public static boolean collides(long[] board)
	{
		
	}
	
	
	
	
	
	
}
