package objects;

import javafx.scene.canvas.GraphicsContext;
import utils.Cloner;

public interface ObjectDataHolder extends Cloner
{
	public double getX();
	
	public double getY();
	
	public Faction getFaction();
	
	public void draw(GraphicsContext g);
	
		
	
	
	
}
