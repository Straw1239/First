package clicker;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Clicker
{
	

	static volatile boolean clicking = false;
	static JFrame frame = new JFrame();
	static Robot robot;
	static Point COOKIE_LOCATION = new Point(2100, 500);
	static Point BUY_LOCATION = new Point(3000, 950);
	public static void main(String[] args) throws AWTException, InterruptedException
	{
		robot = new Robot();
		frame.setFocusable(true);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		frame.addKeyListener(new KeyTracker());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		robot.setAutoDelay(0);
		while(true)
		{
			
			if(clicking)
			{
				for(int i = 0; i < 2000; i++)
				{
					Thread.sleep(10);
					robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
					robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);	
				}
				buyStuff();
			}
		}
	}
	
	private static void buyStuff()
	{
		robot.mouseMove(BUY_LOCATION.x, BUY_LOCATION.y);
		for(int i = 0; i < 5; i++)
		{
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		}
		robot.mouseMove(COOKIE_LOCATION.x, COOKIE_LOCATION.y);
	}
	
	public static class KeyTracker implements KeyListener
	{

		@Override
		public void keyTyped(KeyEvent e){		
		}

		@Override
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_Q)
			{
				System.exit(0);
			}
			if(e.getKeyCode() == KeyEvent.VK_C)
			{
				clicking = !clicking;
			}
		}

		@Override
		public void keyReleased(KeyEvent e)
		{
			// TODO Auto-generated method stub

		}

	}
}
