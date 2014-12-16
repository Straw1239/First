package player;

import objects.entities.EntityDataHolder;
import static utils.Utils.*;

public interface ReadablePlayer extends EntityDataHolder
{
	public long fireTime();
	
	public default ReadablePlayer copy()
	{
		return cast(deepCopy());
	}
}
