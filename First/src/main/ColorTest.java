package main;

import javafx.scene.paint.Color;

public class ColorTest
{

	public static void main(String[] args)
	{
		Color c = Color.web("0x8897C2");
		//printColor(c);
		printColor(Color.web("0x4B392B"));

	}
	
	static void printColor(Color c)
	{
		System.out.printf("R: %.3f G : %.3f B: %.3f Opacity: %.3f Brightness: %.3f", c.getRed(), c.getGreen(), c.getBlue(), c.getOpacity(), c.getBrightness());
	}

}
