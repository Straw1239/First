package numbers;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import javax.swing.*;



public class ForkSolver 
{
	public static long evalTime = 0;
	private static long positions = 0;
	
	
	
	
	
	public static void main(String[] args) throws FileNotFoundException, InterruptedException 
	{
		
		JFrame frame = new JFrame("Auto Solver");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.white);
		frame.setResizable(false);
		StatePainter painter = new StatePainter();
		painter.setFocusable(true);
		frame.add(painter);
		frame.pack();
		frame.setVisible(true);
		
		int score = 0;
		long startTime = System.nanoTime();
		long totalPositions = 0;
		for(int i = 0; i < 10;i++)
		{
			FastState state = new FastState();
			state = state.addRandomTile(true);
			state = state.addRandomTile(true);
			int moves = 0;
			long time = 0;
			int depth = 3;
			double target = .1;
			long targetTime = (long)(target * 10000000000.0);
			while(true)
			{
				painter.setState(state);
				frame.repaint();
				long a = System.nanoTime();
				Direction d = bestMove(state,depth);
				long b = System.nanoTime() - a;
				totalPositions += positions;
				System.out.println("TPPS: " + ((totalPositions* 1000000.0)/(System.nanoTime() - startTime)) );
				positions = 0;
				evalTime = 0;
				time += b;
				if(d == null) break; 
				System.out.println(d);
				System.out.println();
				state = state.move(d);
				state = state.addRandomTile(true);
				moves++;
				System.out.println(moves);
				if(moves % 10 == 0)
				{
					if(time * 5 < targetTime) depth++;
					if(time > 5 * targetTime) depth--;
					time = 0;
				}
				else 
				{
					if(b > targetTime ) depth--;
					if(b > targetTime * 5) depth--;
				}
				
				System.out.println(depth);
			}
			System.out.println(state.score());
			score += state.score();
		}
		System.out.println(score);
		
	}
	
	public static int getIndex(byte a, byte b, byte c, byte d)
	{
		return (((int)a) << 12) + (((int)b) << 8) + (((int)c) << 4) + ((int)d);
	}
	
	private static byte max(byte a, byte b)
	{
		return a > b ? a : b;
	}
	
	public static Direction bestMove(FastState s, int depth)
	{
		if(depth == 0) throw new IllegalArgumentException();
		double best = Double.NEGATIVE_INFINITY;
		Direction result = null;
		for(Direction d : Direction.values())
		{
			FastState after = s.move(d);
			if(!after.equals(s))
			{
				Collection<FastState> possibleStates = after.possibleRandomAdditions();
				Iterator<FastState> it = possibleStates.iterator();
				double worstCase = Double.POSITIVE_INFINITY;
				while(it.hasNext())
				{
					//results.add(executor.submit(new Evaluator(it.next(),depth-1,best)));
					
					double temp;
					temp = bestWorstCase(it.next(),depth-1,best);
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
	
	private static double bestWorstCase(FastState s, int depth, double currentBest)
	{
		if(depth == 0) return evaluate(s);
		double bestCase = Double.NEGATIVE_INFINITY;
			
		for(int i = 0; i < Direction.values().length;i++)
		{
			FastState after = s.move(Direction.values()[i]);
			if(!after.equals(s))
			{
				Collection<FastState> possibleStates = after.possibleRandomAdditions();
				Iterator<FastState> it = possibleStates.iterator();
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
	
	private static double evaluate(FastState s)
	{
		positions++;
		long a = System.nanoTime();
		double score = 0;
		//Add points for empty squares
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
		double smoothWeight = 1.0;
		//Subtract points for sharp transitions
		for(int i = 1; i < s.size();i++)
		{
			for(int j = 0; j < s.size();j++)
			{
				byte first = s.getSquare(i - 1,j);
				byte second = s.getSquare(i,j);
				int diff = second - first;
				diff = (diff > 0)? diff : -diff;
				boolean ignore = false;
				if(first != 0 && second != 0)
				{
					
					
						if(i == 1 && first > second) ignore = true;
						
						if(i == 3 && second > first) ignore = true;
					
					
					
					//diff *= max(first,second);
					if(!ignore) score -= diff * smoothWeight;
				}
				first = s.getSquare(j,i-1);
				second = s.getSquare(j,i);
				if(first != 0 && second != 0)
				{
					diff = second - first;
					diff = (diff > 0)? diff : -diff;
					//diff *= max(first,second);
					if(!ignore) score -= diff * smoothWeight;
				}
			}
		}
		//Add points for ordering
		double monotonicityWeight = .125;
		int horizontal = 0, vertical = 0;
		for(int i = 0; i < s.size();i++)
		{
			byte[] h = new byte[]{s.getSquare(i, 0),s.getSquare(i, 1),s.getSquare(i, 2),s.getSquare(i, 3)};
			horizontal += /*abs(*/ordering(h) * max(h);//);
			byte[] v = new byte[]{s.getSquare(0, i),s.getSquare(1, i),s.getSquare(2, i),s.getSquare(3, i)};
			vertical += /*abs(*/ordering(v) * max(v);//);
		}
		horizontal = abs(horizontal);
		vertical = abs(vertical);
		score += monotonicityWeight * (vertical + horizontal);
		
		evalTime += System.nanoTime()-a;
		return score;
	}
	
	
	
	private static byte max(byte... arg)
	{
		byte max = arg[0];
		for(int i = 1; i < arg.length;i++)
		{
			max = (max > arg[i])? max : arg[i];
		}
		return max;
	}
	
	private static int abs(int arg)
	{
		return (arg > 0) ? arg : -arg;
	}
	
	private static int ordering(byte... sequence)
	{
		int ordering = 0;
		for(int i = 1; i < sequence.length;i++)
		{
			if(sequence[i-1] != 0 && sequence[i] != 0)
			if(sequence[i-1] < sequence[i])
			{
				ordering++;
			}
			else if(sequence[i-1] > sequence[i])
			{
				ordering--;
			}
		}
		return ordering;
	}
	
	private static Collection<FastState> states(FastState s, Direction d)
	{
		FastState after = s.move(d);
		if(after.equals(s))
			return new ArrayList<>(0);
		else
			return after.possibleRandomAdditions();
	}
	
	private static double min(Collection<Double> args)
	{
		if(args.size() == 0) throw new IllegalArgumentException();
		Iterator<Double> it = args.iterator();
		double min = it.next();
		while(it.hasNext())
		{
			min = Math.min(min, it.next());
		}
		return min;
	}
	
	private static class EvalTask extends RecursiveTask<Double>
	{
		private FastState state;
	
		public EvalTask(FastState state)
		{
			super();
			this.state = state;
		}
		
		@Override
		protected Double compute() 
		{
			return evaluate(state);
		}
	}
	
	private static class SearchEvalTask extends RecursiveTask<Double>
	{
		private FastState state;
		private int depth;
		private double currentBest;
		
		public SearchEvalTask(FastState state, int depth, double currentBest) 
		{
			super();
			this.state = state;
			this.depth = depth;
			this.currentBest = currentBest;
		}

		@Override
		protected Double compute() 
		{
			if(depth == 0)
			{
				RecursiveTask<Double> task = new EvalTask(state);
				task.fork();
				return task.join();
			}
			else
			{
				ArrayList<RecursiveTask<Double>>[] forks = new ArrayList[4];
				for(Direction d : Direction.values())
				{
					Collection<FastState> states = states(state,d);
					Iterator<FastState> it = states.iterator();
					while(it.hasNext())
					{
						SearchEvalTask task = new SearchEvalTask(it.next(),depth-1,currentBest);
						forks[d.ordinal()].add(task);
						task.fork();
					}
				}
				double bestCase = Double.NEGATIVE_INFINITY;
				for(int i = 0; i < forks.length;i++)
				{
					ArrayList<RecursiveTask<Double>> list = forks[i];
					double worstCase = list.get(0).join();
					for(int j = 1; j < list.size();j++)
					{
						worstCase = Math.min(worstCase, list.get(i).join());
					}
					bestCase = Math.max(bestCase,worstCase );
				}
				return bestCase;
			}
		}
		
	}
	
	private static class Evaluator implements Callable<Double>
	{
		FastState state;
		int depth;
		double currentBest;
		
		public Evaluator(FastState state, int depth, double currentBest) 
		{
			this.state = state;
			this.depth = depth;
			this.currentBest = currentBest;
		}
		
		@Override
		public Double call() throws Exception 
		{
			return bestWorstCase(state,depth,currentBest);
		}
	}
	
	private static class StatePainter extends JComponent
	{
		public static final long serialVersionUID = 0L;
		private FastState state;
		
		public StatePainter()
		{
			this(600);
		}
		
		public StatePainter(int size)
		{
			this(size,null);
		}
		
		public StatePainter(int size, FastState state)
		{
			super();
			this.state = state;
			setSize(size, size);
			setVisible(true);
			setPreferredSize(new Dimension(size,size));
		}
		
		public void paintComponent(Graphics g)
		{
			if(state != null)
			state.draw(g, getWidth());
		}
		
		public void setState(FastState s)
		{
			state = s;
		}
	}
}
