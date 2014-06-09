package chapter10;

import java.util.Scanner;
/**
 * A text based menu on System.in and System.out
 * @author Rajan Troll
 *
 */
public class Menu implements AMenu 
{
	/**
	 * Title of the menu
	 */
	public final String title;
	/**
	 * All of the options for this menu
	 */
	private final String[] options;
	/**
	 * Menu with the specified title, and possible options.
	 * @param title of the menu
	 * @param options the menu has
	 */
	public Menu(String title, String... options)
	{
		this.title = title;
		this.options = options;
	}
	
	/**
	 * Gets an integer from a scanner, ignoring non int inputs and printing instructions when invalid input is present
	 * @param Scanner to get a valid int from
	 * @return the next valid int in the scanner
	 */
	public static int getValidInt(Scanner s)
	{
		while(!s.hasNextInt())
		{
			s.nextLine();
			System.out.print("Enter a valid integer ");
		}
		return s.nextInt();
	}



	@Override
	public int printMenuGetSelection() 
	{
		Scanner input = Project3.console;
		System.out.println(title);
		for(int i = 0; i < options.length; i++)
		{
			System.out.printf("\t(%d) %s\n", i + 1, options[i]);
		}
		System.out.print("Enter a valid selection: ");
		int selection = getValidInt(input);
		while(selection < 1 || (selection > options.length))
		{
			System.out.print("Enter a valid selection: ");
			selection = getValidInt(input);
		}
		return selection;
	}

	@Override
	public int getNumberOfMenuItems() 
	{
		return options.length;
	}
	
	
	
	
}
