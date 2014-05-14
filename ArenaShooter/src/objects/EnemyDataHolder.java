package objects;

import javafx.scene.canvas.GraphicsContext;

public interface EnemyDataHolder extends EntityDataHolder
{
	@Override
	public void draw(GraphicsContext g);
	
	
}
