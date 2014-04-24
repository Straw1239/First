package fxcore;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import core.Display;

public class Renderer 
{
	private final Canvas canvas;
	private final GraphicsContext g;
	private Display display;
	
	public Renderer(int width, int height)
	{
		canvas = new Canvas(width, height);
		g = canvas.getGraphicsContext2D();
	}
	
	public void setDisplay(Display d)
	{
		display = d;
	}
}
