package ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyTracker implements KeyListener
{

	private volatile boolean[] keys; 
	
	public KeyTracker()
	{
		this(256);
	}
	
	public KeyTracker(int maxKeyCode)
	{
		keys = new boolean[256];
	}
	
	public boolean isKeyPressed(int keyCode)
	{
		return keys[keyCode];
	}
	
	
	
	public void keyPressed(KeyEvent e) 
	{
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
