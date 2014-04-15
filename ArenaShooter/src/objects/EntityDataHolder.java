package objects;

public interface EntityDataHolder extends ObjectDataHolder
{
	public boolean isDead();
	
	public double health();
	
	public double maxHealth();
	
	public static class Copier extends ObjectDataHolder.Copier implements EntityDataHolder
	{
		private double health, maxHealth;
		public Copier(EntityDataHolder e)
		{
			super(e);
			health = e.health();
			maxHealth = e.maxHealth();
		}
		
		@Override
		public boolean isDead() 
		{
			return health == 0;
		}
		@Override
		public double health() 
		{
			return health;
		}
		@Override
		public double maxHealth() 
		{
			return maxHealth;
		}
		
		
	}
	
	
}
