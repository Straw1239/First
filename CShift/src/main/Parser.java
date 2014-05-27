package main;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Parser
{
	public final String source;
	
	public static final Pattern forLoop = Pattern.compile("for\\s*[(].*;.*;.*[)].*");
	
	public Parser(InputStream s)
	{
		s = new BufferedInputStream(s);
		Scanner scan = new Scanner(s);
		StringBuilder builder = new StringBuilder();
		while(scan.hasNextLine())
		{
			builder.append(scan.nextLine());
		}
		source = builder.toString();
		
		
		scan.close();
	}
	
	public SourceTree parse()
	{
		return new SourceTree();
	}
}
