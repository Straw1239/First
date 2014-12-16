package player;

import objects.Bullet;
import objects.Faction;
import objects.Locatable;
import objects.ReadableObject;
import objects.Bullet.Builder;
import objects.events.GameEvent;

public class BasicGun implements Gun<Void>
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
		GameEvent result = null;
		for(int i = 0; i < 3; i++)
		{
			Bullet.Builder builder = new Bullet.Builder();
			builder.setLocation(x, y).setColor(Player.color).setFaction(Faction.Player).setTarget(target).setDamage(2).setSpeed(10).setRadius(10);
			Bullet b = builder.build();
			b.spread(Math.toRadians(5));
			GameEvent spawner = GameEvent.spawnerOf(b);
			result = (result == null) ? spawner : GameEvent.merge(result, spawner);
		}
		return result;
	}

}
