package core;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class MainGame 
{
	private static JFrame frame = new JFrame();
	private static Engine engine;
	private static Renderer renderer;
	
	public static void main(String[] args)
	{
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.getContentPane().add(renderer);
	}
	
	public static long getTime()
	{
		return 0;
	}
	
	
}
