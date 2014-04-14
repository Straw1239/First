package core;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import objects.Bullet;
import objects.Enemy;
import objects.Faction;
import objects.GameObject;
import objects.Player;
import utils.Utils;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;


public class Engine implements Serializable
{
	public static final long serialVersionUID = 0L;
	
	public final double width, height;
	
	private volatile boolean isDisplaying = false, isUpdating = false;
	
	private volatile Player player;
	private volatile Player.Action playerAction;
	private List<Enemy> enemies = new LinkedList<>();
	private Multimap<Faction, Bullet> bullets = LinkedListMultimap.create();
	
	public Engine(double width, double height)
	{
		this.width = width;
		this.height = height;
		player = new Player(width / 2, height / 2);
		
	}
	
	public Display getDisplay() 
	{
		while(isUpdating)
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
		Display state = new Display(player,enemies,bullets.values(), width, height);
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
		Collection<Bullet> allBullets = bullets.values();
		Iterator<Bullet> it = allBullets.iterator();
		while(it.hasNext())
		{
			Bullet b = it.next();
			b.update();
			if(b.hasHitWall(width, height))
			{
				it.remove();
			}
			
		}
		
	}
	
	private void updateEnemies()
	{
		
	}
	
	private void executePlayerAction()
	{
		if(playerAction == null) return;
		int moveSpeed = 10;
		if(playerAction.isDown()) player.move(0, moveSpeed);
		if(playerAction.isUp()) player.move(0, -moveSpeed);
		if(playerAction.isRight()) player.move(moveSpeed, 0);
		if(playerAction.isLeft()) player.move(-moveSpeed, 0);
		if(playerAction.isShooting())
		{
			double x = playerAction.targetX(), y = playerAction.targetY();
			double distance = Utils.distance(player.getX(), player.getY(), x, y);
			double speed = 1;
			bullets.put(Faction.Player, new Bullet(player,x * speed / distance, y  * speed / distance,5,Player.color));
		}
		
	}
	
	
	
	
	
	
}
