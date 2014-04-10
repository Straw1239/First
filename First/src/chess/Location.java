package chess;

public class Location 
{
	public static final String columns = "abcdefgh"; 
	public int row,column;
	public Location(int row, int column)
	{
		this.row = row;
		this.column = column;
	}
	public Location(int row, char column)
	{
		this(row,getColumn(column));
	}
	public static int getColumn(char a)
	{
		return columns.indexOf(a);
	}
	public static char getName(int a)
	{
		return columns.charAt(a);
	}
}
