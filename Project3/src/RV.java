import java.util.GregorianCalendar;

/**********************************************************************
 * Subclass that extends Site. Deals with data storage and calculation
 * for RV objects.
 * 
 * @author Nick English, Justin Johns, Zachary Thomas, Scott Weaver
 * @version 1.0
 *********************************************************************/
public class RV extends Site {

    /** Represents the power supplied to the site */
	// 30, 40, 50 amps of service.
	private int power;   

    /******************************************************************
	 * Default constructor for RV Site.
	 *****************************************************************/
    public RV(){

    }
    
    /******************************************************************
	 * Overloaded constructor with parameters used to initilize an RV
	 * Site.
	 * 
	 * @param name
	 * 			used to set the name reserving the site
	 * @param date
	 * 			used to set the date in which the Site is reserved
	 * @param days
	 * 			used to set the number of days staying
	 * @param site
	 * 			used to set the RV Site to one of the 5 campsites
	 * @param powerAmps
	 * 			used to set the power supplied to the site	           
	 *****************************************************************/
    public RV(String name, GregorianCalendar date, int days, 
    		int site, int powerAmps) {
    	
    	super(name, date, days, site);
    	this.power = powerAmps;
    }

    /******************************************************************
	 * Overloaded constructor that accepts an int.
	 * 
	 * @param power
	 * 			used to set the power supplied to the site
	 *****************************************************************/
    public RV(int power){
        this.power = power;
    }

    /******************************************************************
	 * Getter for RV power.
	 * 
	 * @return power
	 * 			returns amount of power supplied to the site
	 *****************************************************************/
    public int getPower() {
        return power;
    }

    /******************************************************************
	 * Setter for RV power.
	 * 
	 * @param power
	 * 			used to set the power supplied to the site
	 *****************************************************************/
    public void setPower(int power) {
        this.power = power;
    }
    
    /******************************************************************
	 * Method that calculates the cost for a RV Site.
	 * 
	 * @return cost
	 * 			returns how much an RV Site costs for how many days
	 * 			it is staying
	 *****************************************************************/
    public int cost() {
    	
    	super.cost = super.getDaysStaying() * 30;
    	return super.cost;
    }
}
