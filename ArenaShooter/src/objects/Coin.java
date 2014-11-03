package objects;


import java.util.EnumSet;
import java.util.Set;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import player.Player;
import player.PlayerDataHolder;
import bounds.Bounds;
import bounds.Circle;
import engine.State;
import fxcore.MainGame;
/**
 * a class representing the basic currency
 * @author Hank
 *
 */
public class Coin extends GameObject{
	public static final int RADIUS = 5;
	private Color COLOR = Color.YELLOW;
	public static final Faction FACTION = Faction.Neutral;
	private final long spawnTime;
	private boolean isDead = false;
	private PlayerDataHolder player;
	
	/**
	 * constructor is passed an x and y from the enemy where it last was when it died, which is
	 * passed to the coin object to make a new coin object close to this location
	*/
	public Coin(double x, double y) {
		super(x, y, FACTION);
		spawnTime = MainGame.getTime();
		
	}
	@Override
	public boolean isDead() {
		return isDead;
	}
	
	public Set<Faction> collidableFactions()
	{
		return EnumSet.of(Faction.Player);
	}
	@Override
	public void draw(GraphicsContext g) {
		g.setFill(COLOR);
		g.fillOval(x + RADIUS, y + RADIUS, 2 * RADIUS, 2 * RADIUS);
		
	}
	@Override
	public void update(State d) {
		if(d.time - spawnTime >= MainGame.UPS * 5){ //determines if the coin is > 5 seconds old
			isDead = true;
		}
		player = d.player;
	}
	@Override
	public Bounds bounds() 
	{
		return Circle.of(x, y, RADIUS);
	}
	
	public Impact collideWith(GameObject other){
		if(other instanceof Player)
		{
			isDead = true;
			Player.incrementCoins();
		}
			
		
		return null;
	}
	//TODO: eventually add an effect for when the coin is collected
//	public Collection<? extends GameEvent> onDeath(){
//		return Collections.singleton(new GameEvent(this){
//
//			@Override
//			public void effects(EventHandler handler) {
//
//				
//			}
//
//			@Override
//			public void draw(GraphicsContext g) {
//				
//			}
//
//			@Override
//			public boolean hasExpired() {
//				if(super.startTime() + 0.5 * MainGame.UPS < MainGame.getTime()){
//					return true;
//				}
//				return false;
//			}
//			
//		});
//	}

}

