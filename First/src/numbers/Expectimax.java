package numbers;

import static numbers.LongStates.addRandomTile;
import static numbers.LongStates.columnAt;
import static numbers.LongStates.move;
import static numbers.LongStates.numTiles;
import static numbers.LongStates.possibleRandomAdditions;
import static numbers.LongStates.rowAt;
import static numbers.LongStates.score;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.FileNotFoundException;

import javax.swing.JComponent;
import javax.swing.JFrame;

import net.openhft.koloboke.collect.map.hash.HashLongLongMap;
import net.openhft.koloboke.collect.map.hash.HashLongLongMaps;
public class Expectimax
{
	private static final double LOSS_SCORE = -200_000;
	private static final double PROB_THRESHOLD = 0.00001;
	public static long evalTime = 0;
	private static long positions = 0;
	
	
	
	
	
	
	
	private static Color[] colorTable = new Color[16];
	static
	{
		colorTable[0] = Color.white;
		colorTable[1] = Color.lightGray;
		colorTable[2] = Color.lightGray;
		colorTable[3] = Color.pink;
		colorTable[4] = Color.magenta;
		colorTable[5] = Color.orange;
		colorTable[6] = Color.red;
		colorTable[7] = Color.yellow;
		colorTable[8] = Color.yellow;
		colorTable[9] = Color.yellow;
		colorTable[10] = Color.yellow;
		colorTable[11] = Color.blue;
		colorTable[12] = Color.green;
		colorTable[13] = Color.DARK_GRAY;
		colorTable[14] = Color.BLACK;
		colorTable[15] = Color.BLACK;
	}
	
	private static int abs(int a)
	{
		final int mask = a >> 31;
		return (a + mask) ^ mask;
	}
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
		int reps = 10;
		for(int i = 0; i < reps; i++)
		{
			long state = 0;
			state = addRandomTile(state);
			state = addRandomTile(state);
			int moves = 0;
			long time = 0;
			int depth = 3;
			double target = .05;
			long targetTime = (long)(target * 10000000000.0);
			while(true)
			{
				//depth = 3;
				painter.setState(state);
				frame.repaint();
				long a = System.nanoTime();
				int d = bestMove(state, depth);
				long b = System.nanoTime() - a;
				totalPositions += positions;
				System.out.println("MPPS: " + ((totalPositions* 1000.0)/(System.nanoTime() - startTime)) );
				positions = 0;
				evalTime = 0;
				time += b;
				if(d == -1) break; 
				System.out.println(Direction.values()[d]);
				System.out.println();

				state = move(state, d);
				state = addRandomTile(state);
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
			System.out.println(score(state));
			score += score(state);
		}
		System.out.println(score * 1.0 / reps);
		
	}
	
	private static int concScore(long board)
	{
		int result = 0;
		while(board != 0)
		{
			int score = 1 << ((board & 0xF) * 2);
			result += score;
			board >>>= 4;
		}
		return result;
	}
	
	
	
	public static int bestMove(long state, int depth)
	{
		if(depth == 0) throw new IllegalArgumentException();
		double best = 0;
		int result = -1;
		for(int i = 0; i < 4; i++)
		{
			long after = move(state, i);
			if(after != state)
			{
				long[] possibleStates = possibleRandomAdditions(after);
				double cprob = 1.0 / (possibleStates.length / 2);
				double averageCase = 0;
				for(int j = 0; j < possibleStates.length; j++)
				{
					//results.add(executor.submit(new Evaluator(it.next(),depth-1,best)));
					
					double temp;
					double weight =  0.1 / ((j & 0B1) + (1.0 / 9)); //Converts added twos to have weight 1, 4s 0.1
					temp = bestWorstCase(possibleStates[j], depth-1, best, cprob * weight);
					averageCase += temp * weight;
				
				
				}
				averageCase /= (possibleStates.length / 2);
				best = Math.max(best, averageCase);
				if(best == averageCase) result = i;
			}
		}
		//System.out.println("Estimated Gain: " +  Heuristics.scoreWithAdvancedTables(state));
		return result;
	}
	
