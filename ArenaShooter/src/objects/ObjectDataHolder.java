package objects;

import java.io.Externalizable;

import utils.Cloner;
import fxcore.Renderable;

public interface ObjectDataHolder extends Cloner, Locatable, Renderable, Externalizable
{
	public Faction getFaction();
	
	public boolean isDead();
	
	public boolean supportsOperation(int code);
}
