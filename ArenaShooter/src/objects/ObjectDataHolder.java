package objects;

import java.io.Externalizable;

import utils.Cloner;
import fxcore.Drawable;

public interface ObjectDataHolder extends Cloner, Locatable, Drawable, Externalizable
{
	public Faction getFaction();
	
	public boolean isDead();
}
