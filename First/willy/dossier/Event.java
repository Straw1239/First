package dossier;

/**Nested class: an event that goes on the calendar and the timeline, along with its information (date, time, priority, description).
 * @author William Berman */
public class Event {
	
	//Initialize
	private String EName, EMonth, EDescription;
	private int EDay, EYear, EStartTime, EEndTime, EPriority;
	
	/**Constructor for an Event being made from the adder window: input each parameter separately
	 * @param ENameIn The name
	 * @param EMonthIn The month
	 * @param EDayIn The day
	 * @param EYearIn The year
	 * @param EStartIn The time that it starts at
	 * @param EEndIn The time that it ends at
	 * @param EDescriptionIn The description of the Event
	 * @param EPriorityIn How important the Event is (1 = most, 5 = least) */
	public Event (String ENameIn, String EMonthIn, int EDayIn, int EYearIn, int EStartIn, int EEndIn, String EDescriptionIn, int EPriorityIn) {
		this.EName = ENameIn;
		this.EMonth = EMonthIn;
		this.EDay = EDayIn;
		this.EYear = EYearIn;
		this.EStartTime = EStartIn;
		this.EEndTime = EEndIn;
		this.EDescription = EDescriptionIn;
		this.EPriority = EPriorityIn;
	}
	
	/**Constructor for an Event being made from the scanning of the file that contains the saved data:
	 * input each parameter as part of a giant String, and it separates the parameters.
	 * @param inCompressed The text version of the Event, created exclusively by the toString method of this class */
	public Event (String inCompressed) {
		this.EName = inCompressed.substring (0, inCompressed.indexOf ("~")); //Take the first piece (the name)
		inCompressed = inCompressed.substring (inCompressed.indexOf ("~") + 1); //Remove the first piece and the divider
		this.EMonth = inCompressed.substring (0, inCompressed.indexOf ("~")); //Take the second piece (the month)
		inCompressed = inCompressed.substring (inCompressed.indexOf ("~") + 1); //Remove the second piece and the divider
		this.EDay = Integer.parseInt (inCompressed.substring (0, inCompressed.indexOf ("~"))); //Take the third piece (the day)
		inCompressed = inCompressed.substring (inCompressed.indexOf ("~") + 1); //Remove the third piece and the divider
		this.EYear = Integer.parseInt (inCompressed.substring (0, inCompressed.indexOf ("~"))); //Take the fourth piece (the year)
		inCompressed = inCompressed.substring (inCompressed.indexOf ("~") + 1); //Remove the fourth piece and the divider
		this.EStartTime = Integer.parseInt (inCompressed.substring (0, inCompressed.indexOf ("~"))); //Take the fifth piece (the starting time)
		inCompressed = inCompressed.substring (inCompressed.indexOf ("~") + 1); //Remove the fifth piece and the divider
		this.EEndTime = Integer.parseInt (inCompressed.substring (0, inCompressed.indexOf ("~"))); //Take the sixth piece (the ending time)
		inCompressed = inCompressed.substring (inCompressed.indexOf ("~") + 1); //Remove the sixth piece and the divider
		this.EDescription = inCompressed.substring (0, inCompressed.indexOf ("~")); //Take the seventh piece (the description)
		inCompressed = inCompressed.substring (inCompressed.indexOf ("~") + 1); //Remove the seventh piece and the divider
		this.EPriority = Integer.parseInt (inCompressed); //Take the eighth (last) piece (the priority, the last remnant of the compressed Event)
	}
	
	/**@return the name of this Event */
	public String getName () {
		return this.EName;
	}
	
	/**@return the month in which this Event occurs */
	public String getMonth () {
		return this.EMonth;
	}
	
	/**@return the day in which this Event occurs */
	public int getDay () {
		return this.EDay;
	}
	
	/**@return the year in which this Event occurs */
	public int getYear () {
		return this.EYear;
	}
	
	/**@return the time at which this Event starts */
	public int getStart () {
		return this.EStartTime;
	}
	
	/**@return the time at which this Event ends */
	public int getEnd () {
		return this.EEndTime;
	}
	
	/**@return the description of this Event */
	public String getDescription () {
		return this.EDescription;
	}
	
	/**@return the priority of this Event */
	public int getPriority () {
		return this.EPriority;
	}
	
	/**Compares this Event with another Event
	 * @return whether each field of this Event equals the corresponding field of the other Event */
	public boolean equals (Event other) {
		return ((this.EName.equals (other.getName ())) && (this.EMonth == other.getMonth ())
		&& (this.EDay == other.getDay ()) && (this.EYear == other.getYear ())
		&& (this.EStartTime == other.getStart ()) && (this.EEndTime == other.getEnd ())
		&& (this.EDescription.equals (other.getDescription ())) && (this.EPriority == other.getPriority ()));
	}
	
	/**Converts this Event into a single String, so that it can be put into a text file for saving
	 * @return the text version of the Event */
	@Override
	public String toString () {
		return (this.EName + "~" + this.EMonth + "~" + this.EDay + "~" + this.EYear + "~" +
		this.EStartTime + "~" + this.EEndTime + "~" + this.EDescription + "~" + this.EPriority);
		//The values are separated by a char that none of them should have (~), so the program does not confuse the separators with the values
	}
}