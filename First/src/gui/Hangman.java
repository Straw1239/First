package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.JPanel;

public class Hangman extends JPanel
{
	private static final long serialVersionUID = 0L;
	private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
	private String word;
	private char[] guesses;
	private String usedLetters = "";
	private int textSize = 100;
	
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
	
	public void addSize(int size)
	{
		textSize += size;
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
		c = Character.toLowerCase(c);
		if(usedLetters.indexOf(c) != -1) return;
		for(int i = 0; i < word.length();i++)
		{
			if(Character.toLowerCase(word.charAt(i)) == Character.toLowerCase(c)) guesses[i] = word.charAt(i);
		}
		if(alphabet.indexOf(c) != -1)
		{
			usedLetters += c;
			char[] sort = usedLetters.toCharArray();
			Arrays.sort(sort);
			usedLetters = new String(sort).toLowerCase();
		}
	}
	
	public boolean isSolved()
	{
		return word.equalsIgnoreCase(new String(guesses));
	}
	
	public void paintComponent(Graphics g)
	{
		if(word == null) return;
		int size = textSize;
		g.setFont(new Font("Arial", Font.BOLD, (int)Math.round(size * .8)));
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
					g.drawString(Character.toString(x).toUpperCase(), spaceUsed - (int)(Math.round(spacing * -.1)), height);
					spaceUsed += spacing * .7;
				}
				else 
				{
					if(c != '0')
					{
						double constant = .1;
						char u = Character.toUpperCase(c);
						switch(u)
						{
						case 'I': constant += .325; break;
						case 'M': constant += -.01; break;
						case 'Y':
						case 'V':
						case 'A': constant += .05; break;
						case 'T': constant += .1; break;
						case 'W': constant += -.05;break;
						default: 
						}
						String print = Character.toString(c).toUpperCase();
						g.drawString(print, spaceUsed + (int)Math.round((constant * spacing)), height);
					}
					if(x != ' ')
					{
						g.setColor(Color.white);
						g.drawRect(spaceUsed , (int)Math.round(height - spacing * .9), (int)Math.round(spacing), (int)Math.round(spacing));
					}
					spaceUsed += spacing;
				}
			}
			spaceUsed += spacing;
		}
		drawUsedLetters(g,size/2);
	}
	
	private void drawUsedLetters(Graphics g, int size)
	{
		g.setColor(Color.white);
		g.setFont(new Font("Arial",Font.PLAIN,size));
		g.drawString(usedLetters.toUpperCase(), getWidth()/2 - (usedLetters.length() * size)/3, getHeight()-size/3);
	}
}
