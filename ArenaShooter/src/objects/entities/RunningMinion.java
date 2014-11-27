package objects.entities;

import java.util.Collection;
import java.util.Collections;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.Faction;
import objects.GameObject;
import objects.GameObject.Change;
import objects.GameObject.Impact;
import objects.events.Explosion;
import objects.events.GameEvent;
import bounds.Bounds;
import bounds.Circle;
import engine.State;
import fxcore.MainGame;
//BUG: stops game after a little bit
public class RunningMinion extends MovingEnemy
{

	private static double radius = 10;
	private double spawnTime = MainGame.getTime();
	private boolean timeOut = false;
	
	public RunningMinion(double x, double y) 
	{
		super(x, y, 7, 0.5);
	}
	
	@Override
	public void draw(GraphicsContext g) 
	{
		g.setFill(Color.DARKCYAN);
		bounds().fill(g);
		
	}
	
	@Override
	public Bounds bounds() 
	{
		return Circle.of(x, y, radius);
	}
	
	public Impact collideWith(GameObject other)
	{
		isDead = true;
		return new Impact(this, new Change(DAMAGE, contactDamage));
	}
	
	public void update(State s)
	{
		 super.update(s);
		 if(s.time - spawnTime == MainGame.UPS * 5){
			 isDead = true;
			 health = 0;
		 }
	}
	
	public Collection<GameEvent> onDeath()
	{
		if(isDead)
			return Collections.singleton(new Explosion(x, y, Faction.Enemy, 30, 5));
		return null;
	}
	

}
