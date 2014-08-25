package objects;


import player.PlayerDataHolder;
import javafx.scene.canvas.GraphicsContext;
import bounds.Bounds;
import engine.State;
import fxcore.MainGame;
import javafx.scene.paint.Color;
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
	 * passed to the coin object to make a new coin object at this location
	*/
	public Coin(double x, double y) {
		super(x, y, FACTION);
		spawnTime = MainGame.getTime();
		
	}
	@Override
	public boolean isDead() {
		return isDead;
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
	public Bounds bounds() {
		return Bounds.NONE;
	}
	
	public Impact onColide(GameObject other){
		if(other == player){
			isDead = true;
			System.out.println("**************HIT PLAYER***********");
		}
		return null;
	}

}

