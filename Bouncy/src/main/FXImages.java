package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;

public class FXImages
{
	public static ArrayList<WritableImage> getFrames(File gif) 
	{
		return Images.getFrames(gif).stream().map(i -> SwingFXUtils.toFXImage(i, null)).collect(Collectors.toCollection(ArrayList::new));
	}
	
	public static void main(String[] args)
	{
		List<WritableImage> image = getFrames(new File("teacup.gif"));
		List<WritableImage> filtered = new ArrayList<>();
		for(int i = 0; i < image.size(); i++)
		{
			if(i % 3 == 0) filtered.add(image.get(i));
		}
		//filtered = filtered.stream().map(e -> new WritableImage()).collect(Collectors.toCollection(ArrayList::new));
		writeInGroups(filtered, 4);
		WritableImage result = concatenate(filtered);
		try
		{
			ImageIO.write(SwingFXUtils.fromFXImage(result, null), "png", new File("exploded.png"));
		}
		catch (IOException e)
		{
			System.out.println("Failed to write image to file:");
			e.printStackTrace();
		}
		
	}
	
	public static void writeInGroups(Collection<? extends Image> images, int groupSize)
	{
		String path = "groups\\";
		Iterator<? extends Image> it = images.iterator();
		int count = 0;
		while(it.hasNext())
		{
			Collection<Image> group = new ArrayList<>();
			for(int i = 0; i < groupSize; i++)
			{
				if(!it.hasNext()) break;
				group.add(it.next());
			}
			double width = 0;
			double height = 0;
			for(Image i : group)
			{
				height = i.getHeight();
				width += i.getWidth();
			}
			group.add(new WritableImage((int)(width * .15), (int)height));
			Image result = concatenate(group);
			String name = Integer.toString(count);
			try
			{
				File f = new File(path + name + ".png");
				ImageIO.write(SwingFXUtils.fromFXImage(result, null), "png", f);
			}
			catch (IOException e)
			{
				System.err.println("Unable to write images");
				e.printStackTrace();
			}
			count++;
		}
	}
	
	
	
	public static WritableImage concatenate(Collection<? extends Image> images)
	{
		double width = 0, height = images.iterator().next().getHeight();
		for(Image i : images) width += i.getWidth();
		WritableImage output = new WritableImage((int)width, (int)height);
		PixelWriter writer = output.getPixelWriter();
		int location = 0;
		for(Image i : images)
		{
			writer.setPixels(location, 0, (int)i.getWidth(), (int) height, i.getPixelReader(), 0, 0);
			location += i.getWidth();
		}
		return output;
	}
}
