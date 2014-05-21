package chapter10;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Menu implements AMenu 
{
	public final String title;
	private final NamedRunnable[] options;
	
	public Menu(String title, NamedRunnable... options)
	{
		this.title = title;
		this.options = options;
	}
	
	public Menu(String title, String[] options, Runnable[] results)
	{
		this(title, NamedRunnable.combine(options, results));
	}
	
	/* (non-Javadoc)
	 * @see chapter10.AMenu#showAndRun(java.io.PrintStream, java.io.InputStream)
	 */
	@Override
	public void showAndRun(PrintStream p, InputStream s)
	{
		Scanner input = new Scanner(s);
		p.println(title);
		for(int i = 0; i < options.length; i++)
		{
			p.printf("\t(%d) %s\n", i + 1, options[i].getName());
		}
		p.print("Enter your selection: ");
		int selection = getValidInt(input, p);
		while(selection < 1 || selection > options.length + 1)
		{
			System.out.print("\n Enter a valid selection ");
			selection = getValidInt(input, p);
		}
		options[selection - 1].run();
		input.close();
	}
	
	private static int getValidInt(Scanner s, PrintStream p)
	{
		while(!s.hasNextInt())
		{
			s.nextLine();
			p.print("\nEnter a valid integer ");
		}
		return s.nextInt();
	}
	
	
	
	
}
