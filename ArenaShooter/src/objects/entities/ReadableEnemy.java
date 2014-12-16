package objects.entities;

import javafx.scene.canvas.GraphicsContext;

public interface ReadableEnemy extends EntityDataHolder
{
	@Override
	public void draw(GraphicsContext g);
	
	
}
