package objects;

import java.awt.Color;

public interface BulletDataHolder extends ObjectDataHolder
{
	public double getRadius();
	
	public Color getColor();
	
	public static class Copier extends ObjectDataHolder.Copier implements BulletDataHolder
	{
		private double radius;
		private Color color;
		
		public Copier(BulletDataHolder b) 
		{
			super(b);
			radius = b.getRadius();
			color = new Color(b.getColor().getRGB(),true);
		}

		@Override
		public double getRadius()
		{
			return radius;
		}

		@Override
		public Color getColor()
		{
			return color;
		}

		
		
	}
}
