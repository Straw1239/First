package core;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import objects.Bullet;
import objects.Enemy;
import objects.Faction;
import objects.Player;


public class Engine implements Serializable
{
	public static final long serialVersionUID = 0L;
	
	public final double width, height;
	
	private Player player;
	private List<Bullet>[] bullets = new List[Faction.values().length];
	private List<Enemy> enemies = new LinkedList<>();
	
	public Engine(double width, double height)
	{
		this.width = width;
		this.height = height;
		player = new Player(width / 2, height / 2);
		for(int i = 0; i < bullets.length; i++)
			bullets[i] = new LinkedList<>();	
	}
	
	public void update()
	{
		player.update();
		updateEnemies();
		updateBullets();
	}
	
	private void updateBullets()
	{
		
		
		
	}
	
	private void updateEnemies()
	{
		
	}

	public Display getDisplay() 
	{
		return null;
	}
	
	
	
	
}
