package objects;

import static fxcore.MainGame.rand;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import objects.entities.Entity;
import utils.Utils;
import utils.Vector;
import bounds.Bounds;
import bounds.Circle;

public class PiercingBullet extends MovingObject // Should this extend Bullet?
{
	public double damage = 1;
	public double radius = 5;
	private boolean isDead = false;
	public Paint color = Color.WHITE;
	
	public PiercingBullet(double x, double y, double dx, double dy, Faction faction, double mass)
	{
		super(x, y, dx, dy, faction, mass);
		// TODO Auto-generated constructor stub
	}
	
	public PiercingBullet(ObjectDataHolder odh, double dx, double dy, double mass)
	{
		this(odh.getX(), odh.getY(), dx, dy, odh.getFaction(), mass);
	}
	
	public void setTarget(Locatable target, double speed)
	{
		double distance = Utils.distance(target, this);
		dx = (target.getX() - x) * speed / distance;
		dy = (target.getY() - y) * speed / distance;
	}
	
	public boolean isDead()
	{
		return isDead || Utils.hypotSquared(dx, dy) < 1;
	}
	
	protected void onWallHit()
	{
		isDead = true;
	}
	
	public void spread(double maxAngle)
	{
		double angle = Math.atan2(dy, dx);
		double speed = Math.hypot(dx, dy);
		angle += maxAngle * (rand.nextDouble() * 2 - 1);
		dx = Math.cos(angle) * speed;
		dy = Math.sin(angle) * speed;
	}
	
	public Impact collideWith(GameObject other)
	{
		if(other.supportsOperation(Entity.DAMAGE))
		{
			double momentumTransfer = .1;
			double dx = this.dx;
			double dy = this.dy;
			//this.dx *= 1 - momentumTransfer;
			//this.dy *= 1 - momentumTransfer;
			return new Impact(this, 
					new Change(Entity.DAMAGE, damage), 
					new Change(MovingObject.FORCE, new Vector(dx * mass * momentumTransfer, dy * mass * momentumTransfer)));
		}
		return null;
	}
	
	public Bounds bounds()
	{
		return Circle.of(x, y, radius);
	}

	@Override
	public void draw(GraphicsContext g)
	{
		g.setFill(color );
		bounds().fill(g);
		
	}
	
	

}
