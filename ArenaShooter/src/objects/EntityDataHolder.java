package objects;

import javafx.scene.canvas.GraphicsContext;

public interface EntityDataHolder extends ObjectDataHolder
{
	public boolean isDead();
	
	public double health();
	
	public double maxHealth();
	
	public void draw(GraphicsContext g);
	
	
	
}
