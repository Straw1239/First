package gui;

import java.awt.Color;

import javax.swing.JFrame;

public class Fullscreen 
{

	public static JFrame frame = new JFrame();
	public static Hangman game = new Hangman();
	public static void main(String[] args) 
	{
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setBackground(Color.cyan);
		
	}

}
