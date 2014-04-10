package chess;

public class Square 
{
	public Piece occupant;
	public Square()
	{
		occupant = null;
	}
	public Square(Piece piece)
	{
		occupant = piece;
	}
	public void remove()
	{
		occupant = null;
	}
	public void place(Piece piece)
	{
		if(!isEmpty()) throw new IllegalStateException();
		occupant = piece;
	}
	public boolean isEmpty()
	{
		return occupant == null;
	}
	public Square copy()
	{
		if(!isEmpty())
		return new Square(occupant.copy());
		return new Square();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((occupant == null) ? 0 : occupant.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Square))
			return false;
		Square other = (Square) obj;
		if (occupant == null) {
			if (other.occupant != null)
				return false;
		} else if (!occupant.equals(other.occupant))
			return false;
		return true;
	}
}
