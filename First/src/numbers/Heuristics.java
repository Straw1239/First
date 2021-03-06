package numbers;
import static numbers.LongStates.*;
import static java.lang.Math.*;
import static numbers.DataProc.*;
public class Heuristics
{
	

	public static double scoreWithTable(long state, float[] scoreTable)
	{
		double result = 0;
		result += scoreTable[(int) state & 0xFFFF];
		result += scoreTable[(int)(state >>> 16) & 0xFFFF];
		result += scoreTable[(int)(state >>> 32) & 0xFFFF];
		result += scoreTable[(int)(state >>> 48) & 0xFFFF];
		result += scoreTable[Short.toUnsignedInt(columnAt(state, 0))];
		result += scoreTable[Short.toUnsignedInt(columnAt(state, 1))];
		result += scoreTable[Short.toUnsignedInt(columnAt(state, 2))];
		result += scoreTable[Short.toUnsignedInt(columnAt(state, 3))];
		return result;
	}
	
	public static short[] centerTable = new short[1 << 16];
	static
	{
		for(int row = 0; row < centerTable.length; row++)
		{
			int[] line = {row & 0xF, (row >>> 4) & 0xF, (row >>> 8) & 0xF, row >>> 12};
			for(int i : line)
			{
				centerTable[i] += 1 << i;
			}
		}
	}
	static
	{
		DataProc.readData();
		
	}
	
	public static double scoreWithAdvancedTables(long start)
	{
//		double result = 1000000000000.0;
//		short[] data = {			
//				center[Short.toUnsignedInt(LongStates.center(start))], 
//				corner[Short.toUnsignedInt(LongStates.ULCorner(start))],
//                corner[Short.toUnsignedInt(LongStates.URCorner(start))],
//                corner[Short.toUnsignedInt(LongStates.LLCorner(start))], 
//                corner[Short.toUnsignedInt(LongStates.LRCorner(start))],
//                inner[Short.toUnsignedInt(LongStates.rowAt(start, 1))] ,
//				inner[Short.toUnsignedInt(LongStates.rowAt(start, 2))] ,
//				inner[Short.toUnsignedInt(LongStates.columnAt(start, 1))],
//				inner[Short.toUnsignedInt(LongStates.columnAt(start, 2))],
//			
//				
//				outer[Short.toUnsignedInt(LongStates.rowAt(start, 0))],
//				outer[Short.toUnsignedInt(LongStates.rowAt(start, 3))],
//				outer[Short.toUnsignedInt(LongStates.columnAt(start, 0))],
//				outer[Short.toUnsignedInt(LongStates.columnAt(start, 3))]};
//		for(int i = 0; i < data.length; i++)
//		{
//			short s = data[i];
//			result = min(result, s);
//			if(s == 0) System.out.println("ARGGG " + i);
//		}
		return 1 / 13.0 * (
		  center[Short.toUnsignedInt(LongStates.center(start))]
		
		 +corner[Short.toUnsignedInt(LongStates.ULCorner(start))] 
		 +corner[Short.toUnsignedInt(LongStates.URCorner(start))] 
		 +corner[Short.toUnsignedInt(LongStates.LLCorner(start))] 
		 +corner[Short.toUnsignedInt(LongStates.LRCorner(start))] 
	
		
		 +inner[Short.toUnsignedInt(LongStates.rowAt(start, 1))] 
		 +inner[Short.toUnsignedInt(LongStates.rowAt(start, 2))] 
		 +inner[Short.toUnsignedInt(LongStates.columnAt(start, 1))] 
		 +inner[Short.toUnsignedInt(LongStates.columnAt(start, 2))] 
	
		
		 +outer[Short.toUnsignedInt(LongStates.rowAt(start, 0))] 
		 +outer[Short.toUnsignedInt(LongStates.rowAt(start, 3))] 
		 +outer[Short.toUnsignedInt(LongStates.columnAt(start, 0))] 
		 +outer[Short.toUnsignedInt(LongStates.columnAt(start, 3))]); 
	
		
		
	}
	
	

	public static float[] stolenTables = new float[1 << 16];
	public static float[] myTable = new float[1 << 16];
	static
	{
		final float SCORE_LOST_PENALTY = 200000.0f;
		final double SCORE_SUM_POWER = 3.5;
		final double SCORE_MONOTONICITY_POWER = 4.0;
		final double SCORE_EMPTY_WEIGHT = 270;
		final double SCORE_MONOTONICITY_WEIGHT = 47.0;
		final double SCORE_SUM_WEIGHT = 11.0;
		final double SCORE_MERGES_WEIGHT = 700.0;
		for(int row = 0; row < 65536; row++)
		{
				
			int[] line = {row & 0xF, (row >>> 4) & 0xF, (row >>> 8) & 0xF, row >>> 12};
			float sum = 0;
			int empty = 0;
			int merges = 0;
	
			int prev = 0;
			int counter = 0;
			for (int i = 0; i < 4; ++i) {
				int rank = line[i];
				
				sum += pow(rank, SCORE_SUM_POWER);
				if (rank == 0) {
					empty++;
				} else {
					if (prev == rank) {
						counter++;
					} else if (counter > 0) {
						merges += 1 + counter;
						counter = 0;
					}
					prev = rank;
				}
			}
			if (counter > 0) {
				merges += 1 + counter;
			}
	
			float monotonicity_left = 0;
			float monotonicity_right = 0;
			for (int i = 1; i < 4; ++i) {
				if (line[i-1] > line[i]) {
					monotonicity_left += pow(line[i-1], SCORE_MONOTONICITY_POWER) - pow(line[i], SCORE_MONOTONICITY_POWER);
				} else {
					monotonicity_right += pow(line[i], SCORE_MONOTONICITY_POWER) - pow(line[i-1], SCORE_MONOTONICITY_POWER);
				}
			}
	
			stolenTables[row] = SCORE_LOST_PENALTY +
					(float) (SCORE_EMPTY_WEIGHT * empty +
					SCORE_MERGES_WEIGHT * merges -
					SCORE_MONOTONICITY_WEIGHT * min(monotonicity_left, monotonicity_right) -
					SCORE_SUM_WEIGHT * sum);
			myTable[row] = stolenTables[row];
			
		}
	}
	
	
	
	
	public static double scoreWithTable(long state, short[] scoreTable)
	{
		double result = 0;
		result += scoreTable[(int) state & 0xFFFF];
		result += scoreTable[(int)(state >>> 16) & 0xFFFF];
		result += scoreTable[(int)(state >>> 32) & 0xFFFF];
		result += scoreTable[(int)(state >>> 48) & 0xFFFF];
		result += scoreTable[Short.toUnsignedInt(columnAt(state, 0))];
		result += scoreTable[Short.toUnsignedInt(columnAt(state, 1))];
		result += scoreTable[Short.toUnsignedInt(columnAt(state, 2))];
		result += scoreTable[Short.toUnsignedInt(columnAt(state, 3))];
		return result;
	}
}
