package chapter10;

public interface AMenu 
{
	/**
	 * Prints a menu with selections and logic to return a valid selection.
	 * @return the selected item
	 */
	public int printMenuGetSelection();
	
	 /**
	 * @return the numberOfMenuItems
	 */
	public int getNumberOfMenuItems();
}