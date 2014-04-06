package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.JPanel;

public class Hangman extends JPanel
{
	private static final long serialVersionUID = 0L;
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
			char c = word.charAt(i);
			guesses[i] = (c == ' ' || c == '\'' || c == '"') ? c : '0';
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
		int size = 100;
		g.setFont(new Font("Arial", Font.BOLD, size));
		double spacing = .8 * size;
		String[] words = word.split(" ");
		String[] guessed = new String(guesses).split(" ");
		int height = getHeight()/3;
		int start = (int)Math.round(spacing * .5);
		int spaceUsed = start;
		for(int i = 0; i < words.length;i++)
		{
			if((words[i].length() * spacing) > (getWidth() - spaceUsed))	
			{
				height += size;
				spaceUsed = start;
			}
			for(int j = 0; j < words[i].length();j++)
			{
				char c = guessed[i].charAt(j);
				char x = words[i].charAt(j);
				g.setColor(Color.black);
				if(x == '"' || x == '\'' || x == ',')
				{
					g.drawString(Character.toString(x).toUpperCase(), spaceUsed - (int)(Math.round(spacing * .0)), height);
					spaceUsed += spacing * .4;
				}
				else 
				{
					if(c != '0')
					{
						double constant = 0;
						if(Character.toUpperCase(c) == 'I')
						{
							constant = .2;
						}
						String print = Character.toString(c).toUpperCase();
						g.drawString(print, spaceUsed + (int)Math.round((constant * spacing)), height);
					}
					if(x != ' ')
					{
						g.setColor(Color.white);
						g.drawRect(spaceUsed, (int)Math.round(height - spacing * .95), (int)Math.round(spacing), (int)Math.round(spacing));
					}
					spaceUsed += spacing;
				}
			}
			spaceUsed += spacing;
		}
		
	}
}
