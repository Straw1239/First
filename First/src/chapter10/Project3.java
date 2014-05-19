package chapter10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import static java.util.Map.Entry;

/* THIS IS A VERY LENGTHY ASSIGNMENT AND WILL TAKE YOU A SIGNIFICANT AMOUNT OF TIME.
 * DO NOT WAIT UNTIL THERE IS ONLY 2 OR 3 DAYS LEFT TO WORK ON IT.
 * THE GRADING WILL BE VERY STRICT. PART OF THE GRADING (IMPLEMENTATION OF FUNCTIONALITY)
 * MAY TEND TO BE SLIGHLY SUBJECTIVE...THAT MEANS BE THOROUGH AND WHEN YOU ARE IN 
 * DOUBT ABOUT INLCUDING OR DOCUMENTING - JUST DO IT! PLEASE MAKE SURE YOU READ EVERYTHING
 * AND UNDERSTAND THE REQUIREMENTS. A DISCUSSION TOPIC HAS BEEN PROVIDED ON THE FUSION PAGE
 * WHERE YOU CAN SUBMIT COMMENTS AND QUESTIONS. I WILL TRY TO MODERATE THE PAGE AT LEAST ONCE A
 * DAY.
 * 
 * Problem #1 RewardCustomer
 * A supermarket wants to reward its best customer of each day, showing the 
 * customer’s name on a screen in the supermarket. For that purpose, 
 * the customer’s purchase amount is stored in an ArrayList<Double> and the 
 * customer’s name is stored in a corresponding ArrayList<String>.
 * 
 * Implement a method
 * 		public static String nameOfBestCustomer(ArrayList<Double> sales,
 * 			ArrayList<String> customers)
 * that returns the name of the customer with the largest number of sales in $.
 * Write a program that prompts the cashier for a file name which contains a record 
 * of transactions (sale price, customer name). Your program should read in the 
 * transactions and then implement the method above. Note that the transactions are in random 
 * order.
 * 
 * The data are stored in a file named transactions1.dat and transactions2.dat, however the
 * second file is empty. 
 * 
 * Problem #2 
 * You must write a method that will create the file transactions2.dat. Inputs to the
 * process of creating the file should be a file name and number of transactions (a positive
 * number). The format of the file is the same as transactions1.dat. A double followed by
 * a customer name. The customer name must be a single upper or lower case alphabetic letter.
 * You should make sure that you test the program with at least 52 different customer names
 * and at least 53 transactions. You must generate the file during execution and are prohibited 
 * from saving any part of the transactions in any method before execution begins. A mechanism must 
 * be provided to print the file and to query the file for printouts which should contain 
 * appropriate statistics for the name(s).
 * 
 * Problem #3 TopCustomer
 * Improve Problem 1 so that it displays the top customers, that is, 
 * the topN customers with the largest sales, where topN(>0) is a 
 * value that the user of the program supplies. The customers should be
 * displayed in largest sales first and so on.
 * 
 * Implement a method
 * 		public static ArrayList<String> nameOfBestCustomers(ArrayList<Double> sales, 
 *  		ArrayList<String> names, int topN)
 * If there were fewer than topN customers, include all of them.
 * 
 * Problem #4 UpdateStats
 * As you are working on these problems you must update a file which contains the date 
 * you worked on the problem, the problem number and the number of minutes you spent 
 * working on the problem on that particular day. The file may be updated manually
 * and you should provide a mechanism which provides printouts of the dates and number 
 * of minutes that you worked on any problem and the total minutes. A mechanism must also 
 * be provided to query the file for statistics and to print the file. The file should be 
 * named stats.dat. You will not be graded on the amount of time you spend on this assignment.
 * At the end of each day that you work on the assignment you should submit a .zip file as
 * called out in #4 below.  
 * 
 * Additional requirements:
 * 		1. You must use the for-each statement wherever possible.
 * 		2. You must put your program in an infinite loop and you must provide
 * 		   a programmatic means to terminate the loop.
 * 		3. Each problem is worth 11 points:
 * 		   a. 7 pts for complete and correct functionality.
 * 		   b. 1 pt for following instructions.
 * 		   c. 1 pt for no method longer than 20 lines of code. The main() method must 
 *            be no longer than 10 lines of code.
 *         d. 2 pts for THOROUGH javadoc documentation.
 *      4. The submittal for this assignment should be a .zip file with the
 *         program and the stats.dat file. You may chose to use other classes for
 *         any of the problems. In that case you must also submit those files.
 *      5. ****WARNING:**** THE USER (ME) IS NOT VERY COMPUTER LITERATE. I HAVE A TENDENCY
 *         TO ENTER DATA INCORRECTLY. YOU MUST TAKE ACCOUNT OF THIS IN YOUR CODE.
 *      6. There are a number of menus in this assignment. Extra credit (5 pts) for using a class
 *         which takes care of all the details of printing, selection, etc. You must implement
 *         the interface AMenu. Hint: Figure out how to use a constructor with a variable 
 *         number of arguments. 
 *      7. THE ASSIGNMENT IS DUE BEFORE 11:59PM ON 5/25/14. NO LATE ASSIGNEMTS ACCEPTED.!
 * 		
 */

public class Project3  
{
	public static void main(String[] args)
	{
		ArrayList<String> customers = new ArrayList<>(Arrays.asList("Bob", "Fred", "Nancy", "Bob"));
		ArrayList<Double> sales = new ArrayList<>(Arrays.asList(10.0, 20.0, 30.0, 25.0));
		System.out.println(nameOfBestCustomers(sales, customers, 5));
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
	
	private static Map<String, ReferencedDouble> buildMap(List<Double> sales, List<String> customers)
	{
		Map<String, ReferencedDouble> customerSales = new HashMap<>();
		for(int i = 0; i < sales.size(); i++)
		{
			ReferencedDouble d = customerSales.get(customers.get(i));
			if(d == null) 
			{
				customerSales.put(customers.get(i), new ReferencedDouble(sales.get(i)));
			}
			else d.value += sales.get(i);	
		}
		return customerSales;
	}
	
	public static ArrayList<String> nameOfBestCustomers(ArrayList<Double> sales, ArrayList<String> names, int topN)
	{
		if(sales.size() != names.size()) throw new IllegalArgumentException();
		Map<String, ReferencedDouble> transactions = buildMap(sales, names);
		PriorityQueue<Entry<String, ReferencedDouble>> queue = buildQueue();
		for(Entry<String, ReferencedDouble> m : transactions.entrySet()) 
		{
			queue.add(m);
		}
		ArrayList<String> results = new ArrayList<>(queue.size());
		for(int i = 0; i < topN; i++) 
		{
			if(queue.peek() != null) 
			{
				results.add(queue.poll().getKey());
			} else break;
		}
		return results;
	}
	
	private static PriorityQueue<Entry<String, ReferencedDouble>> buildQueue()
	{
		return new PriorityQueue<>(
				new Comparator<Entry<String, ReferencedDouble>>()
				{
					public int compare(Entry<String, ReferencedDouble> o1, Entry<String, ReferencedDouble> o2)
					{
						return o2.getValue().compareTo(o1.getValue());
					}
				});		
	}
}
