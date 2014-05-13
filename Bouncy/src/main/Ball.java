package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import utils.Cloner;
import utils.Copyable;

public class Ball implements Drawable, Cloner, Copyable<Ball>
{
	public Vector position;
	public Vector velocity;
	
	private double radius;
	private Color color;
	
	public Ball(Vector position, Vector velocity, double radius, Color color)
	{
		this.position = position;
		this.velocity = velocity;
		this.radius = radius;
		this.color = color;
	}
	
	
	
	public double getRadius()
	{
		return radius;
	}
	
	public void update(double dt)
	{
		position = position.add(velocity.scale(dt));
	}
	
	public void draw(GraphicsContext g)
	{
		g.setFill(color);
		g.fillOval(position.x - radius, position.y - radius, 2 * radius, 2 * radius);
	}
	
	public Object clone()
	{
		try 
		{
			return super.clone();
		}
		catch (CloneNotSupportedException e) 
		{
			throw new InternalError(e);
		}
	}

	@Override
	public String toString() 
	{
		return String.format(
				"Ball [position=%s, velocity=%s, radius=%s, color=%s]",
				position, velocity, radius, color);
	}
	

}
