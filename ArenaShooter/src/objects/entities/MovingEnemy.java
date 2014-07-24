package objects.entities;




import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.GameObject;
import objects.GameObject.Change;
import objects.GameObject.Impact;
import player.PlayerDataHolder;
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
	}
	
	public Impact collideWith(GameObject other)
	{
		return new Impact(this, new Change(DAMAGE, contactDamage));
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
		double speed = 3;
		PlayerDataHolder p = d.player;
		double distance = Utils.distance(this, p);
		dx = speed * (p.getX() - x) / distance;
		dy = speed * (p.getY() - y) / distance;
		super.update(d);
	}

	@Override
	public Bounds bounds()
	{
		return bounds;
	}

	

}
