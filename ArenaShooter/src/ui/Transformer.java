package ui;

public interface Transformer 
{
	public int screenX(double gameX);
	
	public int screenY(double gameY);
	
	public int pixels(double gameLength);
}
