package player;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import objects.Bullet;
import objects.Faction;
import objects.GameObject;
import objects.Locatable;
import objects.ReadableObject;
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
public class Player extends Entity implements ReadablePlayer
{
	public static double radius = 30;
	public static Color color = Color.rgb(170, 0, 170);
	public static volatile boolean laserING = false;
	public static ReadablePlayer THE;
	
	private static ArrayList<Gun<?>> guns = new ArrayList<>();
	
	private long fireTime;
	private Action action;
	private Collection<GameEvent> nextEvents = new ArrayList<>();
	
	private int coinsCollected;
	private double score;
	
	
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
	/**
	 * Creates a new player at the specified coordinates
	 * @param x
	 * @param y
	 */
	public Player(double x, double y) 
	{
		super(x, y);
		faction = Faction.Player;
		maxHealth = 10;
		//maxHealth = Double.POSITIVE_INFINITY;
		health = maxHealth;
		score = 0;
		THE = this;
	}
	/**
	 * Creates a new player at (0, 0)
	 */
	public Player()
	{
		this(0, 0);
	}
	/**
	 * Specifies what action the player will take next tick
	 * @param a
	 */
	public void setAction(Action a)
	{
		action = a;
	}

	public Collection<GameEvent> onDeath()
	{
		return Collections.singleton(new GameEvent(this)
		{
			@Override
			public void effects(EventHandler handler)
			{
			
				
			}

			@Override
			public void draw(GraphicsContext g)
			{
				Color c = Color.WHITE;
				g.setFill(Color.color(c.getRed(), c.getGreen(), c.getBlue(), .15));
				g.fillRect(0, 0, MainGame.getGameWidth(), MainGame.getGameHeight());
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
						k.isKeyPressed(KeyCode.A), k.isKeyPressed(KeyCode.D), m.gameX(this), m.gameY(this));
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
		
		if(state.time - fireTime >= (laserING ? 2 : 5))
		if(action.isShooting())
		{
			double x = action.targetX(), y = action.targetY();
			//double distance = distance(this.x, this.y, x, y);
			//double speed = 10;
			//double ratio = speed / distance;
			//
			{	Vector t = new Vector(x, y);
				Gun<?> g;
				if(laserING)
				{
					g = new LaserGun();
				}
				else 
				{
					g = new BasicGun();
				}
				g.setLocation(this);
				g.setTarget(GameObject.dataOf(x, y, Faction.Enemy));
				nextEvents.add(g.fire());
				
			}
			fireTime = state.time;
		}
	}
	
	public void renderHUD(GraphicsContext g)
	{
		double healthBar = 300;
		g.setFill(Player.color);
		g.fillRect(0, 0, healthBar * health() /maxHealth(), 50);
		g.setStroke(Color.WHITE);
		g.strokeRect(0, 0, healthBar, 50);
		g.setFill(Color.YELLOW);
		g.setFont(Font.font("Verdana", 75));
		g.setFill(Color.YELLOW);
		g.fillText(getCoinsCollected() + "", MainGame.getScreenWidth() - 150, 75); //Eventually autosize spacing to the width of the number
		g.setFont(Font.font("Verdana", 50));
		g.setFill(Color.WHITE);
		g.fillText("Score: "  + MainGame.getTime() + "", 0, 100);
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
	
	public int getCoinsCollected()
	{
		return coinsCollected;
	}
	
	public double getRawScore()
	{
		return score;
	}
	
	public void incrementCoins()
	{
		coinsCollected++;
	}
}
