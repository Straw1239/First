package main;

import java.util.Collection;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;

public class Renderer extends Canvas
{
	public Renderer(Vector size)
	{
		this(size.x, size.y);
	}
	
	public Renderer(double width, double height)
	{
		super(width, height);
	}
	
	public void render(Collection<? extends Drawable> stuff, Vector dimensions)
	{
		GraphicsContext g = getGraphicsContext2D();
		g.save();
		g.scale(getWidth() / dimensions.x, getHeight() / dimensions.y);
		//GaussianBlur b = new GaussianBlur();
		//g.setEffect(b);
		for(Drawable d : stuff)
		{
			d.draw(g);
		}
		g.restore();
	}
}
