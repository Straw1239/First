package objects;

import javafx.scene.canvas.GraphicsContext;
import utils.Cloner;

public interface ObjectDataHolder extends Cloner, Locatable
{
	public Faction getFaction();
	
	public void draw(GraphicsContext g);

}
