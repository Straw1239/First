package objects.events;

import java.awt.Graphics;
import java.util.Collection;

import objects.Bullet;
import objects.Enemy;
import objects.Faction;
import objects.ObjectDataHolder;
import objects.Player;
import ui.Transformer;

import com.google.common.collect.Multimap;

public interface EventDataHolder extends ObjectDataHolder
{
	public void playerEffects(Player p);
	
	public void bulletEffects(Multimap<Faction, ? extends Bullet> bullets);
	
	public void enemyEffects(Collection<? extends Enemy> enemies);
	
	public void eventEffects(Collection<? extends GameEvent> events);
	
	public void draw(Graphics g, Transformer t);
	
	public boolean hasExpired();
	
	public long startTime();
	
	
		

			
}
