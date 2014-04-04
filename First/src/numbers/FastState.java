package numbers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.Scanner;

public class FastState 
{
	private byte[][] board;
	private static byte[][][] tables;
	private static Random rand = new Random();
	private static Color[] colorTable = new Color[16];
	
	public FastState()
	{
		this(4);
	}
	
	public FastState(File f)
	{
		this();
		Scanner input = null;
		try 
		{
			input = new Scanner(new BufferedInputStream(new FileInputStream(f)));
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int j = 0;
		while(input.hasNextLine())
		{
			String line = input.nextLine();
			String[] data = line.split(" ");
			for(int i = 0; i < data.length;i++)
			{
				board[i][board.length-j-1] = Byte.parseByte(data[i]);
			}
			j++;
		}
		input.close();
	}
	
	public FastState(byte[][] board)
	{
		this(board,true);
	}

	private FastState(byte[][] board, boolean copy)
	{
		if(board.length != 4) throw new IllegalArgumentException();
		this.board = copy ? arrayCopy(board) : board;
		if(tables == null) initializeTables();
	}
	
	private FastState(int size)
	{
		this(new byte[size][size],false);
	}
	
	
	private static void initializeTables()
	{
		tables = new byte[2][65536][4];
		for(byte i = 0; i < 16; i++)
		{
			for(byte j = 0; j < 16;j++)
			{
				for(byte k = 0; k < 16; k++)
				{
					for(byte m = 0; m < 16; m++)
					{
						int index = (FastSolver.getIndex(i, j, k, m));						
						tables[0][index] = calculate(i,j,k,m);
						tables[1][index] = calculate(m,k,j,i);
						reverse(tables[1][index]);
					}
				}
			}
		}
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
		
				
		
	}
	
	
	public byte getSquare(int row, int column)
	{
		return board[column][row];
	}
	public int size()
	{
		return board.length;
	}
	public void draw(Graphics g,int size)
	{
		int width = size/4;
		g.setColor(Color.white);
		g.fillRect(0,0, size, size);
		for(int i = 0; i < board.length;i++)
		{
			for(int j = 0; j < board.length;j++)
			{
				if(board[i][j] != 0)
				{
					
					g.setColor(colorTable[board[i][j]]);
					g.fillRect(i * width, (board.length - j - 1) * width, width, width);
					int fontSize = (board[i][j] > 6)? ((board[i][j] > 9) ? 50 : 66) : 100;
					g.setFont(new Font("TimesRoman", Font.BOLD, fontSize)); 
					g.setColor(Color.black);
					String display = Integer.toString((int)Math.pow(2, board[i][j]));
					g.drawString(display, i * width + width / 2 - width / 6  , (board.length- j - 1) * width + width / 2 + width / 5);
				}
			}
		}
		g.setColor(Color.black);
		for(int i = 0; i < board.length;i++)
		{
			g.drawLine(0, width * i, size, width * i);
			g.drawLine(i * width, 0, i * width, size);
		}
	}
	public FastState move(Direction d)
	{
		if(d == Direction.Down)
		{
			byte[][] newBoard = arrayCopy(board);
			for(int i = 0; i < newBoard.length;i++)
			{
				collapse(newBoard[i],false);
			}
			return new FastState(newBoard, false);
		} 
		else if(d == Direction.Up) 
		{
			byte[][] newBoard = arrayCopy(board);
			for(int i = 0; i < newBoard.length;i++)
			{		
				collapse(newBoard[i],true);
			}
			return new FastState(newBoard,false);
		} 
		else if(d == Direction.Right)
		{
			byte[][] newBoard = new byte[board.length][board.length];
			for(int i = 0; i < board.length;i++)
			{
				byte[] temp = new byte[board.length];
				for(int j = 0; j < board.length;j++)
				{
					temp[j] = board[j][i];
				}	
				collapse(temp,true);
				for(int j = 0; j < board.length; j++)
				{
					newBoard[j][i] = temp[j];
				}
			}
			return new FastState(newBoard,true);
		}
		else 
		{
			byte[][] newBoard = new byte[board.length][board.length];
			for(int i = 0; i < board.length;i++)
			{
				byte[] temp = new byte[board.length];
				for(int j = 0; j < board.length;j++)
				{
					temp[j] = board[j][i];
				}
				collapse(temp,false);
				for(int j = 0; j < board.length; j++)
				{
					newBoard[j][i] = temp[j];
				}
			}
			return new FastState(newBoard,false);
		}
			
		
		
	}
	private static void reverse(byte[] array)
	{
		for(int i = 0; i < array.length / 2;i++)
		{
			byte temp = array[i];
		    array[i] = array[array.length - i - 1];
		    array[array.length - i - 1] = temp;
		}
	}
	private static byte[][] arrayCopy(byte[][] array)
	{
		byte[][] copy = new byte[array.length][];
		for(int i = 0; i < array.length;i++)
		{
			copy[i] = Arrays.copyOf(array[i], array[i].length);
		}
		return copy;
	}
	private static void collapse(byte[] column, boolean reverse)
	{
		int index = FastSolver.getIndex(column[0], column[1], column[2], column[3]);
		byte[] data = tables[reverse ? 1 : 0][index];
		for(int i = 0; i < data.length;i++)
		{
			column[i] = data[i];
		}
	}
	
	public FastState addRandomTile(boolean four)
	{
		byte[][] newBoard = arrayCopy(board);
		int empty = 0;
		for(int i = 0; i < board.length;i++)
		{
			for(int j = 0; j < board.length;j++)
			{
				if(board[i][j] == 0) empty++;
			}
		}
		if(empty == 0) return new FastState(newBoard,false);
		int square = rand.nextInt(empty);
		boolean is4 = four && (rand.nextInt(10) == 0);
		for(int i = 0; i < board.length;i++)
		{
			for(int j = 0; j < board.length;j++)
			{
				if(board[i][j] == 0)
				{
					if(square == 0) 
					{
						newBoard[i][j] = (byte) (is4 ? 2 : 1);
						i = board.length;
						break;
					}
					square--;
				}
				
			}
		}
		return new FastState(newBoard,false);
		
	}
	private static byte[] calculate(byte... column)
	{
		column = Arrays.copyOf(column, column.length);
		for(int i = 1; i < column.length;i++)
		{
			if(column[i] != 0)
			{
				int j = i;
				while(j >= 1 && column[j-1] == 0)
				{
					j--;
				}
				if(j > 0 && column[j-1] == column[i])
				{
					column[j-1]++;
					column[j-1] *= -1;
					column[i] = 0;
				}
				else if(j != i)
				{
					column[j] = column[i];
					column[i] = 0;
				}
			}
		}
		for(int i = 0; i < column.length;i++)
		{
			if(column[i] < 0) column[i] *= -1;
		}
		return column;
	}
	public void print(PrintStream p)
	{
		for(int i = board.length - 1; i >= 0;i--)
		{
			for(int j = 0; j < board.length;j++)
			{
				p.print(board[j][i] + " ");
			}
			p.println();
		}
		p.flush();
	}
	public Collection<FastState> possibleRandomAdditions()
	{
		ArrayList<FastState> results = new ArrayList<>();
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board.length;j++)
			{
				if(board[i][j] == 0)
				{
					byte[][] temp = arrayCopy(board);
					temp[i][j] = 1;
					results.add(new FastState(temp,false));
					temp = arrayCopy(board);
					temp[i][j] = 2;
					results.add(new FastState(temp,false));
				}
			}
		}
		return results;
	}
	
	public int score()
	{
		int score = 0;
		for(int i = 0; i< board.length;i++)
			for(int j = 0; j < board.length;j++)
			{
				if(board[i][j] != 0)
				{
					score +=  (1 << (board[i][j])) * (board[i][j]-1);
				}
			}
		return score;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(board);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof FastState))
			return false;
		FastState other = (FastState) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		return true;
	}
}	

