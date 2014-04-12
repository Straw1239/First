package objects;

public abstract class GameObject 
{
	protected double x,y;
	protected Faction faction;
	
	public abstract void update();
	
	public abstract boolean collidesWith(GameObject entity);
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
}
