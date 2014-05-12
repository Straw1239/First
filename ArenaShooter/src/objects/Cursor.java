package objects;



import player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import engine.State;

public class Cursor extends GameObject
{
	public static final int radius = 10;
	public static final Color color = Player.color;
	
	public Cursor(double x, double y)
	{
		super(x, y, Faction.Neutral);
	}

	@Override
	public void update(State d)
	{
		
	}

	@Override
	public boolean collidesWith(GameObject entity)
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

	@Override
	public Bounds bounds()
	{
		return Bounds.NONE;
	}
	

}
