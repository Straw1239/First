package objects.events;

import java.util.Collection;

import javafx.scene.canvas.GraphicsContext;
import objects.Bullet;
import objects.Enemy;
import objects.Faction;
import objects.ObjectDataHolder;
import objects.Player;

import com.google.common.collect.Multimap;

public interface EventDataHolder extends ObjectDataHolder
{
	public void playerEffects(Player p);
	
	public void bulletEffects(Multimap<Faction, Bullet> bullets);
	
	public void enemyEffects(Collection<? extends Enemy> enemies);
	
	public void eventEffects(Collection<? extends GameEvent> events);
	
	public void draw(GraphicsContext g);
	
	public boolean hasExpired();
	
	public long startTime();
		
}
