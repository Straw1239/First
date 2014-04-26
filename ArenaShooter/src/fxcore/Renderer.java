package fxcore;

import java.util.Iterator;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.BulletDataHolder;
import objects.EnemyDataHolder;
import objects.Player;
import objects.PlayerDataHolder;

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
		scaleGraphics(d);
		g.setFill(Color.BLACK);
		g.fillRect(0, 0, width, height);
		drawPlayer(d);
		drawEnemies(d);
		drawBullets(d);
		g.restore();
	}
	
	private void drawBullets(Display d)
	{
		Iterator<? extends BulletDataHolder> it = d.bullets.iterator();
		while(it.hasNext())
		{
			BulletDataHolder b = it.next();
			g.setFill(b.getColor());
			double radius = b.getRadius();
			g.fillOval(b.getX() - radius, b.getY() - radius, 2 * radius, 2 * radius);
		}
	}

	private void drawPlayer(Display d)
	{
		PlayerDataHolder p = d.player;
		g.setFill(Player.color);
		double radius = Player.radius;
		g.fillOval(p.getX() - radius, p.getY() - radius, 2 * radius, 2 * radius);
		
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
}
