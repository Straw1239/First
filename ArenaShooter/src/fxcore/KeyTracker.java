package fxcore;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyTracker implements EventHandler<KeyEvent>
{
	private boolean[] keys = new boolean[256];
	
	public boolean isKeyPressed(KeyCode code)
	{
		return keys[code.ordinal()];
	}

	@Override
	public void handle(KeyEvent event)
	{
		if(event.getEventType() == KeyEvent.KEY_PRESSED)
		{
			keys[event.getCode().ordinal()] = true;
		}
		
		if(event.getEventType() == KeyEvent.KEY_RELEASED)
		{
			keys[event.getCode().ordinal()] = false;
		}
		
	}

}
