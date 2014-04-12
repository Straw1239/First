package core;

import java.util.LinkedList;
import java.util.List;

import objects.Bullet;
import objects.Player;

public class Engine 
{
	public final double width, height;
	
	private Player player;
	private List<Bullet> bullets = new LinkedList<>();
	
	
	public Engine(double width, double height)
	{
		this.width = width;
		this.height = height;
	}
	
	public void update()
	{
		
	}
}
