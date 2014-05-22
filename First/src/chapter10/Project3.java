package chapter10;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/* THIS IS A VERY LENGTHY ASSIGNMENT AND WILL TAKE YOU A SIGNIFICANT AMOUNT OF TIME.
 * DO NOT WAIT UNTIL THERE ARE ONLY 3 OR 4 DAYS LEFT TO WORK ON IT!!!
 * THE GRADING WILL BE VERY STRICT. 
 * 
 * PART OF THE GRADING (DISCRETION POINTS) MAY TEND TO BE SLIGHLY SUBJECTIVE...
 * THAT MEANS BE THOROUGH AND WHEN YOU ARE IN DOUBT ABOUT INLCUDING OR DOCUMENTING - 
 * JUST DO IT! YOUR CODE NEEDS TO BE EASY FOR ME TO FOLLOW. PLEASE MAKE SURE YOU 
 * READ THE REQUIREMENTS AND UNDERSTAND THEM. 
 * 
 * A DISCUSSION TOPIC HAS BEEN PROVIDED ON THE FUSION PAGE WHERE YOU CAN SUBMIT COMMENTS.
 * AND QUESTIONS. I WILL TRY TO MODERATE THE PAGE AT LEAST ONCE A DAY. ON THE FUSION PAGE 
 * YOU WILL ALSO FIND A SCORING RUBRIC WHICH I WILL USE AND YOU CAN ALSO TO EVALUATE 
 * YOURSELF. PLEASE SUBMIT IT IN THE .ZIP FILE EACH TIME YOU SUBMIT YOUR CODE. 
 * 
 * THERE IS AN EXAMPLE OF A transactions2.dat FILE (52 TRANSACTIONS)ON THE FUSION PAGE. 
 * IF YOU ARE UNSURE ABOUT THE RESULTS FROM ANY TRANSACTION FILE, SUBMIT IT TO ME 
 * AND ASK ME TO RUN IT WITH THE SOFTWARE I HAVE CREATED. I WILL THEN PROVIDE THE 
 * RESULTS TO YOU AND THE REST OF THE CLASS. 
 * 
 * THE ASSIGNMENT WILL BE GRADED ON THE CURVE.
 ********************************************************************************************************* 
 * Problem #1 RewardCustomer
 * A supermarket wants to reward its best customer of each day, showing the 
 * customer’s name on a screen in the supermarket. For that purpose, 
 * the customer’s purchase amount is stored in an ArrayList<Double> and the 
 * customer’s name is stored in a corresponding ArrayList<String>.
 * 
 * Implement a method
 * 		public static String nameOfBestCustomer(ArrayList<Double> purchases,
 * 			ArrayList<String> customers)
 * that returns the name of the customer with the largest number of purchases in $.
 * Write a program that prompts the cashier for a file name which contains a record 
 * of transactions (purchase, customer name). Your program should read in the 
 * transactions and then implement the method above. Note that the transactions are in random 
 * order.
 * 
 * The data are stored in a file named transactions1.dat and transactions2.dat, however the
 * second file is empty. 
 *********************************************************************************************************  
 * Problem #2 ProcessTransactionFile
 * You must write a method that will create the file transactions2.dat. Inputs to the
 * process of creating the file should be a file name and number of transactions (a positive
 * number). The format of the file is the same as transactions1.dat. A double followed by
 * a customer name. The customer name must be a single upper or lower case alphabetic letter.
 * If the file contains less than 52 transactions, then the customers names must be unique.
 * You must generate the file during execution and are prohibited from saving any 
 * part of the transactions (that means purchase or name) in any method before execution begins.
 * You must be able to generate files with 0, 26, 52, 53 and 100 transactions. Any file with
 * less than 53 transactions must have unique names. Software must be provided to print 
 * the file and to query the file for printouts which should contain appropriate statistics 
 * for the name(s). 
 * Hint: http://www.ascii-code.com/.
 *********************************************************************************************************  
 * Problem #3 TopCustomer
 * Improve Problem 1 so that it displays the top customers, that is, 
 * the topN customers with the largest purchases, where topN(>0) is a 
 * value that the user of the program supplies. The customers should be
 * displayed in largest purchases first and so on.
 * 
 * Implement a method
 * 		public static ArrayList<String> nameOfBestCustomers(ArrayList<Double> purchases, 
 *  		ArrayList<String> names, int topN)
 * If there were fewer than topN customers, include all of them.
 *********************************************************************************************************  
 * Problem #4 UpdateStats
 * As you are working on these problems you must update a file which contains the date 
 * you worked on the problem, the problem number and the number of minutes you spent 
 * working on the problem on that particular day. The file may be updated manually
 * and you should provide software which provides printouts of the dates and number 
 * of minutes that you worked on any problem and the total minutes. The file should be 
 * named stats.dat. You will not be graded on the amount of time you spend on this 
 * assignment.
 *********************************************************************************************************  
 * Extra Credit #1
 * a. There are a number of menus in this assignment. Extra credit (5 pts) for using a class
 * which takes care of all the details of printing, selection, etc. You must implement
 * the interface AMenu. Hint: Figure out how to use a constructor with a variable 
 * number of arguments. 
 * b. A scanner can be set up that allow it to parse a line with different
 * delimiters . Extra credit (2 pts) for being able to create and read transaction
 * files with both a space and comma as the delimiting character. transactions3.dat is an
 * example.
 *********************************************************************************************************  
 * Additional requirements:
 * 		1. You must use the for-each statement wherever possible.
 * 		2. You must put your program in an infinite loop and you must provide
 * 		   a programmatic means to terminate the loop.
 * 		3. At the end of each day that you work on the assignment you should submit 
 * 		   a .zip file as called out in #4 below. You will not be graded on 
 * 		   intermediate submittals.       
 *      4. The submittal for this assignment should be a .zip file with the
 *         program, the stats.dat file and a filled out rubric. You may chose to use 
 *         your own defined classes for any of the problems. In that case you must 
 *         also submit those files.
 *      4. ****WARNING:**** THE USER (ME) IS NOT VERY COMPUTER LITERATE. I HAVE A TENDENCY
 *         TO ENTER DATA INCORRECTLY. YOU MUST TAKE ACCOUNT OF THIS IN YOUR CODE.
 *      5. THE ASSIGNMENT IS DUE BEFORE 11:59PM ON 5/25/14. NO LATE ASSIGNEMTS ACCEPTED.!
 *********************************************************************************************************  
 * Clarifications:
 * 		1. You must receive a score of at most 6 points lower than the highest score
 *         in order to receive any extra credit.
 * 	    2. transactions1.dat should not be changed. It does not adhere to the requirements
 * 	       for transactions2.dat on purpose.
 * 	    3. Number of lines of code. 
 * 		   Don't include blank lines, declarations, lines which contain only an 
 * 		   else statement, break statements, continue statements,
 * 		   return statements, and lines with only brackets as a line of code. 
 * 		   A line of code continued on another line only counts as one line of code.
 * 		4. You may not use any other type of collection (linked list, set, map, queue, 
 * 		   etc.) in your solution. 
 *********************************************************************************************************  
 */

