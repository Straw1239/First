package clicker;

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class RiskZ
{
	static volatile boolean pressing = false;
	static Robot robot;
	
	public static void main(String[] args) throws Throwable
	{
		robot = new Robot();
		GlobalScreen.unregisterNativeHook();
		GlobalScreen.registerNativeHook();
		GlobalScreen.getInstance().addNativeKeyListener(new KeyTracker());
		System.out.println("REGISTERED HOOKS");
		while(true)
		{
			if(pressing)
			{
				Thread.sleep(20);
				press();
				
			}
			
		}
	}
	
	
	
	
	private static void press()
	{
		robot.keyPress(KeyEvent.VK_Z);
		robot.keyRelease(KeyEvent.VK_Z);
		
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
			if(e.getKeyCode() == KeyEvent.VK_Z)
			{
				pressing = true;
			}
		}

		@Override
		public void nativeKeyReleased(NativeKeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_Z)
			{
				pressing = false;
			}
			
		}

		@Override
		public void nativeKeyTyped(NativeKeyEvent e)
		{
			
			
			
		}

	}
	
}
