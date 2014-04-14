package core;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import objects.Bullet;
import objects.Enemy;
import objects.Faction;
import objects.Player;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;


public class Engine implements Serializable
{
	public static final long serialVersionUID = 0L;
	
	public final double width, height;
	
	private volatile boolean isDisplaying = false, isUpdating = false;
	
	private Player player;
	private Player.Action playerAction;
	private List<Enemy> enemies = new LinkedList<>();
	private Multimap<Faction, Bullet> bullets = HashMultimap.create();
	
	public Engine(double width, double height)
	{
		this.width = width;
		this.height = height;
		player = new Player(width / 2, height / 2);
		
	}
	
	public Display getDisplay() 
	{
		while(isUpdating )
		{
			try 
			{
				Thread.sleep(0,500);
			} 
			catch (InterruptedException e) 
			{
				throw new RuntimeException(e);
			}
		}
		isDisplaying = true;
		Display state = new Display(player,enemies,bullets.values());
		isDisplaying = false;
		return state;
	}
	
	public void setPlayerAction(Player.Action action)
	{
		playerAction = action;
	}
	
	public void update()
	{
		while(isDisplaying)
		{
			try 
			{
				Thread.sleep(0,500);
			} 
			catch (InterruptedException e) 
			{
				throw new RuntimeException(e);
			}
		}
		isUpdating = true;
		player.update();
		executePlayerAction();
		updateEnemies();
		updateBullets();
		isUpdating = false;
		
	}

	private void updateBullets()
	{
		
		
		
	}
	
	private void updateEnemies()
	{
		
	}
	
	private void executePlayerAction()
	{
		if(playerAction == null) return;
		
	}
	
	
	
	
}
