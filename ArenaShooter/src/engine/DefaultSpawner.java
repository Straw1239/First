package engine;

import static fxcore.MainGame.getGameHeight;
import static fxcore.MainGame.getGameWidth;
import static fxcore.MainGame.rand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import objects.GameObject;
import objects.entities.BasicEnemy;
import objects.entities.Charger;
import objects.entities.FirstBoss;
import objects.entities.MannyEnemy;
import objects.entities.MovingEnemy;

public class DefaultSpawner implements Spawner
{
	
	public DefaultSpawner(double width, double height)
	{
		
	}
	

	private long lastSpawn = 0;
	private long lastBoss = -10000;// Spawn immediately for testing
	private long boss = -10000; // Spawn immediately for testing
	@Override
	public Collection<GameObject> spawn(State d)
	{
		List<GameObject> spawns = new ArrayList<>();
		double x = rand.nextDouble() * (getGameWidth() - 200) + 100, y = rand.nextDouble() * (getGameHeight() - 200) + 100;
		if(d.time - lastSpawn >= 60)
		{
			spawns.add(rand.nextBoolean() ? new BasicEnemy(x, y) : new MovingEnemy(x, y));
			lastSpawn = d.time;
		}
		if(d.time - lastBoss >= 2000)
		{
			spawns.add(new Charger(x, y));
			//spawns.add(new MannyEnemy(x, y));
			//spawns.add(new SpawningEnemy(x, y));
			lastBoss = d.time;
			x = rand.nextDouble() * (getGameWidth() - 200) + 100; 
			y = rand.nextDouble() * (getGameHeight() - 200) + 100;
		}
		/*
		if(d.time - boss >= 10000)
		{
			//spawns.add(new FirstBoss(x, y));
			boss = d.time;
		}*/
		return spawns;
	}

}
