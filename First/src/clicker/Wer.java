package clicker;

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class Wer
{
	static volatile boolean wing = false;
	static Robot robot;
	
	
	public static void main(String[] args) throws Throwable
	{
		System.out.println("WER V2.0");
		robot = new Robot();
		GlobalScreen.unregisterNativeHook();
		GlobalScreen.registerNativeHook();
		GlobalScreen.getInstance().addNativeKeyListener(new KeyTracker());
		int counter = 0;
		System.out.println("REGISTERED HOOKS");
		while(true)
		{
			if(wing)
			{
				w();
				Thread.sleep(50);
			}
			counter = (counter + 1) % 2000;
		}
	}
	
	private static void w()
	{
		robot.keyPress(KeyEvent.VK_W);
		robot.keyRelease(KeyEvent.VK_W);
	}


	
	
	public static class KeyTracker implements NativeKeyListener
	{
		@Override
		public void nativeKeyPressed(NativeKeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			{
				GlobalScreen.unregisterNativeHook();
				System.exit(0); 
			}
			
			if(e.getKeyCode() == KeyEvent.VK_E)
			{
				wing = true;
			}
		}

		@Override
		public void nativeKeyReleased(NativeKeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_E)
			{
				wing = false;
			}
			
		}

		@Override
		public void nativeKeyTyped(NativeKeyEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}

		

	}
	
}
