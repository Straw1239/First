package fxcore;

import java.util.Iterator;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.BulletDataHolder;
import objects.EntityDataHolder;
import objects.events.EventDataHolder;
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
	
	public void render(State d)
	{
		g.save();
	//	g.setGlobalAlpha(.5);
		g.setFill(Color.BLACK);
		g.fillRect(0, 0, width, height);
		scaleGraphics(d);
		drawEvents(d);
		drawBullets(d);
		drawPlayer(d);
		drawEntities(d);
		d.mouse.draw(g);
		g.restore();
	}
	
	private void drawBullets(State d)
	{
		g.save();
		Iterator<? extends BulletDataHolder> it = d.bullets.iterator();
		//BoxBlur effect = new BoxBlur(5, 5, 3);
		//effect.setInput(new Bloom(0));
		//g.setEffect(effect);
		while(it.hasNext())
		{
			it.next().draw(g);
		}
		g.restore();
		
	}

	private void drawPlayer(State d)
	{
		d.player.draw(g);
	}
	
	private void scaleGraphics(State d)
	{
		g.scale(width / d.width, height / d.height);
	}
	
	private void drawEntities(State d)
	{
		Iterator<? extends EntityDataHolder> it = d.entities.iterator();
		while(it.hasNext())
		{
			it.next().draw(g);
		}
	}
	
	private void drawEvents(State d)
	{
		Iterator<? extends EventDataHolder> it = d.events.iterator();
		while(it.hasNext())
		{
			it.next().draw(g);
		}
	}
}
