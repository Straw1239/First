package objects;

import java.io.Externalizable;

import utils.Copyable;
import fxcore.Renderable;

public interface ObjectDataHolder extends Copyable<ObjectDataHolder>, Locatable, Renderable, Externalizable
{
	public Faction getFaction();
	
	public boolean isDead();
	
	public boolean supportsOperation(int code);
}
