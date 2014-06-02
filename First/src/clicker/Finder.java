package clicker;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;

import javax.swing.JFrame;

import clicker.Clicker.KeyTracker;

public class Finder
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
			
			if(true)
			{
				for(int i = 0; i < 2000; i++)
				{
					Thread.sleep(5);
					System.out.println(MouseInfo.getPointerInfo().getLocation());
				}
				//buyStuff();
			}
		}
	}
}
