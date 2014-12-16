package fxcore;

import java.util.Collection;
import java.util.Collections;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Effect;

public interface Renderable extends Drawable
{

	default Effect specialEffects()
	{
		return null;
	}
	
	void renderHUD(GraphicsContext g);

}
