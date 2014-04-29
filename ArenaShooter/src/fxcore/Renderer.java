package fxcore;

import java.util.Iterator;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.effect.*;
import objects.BulletDataHolder;
import objects.EnemyDataHolder;
import objects.Player;
import objects.PlayerDataHolder;
import objects.events.EventDataHolder;

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
	
	public void render(Display d)
	{
		g.save();
		g.setFill(Color.BLACK);
		g.fillRect(0, 0, width, height);
		scaleGraphics(d);
		drawEvents(d);
		drawBullets(d);
		drawPlayer(d);
		drawEnemies(d);
		d.mouse.draw(g);
		g.restore();
	}
	
	private void drawBullets(Display d)
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

	private void drawPlayer(Display d)
	{
		d.player.draw(g);
	}
	
	private void scaleGraphics(Display d)
	{
		g.scale(width / d.width, height / d.height);
	}
	
	private void drawEnemies(Display d)
	{
		Iterator<? extends EnemyDataHolder> it = d.enemies.iterator();
		while(it.hasNext())
		{
			it.next().draw(g);
		}
	}
	
	private void drawEvents(Display d)
	{
		Iterator<? extends EventDataHolder> it = d.events.iterator();
		while(it.hasNext())
		{
			it.next().draw(g);
		}
	}
}
