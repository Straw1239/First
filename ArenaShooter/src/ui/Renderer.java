package ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

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
	}
	
	private void drawPlayer(Graphics g)
	{
		g.setColor(Player.color);
		PlayerDataHolder player = display.player;
		g.fillOval(getX(player.getX() - Player.radius), getY(player.getY() - Player.radius), getX(Player.radius * 2), getY(Player.radius * 2));
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
