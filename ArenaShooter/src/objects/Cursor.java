package objects;



import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import fxcore.Display;

public class Cursor extends GameObject
{
	public static final int radius = 10;
	public static final Color color = Player.color;
	
	public Cursor(double x, double y)
	{
		super(x, y, Faction.Neutral);
	}

	@Override
	public void update(Display d)
	{
		
	}

	@Override
	public boolean collidesWith(GameObject entity)
	{
		return false;
	}

	@Override
	public boolean collidesWithPlayer(Player p)
	{
		return false;
	}

	@Override
	public boolean collidesWithBullet(Bullet b)
	{
		return false;
	}

	@Override
	public boolean collidesWithEnemy(Enemy e)
	{
		return false;
	}
	
	public void draw(GraphicsContext g)
	{
		g.setStroke(color);
		g.setLineWidth(5);
		g.strokeOval(x - radius, y - radius,  2 * radius, 2 * radius);
		g.setStroke(Color.WHITE);
		double radius = this.radius - 5;
		g.strokeOval(x - radius, y - radius, 2 * radius, 2 * radius);
	}
	

}
