package objects.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.events.GameEvent;
import bounds.Bounds;
import bounds.Circle;
import engine.State;
import fxcore.MainGame;

public class SpawningEnemy extends Enemy {

	public static final double radius = 50;
	public static final double maxHealth = 200;
	
	public SpawningEnemy(double x, double y) 
	{
		super(x, y);
		health = maxHealth;
		super.maxHealth = maxHealth;
		mass = 100;
		
	}

	@Override
	public void draw(GraphicsContext g) 
	{
		g.setFill(Color.DARKORANGE);
		bounds().fill(g);

	}

	@Override
	public Bounds bounds()
	{
		return Circle.of(x, y, radius);
	}
	
	public Collection<GameEvent> events(State s)
	{
		Collection<GameEvent> spawns = Collections.emptyList();
		spawns = new ArrayList<>(5);
		if(s.time % 400 == 0){
			for(int i = 0; i < 10; i++){
				spawns.add(GameEvent.spawnerOf(new RunningMinion(x + MainGame.rand.nextInt((int)(2 * radius)) - radius, y + MainGame.rand.nextInt((int)(2 * radius)) - radius)));
			}
		}
		return spawns;
	}

}
