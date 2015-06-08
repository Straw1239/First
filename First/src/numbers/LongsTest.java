package numbers;


public class LongsTest
{
	static Random rand = new ParallelRandom();
	public static void main(String[] args)
	{
		double newTime = testLongs() * 1.0 / 1_000_000_000;
		double oldTime = testStates() * 1.0 / 1_000_000_000;
		System.out.printf("Old:%f New:%f Ratio:%f\n", oldTime, newTime, oldTime / newTime );
		
	}
	static long[] newTestCases = new long[1024];
	static FastState[] testCases = new FastState[1024];
	static
	{
		for(int i = 0; i < 1024; i++)
		{
			FastState s = new FastState();
			s = s.addRandomTile(true);
			s = s.addRandomTile(true);
			s = s.addRandomTile(true);
			s = s.addRandomTile(true);
			s = s.addRandomTile(true);
			s = s.addRandomTile(true);
			s = s.addRandomTile(true);
			s = s.addRandomTile(true);
			s = s.addRandomTile(true);
			s = s.addRandomTile(true);
			s = s.addRandomTile(true);
			s = s.addRandomTile(true);
			s = s.addRandomTile(true);
			s = s.addRandomTile(true);
			s = s.addRandomTile(true);
			s = s.addRandomTile(true);
			testCases[i] = s;
			newTestCases[i] = LongStates.fromFastState(s);
		}
		
	}
	public static long testLongs()
	{
		long board = 0;
		for(int j = 0; j < 512; j++)
		{
			board = newTestCases[j];
			for(int i = 0; i < 1024; i++)
			{
				board = LongStates.move(board, i % 4);
			}
		}
		long time = System.nanoTime();
		for(int j = 0; j < 512; j++)
		{
			board = newTestCases[j];
			for(int i = 0; i < 1024; i++)
			{
				board = LongStates.move(board, i % 4);
			}
			
		}
		return System.nanoTime() - time;
	}
	
	public static long testStates()
	{
		FastState board = null;
		for(int j = 0; j < 512; j++)
		{
			board = testCases[j];
			for(int i = 0; i < 1024; i++)
			{
				board = board.move(Direction.values()[i % 4]);
			}
		}
		long time = System.nanoTime();
		for(int j = 0; j < 512; j++)
		{
			board = testCases[j];
			for(int i = 0; i < 1024; i++)
			{
				board = board.move(Direction.values()[i % 4]);
			}
		}
		//board.print(System.out);
		return System.nanoTime() - time;
	}
	
	

}
