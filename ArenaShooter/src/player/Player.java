package player;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import objects.Faction;
import objects.GameObject;
import objects.entities.Entity;
import objects.events.GameEvent;
import utils.Vector;
import bounds.Bounds;
import bounds.Circle;
import bounds.Line;
import engine.EventHandler;
import engine.State;
import fxcore.KeyTracker;
import fxcore.MainGame;
import fxcore.MouseTracker;


/**
 * Represents the player of the game. 
 * @author Rajan
 *
 */
public class Player extends Entity implements PlayerDataHolder
{
	public static double radius = 30;
	public static Color color = Color.rgb(170, 0, 170);
	private long fireTime;
	private Action action;
	private Collection<GameEvent> nextEvents = new ArrayList<>();
	private static int coinsCollected;
	private static double score;
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
	//Hi
	public Player(double x, double y) 
	{
		super(x, y);
		faction = Faction.Player;
		maxHealth = 10;
		//maxHealth = Double.POSITIVE_INFINITY;
		health = maxHealth;
		score = 0;
	}
	
	public Player()
	{
		this(0, 0);
	}
	
	public void setAction(Action a)
	{
		action = a;
	}

	public Collection<GameEvent> onDeath()
	{
		return Collections.singleton(new GameEvent(this)
		{
			long time = 5;
			@Override
			public void effects(EventHandler handler)
			{
				time--;
				
			}

			@Override
			public void draw(GraphicsContext g)
			{
				Color c = Color.WHITE;
				g.setFill(Color.color(c.getRed(), c.getGreen(), c.getBlue(), .15));
				g.fillRect(0, 0, MainGame.getGameWidth(), MainGame.getGameHeight());
			}
			
			
			@Override
			public boolean hasExpired()
			{
				return time == 0;
			}
			
		});
	}
	
	@Override
	public void update(State d) 
	{
		
		nextEvents.clear();
		if(!isDead()) 
		{
			heal(.03);
			score += 1/60.0;
			//heal(Double.POSITIVE_INFINITY);
			KeyTracker k = MainGame.getKeyTracker();
			MouseTracker m = MainGame.getMouseTracker();
			
			if(m.isPressed(MouseButton.PRIMARY))
			{
				action = new Action(k.isKeyPressed(KeyCode.W), k.isKeyPressed(KeyCode.S), 
						k.isKeyPressed(KeyCode.A), k.isKeyPressed(KeyCode.D), m.gameX(d), m.gameY(d));
			}
			else
			{
				action = new Action(k.isKeyPressed(KeyCode.W), k.isKeyPressed(KeyCode.S), 
						k.isKeyPressed(KeyCode.A), k.isKeyPressed(KeyCode.D));
			}
			
			executeAction(d);
		}
		super.update(d);
		dx *= .97;
		dy *= .97;
	}
	
	private void executeAction(State state)
	{
		double moveSpeed = 5;
		if(action.isDown() || action.isLeft() || action.isRight() || action.isUp())
		{
			dx = 0;
			dy = 0;
		}
		if(action.isDown()) dy++;
		if(action.isUp()) dy--;
		if(action.isRight()) dx++;
		if(action.isLeft()) dx--;
		if(action.isDown() || action.isLeft() || action.isRight() || action.isUp())
		{
			double hypot = Math.hypot(dx, dy);
			if(hypot > 0)
			{
				dx /= hypot;
				dy /= hypot;
				dx *= moveSpeed;
				dy *= moveSpeed;
			}
		}
		
		if(state.time - fireTime >= 2)
		if(action.isShooting())
		{
			double x = action.targetX(), y = action.targetY();
			//double distance = distance(this.x, this.y, x, y);
			//double speed = 10;
			//double ratio = speed / distance;
			//for(int i = 0; i < 3; i++)
			{
				Vector t = new Vector(x, y);
				Line fire = new Line(this, t.addScaled(t.sub(new Vector(this)), 100000));
				GameEvent shot = new GameEvent(this)
				{

					private boolean done = false;

					@Override
					public void effects(EventHandler handler)
					{
						done = true;
						for(GameObject o : handler.objectsOfFaction(Faction.Enemy))
						{
							if(o.bounds().intersects(fire)) 
							{
								o.hitBy(new Impact(Player.this, Collections.singleton(new Change(Entity.DAMAGE, 10.0))));
							}
						}
					}

					@Override
					public void draw(GraphicsContext g)
					{
						g.setStroke(Color.ORANGERED);
						fire.fill(g);
					}

					@Override
					public boolean hasExpired()
					{
						return done;
					}
				};
				nextEvents.add(shot);
				
				//Bullet bullet = new Bullet(this, GameObject.dataOf(x, y, faction), 10, 10, color);
				//bullet.damage = 2;
				//bullet.spread(Math.toRadians(5)) ;
				//nextEvents.add(GameEvent.spawnerOf(bullet));
				
				//PiercingBullet bullet = new PiercingBullet(this, 0, 0, 2);
				//bullet.setTarget(new Vector(x, y), 20);
				//bullet.color = color;
				//bullet.radius = 10;
				//bullet.spread(Math.toRadians(5));
				//nextEvents.add(GameEvent.spawnerOf(bullet));
			}
			fireTime = state.time;
		}
	}
	
	@Override
	public Collection<GameEvent> events(State d)
	{
		return nextEvents;
	}
	

	
	public static class Action
	{
		private boolean up = false, down = false, left = false, right = false;
		private boolean shoot = false;
		private double targetX, targetY;
		
		public Action(boolean up, boolean down, boolean left, boolean right, double targetX, double targetY)
		{
			this(up ,down, left, right);
			this.targetX = targetX;
			this.targetY = targetY;
			shoot = true;
		}
		
		public Action(boolean up, boolean down, boolean left, boolean right) 
		{
			this.up = up;
			this.down = down;
			this.left = left;
			this.right = right;
		}

		public Action(){}
		
		public boolean isShooting()
		{
			return shoot;
		}
		
		public double targetX()
		{
			if(!shoot) throw new IllegalStateException("Action does not call for shooting: no target");
			return targetX;
		}
		
		public double targetY()
		{
			if(!shoot) throw new IllegalStateException("Action does not call for shooting: no target");
			return targetY;
		}

		public boolean isUp() 
		{
			return up;
		}

		public boolean isDown() 
		{
			return down;
		}

		public boolean isLeft() 
		{
			return left;
		}

		public boolean isRight() 
		{
			return right;
		}
	}

	@Override
	public long fireTime()
	{
		return fireTime;
	}

	@Override
	public void draw(GraphicsContext g)
	{
		g.setFill(Player.color);
		g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
		
	}

	@Override
	public Bounds bounds()
	{
		return bounds;
	}
	
	public void damage(double damage)
	{
		super.damage(damage);
		if(!isDead()) MainGame.sleep((long)(damage * 40));
	}
	
	public static int getCoinsCollected()
	{
		return coinsCollected;
	}
	
	public static double getRawScore()
	{
		return score;
	}
	
	public static void incrementCoins(){
		
		coinsCollected++;
	}


}
