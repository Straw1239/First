package chapter10;

import java.util.Scanner;

public class Menu implements AMenu 
{
	public final String title;
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
	private static int getValidInt(Scanner s)
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
		Scanner input = new Scanner(System.in);
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
