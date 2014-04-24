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
import ui.BiTransformer;
import ui.Window;
import utils.XRandom;

/**
 * The main class for the game. 
 * Contains static methods and variables including main()
 * Not used as an object, should not be instantiated.
 * Also provides the random number generator for the entire program,
 * and gives the current engine time in ticks to all parts of the program.
 * @author Rajan Troll
 *
 */
public class MainGame 
{
	public static final double FPS = 60;
	public static final double UPS = 60;
	
	/**
	 * Main window for the game. Handles all user interfaces.
	 */
	private static Window window;
	
	/**
	 * Main engine for the game. Performs all core calculations,
	 * provides an immutable representation of it's internal state
	 */
	private static Engine engine;
	private static KeyListen keyListener = new KeyListen();
	private static MouseListen mouseListener = new MouseListen();
	private static volatile boolean paused = false;
	
	/**
	 * The random number generator for the game as a whole.
	 * Uses the XORSHIFT algorithm instead of the default algorithm
	 * for increased performance and period.
	 */
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
		while(window == null)
		try 
		{
			Thread.sleep(10);
		}
		catch (InterruptedException e) 
		{
			throw new RuntimeException(e);
		}
		engine = new Engine(window.getWidth(), window.getHeight());
		window.addKeyListener(keyListener);
		window.addMouseListener(mouseListener);
		runEngine();
		runGraphics();
	}
	/**
	 * Starts engine execution in a new thread.
	 * Continues engine updates indefinitely, 
	 * limits engine speed to UPS, and stops execution while
	 * game is paused.
	 */
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
	/**
	 * Starts rendering execution on the main thread.
	 * Updates screen at FPS rate. Obtains data to display from engine,
	 * passes to window. Currently does not handle paused.
	 */
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
	
	/**
	 * Returns the engine time in ticks since the start of the game.
	 * 
	 * @return ticks
	 */
	
	public static long getTime()
	{
		return engine.getTime();
	}
	
	/**
	 * Creates the game window and engine.
	 * In the future it will only create the game window.
	 */
	
	private static void createWindow()
	{
		window = new Window();
	}
	
	/**
	 * Calculates the player's action based on user input
	 * @return	Player Action this tick
	 */
	
	private static Player.Action getPlayerAction()
	{
		boolean up = window.isKeyPressed(KeyEvent.VK_W);
		boolean down = window.isKeyPressed(KeyEvent.VK_S);
		boolean right = window.isKeyPressed(KeyEvent.VK_D);
		boolean left = window.isKeyPressed(KeyEvent.VK_A);
		if(window.isClicked(1))
			return new Player.Action(up, down, left, right, window.mouseX(), window.mouseY());
		return new Player.Action(up, down, left, right);
	}
	
	
	/**
	 * MainGame's key listener. Used to trigger high level events,
	 * not for keeping track of general keyboard input. Might be deleted 
	 * in the future.
	 * @author Rajan Troll
	 *
	 */
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
	
	/**
	 * See KeyListen. Should probably be replaced by MouseTracker in
	 * ui.
	 * @author Rajan Troll
	 *
	 */
	
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
