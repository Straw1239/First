package objects;

import javafx.scene.canvas.GraphicsContext;


public abstract class Enemy extends Entity implements EnemyDataHolder
{
	protected Enemy(double x, double y) 
	{
		super(x, y);
		faction = Faction.Enemy;
	}
	
	public abstract void collideWithPlayer(Player p);
	
	public abstract void draw(GraphicsContext g);
	
	
	
	
		
	
	
	
	
}