public class Project3  
{
	private static final Random rand = new Random();
	public static final String alphabet = buildAlphabet();
	
	public static void main(String[] args) throws FileNotFoundException
	{
		Menu mainMenu = new Menu("Main Menu...", 
		"RewardCustomers", "ProcessTransactionFiles", "TopCustomers", "QueryStatsFile", "Quit");
		int selection = mainMenu.printMenuGetSelection();
		switch (selection)
		{
		case 1: rewardCustomersMenu(); break;
		case 2: fileGeneratorMenu(); break;
		case 3: topCustomersMenu(); break;
		case 4: statsMenu(); break;
		case 5: System.exit(0);
		default: throw new InternalError("missing case statement");
		}
	}
	
	private static void statsMenu()
	{
		
	}
	
	private static void topCustomersMenu()
	{
		
	}
	
	private static void fileGeneratorMenu()
	{
		
	}
	
	private static void rewardCustomersMenu()
	{
		Menu menu = new Menu("Reward Customers Menu...", "Use transactions1.dat",  "Use transactions2.dat",  "Quit");
		int selection = menu.printMenuGetSelection();
		String filename = "";
		switch(selection)
		{
		case 1: filename = "transactions1.dat"; break;
		case 2: filename = "transactions2.dat"; break;
		case 3: return;// Change?
		default: throw new InternalError();
		}
		filename = "src/chapter10/" + filename;
		File f = new File(filename);
		InputStream stream;
		try
		{
			stream = new BufferedInputStream(new FileInputStream(f));	
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Error: file not found");
			throw new InternalError(e);
		}
		ArrayList<Sale> sales = buildFromInput(stream);
		ArrayList<String> best = namesOfBestCustomers(values(sales), names(sales));
		System.out.println("The best customer(s) are: " + best);
	}
	
