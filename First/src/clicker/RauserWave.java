package clicker;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import clicker.RiskZ.KeyTracker;

public class RauserWave
{
	static volatile boolean activated = false;
	static Robot robot;
	static double phase = 0;
	static long startTime = System.nanoTime();
	public static void main(String[] args) throws Throwable
	{
		robot = new Robot();
		GlobalScreen.unregisterNativeHook();
		GlobalScreen.registerNativeHook();
		GlobalScreen.getInstance().addNativeKeyListener(new KeyTracker());
		System.out.println("REGISTERED HOOKS");
		while(true)
		{
			phase = ((System.nanoTime() - startTime) / 1_000_000_000) * 1;
			if(activated)
			{
				wave();
			}
			Thread.sleep(10);
			
		}
	}
	
	
	
	
	private static void wave()
	{
		if(Math.sin(phase) > 0)
		{
			robot.keyRelease(KeyEvent.VK_RIGHT);
			robot.keyPress(KeyEvent.VK_LEFT);
		}
		else
		{
			robot.keyRelease(KeyEvent.VK_LEFT);
			robot.keyPress(KeyEvent.VK_RIGHT);
		}
	}






	
	public static class KeyTracker implements NativeKeyListener
	{
		@Override
		public void nativeKeyPressed(NativeKeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_F8)
			{
				GlobalScreen.unregisterNativeHook();
				System.exit(0); 
			}
			if(e.getKeyCode() == KeyEvent.VK_HOME)
			{
				activated = !activated;
			}
		}

		@Override
		public void nativeKeyReleased(NativeKeyEvent e)
		{
			
			
		}

		@Override
		public void nativeKeyTyped(NativeKeyEvent e)
		{
			
			
			
		}

	}
}
