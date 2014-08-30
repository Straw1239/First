package objects.entities;




import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.GameObject;
import objects.MoverDataHolder;
import player.PlayerDataHolder;
import utils.Utils;
import bounds.Bounds;
import bounds.Circle;
import engine.State;
import fxcore.MainGame;

public class MovingEnemy extends Enemy 
{
	public static final double radius = 20;
	public static final double maxHealth = 20;
	public static final double contactDamage = .1;
	public static final Color color = Color.RED;
	private double speed;
	private int level;
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
		GameObject source = imp.source;
		if(source instanceof MoverDataHolder)
		{
			MoverDataHolder data = (MoverDataHolder) source;
			double ratio = data.mass() / mass();
			dx += data.getDX() * ratio;
			dy += data.getDY() * ratio;
		}
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
		if(MainGame.getTime() % 1000 == 0){
			level++;
			speed++;
		}
		PlayerDataHolder p = d.player;
		double distance = Utils.distance(this, p);
		double conservationRatio = 3;
		dx = (dx * conservationRatio + (speed * (p.getX() - x) / distance)) / (1 + conservationRatio);
		dy = (dy * conservationRatio + (speed * (p.getY() - y) / distance)) / (1 + conservationRatio);
		super.update(d);
	}

	@Override
	public Bounds bounds()
	{
		return bounds;
	}

	

}
