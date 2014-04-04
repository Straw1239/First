package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.JPanel;

public class Hangman extends JPanel
{
	private String word;
	private char[] guesses;
	private String usedLetters = "";
	public Hangman(int size)
	{
		setSize(size,size);
		setPreferredSize(new Dimension(size,size));
	}
	
	public Hangman()
	{
		this(0);
	}	
	public Hangman(String word, int size)
	{
		this(size);
		setWord(word);
	}
	
	public void reset()
	{
		if(guesses == null || word == null) throw new IllegalStateException();
		resetGuesses();
	}
	
	private void resetGuesses()
	{
		for(int i = 0; i < guesses.length;i++)
		{
			guesses[i] = ' ';
		}
	}
	
	public void setWord(String word)
	{
		this.word = word;
		guesses = new char[word.length()];
		resetGuesses();
	}
	
	public void guess(char c)
	{
		if(usedLetters.indexOf(c) == -1) return;
		for(int i = 0; i < word.length();i++)
		{
			if(word.charAt(i) == c) guesses[i] = c;
		}
		usedLetters += c;
		char[] sort = usedLetters.toCharArray();
		Arrays.sort(sort);
		usedLetters = new String(sort);
	}
	
	public void paintComponent(Graphics g)
	{
		g.setFont(new Font("TimesRoman", Font.BOLD, 100)); 
		g.setColor(Color.black);
		g.drawString(new String(guesses), getWidth()/2, getHeight()/2);
	}
	
}
