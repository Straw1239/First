package ui;

import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import fxcore.Display;



public class MouseTracker extends MouseAdapter
{
	private boolean[] buttons = new boolean[MouseInfo.getNumberOfButtons()];
	//private double x, y;
	private long[] clickTimes = new long[MouseInfo.getNumberOfButtons()];
	
	public long MilliTimeSinceClick(int button)
	{
		return System.currentTimeMillis() - clickTimes[button];
	}
	
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
		clickTimes[e.getButton()] = e.getWhen();
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
