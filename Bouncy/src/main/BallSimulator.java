package main;

import java.util.LinkedList;
import java.util.List;

import utils.Cloner;
import javafx.scene.paint.Color;

import com.google.common.collect.ImmutableList;

public class BallSimulator 
{
	private List<Ball> balls;
	public final Vector dimensions;
	private ImmutableList<Ball> state;
	private static final Vector BOTTOM_WALL = new Vector(0, -1);
	private static final Vector RIGHT_WALL = new Vector(-1, 0);
	private static final Vector LEFT_WALL = new Vector(1, 0);
	private static final Vector TOP_WALL = new Vector(0, 1);
	private double timeSimulated = 0;
	
	public BallSimulator(Vector dimensions)
	{
		this.dimensions = dimensions;
		balls = new LinkedList<>();
		state = ImmutableList.of();
	}
	/**
	 * Makes a new ball simulator with the specified width and height for the bouncy walls
	 * @param width
	 * @param height
	 */
	public BallSimulator(double width, double height)
	{
		this(new Vector(width, height));
		
	}
	
	public void addBall(Ball b)
	{
		balls.add(b.copy());
	}
	
	public ImmutableList<Ball> getState()
	{
		return state;
	}
	
	public void update(double dt)
	{
		for(Ball b : balls)
		{
			b.update(dt);
			wallBounce(b);
			b.position = new Vector(Main.calcPosition(b.position.x - b.getRadius(), dimensions.x - b.getRadius() * 2) + b.getRadius(), Main.calcPosition(b.position.y - b.getRadius(), dimensions.y - b.getRadius() * 2) + b.getRadius());
		}
		state = ImmutableList.copyOf(balls.stream().map((b) -> b.copy()).iterator());
		timeSimulated += dt;
	}
	
	private void wallBounce(Ball b)
	{
		double radius = b.getRadius();
		Vector p = b.position;
		Vector v = b.velocity;
		double maxX = dimensions.x - radius, maxY = dimensions.y - radius, minX = radius, minY = radius;
		if(p.y >= maxY && v.y > 0)
		{
			b.velocity = b.velocity.reflect(BOTTOM_WALL);
		} 
		if(p.x >= maxX && v.x > 0)
		{
			b.velocity = b.velocity.reflect(RIGHT_WALL);
		}
		if(p.y <= minY && v.y < 0)
		{
			b.velocity = b.velocity.reflect(TOP_WALL);
		}
		if(p.x <= minX && v.x < 0)
		{
			b.velocity = b.velocity.reflect(LEFT_WALL);
		}
	}
	
	public double getTime()
	{
		return timeSimulated;
	}
}
