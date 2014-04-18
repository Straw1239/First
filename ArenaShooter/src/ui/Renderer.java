package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JComponent;

import objects.BulletDataHolder;
import objects.EnemyDataHolder;
import objects.Player;
import objects.PlayerDataHolder;
import objects.events.EventDataHolder;
import core.Display;

public class Renderer extends JComponent 
{
	private static final long serialVersionUID = 0L;
	private Display display;
	private Transformer converter = new ScreenMapper();

	public Renderer()
	{
		
	}
	
	public void setDisplay(Display d)
	{
		if(d == null) throw new NullPointerException();
		display = d;
	}
	
	public void paintComponent(Graphics g)
	{
		//g.setColor(Color.black);
		//g.fillRect(0, 0, getWidth(), getHeight());
		if(display == null) return;
		drawEvents(g);
		drawPlayer(g);
		drawBullets(g);
		drawEnemies(g);
		display.mouse.draw(g, converter);
	}
	
	private void drawPlayer(Graphics g)
	{
		g.setColor(Player.color);
		PlayerDataHolder player = display.player;
		g.fillOval(converter.screenX(player.getX() - Player.radius), converter.screenY(player.getY() - Player.radius), 
				converter.pixels(Player.radius * 2), converter.pixels((Player.radius * 2)));
		g.setColor(Color.black);
		g.setFont(new Font("Arial",Font.BOLD,36));
		g.drawString("Health " + player.health(), 20, 30);
	}
	
	private void drawBullets(Graphics g)
	{
		Iterator<? extends BulletDataHolder> it = display.bullets.iterator();
		while(it.hasNext())
		{
			BulletDataHolder bullet = it.next();
			g.setColor(bullet.getColor());
			g.fillOval(converter.screenX((bullet.getX() - bullet.getRadius())), converter.screenY((bullet.getY() - bullet.getRadius())),
					converter.pixels(bullet.getRadius() * 2), converter.pixels(bullet.getRadius() * 2));
		}
	}
	
	private void drawEnemies(Graphics g)
	{
		Iterator<? extends EnemyDataHolder> it = display.enemies.iterator();
		while(it.hasNext())
		{
			it.next().draw(g, converter);
		}
	}
	
	private void drawEvents(Graphics g)
	{
		Iterator<? extends EventDataHolder> it = display.events.iterator();
		while(it.hasNext())
		{
			it.next().draw(g, converter);
		}
	}
	
	private class ScreenMapper implements Transformer
	{

		@Override
		public int screenX(double gameX) 
		{
			return (int) (gameX * getWidth() / display.width);
		}

		@Override
		public int screenY(double gameY) 
		{
			return (int) (gameY * getHeight() / display.height);
		}

		@Override
		public int pixels(double gameLength) 
		{
			return (int) (gameLength * getWidth() / display.width);
		}
		
	}
}
