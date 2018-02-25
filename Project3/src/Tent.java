import java.util.GregorianCalendar;

/**********************************************************************
 * Subclass that extends Site. Deals with data storage and calculation for Tent
 * objects.
 * 
 * @author Nick English, Justin Johns, Zachary Thomas, Scott Weaver
 * @version 1.0
 *********************************************************************/
public class Tent extends Site {

	/** Represents the number of tenters on this site */
	private int numOfTenters;

	/******************************************************************
	 * Default constructor for RV Site.
	 *****************************************************************/
	public Tent() {

	}

	/******************************************************************
	 * Overloaded constructor with parameters used to initilize a Tent Site.
	 * 
	 * @param name
	 *            used to set the name reserving the site
	 * @param date
	 *            used to set the date in which the Site is reserved
	 * @param days
	 *            used to set the number of days staying
	 * @param site
	 *            used to set the tent Site to one of the 5 campsites
	 * @param numTenters
	 *            used to set the number of tenters staying
	 *****************************************************************/
	public Tent(String name, GregorianCalendar date, int days, 
			int site, int numTenters) {

		super(name, date, days, site);
		this.numOfTenters = numTenters;

	}

	/******************************************************************
	 * Overloaded constructor that accepts an int.
	 * 
	 * @param numOfTenters
	 *            used to set the number of tenters staying
	 *****************************************************************/
	public Tent(int numOfTenters) {
		this.numOfTenters = numOfTenters;
	}

	/******************************************************************
	 * Getter for number of tenters.
	 * 
	 * @return numOfTenters returns the number of tenters staying
	 *****************************************************************/
	public int getNumOfTenters() {
		return numOfTenters;
	}

	/******************************************************************
	 * Setter for number of tenters.
	 * 
	 * @param numOfTenters
	 *            used to set the number of tenters staying
	 *****************************************************************/
	public void setNumOfTenters(int numOfTenters) {
		this.numOfTenters = numOfTenters;
	}

	/******************************************************************
	 * Method that calculates the cost for a Tent Site.
	 * 
	 * @return cost returns how much a Tent Site costs for how many days it is
	 *         staying and the number of tenters staying
	 *****************************************************************/
	public int cost() {

		super.cost = super.getDaysStaying() * numOfTenters * 3;
		return super.cost;
	}
}
