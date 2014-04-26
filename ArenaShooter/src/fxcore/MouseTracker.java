package fxcore;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MouseTracker implements EventHandler<MouseEvent>
{
	private volatile boolean[] clicked = new boolean[MouseButton.values().length];
	private transient volatile long[] timeClicked = new long[MouseButton.values().length];
	private volatile double x, y;
	@Override
	public void handle(MouseEvent event)
	{
		x = event.getX();
		y = event.getY();
		if(event.getEventType() == MouseEvent.MOUSE_PRESSED)
		{
			clicked[event.getButton().ordinal()] = true;
			timeClicked[event.getButton().ordinal()] = System.nanoTime();
		}
		if(event.getEventType() == MouseEvent.MOUSE_RELEASED)
		{
			clicked[event.getButton().ordinal()] = false;
		}
	}
	
	public double x()
	{
		return x;
	}
	
	public double y()
	{
		return y;
	}
	
	public boolean isPressed(MouseButton button)
	{
		return clicked[button.ordinal()];
	}
	
	public long nanoTimeSincePress(MouseButton button)
	{
		return System.nanoTime() - timeClicked[button.ordinal()];
	}
	
	

}
