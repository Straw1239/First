package ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import core.Display;

public class Renderer extends JComponent 
{
	private static final long serialVersionUID = 0L;
	private Display display;
	
	public Renderer()
	{
		setBackground(Color.white);
	}
	
	public void setDisplay(Display d)
	{
		display = d;
	}
	
	public void paintComponent(Graphics g)
	{
		
	}
}
