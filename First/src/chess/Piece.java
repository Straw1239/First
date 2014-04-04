package chess;

public class Piece 
{
	public boolean isWhite;
	public ChessPiece type;
	public Piece(boolean isWhite, ChessPiece type)
	{
		this.isWhite = isWhite;
		this.type = type;
	}
	public Piece copy()
	{
		return new Piece(isWhite,type);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isWhite ? 1231 : 1237);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Piece))
			return false;
		Piece other = (Piece) obj;
		if (isWhite != other.isWhite)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
