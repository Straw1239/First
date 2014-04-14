package objects;

public abstract class GameObject implements ObjectDataHolder
{
	protected double x,y;
	protected Faction faction;
	
	protected GameObject(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	protected GameObject(double x, double y, Faction faction)
	{
		this(x,y);
		this.faction = faction;
	}
	
	public abstract void update();
	
	public abstract boolean collidesWith(GameObject entity);
	
	public abstract boolean collidesWithPlayer(Player p);
	
	public abstract boolean collidesWithBullet(Bullet b);
	
	public abstract boolean collidesWithEnemy(Enemy e);
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public Faction getFaction()
	{
		return faction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((faction == null) ? 0 : faction.hashCode());
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof GameObject)) {
			return false;
		}
		GameObject other = (GameObject) obj;
		if (faction != other.faction) {
			return false;
		}
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
			return false;
		}
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
			return false;
		}
		return true;
	}
}
