package objects;

public interface ObjectDataHolder extends Cloneable
{
	public double getX();
	
	public double getY();
	
	public Faction getFaction();
	
	public Object clone() throws CloneNotSupportedException;

}
