package clicker;

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class Clicker
{
	static volatile boolean clicking = false;
	static Robot robot;
	static Point cookieLocation = new Point(2100, 500);
	static Point buyLocation = new Point(3000, 950);
	public static void main(String[] args) throws Throwable
	{
		robot = new Robot();
		GlobalScreen.registerNativeHook();
		GlobalScreen.getInstance().addNativeKeyListener(new KeyTracker());
		int counter = 0;
		while(true)
		{
			if(clicking)
			{
				Thread.sleep(5);
				click();
				if(counter == 0) buyStuff();
			}
			counter = (counter + 1) % 2000;
		}
	}
	
	private static void click()
	{
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);	
	}
	
	private static void buyStuff()
	{
		robot.mouseMove(buyLocation.x, buyLocation.y);
		for(int i = 0; i < 5; i++)
		{
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		}
		robot.mouseMove(cookieLocation.x, cookieLocation.y);
	}
	
	public static class KeyTracker implements  NativeKeyListener
	{
		@Override
		public void nativeKeyPressed(NativeKeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			{
				System.exit(0);
			}
			if(e.getKeyCode() == KeyEvent.VK_F8)
			{
				clicking = !clicking;
			}
			
		}

		@Override
		public void nativeKeyReleased(NativeKeyEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void nativeKeyTyped(NativeKeyEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}

	}
}
