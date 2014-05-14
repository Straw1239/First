package objects.events;

import javafx.scene.canvas.GraphicsContext;
import objects.ObjectDataHolder;
import engine.EventHandler;
/**
 * Root interface for all events. An event represents something which has effects on engine objects,
 * such as the player, bullets, enemies, and other events. An event can therefore be used to modify the game state
 * in almost any way. In addition, events have graphical effects on the screen, and so can be used for visuals.
 * @author Rajan
 *
 */
public interface EventDataHolder extends ObjectDataHolder
{
	public void effects(EventHandler handler);
	
	@Override
	public void draw(GraphicsContext g);
	
	public boolean hasExpired();
	
	public long startTime();
		
}
