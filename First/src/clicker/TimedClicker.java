package clicker;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Calendar;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.concurrent.locks.LockSupport;

public class TimedClicker
{
	static Robot robot;
	static
	{
		try
		{
			robot = new Robot();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	public static void main(String[] args)
	{
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("PST"));
		Scanner s = new Scanner(args[0]);
		s.useDelimiter("[:\n\r\t ]");
		int hours = s.nextInt();
		int minutes = s.nextInt();
		int seconds = s.nextInt();
		System.out.println(hours);
		System.out.println(minutes);
		System.out.println(seconds);
		c.set(Calendar.HOUR_OF_DAY, hours);
		c.set(Calendar.MINUTE, minutes);
		c.set(Calendar.SECOND, seconds);
		
		s.close();
		long millis = c.getTimeInMillis();
		click(millis);

	}
	static void click(long time)
	{
		LockSupport.parkUntil(time);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);	
	}
	
	static long nextTime(int minSeconds)
	{
		long time = System.currentTimeMillis();
		long seconds = time / 1000;
		if(seconds * 1000 < time) seconds++;
		seconds += minSeconds;
		return seconds * 1000;
	}

}
