package fxcore;

import java.util.Iterator;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import objects.BulletDataHolder;
import objects.entities.EntityDataHolder;
import objects.events.EventDataHolder;
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
	
	public void render(State d)
	{
		g.save();
		//g.setGlobalAlpha(.95);
		//g.clearRect(0, 0, width, height);
		g.setFill(Color.ORANGE);
		g.fillRect(0, 0, width, height);
		scaleGraphics(d);
		//g.setFill(Color.BLACK);
		//g.setFill(new LinearGradient(0, 0, .1, .2, true, CycleMethod.REFLECT, new Stop(0, Color.BLACK), new Stop(.5, Color.SEAGREEN.darker()), new Stop(1, Color.DARKBLUE)));
		g.setFill(new RadialGradient(0, 0, .5, .5, .05, true, CycleMethod.REFLECT, new Stop(0, Color.BLACK), new Stop(1, Color.DARKMAGENTA)));
		g.fillRect(0, 0, d.width, d.height);
		drawEvents(d);
		drawBullets(d);
		drawPlayer(d);
		drawEntities(d);
		d.mouse.draw(g);
		g.restore();
		displayHUD(d);
		
	}
	
	private void displayHUD(State d)
	{
		double healthBar = 300;
		g.setFill(Player.color);
		g.fillRect(0, 0, healthBar * d.player.health() /d.player.maxHealth(), 50);
		g.setStroke(Color.WHITE);
		g.strokeRect(0, 0, healthBar, 50);
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
		double playerX = d.player.getX(), playerY = d.player.getY();
		g.translate(width / 2, height / 2);
		//g.scale(width / d.width, height / d.height);
		g.translate(-playerX , -playerY);
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
