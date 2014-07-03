package objects.events;



import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.Faction;
import objects.ObjectDataHolder;
import bounds.Circle;
import engine.EventHandler;
import fxcore.MainGame;

/**
 * Basic Event representing an explosion. Damages all entities of appropriate factions within it's radius, 
 * displaying a red expanding circle graphic.
 * @author Rajan Troll
 *
 */
public class Explosion extends GameEvent implements ObjectDataHolder
{
	public static final long DURATION = 10;
	
	private double radius, damage;
	private Color color = Color.RED;
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
		
		public double radius()
		{
			return radius;
		}
		
	};
	
	public Explosion(double x, double y, Faction faction, double radius, double damage)
	{
		super(x, y, faction);
		this.radius = radius;
		this.damage = damage;
	}

	public Explosion(ObjectDataHolder source, double radius, double damage)
	{
		super(source);
		this.radius = radius;
		this.damage = damage;
	}
	
	public void setColor(Color c)
	{
		color = c;
	}

	

	@Override
	public void draw(GraphicsContext g)
	{
		g.setFill(color);
		long time = MainGame.getTime() - startTime();
		double radius = this.radius * (time / (double) DURATION);
		double temp = 2 * radius;
		g.fillOval(x - radius, y - radius, temp, temp);
	}

	@Override
	public boolean hasExpired()
	{
		return (MainGame.getTime() - startTime()) > DURATION;
	}

	@Override
	public void effects(EventHandler handler) 
	{
		/*Collection<? extends Entity> entities = handler.entities();
		for(Entity e : entities)
		{
			if(e.getFaction() != faction)
			{
				if(e.bounds().intersects(bounds))
				{
					e.damage(damage);
				}
			}
		}*/
	}

	
}
