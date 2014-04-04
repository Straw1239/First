package numbers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;

public class Solver 
{
	private static long time = 0;
	public static void main(String[] args) throws FileNotFoundException {
		int[][] test = new int[4][4];
		test[0] = new int[]{1,4,3,0};
		test[1] = new int[]{6,1,2,0};
		test[2] = new int[]{9,4,5,2};
		test[3] = new int[]{6,1,2,2};
		State s = new State(test);
		
		s = new State(new File("2048.txt"));
		long a = System.nanoTime();
		Direction d = bestMove(s,5);
		long b = System.nanoTime();
		System.out.println((time)* 100.0/(double)(b-a) + "%");
		System.out.println((b-a)/1000000000.0);
		System.out.println();
		System.out.println(d);
		s = s.move(d);
		//PrintStream p = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File("2048.txt"))));
		//s.print(p);
		s.print(System.out);
		
		

	}
	public static Direction bestMove(State s, int depth)
	{
		if(depth == 0) throw new IllegalArgumentException();
		double best = 0;
		Direction result = null;
		for(Direction d : Direction.values())
		{
			State after = s.move(d);
			if(!after.equals(s))
			{
				Collection<State> possibleStates = after.possibleRandomAdditions();
				Iterator<State> it = possibleStates.iterator();
				double worstCase = Double.POSITIVE_INFINITY;
				while(it.hasNext())
				{
					double temp = bestWorstCase(it.next(),depth-1,0);
					worstCase = Math.min(worstCase, temp);
					if(temp < best)
					{
						break;
					}
				}
				best = Math.max(best, worstCase);
				if(best == worstCase) result = d;
			}
		}
		return result;
	}
	
	private static double bestWorstCase(State s, int depth, double currentBest)
	{
		if(depth == 0) return evaluate(s);
		double bestCase = 0;
		for(int i = 0; i < Direction.values().length;i++)
		{
			State after = s.move(Direction.values()[i]);
			if(!after.equals(s))
			{
				Collection<State> possibleStates = after.possibleRandomAdditions();
				Iterator<State> it = possibleStates.iterator();
				double worstCase = Double.POSITIVE_INFINITY;
				while(it.hasNext())
				{
					double temp = bestWorstCase(it.next(),depth-1,currentBest);
					worstCase = Math.min(worstCase, temp);
					if(temp < currentBest)
					{
						break;
					}
					
				}
				bestCase = Math.max(bestCase, worstCase);
				currentBest = Math.max(currentBest, bestCase);
			}
		}
		return bestCase;
		
	}
	
	private static double evaluate(State s)
	{
		long a = System.nanoTime();
		double score = 0;
		for(int i = 0; i < s.size();i++)
		{
			for(int j = 0; j < s.size();j++)
			{
				if(s.getSquare(i,j) == 0)
				{
					score++;
				}
			}
		}
		time += System.nanoTime()-a;
		return score;
	}

}