	private static double bestWorstCase(long state, int depth, double currentBest, double cprob)
	{
		if(depth == 0 | cprob < PROB_THRESHOLD) 
		{
			//positions++;
			return Heuristics.scoreWithTable(state, Heuristics.myTable);
			//return Heuristics.scoreWithAdvancedTables(state);
		}
		double bestCase = 0;
			
		for(int i = 0; i < 4;i++)
		{
			long after = move(state, i);
			if(after != state)
			{
				long[] possibleStates = possibleRandomAdditions(after);
				double prob = cprob / (possibleStates.length / 2);
				double averageCase = 0;
				for(int j = 0; j < possibleStates.length; j++)
				{
					//results.add(executor.submit(new Evaluator(it.next(),depth-1,best)));
					
					double temp;
					double weight =  0.1 / ((j & 0B1) + (1.0 / 9)); //Converts added twos to have weight 1, 4s 0.1
					temp = bestWorstCase(possibleStates[j], depth - 1, currentBest, prob * weight);
					averageCase += temp * weight;
				}
				averageCase /= (possibleStates.length / 2);
				bestCase = Math.max(bestCase, averageCase);
				currentBest = Math.max(currentBest, bestCase);
				
			}
		}
		return bestCase;
	}
	
	private static double evaluate(long state)
	{
		positions++;
		//long a = System.nanoTime();
		double score = 0;
		//Add points for empty squares
		
		score += (16 - numTiles(state)) * .5;
		/**
		//Add points for ordering
		double monotonicityWeight = .125;
		//double monotonicityWeight = 1;
		int horizontal = 0, vertical = 0;
		for(int i = 0; i < 4;i++)
		{
			short h = rowAt(state, i);
			horizontal += abs(ordering(h) * (max(h) - min(h)));
			short v = columnAt(state, i);
			vertical += abs(ordering(v) * (max(v) - min(v)));
		}
		horizontal = abs(horizontal);
		vertical = abs(vertical);
		score += monotonicityWeight * (vertical + horizontal);
		
		*/
		double sandWeight = 1;
		//System.out.println(score);
		score += sandWeight * sandwichScore(state);
		//double concWeight = 1.0 / (1024 * 1024);
		//score += concWeight  * concScore(state);
		//System.out.println(score);
		
		//double edgeBonusWeight = .12;
		//double eScore = 0;
		//int highestFound = max(max(squareAt(state, 0, 0), squareAt(state, 0, 3)), max(squareAt(state, 3, 0), squareAt(state, 3, 3)));

		//eScore += highestFound * highestFound;
		//score += eScore * edgeBonusWeight;
		//evalTime += System.nanoTime()-a;
		return score;
	}
	
	
	
	
	
	
	
	private static int min(short v)
	{
		//nested inlined branchless min of each nibble in v
		int a = v & 0x000F;
		int b = (v >>> 4) & 0x000F;
		int a1 = (v >>> 8) & 0x000F;
		int b1 = (v >>> 12) & 0x000F;
		int a2 = b + ((a - b) & ((a - b) >> (31)));
		int b2 = b1 + ((a1 - b1) & ((a1 - b1) >> (31)));
		return b2 + ((a2 - b2) & ((a2 - b2) >> (31))); // min(x, y)
	}
	private static int max(short h)
	{
		//nested inlined branchless max of each nibble in h
		int a = h & 0x000F;
		int b = (h >>> 4) & 0x000F;
		int a1 = (h >>> 8) & 0x000F;
		int b1 = (h >>> 12) & 0x000F;
		int a2 = a - ((a - b) & ((a - b) >> 31));
		int b2 = a1 - ((a1 - b1) & ((a1 - b1) >> 31));
		return a2 - ((a2 - b2) & ((a2 - b2) >> 31));
	}
	
