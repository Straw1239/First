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
	
	public void bulletEffects(Multimap<Faction, Bullet> bullets);
	
	public void enemyEffects(Collection<? extends Enemy> enemies);
	
	public void eventEffects(Collection<? extends GameEvent> events);
	
	public void draw(Graphics g, Transformer t);
	
	public boolean hasExpired();
	
	public long startTime();
	
	public static class Copier implements EventDataHolder
	{
		private double x, y;
		private Faction faction;
		private EventDataHolder source;
		
		public Copier(EventDataHolder e)
		{
			x = e.getX();
			y = e.getY();
			faction = e.getFaction();
			source = e;
		}
		@Override
		public double getX()
		{
			return x;
		}

		@Override
		public double getY()
		{
			return y;
		}

		@Override
		public Faction getFaction()
		{
			return faction;
		}

		@Override
		public void playerEffects(Player p)
		{
			source.playerEffects(p);
		}

		@Override
		public void bulletEffects(Multimap<Faction, Bullet> bullets)
		{
			source.bulletEffects(bullets);
		}

		@Override
		public void enemyEffects(Collection<? extends Enemy> enemies)
		{
			source.enemyEffects(enemies);
		}

		@Override
		public void eventEffects(Collection<? extends GameEvent> events)
		{
			source.eventEffects(events);
		}

		@Override
		public void draw(Graphics g, Transformer t)
		{
			source.draw(g, t);
		}

		@Override
		public boolean hasExpired()
		{
			return source.hasExpired();
		}

		@Override
		public long startTime()
		{
			return source.startTime();
		}
		
	}
		
}
