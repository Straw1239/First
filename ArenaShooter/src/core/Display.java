package core;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import objects.BulletDataHolder;
import objects.EnemyDataHolder;
import objects.PlayerDataHolder;


public class Display 
{
	public final Collection<? extends EnemyDataHolder> enemies;
	public final Collection<? extends BulletDataHolder> bullets;
	public final PlayerDataHolder player;
	public final double width, height;
	
	
	public Display(PlayerDataHolder player, Collection<? extends EnemyDataHolder> enemies, Collection<? extends BulletDataHolder> bullets, double width, double height)
	{
		this.enemies = Collections.unmodifiableCollection(enemies.stream().map((e) -> new EnemyDataHolder.Copier(e)).collect(Collectors.toList()));
		this.bullets = Collections.unmodifiableCollection(bullets.stream().map((b) -> new BulletDataHolder.Copier(b)).collect(Collectors.toList()));
		this.player = new PlayerDataHolder.Copier(player);
		this.width = width;
		this.height = height;
	}
}
