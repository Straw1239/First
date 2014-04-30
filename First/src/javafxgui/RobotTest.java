package javafxgui;

import java.awt.AWTException;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;



public class RobotTest 
{
	public static void main(String[] args) 
	{
		if(GraphicsEnvironment.isHeadless())
		{
			System.err.println("Cannot create input robot");
			System.exit(-1);
		}
		Robot rob;
		try 
		{
			rob = new Robot();
		} 
		catch (AWTException e) 
		{
			throw new RuntimeException(e);
		}
		rob.mouseMove(0, 0);
		
	}
}
