import java.io.Serializable;
import java.util.*;

public class Site implements Serializable {

	/** Serializable file value  */
	private static final long serialVersionUID = 1L;

	/** The name of the person who is occupying the Site */
	protected String nameReserving = "John Doe";

	/** The date the Site was checked-in (occupied) */
	protected GregorianCalendar checkIn;

	/** The date the someone checks out of the site */
	protected GregorianCalendar checkOut;

	/** The estimated number of days the person is reserving */
	protected int daysStaying = 1;

	/** The camping site number */
	protected int siteNumber;

	/** The cost of staying at the campsite */
	protected int cost;

	/** The index of the site*/
	private int index;




	/**
	 *Empty, default constructor
	 *
	 *@param none
	 *@Return none
	 */
	public Site() {
		super();
	}

	/**
	 * The constructor creates a 'Site' for registration
	 *
	 * @param nameReserving the name of the person reserving the site
	 * @param checkIn the date of check in to the site
	 * @param daysStaying the number of days the person is staying at the site
	 * @param siteNumber the site number the person will be staying at
	 * @Return none
	 */
	public Site(String nameReserving, GregorianCalendar checkIn,
			int daysStaying, int siteNumber) {
		this.nameReserving = nameReserving;
		this.checkIn = checkIn;
		this.daysStaying = daysStaying;
		this.siteNumber = siteNumber;


	}

	/**
	 * Getter for the serial version ID
	 *
	 * @param none
	 * @return long the version ID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	/**
	 * Getter for the name of the reserver
	 *
	 * @param none
	 * @return String the name of the person reserving the site
	 */
	public String getNameReserving() {
		return nameReserving;
	}

	/**
	 * Setter for the name of the reserver
	 *
	 * @param nameReserving the name of the person reserving the site
	 * @return none
	 */
	public void setNameReserving(String nameReserving) {
		this.nameReserving = nameReserving;
	}

	/**
	 * Getter for the check in date
	 *
	 * @param none
	 * @return GregorianCalendar the name of the person reserving the site
	 */
	public GregorianCalendar getCheckIn() {
		return checkIn;
	}

	/**
	 * Setter for the check in date
	 *
	 * @param checkIn
	 * @return GregorianCalendar the name of the person reserving the site
	 */
	public void setCheckIn(GregorianCalendar checkIn) {
		this.checkIn = checkIn;
	}

	/**
	 * Getter for the number of days staying at the sight
	 *
	 * @param none
	 * @return int the number of days staying
	 */
	public int getDaysStaying() {
		return daysStaying;
	}

	/**
	 * Setter for the days staying
	 *
	 * @param daysStaying the number of days staying at the campsite
	 * @return none
	 */
	public void setDaysStaying(int daysStaying) {
		this.daysStaying = daysStaying;
	}

	/**
	 * Getter for the check out day
	 *
	 * @param none
	 * @return GregorianCalendar check out date
	 */
	public GregorianCalendar getCheckOut() {
		return checkOut;
	}
	
	public void setCheckOut(GregorianCalendar checkOut) {
		this.checkOut = checkOut;
	}

	/**
	 * Getter for site number
	 *
	 * @param none
	 * @return int site number someone stays at
	 */
	public int getSiteNumber() {
		return siteNumber;
	}

	/**
	 * Setter for index
	 *
	 * @param siteNumber site number that someone stays at
	 * @return none
	 */
	public void setSiteNumber(int siteNumber) {
		this.siteNumber = siteNumber;
	}
	
	/**
	 * Getter for index
	 *
	 * @param none
	 * @return int for index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Setter for index
	 *
	 * @param index the index to set
	 * @return none
	 */
	public void setIndex(int index) {
		this.index = index;
	}
}
