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
import objects.ObjectDataHolder;
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
		//g.fillRect(0, 0, d.width, d.height);
		d.gameBounds.fill(g);
		drawObjects(d);
		drawEvents(d);
		drawPlayer(d);
		g.restore();
		displayHUD(d);
		
	}
	
	private void drawObjects(State d)
	{
		for(ObjectDataHolder obj : d.objects)
		{
			obj.draw(g);
		}
	}

	private void displayHUD(State d)
	{
		double healthBar = 300;
		g.setFill(Player.color);
		g.fillRect(0, 0, healthBar * d.player.health() /d.player.maxHealth(), 50);
		g.setStroke(Color.WHITE);
		g.strokeRect(0, 0, healthBar, 50);
		g.setFill(Color.YELLOW);
		g.setFont(Font.font("Verdana", 75));
		g.setFill(Color.YELLOW);
		g.fillText(Player.getCoinsCollected() + "", MainGame.getScreenWidth() - 150, 75); //Eventually autosize spacing to the width of the number
		g.setFont(Font.font("Verdana", 50));
		g.setFill(Color.WHITE);
		g.fillText("Score: "  + (int)(Player.getRawScore() * 100) + "", 0, 100);
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
	
	
	private void drawEvents(State d)
	{
		Iterator<? extends EventDataHolder> it = d.events.iterator();
		while(it.hasNext())
		{
			it.next().draw(g);
		}
	}
}
