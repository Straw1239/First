package core;

import java.util.List;

import objects.BulletDataHolder;
import objects.EntityDataHolder;


public class Display 
{
	public final EntityDataHolder player;
	
	public Display(EntityDataHolder player, List<EntityDataHolder> enemies, List<BulletDataHolder> bullets)
	{
		this.player = player;
	}
	
	
	
}
