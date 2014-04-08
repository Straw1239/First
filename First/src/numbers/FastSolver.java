package numbers;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import javax.swing.*;



public class FastSolver 
{
	public static long evalTime = 0;
	private static boolean[] evalTable;
	private static long positions = 0;
	public static final int MAX_THREADS = 4;
	public static ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
	
	
	
	public static void main(String[] args) throws FileNotFoundException, InterruptedException 
	{
		//Scanner console = new Scanner(System.in);
		initialize();
		
		/*
		byte[][] test = new byte[4][4];
		test[0] = new byte[]{1,4,3,0};
		test[1] = new byte[]{6,1,2,0};
		test[2] = new byte[]{9,4,5,2};
		test[3] = new byte[]{6,1,2,2};
		long a = System.nanoTime();
		FastState s = new FastState(test);
		long b = System.nanoTime();
		System.out.println((b-a)/1000000000.0);
		s = new FastState(new File("2048.txt"));
		a = System.nanoTime();
		Direction d = bestMove(s,6);
		b = System.nanoTime();
		System.out.println((time)* 100.0/(double)(b-a) + "%");
		System.out.println((b-a)/1000000000.0);
		System.out.println(positions);
		System.out.println();
		System.out.println(d);
		s = s.move(d);
		PrintStream p = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File("2048.txt"))));
		s.print(p);
		s.print(System.out);
		*/
		
		
		//DrawingPanel panel = new DrawingPanel(600,600);
		//Graphics g = panel.getGraphics();
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
			double target = .05;
			long targetTime = (long)(target * 10000000000.0);
			while(true)
			{
				
				//state.draw(g, 600);
				painter.setState(state);
				frame.repaint();
				//depth = 3;
				long a = System.nanoTime();
				Direction d = bestMove(state,depth);
				long b = System.nanoTime() - a;
				//System.out.println(evalTime * 100.0/b);
				//System.out.println(positions/1000);
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
				
				
				//console.next();
			}
			System.out.println(state.score());
			score += state.score();
		}
		System.out.println(score);
		
		
		

	}
	public static int getDepth(int moves)
	{
		int depth;
		
		if(moves < 100)
		{
			depth = 3;
		}
		else if(moves < 400)
		{
			depth = 4;
		}
		else if( moves < 700)depth = 5;
		else if(moves < 800)depth = 6;
		else if(moves < 1000)depth = 7;
		else if( moves < 1500)depth = 8;
		else depth = 9;
		return depth;
	}
	
	public static int getIndex(byte a, byte b, byte c, byte d)
	{
		return (((int)a) << 12) + (((int)b) << 8) + (((int)c) << 4) + ((int)d);
	}
	
	private static void initialize()
	{
		evalTable = new boolean[65536];
		for(byte i = 0; i < 16; i++)
		{
			for(byte j = 0; j < 16;j++)
			{
				for(byte k = 0; k < 16; k++)
				{
					for(byte m = 0; m < 16; m++)
					{
						int index = getIndex(i,j,k,m);
						byte max = max(max(i,j),max(k,m));
						if( max == i || max == m)
						{
							evalTable[index] = true;
						}
					}
				}
			}
		}
		
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
	
	public static int max(int a, int b)
	{
		return (a > b) ? a : b;
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
