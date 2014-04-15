package core;

import java.util.Collection;
import java.util.Collections;

import objects.BulletDataHolder;
import objects.EnemyDataHolder;
import objects.PlayerDataHolder;

import java.util.function.*;
import com.google.common.collect.Collections2;


public class Display 
{
	public final Collection<? extends EnemyDataHolder> enemies;
	public final Collection<? extends BulletDataHolder> bullets;
	public final PlayerDataHolder player;
	public final double width, height;
	
	
	public Display(PlayerDataHolder player, Collection<? extends EnemyDataHolder> enemies, Collection<? extends BulletDataHolder> bullets, double width, double height)
	{
		
		
		this.enemies = null;//Collections2.transform(enemies);
		this.bullets = Collections.unmodifiableCollection(bullets);
		this.player = new PlayerDataHolder.Copier(player);
		this.width = width;
		this.height = height;
	}
}
