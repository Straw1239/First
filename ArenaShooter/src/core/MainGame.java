package core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Random;

import javax.swing.SwingUtilities;

import objects.Faction;
import objects.Player;
import objects.events.Explosion;
import ui.Window;
import utils.XRandom;

public class MainGame 
{
	private static final double FPS = 60;
	private static final double UPS = 60;
	private static Window window;
	private static Engine engine;
	private static KeyListen keyListener = new KeyListen();
	private static MouseListen mouseListener = new MouseListen();
	private static volatile boolean paused = false;
	public static final Random rand = new XRandom(((System.nanoTime() + Runtime.getRuntime().hashCode() * 7) + Thread.currentThread().hashCode()) * 31 + Calendar.getInstance().hashCode());
	
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				createWindow();
			}
		});
		while(engine == null)
		try 
		{
			Thread.sleep(10);
		}
		catch (InterruptedException e) 
		{
			throw new RuntimeException(e);
		}
		window.addKeyListener(keyListener);
		window.addMouseListener(mouseListener);
		runEngine();
		runGraphics();
	}
	
	private static void runEngine() 
	{
		Thread t = new Thread(new Runnable()
		{
			public void run()
			{
				long frameNanoTime = (long)((1/UPS) * 1000_000_000);
				long startFrameTime;
				while(true)
				{
					if(paused)
					{
						try 
						{
							Thread.sleep(10);
						} 
						catch (InterruptedException e) 
						{
							throw new RuntimeException(e);
						}
					}
					else
					{
						startFrameTime = System.nanoTime();
						engine.setPlayerAction(getPlayerAction());
						engine.setCursorLocation(window.mouseX(), window.mouseY());
						engine.update();
						
						while(System.nanoTime() - startFrameTime < frameNanoTime)
						{
							try 
							{
								Thread.sleep(1);
							} 
							catch (InterruptedException e) 
							{
								throw new RuntimeException(e);
							}
						}
					}
				}
			}
		});
		t.start();
	}

	private static void runGraphics()
	{
		long frameNanoTime = (long)((1/FPS) * 1000_000_000);
		long startFrameTime;
		while(true)
		{
			if(paused) return;
			startFrameTime = System.nanoTime();
			window.refresh(engine.getDisplay());
			while(System.nanoTime() - startFrameTime < frameNanoTime)
			{
				try 
				{
					Thread.sleep(1);
				} 
				catch (InterruptedException e) 
				{
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	public static long getTime()
	{
		return engine.getTime();
	}
	
	private static void createWindow()
	{
		window = new Window();
		engine = new Engine(window.getWidth(),window.getHeight());
	}
	
	private static Player.Action getPlayerAction()
	{
		boolean up = window.isKeyPressed(KeyEvent.VK_W);
		boolean down = window.isKeyPressed(KeyEvent.VK_S);
		boolean right = window.isKeyPressed(KeyEvent.VK_D);
		boolean left = window.isKeyPressed(KeyEvent.VK_A);
		if(mouseListener.hasClicked())
		{
			double gameX = mouseListener.getX(), gameY = mouseListener.getY();
			gameX /= window.getWidth();
			gameY /= window.getHeight();
			gameX *= engine.width;
			gameY *= engine.height;
			Player.Action action = new Player.Action(up, down, left, right, gameX, gameY);
			mouseListener.reset();
			return action;
		}
		if(window.isClicked(1))
			return new Player.Action(up, down, left, right, window.mouseX(), window.mouseY());
		return new Player.Action(up, down, left, right);
	}
	
	private static class KeyListen implements KeyListener
	{
		@Override
		public void keyPressed(KeyEvent e) 
		{
			if(e.getKeyCode() == KeyEvent.VK_R)
			{
				engine = new Engine(window.getWidth(), window.getHeight());
				System.gc();
				System.runFinalization();
			}
		}

		@Override
		public void keyReleased(KeyEvent e)
		{
			
			
		}

		@Override
		public void keyTyped(KeyEvent e) 
		{	
			
		}	
	} 
	
	private static class MouseListen extends MouseAdapter
	{
		private boolean clicked = false;
		private double x,y;
		
		public boolean hasClicked()
		{
			return clicked;
		}
		
		public double getX()
		{
			if(!clicked) throw new IllegalStateException("Mouse has not been clicked");
			return x;
		}
		
		public double getY()
		{
			if(!clicked) throw new IllegalStateException("Mouse has not been clicked");
			return y;
		}
		
		public void reset()
		{
			clicked = false;
		}
		
		public void mouseClicked(MouseEvent e)
		{
			
		}
		
		public void mousePressed(MouseEvent e)
		{
			if(e.getButton() == MouseEvent.BUTTON1)
			clicked = true;
			x = e.getX();
			y = e.getY();
			if(e.getButton() == MouseEvent.BUTTON3)
			engine.addEvent(new Explosion(e.getX(), e.getY(), Faction.Player, 200, 5));
		}
		
		public void mouseReleased(MouseEvent e)
		{
			
		}
		
		public void mouseMoved(MouseEvent e)
		{
			
		}
	}	
}
