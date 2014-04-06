package gui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;

public class Fullscreen 
{

	public static JFrame frame = new JFrame();
	public static Hangman game = new Hangman();
	public static Scanner input;
	
	public static void main(String[] args) 
	{
		try 
		{
			input = new Scanner(new BufferedInputStream(new FileInputStream(new File("hangman.txt"))));
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setBackground(new Color(0x17,0x4F,0x20));
		frame.add(game);
		frame.setVisible(true);
		game.setPreferredSize(frame.getSize());
		game.setFocusable(true);
		game.addKeyListener(new Listener());
		game.setWord(input.nextLine());
	}
	
	private static class Listener implements KeyListener
	{

		@Override
		public void keyPressed(KeyEvent e) 
		{
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				if(game.isSolved() && input.hasNextLine())
				{
					game.setWord(input.nextLine());
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_ADD)
			{
				game.addSize(10);
			}
			if(e.getKeyCode() == KeyEvent.VK_SUBTRACT)
			{
				game.addSize(-10);
			}
			frame.repaint();
		}

		@Override
		public void keyReleased(KeyEvent e) 
		{
			
		}

		@Override
		public void keyTyped(KeyEvent e) 
		{
			game.guess(e.getKeyChar());
			frame.repaint();
		}
		
	}

}
