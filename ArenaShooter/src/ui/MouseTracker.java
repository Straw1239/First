package ui;

import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import core.Display;



public class MouseTracker extends MouseAdapter
{
	private boolean[] buttons = new boolean[MouseInfo.getNumberOfButtons()];
	
	public boolean isClicked(int button)
	{
		return buttons[button];
	}
	
	
	
	public double getX()
	{
		return MouseInfo.getPointerInfo().getLocation().getX();
	}
	
	public double getY()
	{
		return MouseInfo.getPointerInfo().getLocation().getY();
	}
	
	public void mouseClicked(MouseEvent e)
	{
		
	}
	
	public void mousePressed(MouseEvent e)
	{
		buttons[e.getButton()] = true;
	}
	
	public void mouseReleased(MouseEvent e)
	{
		buttons[e.getButton()] = false;
	}
	
	public void mouseMoved(MouseEvent e)
	{
		
	}
}
