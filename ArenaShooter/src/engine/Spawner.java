package engine;

import java.util.Collection;

import objects.GameObject;

public interface Spawner
{
	public Collection<GameObject> spawn(State d);
}
