package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.gif.GIFImageReaderSpi;

public class Images
{
	public static ArrayList<BufferedImage> getFrames(File gif) 
	{
	    ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();
	    ImageReader ir = new GIFImageReader(new GIFImageReaderSpi());
	    try
	    {
	    	 ir.setInput(ImageIO.createImageInputStream(gif));
	    	 for(int i = 0; i < ir.getNumImages(true); i++)
		 	        frames.add(ir.read(i));
	    }
	    catch(IOException e)
	    {
	    	 System.err.println("Unable to load GIF");
	    	 return null;
	    }
	    return frames;
	}
}
