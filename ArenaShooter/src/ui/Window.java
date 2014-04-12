package ui;

import java.awt.event.KeyListener;

import javax.swing.JFrame;

import core.Display;

public class Window 
{
	private JFrame frame = new JFrame(); 
	private Renderer renderer = new Renderer(); 
	private KeyTracker tracker = new KeyTracker();
	
	public Window()
	{
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.getContentPane().add(renderer);
		renderer.setFocusable(true);
		renderer.setPreferredSize(frame.getSize());
		renderer.setSize(frame.getSize());
		frame.setVisible(true);
		renderer.requestFocusInWindow();
		addKeyListener(tracker);
	}
	
	public int getWidth()
	{
		return frame.getWidth();
	}
	
	public int getHeight()
	{
		return frame.getHeight();
	}
	
	public boolean isKeyPressed(int keyCode)
	{
		return tracker.isKeyPressed(keyCode);
	}
	
	public void refresh(Display display)
	{
		//GIVE DATA TO RENDERER FOR OUTPUT TO SCREEN!!!
		frame.repaint();
	}
	
	public void addKeyListener(KeyListener k)
	{
		renderer.addKeyListener(k);
	}
}
