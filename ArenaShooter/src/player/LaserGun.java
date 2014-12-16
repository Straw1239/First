package player;

import java.util.Collections;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import bounds.Line;
import engine.EventHandler;
import objects.Faction;
import objects.GameObject;
import objects.ReadableObject;
import objects.GameObject.Change;
import objects.GameObject.Impact;
import objects.entities.Entity;
import objects.events.GameEvent;
import utils.Vector;

public class LaserGun implements Gun<Void>
{
	private ReadableObject target;
	private double x, y;
	@Override
	public void setTarget(ReadableObject target)
	{
		this.target = target;
		
	}

	@Override
	public void setLocation(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	@Override
	public GameEvent fire()
	{
		Vector t = new Vector(target);
		Line fire = new Line(new Vector(x, y), t.addScaled(t.sub(new Vector(x, y)), 100000));
		GameEvent shot = new GameEvent(GameObject.dataOf(x, y, Faction.Player))
		{
			@Override
			public void effects(EventHandler handler)
			{
				for(GameObject o : handler.objectsOfFaction(Faction.Enemy))
				{
					if(o.bounds().intersects(fire)) 
					{
						o.hitBy(new Impact((GameObject) Player.THE, Collections.singleton(new Change(Entity.DAMAGE, 10.0))));
					}
				}
			}

			@Override
			public void draw(GraphicsContext g)
			{
				g.setStroke(Color.ORANGERED);
				fire.fill(g);
			}

		};
		return shot;
	}

}
