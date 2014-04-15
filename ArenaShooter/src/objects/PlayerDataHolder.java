package objects;

public interface PlayerDataHolder extends EntityDataHolder
{
	public static class Copier extends EntityDataHolder.Copier implements PlayerDataHolder
	{
		public Copier(PlayerDataHolder e) 
		{
			super(e);
		}
	}
}
