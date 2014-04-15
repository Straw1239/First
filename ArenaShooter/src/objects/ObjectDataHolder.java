package objects;

public interface ObjectDataHolder 
{
	public double getX();
	
	public double getY();
	
	public Faction getFaction();
	
	public static class Copier implements ObjectDataHolder
	{
		private double x, y;
		private Faction faction;
		
		public Copier(ObjectDataHolder o) 
		{
			x = o.getX();
			y = o.getY();
			faction = o.getFaction();
		}

		@Override
		public double getX() 
		{
			return x;
		}

		@Override
		public double getY() 
		{
			return y;
		}

		@Override
		public Faction getFaction() 
		{
			return faction;
		}
		
	}
}
