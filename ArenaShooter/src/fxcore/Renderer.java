package fxcore;

import java.text.DecimalFormat;
import java.util.Iterator;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import objects.Locatable;
import objects.ReadableObject;
import objects.events.ReadableEvent;
import player.Player;
import engine.State;

public class Renderer 
{
	public final Canvas canvas;
	private final GraphicsContext g;
	public final double width, height;
	
	public Renderer(int width, int height)
	{
		canvas = new Canvas(width, height);
		g = canvas.getGraphicsContext2D();
		
		this.width  = width;
		this.height = height;
	}
	
	public void render(State d, Locatable center)
	{
		g.save();
		//g.setGlobalAlpha(.95);
		//g.clearRect(0, 0, width, height);
		g.setFill(Color.ORANGE);
		g.fillRect(0, 0, width, height);
		scaleGraphics(center);
		//g.setFill(Color.BLACK);
		//g.setFill(new LinearGradient(0, 0, .1, .2, true, CycleMethod.REFLECT, new Stop(0, Color.BLACK), new Stop(.5, Color.SEAGREEN.darker()), new Stop(1, Color.DARKBLUE)));
		g.setFill(new RadialGradient(0, 0, .5, .5, .05, true, CycleMethod.REFLECT, new Stop(0, Color.BLACK), new Stop(1, Color.DARKMAGENTA)));
		//g.fillRect(0, 0, d.width, d.height);
		d.gameBounds.fill(g);
		drawObjects(d);
		drawEvents(d);
		//drawPlayer(d);
		g.restore();
		displayHUD(d);
		
		
	}
	
	private void drawObjects(State d)
	{
		for(Renderable obj : d.objects)
		{
			obj.draw(g);
		}
	}

	private void displayHUD(State d)
	{
		for(Renderable r : d.objects)
		{
			r.renderHUD(g);
		}
	}
	
	private void scaleGraphics(Locatable center)
	{
		double playerX = center.getX(), playerY = center.getY();
		g.translate(width / 2, height / 2);
		//g.scale(width / d.width, height / d.height);
		g.translate(-playerX , -playerY);
	}
	
	
	private void drawEvents(State d)
	{
		Iterator<? extends ReadableEvent> it = d.events.iterator();
		while(it.hasNext())
		{
			it.next().draw(g);
		}
	}
}
