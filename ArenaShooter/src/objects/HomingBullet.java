package objects;

import javafx.scene.paint.Color;
import engine.State;

public class HomingBullet extends Bullet
{
	protected double speed;
	protected Locatable target;
	public HomingBullet(ReadableObject source, Locatable target, double speed, double radius, Color color)
	{
		super(source, target, speed, radius, color);
		this.speed = speed;
		this.target = target;
	}
	
	public void update(State s)
	{
		//Make it home
	}
}
