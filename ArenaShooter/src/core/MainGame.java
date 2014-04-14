package core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import objects.Player;
import ui.Window;

public class MainGame 
{
	private static final double FPS = 30;
	private static final double UPS = 60;
	private static Window window;
	private static Engine engine;
	private static KeyListen keyListener = new KeyListen();
	private static long time = 0;
	private static volatile boolean paused = false;
	
	
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
			e.printStackTrace();
		}
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
							e.printStackTrace();
							System.exit(-1);
						}
					}
					else
					{
						startFrameTime = System.nanoTime();
						engine.setPlayerAction(getPlayerAction());
						engine.update();
						while(System.nanoTime() - startFrameTime < frameNanoTime)
						{
							try 
							{
								Thread.sleep((frameNanoTime - (System.nanoTime() - startFrameTime)) / 1000);
							} 
							catch (InterruptedException e) 
							{
								e.printStackTrace();
								System.exit(-1);
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
					Thread.sleep((frameNanoTime - (System.nanoTime() - startFrameTime)) / 1000);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
					System.exit(-1);
				}
			}
		}
	}
	
	public static long getTime()
	{
		return time;
	}
	
	private static void createWindow()
	{
		window = new Window();
		engine = new Engine(window.getWidth(),window.getHeight());
		window.addKeyListener(keyListener);
	}
	
	private static Player.Action getPlayerAction()
	{
		boolean up = window.isKeyPressed(KeyEvent.VK_W);
		boolean down = window.isKeyPressed(KeyEvent.VK_S);
		boolean right = window.isKeyPressed(KeyEvent.VK_D);
		boolean left = window.isKeyPressed(KeyEvent.VK_A);
		return new Player.Action(up, down, left, right);
	}
	
	private static class KeyListen implements KeyListener
	{

		@Override
		public void keyPressed(KeyEvent arg0) 
		{
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) 
		{
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) 
		{	
			
		}
		
		
	}
	private static class MouseListen extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			
		}
		
		public void mousePressed(MouseEvent e)
		{
			
		}
		
		public void mouseReleased(MouseEvent e)
		{
			
		}
		
		public void mouseMoved(MouseEvent e)
		{
			
		}
		
		
	}
	
	
	
	
	
	
}
