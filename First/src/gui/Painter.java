package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class Painter extends JPanel implements KeyListener
{
	public Painter()
	{
		addKeyListener(this);
	}
	
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.red);
		g.fillRect(0, 0,getWidth(), getHeight());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
		}
		
	}
}