	private static int max(int a, int b)
	{
		return a - ((a - b) & ((a - b) >> 31));
	}
	
	private static int min(int a, int b)
	{
		return b + ((a - b) & ((a - b) >> (31))); // min(x, y)
	}





	private static int ordering(short a)
	{
		int b = (a >>> 4 & 0xF);
		int c = (a >>> 8 & 0xF);
		int d = a >>> 12;
		int i = (a & 0xF) - b;
		int i1 = b - c;
		int i2 = c - d;
		return ((i >> 31) | (-i >>> 31)) + ((i1 >> 31) | (-i1 >>> 31)) + ((i2 >> 31) | (-i2 >>> 31));  //inlined signum
	}
	
	private static class StatePainter extends JComponent
	{
		public static final long serialVersionUID = 0L;
		private long  state;
		
		public StatePainter()
		{
			this(600);
		}
		
		public StatePainter(int size)
		{
			this(size, 0);
		}
		
		public StatePainter(int size, long state)
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
			draw(g, state, getWidth());
		}
		
		public void setState(long s)
		{
			state = s;
		}
	}
	public static void draw(Graphics g, long board, int size)
	{
		int width = size/4;
		g.setColor(Color.white);
		g.fillRect(0,0, size, size);
		for(int i = 0; i < 4;i++)
		{
			for(int j = 0; j < 4;j++)
			{
				if(LongStates.squareAt(board, i, j) != 0)
				{
					
					g.setColor(colorTable[LongStates.squareAt(board, i, j)]);
					g.fillRect(j * width, (i) * width, width, width);
					int fontSize = (LongStates.squareAt(board, i, j) > 6)? ((LongStates.squareAt(board, i, j) > 9) ? 50 : 66) : 100;
					g.setFont(new Font("TimesRoman", Font.BOLD, fontSize)); 
					g.setColor(Color.black);
					String display = Integer.toString(1 << LongStates.squareAt(board, i, j));
					g.drawString(display, j * width + width / 2 - width / 6  , (i) * width + width / 2 + width / 5);
				}
			}
		}
		g.setColor(Color.black);
		for(int i = 0; i < 4;i++)
		{
			g.drawLine(0, width * i, size, width * i);
			g.drawLine(i * width, 0, i * width, size);
		}
	}
	
	private static byte sandwichScore(short row)
	{
		int result = 0;
		int a = row & 0xF;
		int b = (row >>> 4) & 0xF;
		int c = (row >>> 8) & 0xF;
		int d = (row >>> 12) & 0xF;
		
		if(b > a)
		{
			result -= b - a + 4;
		}
		else if(a > b & c > b)
		{
			result -= min(a, c) - b + 4;
		}
		if(c > d)
		{
			result -= c - d + 4;
		}
		else if(b > c & d > c)
		{
			result -= min(b, d) - c + 4;
		}
		return (byte) result;
	}
	static byte[] sandwichTable = new byte[1 << 16];
	static
	{
		for(int i = 0;  i < sandwichTable.length; i++)
		{
			sandwichTable[i] = sandwichScore((short)i);
		}
	}
	
	private static int sandwichScore(long board)
	{
		int result = 0;
		result += sandwichTable[Short.toUnsignedInt(rowAt(board, 0))];
		result += sandwichTable[Short.toUnsignedInt(rowAt(board, 1))];
		result += sandwichTable[Short.toUnsignedInt(rowAt(board, 2))];
		result += sandwichTable[Short.toUnsignedInt(rowAt(board, 3))];
		result += sandwichTable[Short.toUnsignedInt(columnAt(board, 0))];
		result += sandwichTable[Short.toUnsignedInt(columnAt(board, 1))];
		result += sandwichTable[Short.toUnsignedInt(columnAt(board, 2))];
		result += sandwichTable[Short.toUnsignedInt(columnAt(board, 3))];
		return result;
	}
	
	private static int doz(int x, int y)
	{
		return (x - y) & ~((x - y) >> 31);
	}
}
