package main;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Parser
{
	public final String source;
	
	public static final Pattern CLASS = Pattern.compile("(public|private|protected)?\\s+class");
	public static final Pattern IMPORT = Pattern.compile("import\\s+static?.+;");
	public static final Pattern FOR_LOOP = Pattern.compile("for\\s*[(].*;.*;.*[)]");
	public static final Pattern WHILE_LOOP = Pattern.compile("while\\s*[(].+[)]");
	public static final Pattern DO_WHILE = Pattern.compile("do.+while\\s*[(].+[)]");
	public static final Pattern SWITCH = Pattern.compile("switch\\s*[(].+[)]\\s*[{]\\s*(case\\s*:.*)*\\s*[}]");
	
	
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
