package objects.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.Locatable;
import utils.Vector;
import bounds.Bounds;
import bounds.Circle;
import engine.State;

public class TinyDot extends Enemy
{
	
	protected Circle body = new Circle()
	{

		public double centerX()
		{
			return x;
		}
		
		public double centerY()
		{
			return y;
		}

		@Override
		public double radius()
		{
			return 10;
		}
		
	};
	public TinyDot(double x, double y)
	{
		super(x, y);
		maxHealth = 10;
		health = maxHealth;
	}

	@Override
	public void draw(GraphicsContext g)
	{
		g.setFill(Color.HOTPINK);
		body.fill(g);
	}

	@Override
	public Bounds bounds()
	{
		return body;
	}
	
	
	public void update(State s)
	{
		Locatable target = findTarget(s);
		double speed = 5;
		Vector movement = new Vector(this).sub(new Vector(target)).normalized(speed);
		dx = movement.x;
		dy = movement.y;
		super.update(s);
	}

}
