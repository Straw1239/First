package objects;

import java.awt.Graphics;

import ui.Transformer;

public interface EnemyDataHolder extends EntityDataHolder
{
	public void draw(Graphics g, Transformer t);
}
