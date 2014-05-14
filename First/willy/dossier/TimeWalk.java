
//William Berman
//willieberman@gmail.com
package dossier;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**Implementation of a GUI calendar application: the "TimeWalk" calendar software.
 * This class defines the main window, the primary feature of which is the calendar itself.
 * The main program also contains the functions that are used by more than one class.
 * There is one non-nested additional class and six nested classes.
 * They are nested because of their reliance on one or both of the following:
 * main-class variables (usually the timeline), or functions used by more than one of them.
 * @author William Berman */
public class TimeWalk extends JFrame {
	/*TO DO:
	* Double-check documentation
	* UPDATE THE CRITERION A/B/New C FILES
	* Day-of-week display? */
	
	//Initialize
	private static final long serialVersionUID = 3149272651489013896L;
	private JPanel contentPane;
	private static int currentMonth = Calendar.getInstance ().get (Calendar.MONTH); //The initial month (January = 0... December = 11)
	private static int currentYear = Calendar.getInstance ().get (Calendar.YEAR); //The initial year
	private static Date today; //The month/day/year that is today: (constant within one run of the program)
	private static Map<Integer, String> months = new TreeMap<Integer, String> (); //Names for numerical values of months (constant)
	private static Map<Integer, Integer> monthSizesComm = new TreeMap<Integer, Integer> (); //Lengths of months in a common year (constant)
	private static Map<Integer, Integer> monthSizesLeap = new TreeMap<Integer, Integer> (); //Lengths of months in a leap year (constant)
	private static List<Event> events = new ArrayList<Event> (); //List of all Events: the timeline
	private static List<DayButton> dayButtons = new ArrayList<DayButton> (); //List of all day-buttons
	private static Scanner inData; //The path to getting the timeline from a file
	private static PrintStream outData; //The path to saving the timeline into that file

	/**Initializes the constants and creates the main window */
	public static void main (String[] args) {
		
		//Initialize the months to their numerical values
		months.put (0, "January"); months.put (1, "February"); months.put (2, "March"); months.put (3, "April");
		months.put (4, "May"); months.put (5, "June"); months.put (6, "July"); months.put (7, "August");
		months.put (8, "September"); months.put (9, "October"); months.put (10, "November"); months.put (11, "December");
		
		//Initialize the sizes of the months in a common year
		monthSizesComm.put (0, 31); monthSizesComm.put (1, 28); monthSizesComm.put (2, 31); monthSizesComm.put (3, 30);
		monthSizesComm.put (4, 31); monthSizesComm.put (5, 30); monthSizesComm.put (6, 31); monthSizesComm.put (7, 31);
		monthSizesComm.put (8, 30); monthSizesComm.put (9, 31); monthSizesComm.put (10, 30); monthSizesComm.put (11, 31);
		
		//Initialize the sizes of the months in a leap year - February has 29 days
		monthSizesLeap.put (0, 31); monthSizesLeap.put (1, 29); monthSizesLeap.put (2, 31); monthSizesLeap.put (3, 30);
		monthSizesLeap.put (4, 31); monthSizesLeap.put (5, 30); monthSizesLeap.put (6, 31); monthSizesLeap.put (7, 31);
		monthSizesLeap.put (8, 30); monthSizesLeap.put (9, 31); monthSizesLeap.put (10, 30); monthSizesLeap.put (11, 31);
		
		//Activate the input pathway from the timeWalkData.txt file
		try {
			inData = new Scanner (new File ("timeWalkData.txt")); //Try initializing the Scanner to the file for inputting compressed Events from
		} catch (FileNotFoundException fnfe) {  //If the file cannot be found,
			JOptionPane.showMessageDialog (null, "Error: Saved-data file not found."); //say so
			fnfe.printStackTrace (); //show where the error is
			System.exit (0); //stop the program
		}
		
		//Get the saved data from the timeWalkData.txt file
		while (inData.hasNext ()) { //While there are still lines (compressed Events) to be added...
			events.add (new Event (inData.nextLine ())); //add the next one to the timeline, using the compressed-Event constructor
		}
		
		/*Activate the output pathway to the timeWalkData.txt file,
		* after getting the data out of the timeWalkData.txt file, since this targeting erases the file,
		* and if its contents have already been read, then this erasure does not matter */
		try {
			outData = new PrintStream (new BufferedOutputStream(new FileOutputStream(new File("timeWalkData.txt")))); //Try initializing the printer to the file for outputting Events to
		} catch (FileNotFoundException fnfe) {  //If the file cannot be found,
			JOptionPane.showMessageDialog (null, "Error: Saved-data file not found."); //say so
			fnfe.printStackTrace (); //show where the error is
			System.exit (0); //stop the program
		}
		
		//Initialize the current Date
		today = new Date (currentMonth, Calendar.getInstance ().get (Calendar.DAY_OF_MONTH), currentYear);
		
		//Create the main window
		EventQueue.invokeLater (new Runnable () {
			@Override
			public void run () {
				try {
					TimeWalk frame = new TimeWalk ();
					frame.setVisible (true);
				} catch (Exception e) {
					e.printStackTrace ();
				}
			}
		});
	}

	/**Constructor: creates the framework and functionality for the main window */
	public TimeWalk () {
		
		//Set up the main window
		this.setTitle ("TimeWalk v1.0");
		this.setResizable (false);
		this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		this.setBounds (100, 100, 900, 700);
		contentPane = new JPanel ();
		contentPane.setBorder (new EmptyBorder (5, 5, 5, 5));
		setContentPane (contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout ();
		gbl_contentPane.columnWidths = new int[] {884};
		gbl_contentPane.rowHeights = new int[] {30, 600, 30};
		gbl_contentPane.columnWeights = new double[] {0.0};
		gbl_contentPane.rowWeights = new double[] {0.0, 0.0, 0.0};
		contentPane.setLayout (gbl_contentPane);
		
		//Where the day-buttons are (middle panel)
		final JPanel calendarPnl = new JPanel ();
		GridBagConstraints gbc_calendarPnl = new GridBagConstraints ();
		gbc_calendarPnl.fill = GridBagConstraints.BOTH;
		gbc_calendarPnl.insets = new Insets (0, 0, 5, 0);
		gbc_calendarPnl.gridx = 0;
		gbc_calendarPnl.gridy = 1;
		contentPane.add (calendarPnl, gbc_calendarPnl);
		calendarPnl.setLayout (new GridLayout (5, 7, 0, 0));
		makeDayButtons (currentMonth, currentYear, calendarPnl); //Make the initial month/year of day-buttons
		
		//Where the navigation buttons are (top panel)
		JPanel topPnl = new JPanel ();
		GridBagConstraints gbc_topPnl = new GridBagConstraints ();
		gbc_topPnl.fill = GridBagConstraints.BOTH;
		gbc_topPnl.insets = new Insets (0, 0, 5, 0);
		gbc_topPnl.gridx = 0;
		gbc_topPnl.gridy = 0;
		contentPane.add (topPnl, gbc_topPnl);
		GridBagLayout gbl_topPnl = new GridBagLayout ();
		gbl_topPnl.columnWidths = new int[] {130, 240, 140, 240, 130};
		gbl_topPnl.rowHeights = new int[] {29};
		gbl_topPnl.columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_topPnl.rowWeights = new double[] {0.0};
		topPnl.setLayout (gbl_topPnl);
		
		//The label that shows what month/year the calendar is displaying
		final JLabel currentLbl = new JLabel (months.get (currentMonth) + " " + currentYear);
		currentLbl.setFont (new Font ("Tahoma", Font.PLAIN, 11));
		currentLbl.setHorizontalAlignment (SwingConstants.CENTER);
		GridBagConstraints gbc_current = new GridBagConstraints ();
		gbc_current.fill = GridBagConstraints.BOTH;
		gbc_current.insets = new Insets (0, 0, 0, 5);
		gbc_current.gridx = 2;
		gbc_current.gridy = 0;
		topPnl.add (currentLbl, gbc_current);
		
		//The button that goes to the previous year in the calendar
		JButton prevYearBtn = new JButton ("Previous year");
		prevYearBtn.setFont (new Font ("Tahoma", Font.PLAIN, 11));
		prevYearBtn.addActionListener (new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent arg0) { /*Go to the previous year, update the label, and update the day-buttons
			* when the previous-year button is pressed */
				currentYear--;
				currentLbl.setText (months.get (currentMonth) + " " + currentYear); //Update the current-date label
				calendarPnl.removeAll (); //Wipe the calendar panel's current day-buttons
				makeDayButtons (currentMonth, currentYear, calendarPnl); //Make the new month/year of day-buttons
			}
		});
		GridBagConstraints gbc_prevYearBtn = new GridBagConstraints ();
		gbc_prevYearBtn.fill = GridBagConstraints.BOTH;
		gbc_prevYearBtn.insets = new Insets (0, 0, 0, 5);
		gbc_prevYearBtn.gridx = 0;
		gbc_prevYearBtn.gridy = 0;
		topPnl.add (prevYearBtn, gbc_prevYearBtn);
		
