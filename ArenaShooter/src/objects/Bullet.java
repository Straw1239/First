package objects;



import static fxcore.MainGame.rand;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.entities.Entity;
import utils.Utils;
import utils.Vector;
import bounds.Bounds;
import bounds.Circle;
import engine.State;
import fxcore.MainGame;
/**
 * Represents a bullet in the game. Provides many contructors for easy building.
 * Automatically moves itself by dx and dy each time update() is called.
 * Subclasses may want to override various methods for different behavior.
 * @author Rajan
 *
 */
public class Bullet extends MovingObject implements BulletDataHolder
{
	public Color color;
	public double damage = 1;
	
	protected long startTime;
	protected boolean isDead = false;
	protected double radius;
	protected Circle bounds = new Circle()
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
	
	
	
	private Bullet(double x, double y)
	{
		super(x,y);
		startTime = MainGame.getTime();
	}
	
	protected Bullet(ObjectDataHolder entity)
	{
		this(entity.getX(), entity.getY());
		faction = entity.getFaction();
	}
	
	public Bullet(double x, double y, double dx, double dy, double radius, Color color)
	{
		this(x,y);
		this.dx = dx;
		this.dy = dy;
		this.radius = radius;
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
		
	public Bullet(ObjectDataHolder source, Locatable target, double speed, double radius, Color color)
	{
		this(source);
		double distance = Utils.distance(source,target);
		dx = speed * (target.getX() - x) / distance;
		dy = speed * (target.getY() - y) / distance;
		this.radius = radius;
		this.color = color;
	}
	
	public Bullet(ObjectDataHolder source, double angle, double speed, double radius, Color color)
	{
		this(source);
		dx = Math.cos(angle) * speed;
		dy = Math.sin(angle) * speed;
		this.radius = radius;
		this.color = color;
	}
	
	public Bullet(ObjectDataHolder source, Locatable target, double speed, double radius, Color color, double damage)
	{
		this(source);
		double distance = Utils.distance(source,target);
		dx = speed * (target.getX() - x) / distance;
		dy = speed * (target.getY() - y) / distance;
		this.radius = radius;
		this.color = color;
		this.damage = damage;
	}
	
	protected void onWallHit(State s)
	{
		isDead = true;
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
	
	public Impact collideWith(GameObject other)
	{
		Impact impact = new Impact(this);
		{
			if(other.supportsOperation(Entity.DAMAGE))
			{
				isDead = true;
				impact.changes.add(new Change(Entity.DAMAGE, damage));
				impact.changes.add(new Change(MovingObject.FORCE, new Vector(dx * mass, dy * mass)));
			}
			
			
		}
		return impact;
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
		return bounds;
	}

	public void spread(double maxAngle)
	{
		double angle = Math.atan2(dy, dx);
		double speed = Math.hypot(dx, dy);
		angle += maxAngle * (rand.nextDouble() * 2 - 1);
		dx = Math.cos(angle) * speed;
		dy = Math.sin(angle) * speed;
	}
	
	public static class Builder
	{
		protected Locatable start;
		protected Faction faction;
		protected double dy, dx;
		protected Locatable target = null;
		protected Color color;
		protected double radius;
		protected boolean piercing;
		
		public Bullet build()
		{
			if(target == null)
			{
				Bullet b = new Bullet(start.getX(), start.getY(), dx, dy, radius, color);
				b.color = color;
				b.faction = faction;
				return b;
			}
			else
			{
				return new Bullet(GameObject.dataOf(start.getX(), start.getY(), faction), target, dx, dx, color);
			}
		}
	}
	
	
	

}
