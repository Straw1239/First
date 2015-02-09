package clicker;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.math.RoundingMode;
import java.util.concurrent.locks.LockSupport;

import com.google.common.math.LongMath;

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
		click(10);

	}
	static void click(int minSeconds)
	{
		LockSupport.parkUntil(nextTime(minSeconds));
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
