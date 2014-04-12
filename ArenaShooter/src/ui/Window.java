package ui;

import java.awt.event.KeyListener;

import javax.swing.JFrame;

import core.Display;

public class Window 
{
	private JFrame frame; 
	private Renderer renderer; 
	
	public Window()
	{
		frame = new JFrame();
		renderer = new Renderer();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.getContentPane().add(renderer);
		renderer.setFocusable(true);
		renderer.setPreferredSize(frame.getSize());
		renderer.setSize(frame.getSize());
		frame.setVisible(true);
		renderer.requestFocusInWindow();
	}
	
	public int getWidth()
	{
		return frame.getWidth();
	}
	
	public int getHeight()
	{
		return frame.getHeight();
	}
	
	public void refresh(Display display)
	{
		frame.repaint();
	}
	
	public void addKeyListener(KeyListener k)
	{
		
	}
}
