package objects.events;

import java.util.Collection;

import javafx.scene.canvas.GraphicsContext;
import objects.Bullet;
import objects.Enemy;
import objects.Faction;
import objects.ObjectDataHolder;
import objects.Player;

import com.google.common.collect.Multimap;
/**
 * Root interface for all events. An event represents something which has effects on engine objects,
 * such as the player, bullets, enemies, and other events. An event can therefore be used to modify the game state
 * in almost any way. In addition, events have graphical effects on the screen, and so can be used for visuals.
 * @author Rajan
 *
 */
public interface EventDataHolder extends ObjectDataHolder
{
	/**
	 * Effects on player. This and other effects methods should only be called once per tick.
	 * @param Player to have effects applied to.
	 */
	public void playerEffects(Player p);
	
	/**
	 * Effects on bullet. This and other effects methods should only be called once per tick.
	 * @param Bullets apply effects on.
	 */
	public void bulletEffects(Multimap<Faction, Bullet> bullets);
	
	/**
	 * Effects on enemies. This and other effects methods should only be called once per tick.
	 * @param Enemies to apply effects on.
	 */
	public void enemyEffects(Collection<Enemy> enemies);
	
	/**
	 * Effects on other events. This and other effects methods should only be called once per tick.
	 * Return any new events created by this one in the returned collection, adding them directly 
	 * to the collection may cause errors.
	 * @param Events to be modified
	 */
	public Collection<GameEvent> eventEffects(Collection<GameEvent> events);
	
	public void draw(GraphicsContext g);
	
	public boolean hasExpired();
	
	public long startTime();
		
}
