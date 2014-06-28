package objects.entities;

import objects.MoverDataHolder;

public interface EntityDataHolder extends MoverDataHolder
{
	public boolean isDead();
	
	public double health();
	
	public double maxHealth();

	
}
