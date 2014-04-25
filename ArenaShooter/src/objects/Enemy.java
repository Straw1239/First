package objects;

import java.awt.Graphics;

import ui.Transformer;
import core.Display;


public abstract class Enemy extends Entity implements EnemyDataHolder
{
	protected Enemy(double x, double y) 
	{
		super(x, y);
		faction = Faction.Enemy;
	}
	
	public abstract void collideWithPlayer(Player p);
	
	public abstract void draw(Graphics g, Transformer t);
	
	
		
	
	
	
	
}
