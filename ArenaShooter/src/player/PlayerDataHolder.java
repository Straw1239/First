package player;

import objects.entities.EntityDataHolder;
import static utils.Utils.*;

public interface PlayerDataHolder extends EntityDataHolder
{
	public long fireTime();
	
	public default PlayerDataHolder copy()
	{
		return cast(deepCopy());
	}
}
