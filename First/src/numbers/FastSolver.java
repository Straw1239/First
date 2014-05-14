package numbers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class FastSolver 
{
	public static long evalTime = 0;
	private static long positions = 0;
	public static final int MAX_THREADS = 1;//Runtime.getRuntime().availableProcessors();
	public static ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
	
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
					if(time * 5 < targetTime)
					{
						depth++;
					}
					if(time > 5 * targetTime)
					{
						depth--;
					}
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
		executor.shutdown();
	}
	
	
	public static int getIndex(byte a, byte b, byte c, byte d)
	{
		return ((a) << 12) + ((b) << 8) + ((c) << 4) + (d);
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
				ArrayList<Future<Double>> results = new ArrayList<>(possibleStates.size());
				while(it.hasNext())
				{
					results.add(executor.submit(new Evaluator(it.next(),depth-1,best)));
					/*
					double temp;
					temp = bestWorstCase(it.next(),depth-1,best);
					worstCase = Math.min(worstCase, temp);
					if(temp < best)
					{
						break;
					}
					*/	
				}
				for(int i = 0; i < results.size();i++)
				{
					double temp = Double.POSITIVE_INFINITY;
					try 
					{
						temp = results.get(i).get();
					} 
					catch (InterruptedException | ExecutionException e) 
					{
						e.printStackTrace();
					}
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
		score = Math.sqrt(score) * 2.5;
		double smoothWeight = .5;
		//Subtract points for sharp transitions
		for(int i = 1; i < s.size();i++)
		{
			for(int j = 0; j < s.size();j++)
			{
				byte first = s.getSquare(i - 1,j);
				byte second = s.getSquare(i,j);
				if(first != 0 && second != 0)
				{
					int diff = second - first;
					diff = (diff > 0)? diff : -diff;
					//diff *= max(first,second);
					score -= diff * smoothWeight;
				}
				first = s.getSquare(j,i-1);
				second = s.getSquare(j,i);
				if(first != 0 && second != 0)
				{
					int diff = second - first;
					diff = (diff > 0)? diff : -diff;
					//diff *= max(first,second);
					score -= diff * smoothWeight;
				}
			}
		}
		//Add points for ordering
		double monotonicityWeight = .11;
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
	
	private static class StatePainter extends JPanel
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
		
		@Override
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
