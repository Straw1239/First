package dossier;

/**Additional class: a set of 3 ints that represents a certain day, a month/day/year, like an int[3] with additional functionality
 * @author William Berman */
public class Date {
	
	 //Initialize
	private int DMonth, DDay, DYear; //The month/day/year that this Date represents
	
	/**Constructor: input the month/day/year that it is */
	public Date (int DMonthIn, int DDayIn, int DYearIn) {
		this.DMonth = DMonthIn;
		this.DDay = DDayIn;
		this.DYear = DYearIn;
	}
	
	/**@return the month that this Date is in */
	public int getMonth () {
		return this.DMonth;
	}
	
	/**@return the day that this Date is in */
	public int getDay () {
		return this.DDay;
	}
	
	/**@return the year that this Date is in */
	public int getYear () {
		return this.DYear;
	}
	
	/**Compares this Date with another Date
	 * @return whether each field of this Date equals the corresponding field of the other Date */
	public boolean equals (Date other) {
		return (this.DMonth == other.getMonth ()) && (this.DDay == other.getDay ()) && (this.DYear == other.getYear ());
	}
	
	/**Returns a String version of this Date.
	 * It increments the month (this.DMonth + 1) because it thinks that January (for example) is the 0th month,
	 * while the user thinks that it is the 1st month.
	 * @return
	 * this Date, in String form */
	public String toString () {
		return ((this.DMonth + 1) + "/" + this.DDay + "/" + this.DYear);
	}
}