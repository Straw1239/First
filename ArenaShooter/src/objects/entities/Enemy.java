package objects.entities;

import objects.Faction;


public abstract class Enemy extends Entity implements EnemyDataHolder
{
	protected Enemy(double x, double y) 
	{
		super(x, y);
		faction = Faction.Enemy;
	}
	
	
	
}