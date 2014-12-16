package objects.entities;

import objects.Faction;
import objects.ReadableObject;
import player.Player;
import engine.State;


public abstract class Enemy extends Entity implements ReadableEnemy
{
	protected Enemy(double x, double y) 
	{
		super(x, y);
		faction = Faction.Enemy;
	}
	
	protected ReadableObject findTarget(State s)
	{
		return Player.THE;
	}
	
	
	
}
