package objects;

import java.awt.Graphics;

import ui.Transformer;

public interface EnemyDataHolder extends EntityDataHolder
{
	public void draw(Graphics g, Transformer t);
	
	public static class Copier extends EntityDataHolder.Copier implements EnemyDataHolder
	{
		private EnemyDataHolder e;
		
		public Copier(EnemyDataHolder e) 
		{
			super(e);
			this.e = e;
		}

		@Override
		public void draw(Graphics g, Transformer t) 
		{
			e.draw(g, t);
		}
		
	}
	
}
