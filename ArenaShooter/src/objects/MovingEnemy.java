package objects;




import player.PlayerDataHolder;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import utils.Utils;
import engine.State;

public class MovingEnemy extends Enemy 
{
	public static final double radius = 20;
	public static final double maxHealth = 30;
	public static final double contactDamage = .1;
	public static final Color color = Color.RED;
	private Circle bounds = new Circle(radius)
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
	};
	
	public MovingEnemy(double x, double y) 
	{
		super(x, y);
		health = maxHealth;
		super.maxHealth = maxHealth;
	}

	@Override
	public void collideWith(Entity p) 
	{
		p.damage(contactDamage);
	}

	@Override
	public void draw(GraphicsContext g) 
	{
		g.setFill(color);
		g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
	}

	

	@Override
	public void hitByBullet(Bullet b) 
	{
		double mass = 2;
		x += b.getDX() / mass;
		y += b.getDY() / mass;
	}

	@Override
	public void update(State d) 
	{
		double speed = 3;
		PlayerDataHolder p = d.player;
		double distance = Utils.distance(this, p);
		x += speed * (p.getX() - x) / distance;
		y += speed * (p.getY() - y) / distance;
	}

	@Override
	public boolean collidesWith(GameObject entity) 
	{
		assert(entity != null);
		return bounds.intersects(entity.bounds());
	}

	@Override
	public Bounds bounds()
	{
		return bounds;
	}

	

}
