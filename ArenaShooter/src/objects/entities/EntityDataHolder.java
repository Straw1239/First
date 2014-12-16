package objects.entities;

import objects.ReadableMover;

public interface EntityDataHolder extends ReadableMover
{
	public boolean isDead();
	
	public double health();
	
	public double maxHealth();

	
}
