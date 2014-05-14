package objects;



import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import utils.Utils;
import engine.State;
import fxcore.MainGame;

/**
 * Represents a bullet in the game. Provides many contructors for easy building.
 * Automatically moves itself by dx and dy each time update(Display) is called.
 * Subclasses may want to override various methods for different behavior.
 * @author Rajan
 *
 */
public class Bullet extends GameObject implements BulletDataHolder
{
	public Color color;
	public double damage = 1;
	
	private double dx,dy;
	private long startTime;
	private boolean isDead = false;
	private Circle bounds;
	
	private void setRadius(double radius)
	{
		bounds = new Circle(radius)
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
	}
	
	private Bullet(double x, double y)
	{
		super(x,y);
		startTime = MainGame.getTime();
	}
	
	private Bullet(ObjectDataHolder entity)
	{
		this(entity.getX(), entity.getY());
		faction = entity.getFaction();
	}
	
	public Bullet(double x, double y, double dx, double dy, double radius, Color color)
	{
		this(x,y);
		this.dx = dx;
		this.dy = dy;
		setRadius(radius);
		this.color = color;
	}
	
	public Bullet(double x, double y, double dx, double dy)
	{
		this(x,y,dx,dy,Color.RED);
	}
	
	public Bullet(double x, double y, double dx, double dy, Color color)
	{
		this(x,y,dx,dy,1,color);
	}
	
	public Bullet(double x, double y, double dx, double dy, double radius)
	{
		this(x,y,dx,dy,radius,Color.RED);
	}
		
	public Bullet(ObjectDataHolder source, ObjectDataHolder target, double speed, double radius, Color color)
	{
		this(source);
		double distance = Utils.distance(source,target);
		dx = speed * (target.getX() - x) / distance;
		dy = speed * (target.getY() - y) / distance;
		setRadius(radius);
		this.color = color;
	}
	
	public Bullet(ObjectDataHolder source, double angle, double speed, double radius, Color color)
	{
		this(source);
		dx = Math.cos(angle) * speed;
		dy = Math.sin(angle) * speed;
		setRadius(radius);
		this.color = color;
	}
	
	
	
	@Override
	public void update(State d) 
	{
		x += dx;
		y += dy;
	}

	@Override
	public boolean collidesWith(GameObject entity) 
	{
		return bounds.intersects(entity.bounds());
	}

	@Override
	public double getRadius() 
	{
		return bounds.radius();
	}

	@Override
	public Color getColor() 
	{
		return color;
	}

	

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bounds == null) ? 0 : bounds.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		long temp;
		temp = Double.doubleToLongBits(damage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(dx);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(dy);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (isDead ? 1231 : 1237);
		result = prime * result + (int) (startTime ^ (startTime >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;
		Bullet other = (Bullet) obj;
		if (bounds == null)
		{
			if (other.bounds != null) return false;
		}
		else if (!bounds.equals(other.bounds)) return false;
		if (color == null)
		{
			if (other.color != null) return false;
		}
		else if (!color.equals(other.color)) return false;
		if (Double.doubleToLongBits(damage) != Double
				.doubleToLongBits(other.damage)) return false;
		if (Double.doubleToLongBits(dx) != Double.doubleToLongBits(other.dx))
			return false;
		if (Double.doubleToLongBits(dy) != Double.doubleToLongBits(other.dy))
			return false;
		if (isDead != other.isDead) return false;
		if (startTime != other.startTime) return false;
		return true;
	}

	public boolean isDead()
	{
		return isDead;
	}
	
	public void collide(Entity e)
	{
		isDead = true;
		e.damage(damage);
	}
	
	@Override
	public double getDX()
	{
		return dx;
	}
	
	@Override
	public double getDY()
	{
		return dy;
	}

	@Override
	public void draw(GraphicsContext g)
	{
		g.setFill(color);
		g.fillOval(x - bounds.radius(), y - bounds.radius(), 2 * bounds.radius(), 2 * bounds.radius());
	}

	@Override
	public Bounds bounds()
	{
		return bounds ;
	}
	
	
	

}
