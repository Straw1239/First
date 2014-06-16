package engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import objects.BasicEnemy;
import objects.GameObject;
import objects.MovingEnemy;
import fxcore.MainGame;
import static fxcore.MainGame.rand;

public class DefaultSpawner implements Spawner
{
	
	public DefaultSpawner(double width, double height)
	{
		
	}
	

	private long lastSpawn = MainGame.getTime();
	@Override
	public Collection<? extends GameObject> spawn(State d)
	{
		List<GameObject> spawns = new ArrayList<>();
		if(d.time - lastSpawn >= 60)
		{
			double x = rand.nextDouble();
			spawns.add(rand.nextBoolean() ? new BasicEnemy() : new MovingEnemy());
			lastSpawn = d.time;
		}
		return spawns;
	}

}
