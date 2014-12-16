package objects.events;

import objects.Factioned;
import objects.Locatable;
import utils.Copyable;
import engine.EventHandler;
import fxcore.Renderable;
/**
 * Root interface for all events. An event represents something which has effects on engine objects,
 * such as the player, bullets, enemies, and other events. An event can therefore be used to modify the game state
 * in almost any way. In addition, events have graphical effects on the screen, and so can be used for visuals.
 * @author Rajan
 *
 */
public interface ReadableEvent extends Renderable, Copyable<ReadableEvent>, Locatable, Factioned
{
	public void effects(EventHandler handler);
	
	public default ReadableEvent copy()
	{
		return this;
	}
		
}
