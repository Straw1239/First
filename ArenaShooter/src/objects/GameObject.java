package objects;

public abstract class GameObject 
{
	protected double x,y;
	protected Faction faction;
	
	protected GameObject(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	protected GameObject(double x, double y, Faction faction)
	{
		this(x,y);
		this.faction = faction;
	}
	
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
