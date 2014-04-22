package objects;

import java.awt.Color;

public interface BulletDataHolder extends ObjectDataHolder, Velocity
{
	public double getRadius();
	
	public Color getColor();
	
	
	
	public static class Copier extends ObjectDataHolder.Copier implements BulletDataHolder
	{
		private double radius;
		private Color color;
		private double dx, dy;
		
		public Copier(BulletDataHolder b) 
		{
			super(b);
			radius = b.getRadius();
			color = new Color(b.getColor().getRGB(),true);
			dx = b.getDX();
			dy = b.getDY();
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

		@Override
		public double getDX() 
		{
			return dx;
		}

		@Override
		public double getDY() 
		{
			return dy;
		}

		
		
	}
}