	public static void buildRandomFile(String name, int numEntries)
	{
		File f = new File(name);
		PrintStream printer = null;
		try
		{
			if(!f.exists()) f.createNewFile();
			printer = new PrintStream(new BufferedOutputStream(new FileOutputStream(f)));
		} 
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
		ArrayList<Character> names = new ArrayList<>();
		for(int i = 0; i < alphabet.length(); i++)
		{
			names.add(alphabet.charAt(i));
			names.add(Character.toUpperCase(alphabet.charAt(i)));
		}
		for(int i = names.size(); i < numEntries; i++)
		{
			char c = alphabet.charAt(rand.nextInt(alphabet.length()));
			names.add(rand.nextBoolean() ? c : Character.toUpperCase(c));
		}
		Collections.shuffle(names, rand);
		for(int i = 0; i < numEntries; i++)
		{
			printer.printf("%.2f %s\n", rand.nextDouble() * 100, names.get(i).toString());
		}
		printer.close();
	}
	
	private static String buildAlphabet()
	{
		String result = "";
		for(char c = 'a'; c <= 'z'; c++)
		{
			result += c;
		}
		return result;
	}
	
	private static ArrayList<Sale> buildFromInput(InputStream s) 
	{
		Scanner input = null;
		input = new Scanner(s);
		input.useDelimiter("[,\\s]+");
		ArrayList<Sale> results = new ArrayList<>();
		while(input.hasNext())
		{
			results.add(new Sale(input.nextDouble(), input.next()));
		}
		input.close();
		return results;
	}
	
	private static ArrayList<String> names(ArrayList<Sale> sales)
	{
		ArrayList<String> names = new ArrayList<>();
		for(Sale s : sales)
		{
			names.add(s.name);
		}
		return names;
	}
	
	private static ArrayList<Double> values(ArrayList<Sale> sales)
	{
		ArrayList<Double> values = new ArrayList<>();
		for(Sale s : sales)
		{
			values.add(s.value);
		}
		return values;
	}
	
	
	
	/**
	 * Searches and finds the best customer for a day. Equivalent to nameOfBestCustomers(sales, customers, 1).get(0)
	 * @param list of all sales for the day
	 * @param list of all customers for the day; names may be repeated as 
	 * each one corresponds to one transaction with the price at the same index in sales
	 * @return Name of customer who bought the most stuff ($)
	 */
	public static String nameOfBestCustomer(ArrayList<Double> sales, ArrayList<String> customers)
	{
		return nameOfBestCustomers(sales, customers, 1).get(0);
	}
	
	public static ArrayList<String> namesOfBestCustomers(ArrayList<Double> sales, ArrayList<String> names)
	{
		ArrayList<Sale> combined = build(sales, names);
		Collections.sort(combined);
		ArrayList<String> results = new ArrayList<>();
		int i = 0;
		double best = combined.get(0).value;
		while(combined.get(i).value >= best)
		{
			results.add(combined.get(i).name);
			i++;
		}
		return results;
	}
	
	private static ArrayList<Sale> combine(List<String> names, List<Double> sales)
	{
		if(names.size() != sales.size()) throw new IllegalArgumentException();
		ArrayList<Sale> results = new ArrayList<>();
		for(int i = 0; i < names.size(); i++)
		{
			results.add(new Sale(names.get(i), sales.get(i)));
		}
		return results;
	}
	
	private static ArrayList<Sale> build(ArrayList<Double> sales, ArrayList<String> names)
	{
		if(sales.size() != names.size()) throw new IllegalArgumentException();
		ArrayList<String> customers = new ArrayList<>();
		ArrayList<Double> totalSales = new ArrayList<>();
		for(int i = 0; i < sales.size(); i++)
		{
			int index = customers.indexOf(names.get(i));
			if(index >= 0)
			{
				totalSales.set(index, totalSales.get(index) + sales.get(i));
			}
			else
			{
				customers.add(names.get(i));
				totalSales.add(sales.get(i));
			}
		}
		return combine(customers, totalSales);
	}
	
	public static ArrayList<String> nameOfBestCustomers(ArrayList<Double> sales, ArrayList<String> names, int topN)
	{
		ArrayList<Sale> customerTotals = build(sales, names);
		ArrayList<String> results = new ArrayList<>();
		Collections.sort(customerTotals);
		for (int i = 0; i < topN; i++)
		{
			if(i >= customerTotals.size()) break;
			results.add(customerTotals.get(i).name);
		}
		return results;
	}
	
	
}
