package core;

import java.util.Collection;

import objects.BulletDataHolder;
import objects.EnemyDataHolder;
import objects.Faction;
import objects.PlayerDataHolder;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;


public class Display 
{
	public final ImmutableCollection<? extends EnemyDataHolder> enemies;
	public final ImmutableCollection<? extends BulletDataHolder> bullets;
	public final PlayerDataHolder player;
	public final double width, height;
	
	
	public Display(PlayerDataHolder player, Collection<? extends EnemyDataHolder> enemies, Collection<? extends BulletDataHolder> bullets, double width, double height)
	{
		this.enemies = ImmutableList.copyOf(enemies);
		this.bullets = ImmutableList.copyOf(bullets);
		this.player = new ImmutablePlayerDataHolder(player);
		this.width = width;
		this.height = height;
	}
	
	private static class ImmutablePlayerDataHolder implements PlayerDataHolder
	{
		private double health, maxHealth, x, y;
		private final Faction faction = Faction.Player;
		
		public ImmutablePlayerDataHolder(PlayerDataHolder data)
		{
			health = data.health();
			maxHealth = data.maxHealth();
			x = data.getX();
			y = data.getY();
		}
		@Override
		public boolean isDead() 
		{
			return health == 0;
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
		public double health() 
		{
			return health;
		}

		@Override
		public double maxHealth() 
		{
			return maxHealth;
		}
		
	}
}
