package objects;




import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import utils.Utils;
import fxcore.Display;

public class MovingEnemy extends Enemy 
{
	public static final double radius = 20;
	public static final double maxHealth = 30;
	public static final double contactDamage = .1;
	public static final Color color = Color.RED;
	
	public MovingEnemy(double x, double y) 
	{
		super(x, y);
		health = maxHealth;
		super.maxHealth = maxHealth;
	}

	@Override
	public void collideWithPlayer(Player p) 
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
	public void update(Display d) 
	{
		double speed = 3;
		PlayerDataHolder p = d.player;
		double distance = Utils.distance(this, p);
		x += speed * (p.getX() - x) / distance;
		y += speed * (p.getY() - y) / distance;
	}

	public boolean collidesWith(GameObject entity) 
	{
		return entity.collidesWithEnemy(this);
	}

	@Override
	public boolean collidesWithPlayer(Player p) 
	{
		return Utils.circleCollide(this, p, radius + Player.radius);
	}

	@Override
	public boolean collidesWithBullet(Bullet b) 
	{
		if(faction == b.faction) return false;
		return Utils.circleCollide(this, b, radius + b.getRadius());
	}

	@Override
	public boolean collidesWithEnemy(Enemy e) 
	{
		//Should enemies collide? for now, no.
		return false;
	}

}
