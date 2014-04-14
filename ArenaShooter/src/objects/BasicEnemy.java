package objects;

public class BasicEnemy extends Enemy 
{

	public BasicEnemy(double x, double y) 
	{
		super(x, y);
	}

	@Override
	public void update() 
	{	

	}

	@Override
	public boolean collidesWith(GameObject entity) 
	{
		return false;
	}

}
