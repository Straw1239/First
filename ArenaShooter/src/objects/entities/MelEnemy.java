package objects.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.GameObject;
import objects.ReadableObject;
import utils.Utils;
import bounds.Bounds;
import bounds.Circle;
import engine.State;

public class MelEnemy extends Enemy
{
	static final double r = 6;
	static final int startHealth = 25;
	static final double G = 73;
	static final double MaxV = 5;
	
	public MelEnemy(double x, double y)
	{
		super(x, y);
		health = startHealth;
	}

	@Override
	public void draw(GraphicsContext g)
	{
		g.setStroke(Color.NAVY);
		g.strokeOval(x - r, y - r, 2 * r, 2 * r);
	}
	
	public void update(State s)
	{
		ReadableObject o = findTarget(s);
		double dist2 = Utils.distanceSquared(this, o);
		dx = Utils.clamp(dx + G*(o.getX() - x) / dist2, -MaxV, MaxV);
		dy = Utils.clamp(dy + G*(o.getY() - y) / dist2, -MaxV, MaxV);
		super.update(s);
	}
	
	public Impact collideWith(GameObject other)
	{
		if(other.getFaction() != faction)
		{
			health = 0;
			return new Impact(this, new Change(DAMAGE, 4.));
		}
		return Impact.NONE;
	}
	
	private Bounds bounds = new Circle()
	{
		@Override
		public double centerX()
		{
			return x;
		}

		@Override
		public double centerY()
		{
			return y;
		}
		
		@Override
		public double radius()
		{
			return r;
		}
	};

	@Override
	public Bounds bounds()
	{
		return bounds;
	}

}
