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
	public Hangman()
	{
		
	}	
	
	public Hangman(String word, int size)
	{
		this();
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
		usedLetters = "";
	}
	
	public void setWord(String word)
	{
		this.word = word;
		guesses = new char[word.length()];
		resetGuesses();
	}
	
	public void guess(char c)
	{
		if(usedLetters.indexOf(c) != -1) return;
		for(int i = 0; i < word.length();i++)
		{
			if(Character.toLowerCase(word.charAt(i)) == Character.toLowerCase(c)) guesses[i] = word.charAt(i);
		}
		usedLetters += c;
		char[] sort = usedLetters.toCharArray();
		Arrays.sort(sort);
		usedLetters = new String(sort).toLowerCase();
	}
	
	public boolean isSolved()
	{
		return word.equalsIgnoreCase(new String(guesses));
	}
	public void paintComponent(Graphics g)
	{
		if(word == null) return;
		//super.paintComponent(g);
		int fontSize = (int)Math.round(1.3 * (getWidth()/word.length()));
		drawChars(g,fontSize);
	}
	
	private void drawChars(Graphics g, int size)
	{
		g.setFont(new Font("TimesRoman", Font.BOLD, size));
		double shift = .8*size;
		int offset = (int)Math.round((shift * word.length())/2);
		int position = getWidth()/2 - offset;
		for(int i = 0; i < word.length();i++)
		{
			int constant = 0;
			g.setColor(Color.black);
			if(word.charAt(i) == 'l' || word.charAt(i) == 'i') constant = 5;
			if(word.charAt(i) == 'm') constant = -8;
			if(word.charAt(i) == '"' || word.charAt(i) == '\'')
			{
				
			}
			else if(word.charAt(i) == ' ')
			{
				position += shift;
			}
			else
			{
				String print = Character.toString(guesses[i]);
				g.drawString(print, position + constant, getHeight()/2);
				g.setColor(Color.white);
				g.drawRect(position - 5 , getHeight()/2 - (3*size)/4, (size*31)/40, size);
				position += shift;
			}
		}
	}
}
