package gui;

import java.awt.Color;

import javax.swing.JFrame;

public class Fullscreen 
{

	public static JFrame frame = new JFrame();
	
	public static void main(String[] args) 
	{
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setBackground(Color.white);
		Painter painter = new Painter();
		painter.setSize(frame.getSize());
		frame.getContentPane().add(painter);
		frame.setVisible(true);
	}

}
