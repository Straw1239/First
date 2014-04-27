package objects;



import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.events.Explosion;
import objects.events.GameEvent;
import utils.Utils;
import fxcore.Display;
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
	private double radius;
	private boolean isDead = false;
	
	
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
	
	
	
	public Bullet(ObjectDataHolder source, ObjectDataHolder target, double speed, double radius, Color color)
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
	
	public boolean hasHitWall(double width, double height)
	{
		return (x + radius >= width || x - radius <= 0 || y + radius >= height || y - radius  <= 0);
	}
	
	
	
	@Override
	public void update(Display d) 
	{
		x += dx;
		y += dy;
	}

	@Override
	public boolean collidesWith(GameObject entity) 
	{
		return entity.collidesWithBullet(this);
	}

	@Override
	public double getRadius() 
	{
		return radius;
	}

	@Override
	public Color getColor() 
	{
		return color;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		long temp;
		temp = Double.doubleToLongBits(damage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(dx);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(dy);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(radius);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (startTime ^ (startTime >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Bullet)) {
			return false;
		}
		Bullet other = (Bullet) obj;
		if (color == null) {
			if (other.color != null) {
				return false;
			}
		} else if (!color.equals(other.color)) {
			return false;
		}
		if (Double.doubleToLongBits(damage) != Double
				.doubleToLongBits(other.damage)) {
			return false;
		}
		if (Double.doubleToLongBits(dx) != Double.doubleToLongBits(other.dx)) {
			return false;
		}
		if (Double.doubleToLongBits(dy) != Double.doubleToLongBits(other.dy)) {
			return false;
		}
		if (Double.doubleToLongBits(radius) != Double
				.doubleToLongBits(other.radius)) {
			return false;
		}
		if (startTime != other.startTime) {
			return false;
		}
		return true;
	}

	@Override
	public boolean collidesWithPlayer(Player p) 
	{
		return p.collidesWithBullet(this);
	}

	@Override
	public boolean collidesWithBullet(Bullet b) 
	{
		// Should Bullets collide? for now, no.
		return false;
	}

	@Override
	public boolean collidesWithEnemy(Enemy e) 
	{
		return e.collidesWithBullet(this);
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
	
	public double getDX()
	{
		return dx;
	}
	
	public double getDY()
	{
		return dy;
	}

	@Override
	public void draw(GraphicsContext g)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	

}
