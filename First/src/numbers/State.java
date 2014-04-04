package numbers;

import java.awt.Graphics;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

public class State 
{
	private int[][] board;
	
	public State()
	{
		this(4);
	}
	public State(String state)
	{
		
	}
	public State(File f)
	{
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
			if(board == null) board = new int[data.length][data.length];
			for(int i = 0; i < data.length;i++)
			{
				board[i][board.length-j-1] = Integer.parseInt(data[i]);
			}
			j++;
		}
		input.close();
		
	}
	public State(int[][] board)
	{
		this.board = arrayCopy(board);
	}

	private State(int[][] board, boolean copy)
	{
		this.board = board;
	}
	
	public State(int size)
	{
		board = new int[size][size];
	}
	public int getSquare(int row, int column)
	{
		return board[row][column];
	}
	public int size()
	{
		return board.length;
	}
	public void draw(Graphics g,int size)
	{
		
	}
	public State move(Direction d)
	{
		if(d == Direction.Down)
		{
			int[][] newBoard = arrayCopy(board);
			for(int i = 0; i < newBoard.length;i++)
			{
				collapse(newBoard[i]);
			}
			return new State(newBoard, false);
		} 
		else if(d == Direction.Up) 
		{
			int[][] newBoard = arrayCopy(board);
			for(int i = 0; i < newBoard.length;i++)
			{
				reverse(newBoard[i]);
				collapse(newBoard[i]);
				reverse(newBoard[i]);
			}
			return new State(newBoard,false);
		} 
		else if(d == Direction.Right)
		{
			int[][] newBoard = new int[board.length][board.length];
			for(int i = 0; i < board.length;i++)
			{
				int[] temp = new int[board.length];
				for(int j = 0; j < board.length;j++)
				{
					temp[j] = board[j][i];
				}
				reverse(temp);
				collapse(temp);
				reverse(temp);
				for(int j = 0; j < board.length; j++)
				{
					newBoard[j][i] = temp[j];
				}
			}
			return new State(newBoard,false);
		}
		else 
		{
			int[][] newBoard = new int[board.length][board.length];
			for(int i = 0; i < board.length;i++)
			{
				int[] temp = new int[board.length];
				for(int j = 0; j < board.length;j++)
				{
					temp[j] = board[j][i];
				}
				collapse(temp);
				for(int j = 0; j < board.length; j++)
				{
					newBoard[j][i] = temp[j];
				}
			}
			return new State(newBoard,false);
		}
			
		
		
	}
	private static void reverse(int[] array)
	{
		for(int i = 0; i < array.length / 2;i++)
		{
			int temp = array[i];
		    array[i] = array[array.length - i - 1];
		    array[array.length - i - 1] = temp;
		}
	}
	private static int[][] arrayCopy(int[][] array)
	{
		int[][] copy = new int[array.length][];
		for(int i = 0; i < array.length;i++)
		{
			copy[i] = Arrays.copyOf(array[i], array[i].length);
		}
		return copy;
	}
	
	private static void collapse(int... column)
	{
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
	public Collection<State> possibleRandomAdditions()
	{
		ArrayList<State> results = new ArrayList<>();
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board.length;j++)
			{
				if(board[i][j] == 0)
				{
					int[][] temp = arrayCopy(board);
					temp[i][j] = 1;
					results.add(new State(temp,false));
					temp = arrayCopy(board);
					temp[i][j] = 2;
					results.add(new State(temp,false));
				}
			}
		}
		return results;
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
		if (!(obj instanceof State))
			return false;
		State other = (State) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		return true;
	}
}	

