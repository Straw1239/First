package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JComponent;

import objects.BulletDataHolder;
import objects.EnemyDataHolder;
import objects.Player;
import objects.PlayerDataHolder;
import core.Display;

public class Renderer extends JComponent 
{
	private static final long serialVersionUID = 0L;
	private Display display;

	public Renderer()
	{
		setBackground(Color.white);
	}
	
	public void setDisplay(Display d)
	{
		if(d == null) throw new NullPointerException();
		display = d;
	}
	
	public void paintComponent(Graphics g)
	{
		if(display == null) return;
		drawPlayer(g);
		drawBullets(g);
		drawEnemies(g);
	}
	
	private void drawPlayer(Graphics g)
	{
		g.setColor(Player.color);
		PlayerDataHolder player = display.player;
		g.fillOval(getX(player.getX() - Player.radius), getY(player.getY() - Player.radius), getX(Player.radius * 2), getY(Player.radius * 2));
	}
	
	private void drawBullets(Graphics g)
	{
		Iterator<? extends BulletDataHolder> it = display.bullets.iterator();
		while(it.hasNext())
		{
			BulletDataHolder bullet = it.next();
			g.setColor(bullet.getColor());
			g.fillOval(getX(bullet.getX() - bullet.getRadius()), getY(bullet.getY() - bullet.getRadius()), getX(bullet.getRadius() * 2), getY(bullet.getRadius() * 2));
		}
	}
	
	private void drawEnemies(Graphics g)
	{
		Iterator<? extends EnemyDataHolder> it = display.enemies.iterator();
		while(it.hasNext())
		{
			
		}
	}
	
	private int getX(double gameX)
	{
		return (int) (gameX * getWidth() / display.width);
	}
	
	
	
	private int getY(double gameY)
	{
		return (int) (gameY * getHeight() / display.height);
	}
}
