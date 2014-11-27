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
 * Represents a bullet in the game. Provides a builder for easy customization.
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
		mass = .03;
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
		protected double dy = 0, dx = 0;
		protected Locatable target = null;
		protected Color color = Color.RED;
		protected double radius =5;
		protected double damage = 1;
		
		public Bullet build()
		{
			if(target == null)
			{
				Bullet b = new Bullet(start.getX(), start.getY(), dx, dy, radius, color);
				b.color = color;
				b.faction = faction;
				b.damage = damage;
				return b;
			}
			else
			{
				Bullet b = new Bullet(GameObject.dataOf(start.getX(), start.getY(), faction), target, dx, dx, color);
				b.damage = damage;
				return b;
			}
		}
		
		public Builder setLocation(Locatable l)
		{
			start = new Vector(l);
			return this;
		}
		
		public Builder setFaction(Faction f)
		{
			faction = f;
			return this;
		}
		
		public Builder setVelocity(Vector v)
		{
			dy = v.y;
			dx = v.x;
			target = null;
			return this;
		}
		
		public Builder setColor(Color c)
		{
			color = c;
			return this;
		}
		
		public Builder setRadius(double r)
		{
			radius = r;
			return this;
		}
		
		public Builder from(ObjectDataHolder source)
		{
			return setLocation(source).setFaction(source.getFaction());
		}
		
		public Builder setTarget(Locatable l)
		{
			target = new Vector(l);
			return this;
		}
		
		
	}
	
	
	

}
