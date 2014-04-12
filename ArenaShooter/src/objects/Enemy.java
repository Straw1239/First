package objects;

public abstract class Enemy extends Entity 
{

	protected Enemy(double x, double y) 
	{
		super(x, y);
		faction = Faction.Enemy;
	}
	
}
