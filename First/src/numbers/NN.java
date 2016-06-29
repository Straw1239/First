package numbers;

import java.util.Arrays;

import main.LongList;
import net.openhft.koloboke.collect.map.LongObjCursor;

public class NN
{
	public static void main(String[] args)
	{
		DataGen.record.cursor();
			
		
	}
	
	
	static final int FP_SHIFT = 16;
	static int basicPNN(long board, int[][] hiddenWeights, int[] bias, int[] outputWeights, int outputBias)
	{
		int total = 0;
		for(int i = 0; i < hiddenWeights.length; i++)
		{
			int[] s = hiddenWeights[i];
			int output = rectify((dot(board, s) >> FP_SHIFT) + bias[i]);
			total += output * outputWeights[i];
		}
		return (total >> FP_SHIFT) + outputBias;
	}
	static double estimateBasicPNNError(int[][] hiddenWeights, int[] bias, int[] outputWeights, int outputBias, int trials)
	{
		double totalError = 0;
		LongObjCursor<LongList> c = DataGen.record.cursor();
		for(int j = 0; j < trials; j++)
		{
			c.moveNext();
			int total = 0;
			
			long start = c.key();
			int sValue = LongStates.totalValue(start);
			LongList results = c.value();
			for(int i = 0; i < results.size(); i++)
			{
				total += (LongStates.totalValue(results.get(i))); //- sValue);
			}
			double averageGain = 1.0 / results.size() * total;
			double predictedGain = basicPNN(start, hiddenWeights, bias, outputWeights,outputBias);
			totalError += (averageGain - predictedGain) * (averageGain - predictedGain);
		}
		totalError /= trials;
		return Math.sqrt(totalError);
	}
	
	
	static int dot(long board, int[] weights)
	{
//		long temp = board >>> 32;
//		long temp1 = board >>> 16;
//		long temp2 = board >>> 48;
//		return 	weights[0] * (1 << (board & 0xF) - 1) +
//				weights[1] * (1 << ((board >>>= 4) & 0xF) - 1) +
//				weights[2] * (1 << ((board >>>= 4) & 0xF) - 1) +
//				weights[3] * (1 << ((board >>>= 4) & 0xF) - 1) +
//				weights[4] * (1 << (temp1 & 0xF) - 1) +
//				weights[5] * (1 << ((temp1 >>>= 4) & 0xF) - 1) +
//				weights[6] * (1 << ((temp1 >>>= 4) & 0xF) - 1) +
//				weights[7] * (1 << ((temp1 >>>= 4) & 0xF) - 1) +
//				weights[8] * (1 << (temp & 0xF) - 1) +
//				weights[9] * (1 << ((temp >>>= 4) & 0xF) - 1) +
//				weights[10] * (1 << ((temp >>>= 4) & 0xF) - 1) +
//				weights[11] * (1 << ((temp >>>= 4) & 0xF) - 1) +
//				weights[12] * (1 << ((temp2) & 0xF) - 1) +
//				weights[13] * (1 << ((temp2 >>>= 4) & 0xF) - 1) +
//				weights[14] * (1 << ((temp2 >>>= 4) & 0xF) - 1) +
//				weights[15] * (1 << ((temp2 >>>= 4) & 0xF) - 1);
		
		long temp = board >>> 32;
		long temp1 = board >>> 16;
		long temp2 = board >>> 48;
		return 	weights[0] * (int)(board & 0xFF) +
				weights[1] * (int)((board >>>= 4) & 0xF) +
				weights[2] * (int)((board >>>= 4) & 0xF) +
				weights[3] * (int)((board >>>= 4) & 0xF) +
				weights[4] * (int)(temp1 & 0xF) +
				weights[5] * (int)((temp1 >>>= 4) & 0xF) +
				weights[6] * (int)((temp1 >>>= 4) & 0xF) +
				weights[7] * (int)((temp1 >>>= 4) & 0xF) +
				weights[8] * (int)(temp & 0xF) +
				weights[9] * (int)((temp >>>= 4) & 0xF) +
				weights[10] * (int)((temp >>>= 4) & 0xF) +
				weights[11] * (int)((temp >>>= 4) & 0xF) +
				weights[12] * (int)((temp2) & 0xF) +
				weights[13] * (int)((temp2 >>>= 4) & 0xF) +
				weights[14] * (int)((temp2 >>>= 4) & 0xF) +
				weights[15] * (int)((temp2 >>>= 4) & 0xF);
		
//		long temp = board >>> 32;
//		int i = 0;
//		return 	weights[0] * (int)(board & 0xFF) +
//				weights[1] * (int)((board >>> (i += 4)) & 0xF) +
//				weights[2] * (int)((board >>> (i += 4)) & 0xF) +
//				weights[3] * (int)((board >>> (i += 4)) & 0xF) +
//				weights[4] * (int)((board >>> (i += 4)) & 0xF) +
//				weights[5] * (int)((board >>> (i += 4)) & 0xF) +
//				weights[6] * (int)((board >>> (i += 4)) & 0xF) +
//				weights[7] * (int)((board >>> (i += 4)) & 0xF) +
//				weights[8] * (int)((board >>> (i += 4)) & 0xF) +
//				weights[9] * (int)((board >>> (i += 4)) & 0xF) +
//				weights[10] * (int)((board >>> (i += 4)) & 0xF) +
//				weights[11] * (int)((board >>> (i += 4)) & 0xF) +
//				weights[12] * (int)((board >>> (i += 4)) & 0xF) +
//				weights[13] * (int)((board >>> (i += 4)) & 0xF) +
//				weights[14] * (int)((board >>> (i += 4)) & 0xF) +
//				weights[15] * (int)((board >>> (i += 4)) & 0xF);
	}
	
	static int centerDot(long board, int[] weights)
	{
		return weights[0]*(int)((board >>> 20) & 0xF) +
				weights[1]*(int)((board >>> 24) & 0xF) +
				weights[2]*(int)((board >>> 36) & 0xF) +
				weights[3]*(int)((board >>> 40) & 0xF);
	}
	
	static int rectify(int x)
	{
		return x & ~(x >> 31);
	}
	
	private static int doz(int x, int y)
	{
		return (x - y) & ~((x - y) >> 31);
	}
}
