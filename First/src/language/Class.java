package language;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Class 
{
	private String name;
	private Map<String,Field> fields;
	private Map<String,Function> functions;
	private Map<String,Class> classes;
	private Map<String,Field> staticFields;
	private Map<String,Function> staticFunctions;
	private Map<String,Class> staticClasses;
	private boolean isFinal = false;
	private boolean isAbstract = false;
	
	public Class(File f)
	{
		Scanner input = null;
		try 
		{
			input = new Scanner(new BufferedInputStream(new FileInputStream(f)));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		StringBuilder builder = new StringBuilder();
		while(input.hasNextLine())
		{
			builder.append(input.nextLine());
			builder.append("\n");
		}
		buildFromSource(builder.toString());
	}
	
	public Class(String source)
	{
		buildFromSource(source);
	}
	
	public Class(List<String> lines)
	{
		
	}
	
	private void buildFromSource(String source)
	{
		int i = 0;
		while(i < source.length())
		{
			
		}
	}
}
