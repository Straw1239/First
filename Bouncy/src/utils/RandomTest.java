package utils;

import java.io.File;
import java.util.Arrays;

import com.google.common.io.Files;



public class RandomTest
{
	
	public static void main(String[] args) throws Throwable
	{
		File f = new File("Random.txt");
		f.delete();
		f.createNewFile();
		Random rand = Random.create();
		Files.write(rand.nextBytes(1 << 15), f);
		System.out.println(Arrays.toString(Files.toByteArray(f)));
		
	}

}