		//The button that goes to the previous month in the calendar
		JButton prevMonthBtn = new JButton ("Previous month");
		prevMonthBtn.setFont (new Font ("Tahoma", Font.PLAIN, 11));
		prevMonthBtn.addActionListener (new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent arg0) { /*Go to the previous month, update the label, and update the day-buttons
			* when the previous-month button is pressed */
				if (currentMonth == 0) { //If it is going to the month before January,
					currentMonth = 11; //make it go to December
					currentYear--; //of the previous year.
				} else currentMonth--; //Otherwise, send it to the previous month
				currentLbl.setText (months.get (currentMonth) + " " + currentYear); //Update the current-date label
				calendarPnl.removeAll (); //Wipe the calendar panel's current day-buttons
				makeDayButtons (currentMonth, currentYear, calendarPnl); //Make the new month/year of day-buttons
			}
		});
		GridBagConstraints gbc_prevMonthBtn = new GridBagConstraints ();
		gbc_prevMonthBtn.fill = GridBagConstraints.BOTH;
		gbc_prevMonthBtn.insets = new Insets (0, 0, 0, 5);
		gbc_prevMonthBtn.gridx = 1;
		gbc_prevMonthBtn.gridy = 0;
		topPnl.add (prevMonthBtn, gbc_prevMonthBtn);
		
		//The button that goes to the next month in the calendar
		JButton nextMonthBtn = new JButton ("Next month");
		nextMonthBtn.setFont (new Font ("Tahoma", Font.PLAIN, 11));
		nextMonthBtn.addActionListener (new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent arg0) { /*Go to the next month, update the label, and update the day-buttons
			* when the next-month button is pressed */
				if (currentMonth == 11) { //If it is going to the month after December,
					currentMonth = 0; //make it go to January
					currentYear++; //of the next year.
				} else currentMonth++; //Otherwise, send it to the next month
				currentLbl.setText (months.get (currentMonth) + " " + currentYear); //Update the current-date label
				calendarPnl.removeAll (); //Wipe the calendar panel's current day-buttons
				makeDayButtons (currentMonth, currentYear, calendarPnl); //Make the new month/year of day-buttons
			}
		});
		GridBagConstraints gbc_nextMonthBtn = new GridBagConstraints ();
		gbc_nextMonthBtn.fill = GridBagConstraints.BOTH;
		gbc_nextMonthBtn.insets = new Insets (0, 0, 0, 5);
		gbc_nextMonthBtn.gridx = 3;
		gbc_nextMonthBtn.gridy = 0;
		topPnl.add (nextMonthBtn, gbc_nextMonthBtn);
		
		//The button that goes to the next year in the calendar
		JButton nextYearBtn = new JButton ("Next year");
		nextYearBtn.setFont (new Font ("Tahoma", Font.PLAIN, 11));
		nextYearBtn.addActionListener (new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent arg0) { /*Go to the next year, update the label, and update the day-buttons
			* when the next-year button is pressed */
				currentYear++;
				currentLbl.setText (months.get (currentMonth) + " " + currentYear); //Update the current-date label
				calendarPnl.removeAll (); //Wipe the calendar panel's current day-buttons
				makeDayButtons (currentMonth, currentYear, calendarPnl); //Make the new month/year of day-buttons
			}
		});
		GridBagConstraints gbc_nextYearBtn = new GridBagConstraints ();
		gbc_nextYearBtn.fill = GridBagConstraints.BOTH;
		gbc_nextYearBtn.gridx = 4;
		gbc_nextYearBtn.gridy = 0;
		topPnl.add (nextYearBtn, gbc_nextYearBtn);
		
		//Where the other useful buttons are (bottom panel)
		JPanel bottomPnl = new JPanel ();
		GridBagConstraints gbc_bottomPnl = new GridBagConstraints ();
		gbc_bottomPnl.fill = GridBagConstraints.BOTH;
		gbc_bottomPnl.gridx = 0;
		gbc_bottomPnl.gridy = 2;
		contentPane.add (bottomPnl, gbc_bottomPnl);
		bottomPnl.setLayout (new GridLayout (1, 2, 0, 0));
		
		//The button that adds an Event (creates an adder window)
		JButton addEventBtn = new JButton ("Add New Event");
		addEventBtn.setFont (new Font ("Tahoma", Font.PLAIN, 11));
		addEventBtn.addActionListener (new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent arg0) { //Create an adder window when the add-event button is pressed
				EventQueue.invokeLater (new Runnable () {
					@Override
					public void run () {
						try {
							AdderWindow aw = new AdderWindow ();
							aw.setVisible (true);
						} catch (Exception e) {
							e.printStackTrace ();
						}
					}
				});
			}
		});
		bottomPnl.add (addEventBtn);
		
		//The button that can delete all Events (can clear the timeline)
		JButton deleteAllBtn = new JButton ("Delete All Events");
		deleteAllBtn.setFont (new Font ("Tahoma", Font.PLAIN, 11));
		deleteAllBtn.addActionListener (new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent arg0) { //Delete the contents of the timeline when the delete-all button is pressed (and confirmed)
				if (JOptionPane.showConfirmDialog (null, "Do you want to clear the timeline?", "Delete All Events", JOptionPane.YES_NO_OPTION)
				== JOptionPane.YES_OPTION) { //If the user presses "yes" in the JOptionPane.
					events.clear (); //clear the timeline
					updateDayButtons (); //update the day-buttons, now that they have no Events
					saveData (); //send the new timeline to the timeWalkData.txt file
				}
				//(If the user presses "no" in the JOptionPane, close the JOptionPane without deleting any Events)
			}
		});
		bottomPnl.add (deleteAllBtn);
		
		//Create the reminder window
		EventQueue.invokeLater (new Runnable () {
			@Override
			public void run () {
				try {
					ReminderWindow rw = new ReminderWindow (today);
					rw.setVisible (true);
				} catch (Exception e) {
					e.printStackTrace ();
				}
			}
		});
	}
	
	/**Checks if a number (representing a year) is a leap year or not.
	 * Used by both the main window and the adder window's day-input field.
	 * Credit goes to Wikipedia for its pseudocode - http://en.wikipedia.org/wiki/Leap_year#Algorithm
	 * @param inYear
	 * The year to be tested
	 * @return
	 * Whether that year is a leap year or not */
	public boolean isLeapYear (int yearIn) {
		if (yearIn % 4 != 0) return false; //If it is not divisible by 4, then it is not a leap year
		else if (yearIn % 100 != 0) return true; //Otherwise, if it is not divisible by 100, then it is a leap year
		else if (yearIn % 400 == 0) return true; //Otherwise, if it is divisible by 400, then it is a leap year
		else return false; //Otherwise, it is not a leap year
	}
	
	/**Converts a month's name to the number that that month is (January = 0... December = 11).
	 * This function gets the key from the "months" map when that key's value is inputted, like the months.get (key) function in reverse.
	 * Used by both the adder window's day-input field and the day-button's search-for-events function.
	 * @param monthIn
	 * The month (name) to be converted
	 * @return
	 * The number that that month is */
	public int monthNameToNum (String monthIn) {
		for (int x = 0; x < 12; x++) { //For each month (by number)...
			if (monthIn.equals (months.get (x))) return x; /*if that month's name is the same as the inputted month,
			* then that month's number is the inputted month's number, and it returns that number */
		}
		return -1; //It never gets here (if the inputted month was a valid month, which it should always be)
	}
	
	/**Constructs a month/year of day-buttons (the exact amount is based on the inputted month/year),
	 * adds them to the specified panel, and adds them to the list of day-buttons (in order to access them for updating).
	 * @param targetMonth
	 * The month that the day-buttons are being constructed for
	 * @param targetYear
	 * The year that the day-buttons are being constructed for
	 * @param targetPanel
	 * The panel to put the day-buttons into */
	public void makeDayButtons (int targetMonth, int targetYear, JPanel targetPanel) {
		dayButtons = new ArrayList<DayButton> (); //Clear what is currently in the day-button list
		//Find the last day in the specified month/year
		int maxInMonth;
		if (isLeapYear (targetYear)) maxInMonth = monthSizesLeap.get (targetMonth);
		else maxInMonth = monthSizesComm.get (targetMonth);
		//Make and add the day-buttons
		for (int x = 1; x <= 35; x++) { //For each day in the maximum possible number of days in any month/year...
			if (x <= maxInMonth) { //if it's a valid day in this month/year,
				DayButton DBNew = new DayButton (new Date (targetMonth, x, targetYear)); //make a day-button for that Date
				targetPanel.add (DBNew); //add that day-button to the panel, overwriting what was below it
				dayButtons.add (DBNew); //and to the day-button list
			} else targetPanel.add (new JLabel ()); //if it isn't a valid day in this month/year, put a functionless label there instead of a JButton, overwriting what was below it
		}
	}
	
	/**Updates the current month/year of day-buttons:
	 * makes them all scan for Events that they recently acquired, and makes them update their Strings afterwards.
	 * Used by anything that changes the timeline, but not the month/year (Navigation buttons do not use this)
	 * (The adder window, the delete-all button, and the delete-event button do use this)
	 * (Day-buttons automatically do this when initialized, so this is needed to update them if they already exist) */
	public void updateDayButtons () {
		for (DayButton DBIn : dayButtons) { //For each day-button in the current list of day-buttons...
			DBIn.scan (); //have it scan (have it look for Events that it should contain, then put them into its String)
		}
	}
	
	/**Finds all of the Events that take place on a certain Date.
	 * Used by both the day-button's name creation and the reminder window's getting of Events
	 * @param dateTarget
	 * The Date to look for
	 * @return
	 * The list of Events that take place on that Date */
	public ArrayList<Event> collectEventsFrom (Date dateTarget) {
		ArrayList<Event> eventsOut = new ArrayList<Event> ();
		for (Event eventIn : events) { //For each Event on the timeline...
			if (new Date (monthNameToNum (eventIn.getMonth ()), eventIn.getDay (), eventIn.getYear ()).equals (dateTarget))
				eventsOut.add (eventIn); //put it into this day-button's list if it's on the same Date
		}
		return eventsOut;
	}
	
	/**Adds an regularly occurring series of <br> pieces to a String, adds <html> to the start, and adds </html> to the end,
	 * therefore making the description wrappable, so that it doesn't distort the Object that it's in
	 * @param stringIn
	 * The String to break
	 * @param pieceLength
	 * How long each piece should be, how many chars can fit across the Object that the String is going to
	 * @return
	 * A version of the String with breaks in it */
	public String breakString (String stringIn, int pieceLength) {
		int stringLength = stringIn.length (); //Get how long the String is
		for (int x = 0; x < stringLength; x++) { //For each character in the String...
			if ((x % pieceLength == 0) && (x != 0)) { //if it isn't the first one and it's a multiple of pieceLength,
				stringIn = stringIn.substring (0, x) + "<br>" + stringIn.substring (x); //put in a break at that index
				stringLength += 4; //compensate for the <br>
			}
		}
		return stringIn;
	}
	
	/**Saves the current timeline to the timeWalkData.txt file,
	 * by overwriting the save that's currently in the file,
	 * and printing each Event currently on the timeline in its place in the file */
	public void saveData () {
		//Clear the previously saved data
		try {
			PrintWriter eraser = new PrintWriter ("timeWalkData.txt"); //Try initializing a printer to clear the data,
			eraser.print (""); //clearing the data,
			eraser.close (); //and closing the printer that cleared the data
		} catch (FileNotFoundException fnfe) { //If the file cannot be found,
			JOptionPane.showMessageDialog (null, "Error: Saved-data file not found."); //say so
			fnfe.printStackTrace (); //show where the error is
			System.exit (0); //stop the program
		}
		//Save the current data
		for (Event eventOut : events) { //For each Event on the timeline...
			outData.println (eventOut.toString ()); //print its compressed form to the file
		}
	}
	
	/**Nested class: the adder window,
	 * a window with the capability to add an Event to the timeline.
	 * Generated by the add-event button in the main window.
	 * @author William Berman */
	private class AdderWindow extends JFrame {

		//Initialize
		private static final long serialVersionUID = 5447330888219487528L;
		private JPanel AWContentPane;
		private String AWName, AWMonth, AWDescription; //The components of an Event to add that are Strings
		private int AWYear, AWDay, AWStart, AWEnd, AWPriority; //The components of an Event to add that are ints
		private boolean[] addOK = {false, false, false, false, false, false, false, false}; //Whether each component is ready for adding

		/**Constructor: creates the framework and functionality for an adder window */
		public AdderWindow () {
			
			//Set up the adder window
			this.setResizable (false);
			this.setTitle ("Add Event (ENTER finalizes fields)"); //Rudimentary instructions
			this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
			this.setBounds (150, 150, 325, 350);
			AWContentPane = new JPanel ();
			AWContentPane.setBorder (new EmptyBorder (5, 5, 5, 5));
			setContentPane (AWContentPane);
			GridBagLayout gbl_AWContentPane = new GridBagLayout ();
			gbl_AWContentPane.columnWidths = new int[] {309};
			gbl_AWContentPane.rowHeights = new int[] {40, 30, 30, 182, 30};
			gbl_AWContentPane.columnWeights = new double[] {0.0};
			gbl_AWContentPane.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0};
			AWContentPane.setLayout (gbl_AWContentPane);
			
			//The field that receives the name of the Event
			final JTextField AWNameFld = new JTextField ();
			AWNameFld.setFont (new Font ("Tahoma", Font.PLAIN, 11));
			AWNameFld.addActionListener (new ActionListener () {
				@Override
				public void actionPerformed (ActionEvent arg0) { //Use the value currently in the field when ENTER is pressed (a String can have any character)
					AWName = AWNameFld.getText (); //Use the current value for the name when ENTER is pressed
					AWNameFld.setBackground (new Color (0, 255, 0)); //Indicate no problem with a green field
					addOK[0] = true; //Set the "name" part of the OK-to-add boolean[] to true
				}
			});
			AWNameFld.setText ("Name");
			AWNameFld.setHorizontalAlignment (SwingConstants.CENTER);
			GridBagConstraints gbc_AWNameFld = new GridBagConstraints ();
			gbc_AWNameFld.fill = GridBagConstraints.BOTH;
			gbc_AWNameFld.insets = new Insets (0, 0, 5, 0);
			gbc_AWNameFld.gridx = 0;
			gbc_AWNameFld.gridy = 0;
			AWContentPane.add (AWNameFld, gbc_AWNameFld);
			AWNameFld.setColumns (10);
			
			//Where the year, month, and day fields are
			JPanel AWDatePnl = new JPanel ();
			GridBagConstraints gbc_AWDatePnl = new GridBagConstraints ();
			gbc_AWDatePnl.fill = GridBagConstraints.BOTH;
			gbc_AWDatePnl.insets = new Insets (0, 0, 5, 0);
			gbc_AWDatePnl.gridx = 0;
			gbc_AWDatePnl.gridy = 1;
			AWContentPane.add (AWDatePnl, gbc_AWDatePnl);
			AWDatePnl.setLayout (new GridLayout (1, 3, 0, 0));
			
			//The field that receives the year that the Event takes place in
			final JTextField AWYearFld = new JTextField ();
			AWYearFld.setFont (new Font ("Tahoma", Font.PLAIN, 11));
			AWYearFld.addActionListener (new ActionListener () {
				@Override
				public void actionPerformed (ActionEvent arg0) { //Try the value currently in the field when ENTER is pressed
					try {
						AWYear = Integer.parseInt (AWYearFld.getText ()); //Try using the current value for the year when ENTER is pressed
						AWYearFld.setBackground (new Color (0, 255, 0)); //Indicate no problem with a green field
						addOK[1] = true; //set the "year" part of the OK-to-add boolean[] to true
					} catch (Exception error) {
						AWYearFld.setBackground (new Color (255, 0, 0)); //Indicate an error with a red field
						addOK[1] = false; //set the "year" part of the OK-to-add boolean[] to false
					}
				}
			});
			AWYearFld.setText ("Year");
			AWYearFld.setHorizontalAlignment (SwingConstants.CENTER);
			AWDatePnl.add (AWYearFld);
			AWYearFld.setColumns (10);
			
			//The field that receives the month that the Event takes place in
			final JTextField AWMonthFld = new JTextField ();
			AWMonthFld.setFont (new Font ("Tahoma", Font.PLAIN, 11));
			AWMonthFld.addActionListener (new ActionListener () {
				@Override
				public void actionPerformed (ActionEvent arg0) { //Try the value currently in the field when ENTER is pressed
					try {
						AWMonth = AWMonthFld.getText (); //Try using the current value for the month when ENTER is pressed
						AWMonthFld.setBackground (new Color (0, 255, 0)); //Indicate no problem (with the input itself) with a green field
						addOK[2] = true; //Set the "month" part of the OK-to-add boolean[] to true
						//Format the inputted month in the way that the month-number map is formatted
						AWMonth = AWMonth.substring (0, 1).toUpperCase () + AWMonth.substring (1).toLowerCase ();
						if (!months.values ().contains (AWMonth)) { //If the month does not exist...
							AWMonthFld.setBackground (new Color (255, 0, 0)); //red field
							addOK[2] = false; //set the "month" part of the OK-to-add boolean[] to false
						}
					} catch (Exception error) {
						AWMonthFld.setBackground (new Color (255, 0, 0)); //Indicate an error with a red field
						addOK[2] = false; //Set the "month" part of the OK-to-add boolean[] to false
					}
				}
			});
			AWMonthFld.setText ("Month (name)"); //Use the name of the month, not an affiliated number
			AWMonthFld.setHorizontalAlignment (SwingConstants.CENTER);
			AWDatePnl.add (AWMonthFld);
			AWMonthFld.setColumns (10);
			
			//The field that receives the day that the Event takes place in
			final JTextField AWDayFld = new JTextField ();
			AWDayFld.setFont (new Font ("Tahoma", Font.PLAIN, 11));
			AWDayFld.addActionListener (new ActionListener () {
				@Override
				public void actionPerformed (ActionEvent arg0) { //Try the value currently in the field when ENTER is pressed
					//Get the new value
					try {
						//Check if the input is an int
						AWDay = Integer.parseInt (AWDayFld.getText ()); //Try using the current value for the day when ENTER is pressed
						AWDayFld.setBackground (new Color (0, 255, 0)); //Indicate no problem with a green field
						addOK[3] = true; //Set the "day" part of the OK-to-add boolean[] to true
						//Check if the input is a valid day in the current year
						if (isLeapYear (AWYear)) { //(If it is a leap year, do the following with month sizes fit for a leap year.)
							if ((AWDay < 1) || (AWDay > monthSizesLeap.get (monthNameToNum (AWMonth)))) { //If this day does not exist in the current month...
								AWDayFld.setBackground (new Color (255, 0, 0)); //red field
								addOK[3] = false; //set the "day" part of the OK-to-add boolean[] to false
							}
						} else { //(Otherwise, if it is a common year, do the following with month sizes fit for a common year.)
							if ((AWDay < 1) || (AWDay > monthSizesComm.get (monthNameToNum (AWMonth)))) { //If this day does not exist in the current month...
								AWDayFld.setBackground (new Color (255, 0, 0)); //red field
								addOK[3] = false; //set the "day" part of the OK-to-add boolean[] to false
							}
						}
					} catch (Exception error) {
						AWDayFld.setBackground (new Color (255, 0, 0)); //Indicate an error with a red field
						addOK[3] = false; //Set the "day" part of the OK-to-add boolean[] to false
					}
				}
			});
			AWDayFld.setText ("Day");
			AWDayFld.setHorizontalAlignment (SwingConstants.CENTER);
			AWDatePnl.add (AWDayFld);
			AWDayFld.setColumns (10);
			
			//Where the start-time and end-time fields are
			JPanel AWTimePnl = new JPanel ();
			GridBagConstraints gbc_AWTimePnl = new GridBagConstraints ();
			gbc_AWTimePnl.fill = GridBagConstraints.BOTH;
			gbc_AWTimePnl.insets = new Insets (0, 0, 5, 0);
			gbc_AWTimePnl.gridx = 0;
			gbc_AWTimePnl.gridy = 2;
			AWContentPane.add (AWTimePnl, gbc_AWTimePnl);
			AWTimePnl.setLayout (new GridLayout (1, 2, 0, 0));
			
			//The field that receives the time that the Event starts at
			final JTextField AWStartFld = new JTextField ();
			AWStartFld.setFont (new Font ("Tahoma", Font.PLAIN, 11));
			AWStartFld.addActionListener (new ActionListener () {
				@Override
				public void actionPerformed (ActionEvent arg0) { //Try the value currently in the field when ENTER is pressed
					try {
						AWStart = Integer.parseInt (AWStartFld.getText ()); //Try using the current value for the start when ENTER is pressed
						AWStartFld.setBackground (new Color (0, 255, 0)); //Indicate no problem with a green field
						addOK[4] = true; //set the "start time" part of the OK-to-add boolean[] to true
						if (!isValidTime (AWStart)) { //If this starting time does not exist...
							AWStartFld.setBackground (new Color (255, 0, 0)); //red field
							addOK[4] = false; //set the "start time" part of the OK-to-add boolean[] to false
						}
					} catch (Exception error) {
						AWStartFld.setBackground (new Color (255, 0, 0)); //Indicate an error with a red field
						addOK[4] = false; //Set the "start time" part of the OK-to-add boolean[] to false
					}
				}
			});
			AWStartFld.setText ("Starting time (24-hr.)"); //Military time
			AWStartFld.setHorizontalAlignment (SwingConstants.CENTER);
			AWStartFld.setColumns (10);
			AWTimePnl.add (AWStartFld);
			
			//The field that receives the time that the Event ends at
			final JTextField AWEndFld = new JTextField ();
			AWEndFld.setFont (new Font ("Tahoma", Font.PLAIN, 11));
			AWEndFld.addActionListener (new ActionListener () {
				@Override
				public void actionPerformed (ActionEvent arg0) { //Try the value currently in the field when ENTER is pressed
					try {
						AWEnd = Integer.parseInt (AWEndFld.getText ()); //Try using the current value for the end when ENTER is pressed
						AWEndFld.setBackground (new Color (0, 255, 0)); //Indicate no problem with a green field
						addOK[5] = true; //set the "end time" part of the OK-to-add boolean[] to true
						if (!isValidTime (AWEnd)) { //If this ending time does not exist...
							AWEndFld.setBackground (new Color (255, 0, 0)); //red field
							addOK[5] = false; //set the "end time" part of the OK-to-add boolean[] to false
						}
					} catch (Exception error) {
						AWEndFld.setBackground (new Color (255, 0, 0)); //Indicate an error with a red field
						addOK[5] = false; //Set the "end time" part of the OK-to-add boolean[] to false
					}
				}
			});
			AWEndFld.setText ("Ending time (24-hr.)"); //Military time
			AWEndFld.setHorizontalAlignment (SwingConstants.CENTER);
			AWEndFld.setColumns (10);
			AWTimePnl.add (AWEndFld);
			
			//The field that receives the description of the Event
			final JTextArea AWDescriptionArea = new JTextArea ();
			AWDescriptionArea.addKeyListener (new KeyAdapter () {
				@Override
				public void keyReleased (KeyEvent arg0) { /*Use the value currently in the field when a keystroke is finished
				* (a String can have any character) (ENTER will both go to the next line and do this) */
					AWDescription = AWDescriptionArea.getText (); //Use the current value for the description when a keystroke is finished
					AWDescriptionArea.setBackground (new Color (0, 255, 0)); //Indicate no problem with a green field
					addOK[6] = true; //Set the "description" part of the OK-to-add boolean[] to true
				}
			});
			AWDescriptionArea.setFont (new Font ("Tahoma", Font.PLAIN, 11));
			AWDescriptionArea.setTabSize (4);
			AWDescriptionArea.setLineWrap (true);
			AWDescriptionArea.setText ("Description");
			GridBagConstraints gbc_AWDescriptionArea = new GridBagConstraints ();
			gbc_AWDescriptionArea.fill = GridBagConstraints.BOTH;
			gbc_AWDescriptionArea.insets = new Insets (0, 0, 5, 0);
			gbc_AWDescriptionArea.gridx = 0;
			gbc_AWDescriptionArea.gridy = 3;
			AWContentPane.add (AWDescriptionArea, gbc_AWDescriptionArea);
			
			//Where the priority field, cancel button, and add button are
			JPanel AWPriorityButtonPnl = new JPanel ();
			GridBagConstraints gbc_AWPriorityButtonPnl = new GridBagConstraints ();
			gbc_AWPriorityButtonPnl.fill = GridBagConstraints.BOTH;
			gbc_AWPriorityButtonPnl.gridx = 0;
			gbc_AWPriorityButtonPnl.gridy = 4;
			AWContentPane.add (AWPriorityButtonPnl, gbc_AWPriorityButtonPnl);
			AWPriorityButtonPnl.setLayout (new GridLayout (1, 3, 0, 0));
			
			//The field that receives the priority (1 = highest, 5 = least) of the Event
			final JTextField AWPriorityFld = new JTextField ();
			AWPriorityFld.setFont (new Font ("Tahoma", Font.PLAIN, 11));
			AWPriorityFld.addActionListener (new ActionListener () {
				@Override
				public void actionPerformed (ActionEvent arg0) { //Try the value currently in the field when ENTER is pressed
					try {
						AWPriority = Integer.parseInt (AWPriorityFld.getText ()); //Try using the current value for the name when ENTER is pressed
						AWPriorityFld.setBackground (new Color (0, 255, 0)); //Indicate no problem with a green field
						addOK[7] = true; //Set the "priority" part of the OK-to-add boolean[] to true
						if ((AWPriority < 1) || (AWPriority > 5)) { //If this priority does not exist...
							AWPriorityFld.setBackground (new Color (255, 0, 0)); //red field
							addOK[7] = false; //set the "priority" part of the OK-to-add boolean[] to false
						}
					} catch (Exception error) {
						AWPriorityFld.setBackground (new Color (255, 0, 0)); //Indicate an error with a red field
						addOK[7] = false; //Set the "priority" part of the OK-to-add boolean[] to false
					}
				}
			});
			AWPriorityFld.setText ("Priority (1-5)");
			AWPriorityFld.setHorizontalAlignment (SwingConstants.CENTER);
			AWPriorityFld.setColumns (10);
			AWPriorityButtonPnl.add (AWPriorityFld);
			
			//The button that can cancel this adder window
			JButton AWCancelBtn = new JButton ("Cancel");
			AWCancelBtn.addActionListener (new ActionListener () {
				@Override
				public void actionPerformed (ActionEvent arg0) { //Stop this Event from being made when the cancel button is pressed
					dispose ();
				}
			});
			AWCancelBtn.setFont (new Font ("Tahoma", Font.PLAIN, 11));
			AWPriorityButtonPnl.add (AWCancelBtn);
			
			//The button that adds the Event to the timeline
			JButton AWAddBtn = new JButton ("Add Event");
			AWAddBtn.addActionListener (new ActionListener () {
				@Override
				public void actionPerformed (ActionEvent arg0) { /*Try adding the Event to the timeline when the add-button is pressed
				* (add the Event if all of the fields are OK, but do not add it (and tell the user) if one or more fields are invalid) */
					if (isAllOK (addOK)) { //If all of the fields are OK...
						events.add (new Event (AWName, AWMonth, AWDay, AWYear, AWStart, AWEnd, AWDescription, AWPriority)); //add the Event
						updateDayButtons (); //have the day-buttons scan for new Events and add them to their Strings
						saveData (); //send the new timeline to the timeWalkData.txt file
						dispose (); //kill the adder window
					}
				}
			});
			AWAddBtn.setFont (new Font ("Tahoma", Font.PLAIN, 11));
			AWPriorityButtonPnl.add (AWAddBtn);
		}
		
		/**Checks if a number (representing a military time) is a valid military time.
		 * This method checks if the input is out of the range of 0 and 2359, inclusive: if it is out of that range, then it is invalid.
		 * Also checks if the tens digit of the minutes is 6 or greater (only 60 minutes in an hour): if it above that amount, then it is invalid.
		 * @param inTime
		 * The (military) time to be tested
		 * @return
		 * Whether that time is valid or not */
		public boolean isValidTime (int timeIn) {
			if ((timeIn < 0) || (timeIn > 2359) || (((timeIn / 10) % 10) >= 6)) return false;
			else return true;
		}
		
		/**Checks if the adder window has all of the parameters inputted
		 * @param OKs
		 * The addOK boolean[] of the adder window
		 * @return
		 * Whether all of them are true:
		 * if at least one of them is not true, it will return false for that one, and therefore stop.
		 * If none of them are false, it will return true at the end. */
		public boolean isAllOK (boolean[] OKs) {
			for (int x = 0; x < OKs.length; x++) { //For each parameter...
				if (!OKs[x]) { //if it is not inputted properly,
					JOptionPane.showMessageDialog (null, "Invalid entry."); //tell the user that,
					return false; //and prevent the adder window from adding the Event
				}
			}
			return true; //Allow the adder window to add the Event if the entire thing is inputted properly
		}
	}
	
	/**Nested class: a form of JButton with special capabilities that goes onto the calendar in the middle of the main window.
	 * Each day-button has a Date that it represents and that it uses to find Events that occur on its day,
	 * and a way of displaying the Events for its day on itself.
	 * @author William Berman */
	private class DayButton extends JButton {
		
		//Initialize
		private static final long serialVersionUID = 8490198265443181877L;
		private Date DBDate; //The Date that this day-button represents
		private List<Event> DBEvents = new ArrayList<Event> (); //The Events that occur on this Date
		private String DBOut; //The String to put on the day-button
			
		/**Constructor: input the Date that it represents, and it will retrieve its Events, make its String,
		 * check if it is today, and give itself functionality */
		public DayButton (Date DBDateIn) {
			
			//Set up the day-button
			this.setFont (new Font ("Tahoma", Font.PLAIN, 11));
			this.DBDate = new Date (DBDateIn.getMonth (), DBDateIn.getDay (), DBDateIn.getYear ()); //Initialize the Date
			this.scan (); //Get all of the Events for this Date, initialize the String, and add the Events to the String
			if (this.DBDate.equals (today)) this.setBorder (new LineBorder (Color.BLACK, 3)); //Check if this day-button is today: if it is, give it a border
			
			this.addActionListener (new ActionListener () {
				@Override
				public void actionPerformed (ActionEvent arg0) { //Make a day-button window for this day-button when it is clicked
					EventQueue.invokeLater (new Runnable () {
						@Override
						public void run () {
							try {
								DayButtonWindow dbw = new DayButtonWindow (DBDate, DBEvents);
								dbw.setVisible (true);
							} catch (Exception e) {
								e.printStackTrace ();
							}
						}
					});
				}
			});
		}
		
		/**Scans the timeline for Events that occur on the Date that this day-button represents, and updates itself accordingly */
		public void scan () {
			this.DBEvents = collectEventsFrom (this.DBDate); //Put all of the Events from this day-button's Date into its list
			this.updateString (); //Update the String for the day-button, just in case it found a new Event
		}
		
		/**Changes the String of this day-button, based on what Events are in the day-button and what Date the day-button represents */
		public void updateString () {
			this.DBOut = "<html>" + this.DBDate.toString (); //The start of the HTML statement that allows line breaks, plus the default
			for (Event eventIn : this.DBEvents) { //For each Event in this day-button...
				this.DBOut += ("<br>*" + breakString (eventIn.getName (), 12)); /*update this day-button's text, adding a new line with the new addition,
				* giving the start of this name a marking (*), and wrapping this individual name if it's too long */
			}
			this.DBOut += "</html>"; //End the HTML statement that allows line breaks in the day-button's text
			this.setText (this.DBOut);
		}
	}
	
	/**Nested class: a panel that displays the information of an Event, and that has a button to delete that Event from the timeline.
	 * It is put into day-button windows and reminder windows.
	 * @author William Berman */
	private class EventPanel extends JPanel {

		private static final long serialVersionUID = -6386406264157579738L;

		/**Constructor: input the Event that it should contain,
		 * and it creates the framework and functionality for an event-panel */
		public EventPanel (final Event EPEventIn) {
			
			//Set up the event-panel
			this.setBorder (new LineBorder (Color.BLACK, 3));
			this.setLayout (new BorderLayout (0, 0));
			
			//Where all of the information-containing panels are
			JPanel EPInfoPnl = new JPanel ();
			this.add (EPInfoPnl, BorderLayout.CENTER);
			EPInfoPnl.setLayout (new BorderLayout (0, 0));
			
			//Where the information-containing panels are that are not the description are
			JPanel EPNotDescriptPnl = new JPanel ();
			EPInfoPnl.add (EPNotDescriptPnl, BorderLayout.NORTH);
			EPNotDescriptPnl.setLayout (new GridLayout (2, 2, 0, 0));
			
			//Where the name of the Event is
			final JLabel EPNameLbl = new JLabel ("<html>" + breakString (EPEventIn.getName (), 20) + "</html>"); //Make the name wrap if it's too long
			EPNameLbl.setFont (new Font ("Tahoma", Font.PLAIN, 11));
			EPNameLbl.setHorizontalAlignment (SwingConstants.LEFT);
			EPNotDescriptPnl.add (EPNameLbl);
			
			//Where the Date that the Event takes place on is
			final JLabel EPDateLbl = new JLabel (EPEventIn.getDay () + " " + EPEventIn.getMonth () + " " + EPEventIn.getYear ());
			EPDateLbl.setFont (new Font ("Tahoma", Font.PLAIN, 11));
			EPDateLbl.setHorizontalAlignment (SwingConstants.RIGHT);
			EPNotDescriptPnl.add (EPDateLbl);
			
			//Where the priority of the Event is
			final JLabel EPPriorityLbl = new JLabel ("Priority " + EPEventIn.getPriority ());
			EPPriorityLbl.setFont (new Font ("Tahoma", Font.PLAIN, 11));
			EPPriorityLbl.setHorizontalAlignment (SwingConstants.LEFT);
			EPNotDescriptPnl.add (EPPriorityLbl);
			
			//Where the time that the Event takes place at is
			final JLabel EPTimeLbl = new JLabel ("From " + EPEventIn.getStart () + " to " + EPEventIn.getEnd ());
			EPTimeLbl.setFont (new Font ("Tahoma", Font.PLAIN, 11));
			EPTimeLbl.setHorizontalAlignment (SwingConstants.RIGHT);
			EPNotDescriptPnl.add (EPTimeLbl);
			
			//Where the description of the Event is
			final JLabel EPDescriptionLbl = new JLabel ("<html>" + breakString (EPEventIn.getDescription (), 45) + "</html>"); //Make the description wrap if it's too long
			EPDescriptionLbl.setVerticalAlignment (SwingConstants.TOP);
			EPDescriptionLbl.setFont (new Font ("Tahoma", Font.PLAIN, 11));
			EPDescriptionLbl.setHorizontalAlignment (SwingConstants.LEFT);
			EPInfoPnl.add (EPDescriptionLbl, BorderLayout.CENTER);
			
			//The button that deletes this specific Event
			JButton EPDeleteEventBtn = new JButton ("Delete Event");
			EPDeleteEventBtn.setFont (new Font ("Tahoma", Font.PLAIN, 11));
			EPDeleteEventBtn.addActionListener (new ActionListener () {
				@Override
				public void actionPerformed (ActionEvent arg0) { //Delete the Event from the timeline, and clear this panel, when this button is pressed
					int indexToDelete = 0;
					for (int x = 0; x < events.size (); x++) { //For each Event on the timeline...
						if (EPEventIn.equals (events.get (x))) indexToDelete = x; //check if it's the same as the one to delete; if it is, mark its index
					}
					events.remove (indexToDelete); /*Remove from the timeline the Event with the marked index
					* (Logic: This event-panel contains an Event, and the user wanted to delete that Event, and the event-panel is merely a display.
					* So, if an Event on the timeline is this display's source, since each event-panel comes from an Event on the timeline, then get rid of that source.) */
					updateDayButtons (); /*Update the day-buttons to remove the Event from its day-button,
					* since the day-buttons only check the timeline when told to, not actively,
					* and since the day-button windows only check their non-actively-updating day-buttons, not the timeline */
					saveData (); //send the new timeline to the timeWalkData.txt file
					//Clear all of the labels in this event-panel, making it look deleted
					EPNameLbl.setText (null);
					EPDateLbl.setText (null);
					EPPriorityLbl.setText (null);
					EPTimeLbl.setText (null);
					EPDescriptionLbl.setText (null);
				}
			});
			this.add (EPDeleteEventBtn, BorderLayout.SOUTH);
		}
	}
	
	/**Nested class: a window created by clicking a day-button, that displays the Events from that day-button in more detail.
	 * @author William Berman */
	public class DayButtonWindow extends JFrame {

		//Initialize
		private static final long serialVersionUID = 593079572276153404L;
		private JPanel DBWContentPane;
		private Date DBWDate; //The Date of this window's day-button
		private List<Event> DBWEvents = new ArrayList<Event> (); //The Events that occur on this window's day-button's Date

		/**Constructor: input the Date that it represents and the Events for that Date,
		 * and it creates the framework and functionality for the day-button window */
		public DayButtonWindow (Date DBWDateIn, List<Event> DBWEventsIn) {
			
			//Set up the day-button window
			this.DBWDate = DBWDateIn;
			this.DBWEvents = DBWEventsIn;
			this.setTitle ("Events for " + this.DBWDate.toString ());
			this.setResizable (false);
			this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
			this.setBounds (150, 150, 250, 500);
			DBWContentPane = new JPanel ();
			DBWContentPane.setBorder (new EmptyBorder (5, 5, 5, 5));
			setContentPane (DBWContentPane);
			GridBagLayout gbl_DBWContentPane = new GridBagLayout ();
			gbl_DBWContentPane.columnWidths = new int[] {240};
			gbl_DBWContentPane.rowHeights = new int[] {438, 30};
			gbl_DBWContentPane.columnWeights = new double[] {0.0};
			gbl_DBWContentPane.rowWeights = new double[] {0.0, 0.0};
			DBWContentPane.setLayout (gbl_DBWContentPane);
			
			//Where the event-panels are
			JPanel DBWEventsPnl = new JPanel ();
			GridBagConstraints gbc_DBWEventsPnl = new GridBagConstraints ();
			gbc_DBWEventsPnl.fill = GridBagConstraints.BOTH;
			gbc_DBWEventsPnl.insets = new Insets (0, 0, 5, 0);
			gbc_DBWEventsPnl.gridx = 0;
			gbc_DBWEventsPnl.gridy = 0;
			DBWContentPane.add (DBWEventsPnl, gbc_DBWEventsPnl);
			DBWEventsPnl.setLayout (new GridLayout (DBWEventsIn.size (), 1, 0, 0)); //Make a space for each of the incoming event-panels
			//Add this day's Events to the panel
			for (Event eventToAdd : this.DBWEvents) { //For each Event in this day-button window...
				DBWEventsPnl.add (new EventPanel (eventToAdd)); //add an event-panel for it
			}
			
			//The button that closes this day-button window
			JButton DBWCloseBtn = new JButton ("Close");
			DBWCloseBtn.setFont (new Font ("Tahoma", Font.PLAIN, 11));
			DBWCloseBtn.addActionListener (new ActionListener () {
				@Override
				public void actionPerformed (ActionEvent arg0) { //Close the day-button window when this button is pressed
					dispose ();
				}
			});
			GridBagConstraints gbc_DBWCloseBtn = new GridBagConstraints ();
			gbc_DBWCloseBtn.fill = GridBagConstraints.BOTH;
			gbc_DBWCloseBtn.gridx = 0;
			gbc_DBWCloseBtn.gridy = 1;
			DBWContentPane.add (DBWCloseBtn, gbc_DBWCloseBtn);
		}
	}
	
	/**Nested class: a window that is created at the start of the program, that shows the Events occurring over the next 3 days, including today.
	 * Like a day-button window, but with 3 days instead of one, and automatically generated.
	 * @author William Berman */
	private class ReminderWindow extends JFrame {

		//Initialize
		private static final long serialVersionUID = -393800172523136250L;
		private JPanel RWContentPane;
		private Date RWToday; //Today: the reminder window gets Events from both today and the next two days


		/**Constructor: input the Date that is today,
		 * and it creates the framework and functionality for the reminder window */
		public ReminderWindow (Date RWTodayIn) {
			
			//Set up the reminder window
			this.RWToday = RWTodayIn; //Initialize today
			this.setTitle ("Upcoming Events");
			this.setResizable (false);
			this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
			this.setBounds (50, 50, 600, 500);
			RWContentPane = new JPanel ();
			RWContentPane.setBorder (new EmptyBorder (5, 5, 5, 5));
			setContentPane (RWContentPane);
			GridBagLayout gbl_RWContentPane = new GridBagLayout ();
			gbl_RWContentPane.columnWidths = new int[] {590};
			gbl_RWContentPane.rowHeights = new int[] {438, 30};
			gbl_RWContentPane.columnWeights = new double[] {0.0};
			gbl_RWContentPane.rowWeights = new double[] {0.0, 0.0};
			RWContentPane.setLayout (gbl_RWContentPane);
			
			//Where the panels for each day and their labels are
			JPanel RWUpcomingPnl = new JPanel ();
			GridBagConstraints gbc_RWUpcomingPnl = new GridBagConstraints ();
			gbc_RWUpcomingPnl.fill = GridBagConstraints.BOTH;
			gbc_RWUpcomingPnl.insets = new Insets (0, 0, 5, 0);
			gbc_RWUpcomingPnl.gridx = 0;
			gbc_RWUpcomingPnl.gridy = 0;
			RWContentPane.add (RWUpcomingPnl, gbc_RWUpcomingPnl);
			GridBagLayout gbl_RWUpcomingPnl = new GridBagLayout ();
			gbl_RWUpcomingPnl.columnWidths = new int[] {196, 196, 196};
			gbl_RWUpcomingPnl.rowHeights = new int[] {30, 402};
			gbl_RWUpcomingPnl.columnWeights = new double[] {0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_RWUpcomingPnl.rowWeights = new double[] {0.0, 0.0, Double.MIN_VALUE};
			RWUpcomingPnl.setLayout (gbl_RWUpcomingPnl);
			
			//Label for the column of today's Events
			JLabel RWTodayLbl = new JLabel ("Today");
			RWTodayLbl.setFont (new Font ("Tahoma", Font.PLAIN, 11));
			GridBagConstraints gbc_RWTodayLbl = new GridBagConstraints ();
			gbc_RWTodayLbl.fill = GridBagConstraints.BOTH;
			gbc_RWTodayLbl.insets = new Insets (0, 0, 5, 5);
			gbc_RWTodayLbl.gridx = 0;
			gbc_RWTodayLbl.gridy = 0;
			RWUpcomingPnl.add (RWTodayLbl, gbc_RWTodayLbl);
			RWTodayLbl.setHorizontalAlignment (SwingConstants.CENTER);
			
			//Label for the column of tomorrow's Events
			JLabel RWTomorrowLbl = new JLabel ("Tomorrow");
			RWTomorrowLbl.setFont (new Font ("Tahoma", Font.PLAIN, 11));
			GridBagConstraints gbc_RWTomorrowLbl = new GridBagConstraints ();
			gbc_RWTomorrowLbl.fill = GridBagConstraints.BOTH;
			gbc_RWTomorrowLbl.insets = new Insets (0, 0, 5, 5);
			gbc_RWTomorrowLbl.gridx = 1;
			gbc_RWTomorrowLbl.gridy = 0;
			RWUpcomingPnl.add (RWTomorrowLbl, gbc_RWTomorrowLbl);
			RWTomorrowLbl.setHorizontalAlignment (SwingConstants.CENTER);
			
			//Label for the column of the day after tomorrow's Events
			JLabel RWDayAfterTomorrowLbl = new JLabel ("The Day After Tomorrow");
			RWDayAfterTomorrowLbl.setFont (new Font ("Tahoma", Font.PLAIN, 11));
			GridBagConstraints gbc_RWDayAfterTomorrowLbl = new GridBagConstraints ();
			gbc_RWDayAfterTomorrowLbl.fill = GridBagConstraints.BOTH;
			gbc_RWDayAfterTomorrowLbl.insets = new Insets (0, 0, 5, 0);
			gbc_RWDayAfterTomorrowLbl.gridx = 2;
			gbc_RWDayAfterTomorrowLbl.gridy = 0;
			RWUpcomingPnl.add (RWDayAfterTomorrowLbl, gbc_RWDayAfterTomorrowLbl);
			RWDayAfterTomorrowLbl.setHorizontalAlignment (SwingConstants.CENTER);
			
			//Where today's Events are
			JPanel RWTodayPnl = new JPanel ();
			GridBagConstraints gbc_RWTodayPnl = new GridBagConstraints ();
			gbc_RWTodayPnl.fill = GridBagConstraints.BOTH;
			gbc_RWTodayPnl.insets = new Insets (0, 0, 0, 5);
			gbc_RWTodayPnl.gridx = 0;
			gbc_RWTodayPnl.gridy = 1;
			RWUpcomingPnl.add (RWTodayPnl, gbc_RWTodayPnl);
			List<Event> RWTodayEvents = collectEventsFrom (this.RWToday); //Initialize Events for today
			RWTodayPnl.setLayout (new GridLayout (RWTodayEvents.size (), 1, 0, 0)); //Make a space for each of the incoming event-panels
			//Add today's Events to the panel
			for (Event eventTodayToAdd : RWTodayEvents) { //For each Event today...
				RWTodayPnl.add (new EventPanel (eventTodayToAdd)); //add an event-panel for it in the today panel
			}
			
			//Where tomorrow's Events are
			JPanel RWTomorrowPnl = new JPanel ();
			GridBagConstraints gbc_RWTomorrowPnl = new GridBagConstraints ();
			gbc_RWTomorrowPnl.fill = GridBagConstraints.BOTH;
			gbc_RWTomorrowPnl.insets = new Insets (0, 0, 0, 5);
			gbc_RWTomorrowPnl.gridx = 1;
			gbc_RWTomorrowPnl.gridy = 1;
			RWUpcomingPnl.add (RWTomorrowPnl, gbc_RWTomorrowPnl);
			List<Event> RWTomorrowEvents = collectEventsFrom (dateAfter (this.RWToday)); //Initialize Events for tomorrow
			RWTomorrowPnl.setLayout (new GridLayout (RWTomorrowEvents.size (), 1, 0, 0)); //Make a space for each of the incoming event-panels
			//Add tomorrow's Events to the panel
			for (Event eventTomorrowToAdd : RWTomorrowEvents) { //For each Event tomorrow...
				RWTomorrowPnl.add (new EventPanel (eventTomorrowToAdd)); //add an event-panel for it in the tomorrow panel
			}
			
			//Where the day after tomorrow's Events are
			JPanel RWDayAfterTomorrowPnl = new JPanel ();
			GridBagConstraints gbc_RWDayAfterTomorrowPnl = new GridBagConstraints ();
			gbc_RWDayAfterTomorrowPnl.fill = GridBagConstraints.BOTH;
			gbc_RWDayAfterTomorrowPnl.gridx = 2;
			gbc_RWDayAfterTomorrowPnl.gridy = 1;
			RWUpcomingPnl.add (RWDayAfterTomorrowPnl, gbc_RWDayAfterTomorrowPnl);
			List<Event> RWDayAfterTomorrowEvents = collectEventsFrom (dateAfter (dateAfter (this.RWToday))); //Initialize Events for the day after tomorrow
			RWDayAfterTomorrowPnl.setLayout (new GridLayout (RWDayAfterTomorrowEvents.size (), 1, 0, 0)); //Make a space for each of the incoming event-panels
			//Add the day after tomorrow's Events to the panel
			for (Event eventDayAfterTomorrowToAdd : RWDayAfterTomorrowEvents) { //For each Event the day after tomorrow...
				RWDayAfterTomorrowPnl.add (new EventPanel (eventDayAfterTomorrowToAdd)); //add an event-panel for it in the day-after-tomorrow panel
			}
			
			//The button that closes this reminder window
			JButton RWCloseBtn = new JButton ("Close");
			RWCloseBtn.setFont (new Font ("Tahoma", Font.PLAIN, 11));
			RWCloseBtn.addActionListener (new ActionListener () {
				@Override
				public void actionPerformed (ActionEvent arg0) { //Close the reminder window when this button is pressed
					dispose ();
				}
			});
			GridBagConstraints gbc_RWCloseBtn = new GridBagConstraints ();
			gbc_RWCloseBtn.fill = GridBagConstraints.BOTH;
			gbc_RWCloseBtn.gridx = 0;
			gbc_RWCloseBtn.gridy = 1;
			RWContentPane.add (RWCloseBtn, gbc_RWCloseBtn);
		}
		
		/**Says what the Date after the inputted Date is
		 * @param dateIn
		 * The "before" Date
		 * @return
		 * The "after" Date, one day after the "before" Date */
		public Date dateAfter (Date dateIn) {
			//Initialize
			int monthNew = dateIn.getMonth ();
			int dayNew = dateIn.getDay ();
			int yearNew = dateIn.getYear ();
			//Go to the next day
			dayNew++;
			//If that day is in the next month, go to the next month.
			if (isLeapYear (yearNew)) { //If it's a leap year,
				if (dayNew > monthSizesLeap.get (monthNew)) { //and the day just went out of the month (with leap-year sizes),
					monthNew++; //go to the next month
					dayNew = 1; //and go to the 1st day of that month.
				}
			} else { //Otherwise, if it's a common year,
				if (dayNew > monthSizesComm.get (monthNew)) { //and the day just went out of the month (with common-year sizes),
					monthNew++; //go to the next month
					dayNew = 1; //and go to the 1st day of that month.
				}
			}
			//If that month is in the next year, go to the next year.
			if (monthNew > 11) { //If the month just went past December...
				yearNew++; //go to the next year
				monthNew = 0; //and go to January of that year.
			}
			return new Date (monthNew, dayNew, yearNew); //The Date one day after the inputted Date
		}
	}
}