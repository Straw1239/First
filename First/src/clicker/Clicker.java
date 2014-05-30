package clicker;

import java.awt.AWTException;
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
	public static void main(String[] args) throws AWTException, InterruptedException
	{
		robot = new Robot();
		frame.setFocusable(true);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		frame.addKeyListener(new KeyTracker());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		while(true)
		{
			Thread.sleep(10);
			if(clicking)
			{
				//System.out.println("Clicking");
				robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			}
		}
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
