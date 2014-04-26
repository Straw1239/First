package fxcore;

import java.util.Collection;

import objects.BulletDataHolder;
import objects.Cursor;
import objects.EnemyDataHolder;
import objects.PlayerDataHolder;
import objects.events.EventDataHolder;
import utils.Cloner;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;

/**
 * Class representing a display of an internal engine view.
 * Should be immutable, currently needs fixing
 * Passed from the engine to the renderer for displaying to the screen
 * also passed to all engine objects in update.
 * @author Rajan Troll
 *
 */
public class Display 
{
	public final ImmutableCollection<? extends EnemyDataHolder> enemies;
	public final ImmutableCollection<? extends BulletDataHolder> bullets;
	public final ImmutableCollection<? extends EventDataHolder> events;
	public final PlayerDataHolder player;
	public final Cursor mouse;
	public final double width, height;
	
	
	public Display(PlayerDataHolder player, Collection<? extends EnemyDataHolder> enemies, Collection<? extends BulletDataHolder> bullets, Collection<? extends EventDataHolder> events, Cursor cursor, double width, double height)
	{
		this.enemies = ImmutableList.copyOf(enemies.stream().map(e -> (EnemyDataHolder) e.clone()).iterator());
		this.bullets = ImmutableList.copyOf(bullets.stream().map(b -> (BulletDataHolder) b.clone()).iterator());
		this.events = ImmutableList.copyOf(events.stream().map(e -> (EventDataHolder) e.clone()).iterator());
		this.player = Cloner.clone(player);
		this.mouse = Cloner.clone(cursor);
		this.width = width;
		this.height = height;
	}
}
