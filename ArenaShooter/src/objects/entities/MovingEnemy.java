package objects.entities;




import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.GameObject;
import objects.ReadableObject;
import utils.Utils;
import bounds.Bounds;
import bounds.Circle;
import engine.State;

public class MovingEnemy extends Enemy 
{
	public static final double radius = 20;
	public static final double maxHealth = 20;
	public static final double contactDamage = .1;
	public static final Color color = Color.RED;
	private double speed;
	public boolean isDead = false;
	private Circle bounds = new Circle()
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
			return radius;
		}
	};
	
	public MovingEnemy(double x, double y) 
	{
		super(x, y);
		health = maxHealth;
		super.maxHealth = maxHealth;
		mass = 2;
		speed = 4;
	}
	
	public MovingEnemy(double x, double y, double speed, double mass) 
	{
		super(x, y);
		health = maxHealth;
		super.maxHealth = maxHealth;
		this.mass = mass;
		this.speed = speed;
	}
	
	public Impact collideWith(GameObject other)
	{
		return new Impact(this, new Change(DAMAGE, contactDamage));
	}
	
	public void hitBy(Impact imp)
	{
		super.hitBy(imp);
		//Special behavior when hit by bullets?
	
	}
	
	

	

	@Override
	public void draw(GraphicsContext g) 
	{
		g.setFill(color);
		g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
	}


	@Override
	public void update(State d) 
	{
		speed += 1 / 1000.0;
		ReadableObject target = findTarget(d);
		double distance = Utils.distance(this, target);
		double conservationRatio = 3;
		dx = (dx * conservationRatio + (speed * (target.getX() - x) / distance)) / (1 + conservationRatio);
		dy = (dy * conservationRatio + (speed * (target.getY() - y) / distance)) / (1 + conservationRatio);
		super.update(d);
	}

	@Override
	public Bounds bounds()
	{
		return bounds;
	}

	

}
