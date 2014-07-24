package engine;

import java.util.ArrayList;
import java.util.Collection;

import objects.GameObject;
import objects.entities.BasicEnemy;

public class HankSpawner implements Spawner
{
	long lastSpawn = 0;
	@Override
	public Collection<? extends GameObject> spawn(State d)
	{
		Collection<GameObject> spawns = new ArrayList<>();
		if(d.time > lastSpawn + 180)
		{
			spawns.add(new BasicEnemy(500, 500));
			lastSpawn = d.time;
		}
		return spawns;
	}

}
