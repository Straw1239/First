package ui;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;

import javax.swing.JFrame;

import core.Display;

public class Window 
{
	private JFrame frame = new JFrame(); 
	private Renderer renderer = new Renderer(); 
	private KeyTracker tracker = new KeyTracker();
	private MouseTracker mouse = new MouseTracker();
	
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
		addMouseListener(mouse);
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
	
	public boolean isClicked(int button)
	{
		return mouse.isClicked(button);
	}
	
	public double mouseX()
	{
		return getTransformer().gameX((mouse.getX()));
	}
	
	public double mouseY()
	{
		return getTransformer().gameY(mouse.getY());
	}
	
	public void refresh(Display display)
	{
		renderer.setDisplay(display);
		frame.repaint();
	}
	
	public void addKeyListener(KeyListener k)
	{
		renderer.addKeyListener(k);
	}
	
	public void addMouseListener(MouseAdapter m)
	{
		renderer.addMouseListener(m);
		renderer.addMouseMotionListener(m);
		renderer.addMouseWheelListener(m);
	}
	
	public BiTransformer getTransformer()
	{
		return renderer.converter;
	}
}
