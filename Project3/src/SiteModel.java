/**********************************************************************
 * 
 * A Java program that acts as a camp reservation program. The program 
 * will be able to occupy a site for tents and RVs. That is, one can 
 * check-in a tent or RV site using the program, and at a specific time
 * period and site number will be used. The campground has only five
 * sites that can be occupied at one time.
 * 
 * @author Justin Johns, Nicholas English, Scott Weaver, and 
 * 	Zachary Thomoas
 * @version 1.0
 *
 *********************************************************************/

import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class SiteModel extends AbstractTableModel {

	/** Used to store the sites and their information **/
	protected ArrayList<Site> listSites;
	
	/** Used to store the column header names **/
	private String[] columnNames = { "Name Reserving", "Checked In",
			"Days Staying", "Site #", "Tent/RV Info" };

	/** Used to store the dates that are occupied on a site **/
	protected ArrayList<siteDate> datesOccupied;
	
	/** Used to store the operations so that they can be undone **/
	protected ArrayList<UndoSite> undo;

	/******************************************************************
	 *
	 * A default constructor that creates some of the major functions
	 * of the code.
	 * 
	 * @param none
	 * 
	 *****************************************************************/
	public SiteModel() {
		super();
		undo = new ArrayList<UndoSite>();
		listSites = new ArrayList<Site>();
		datesOccupied = new ArrayList<siteDate>();
		for (int i = 0; i < 3600; i++) {
			datesOccupied.add(i, new siteDate());
		}
	}
	
	/******************************************************************
	 *
	 * This method will add a site to the bottom of a table each time
	 * the user calls the checkIn methods.
	 * 
	 * @param site Holds the site that the user wishes to delete.
	 * @return none
	 * 
	 *****************************************************************/
	public void addSite(Site a, int index) {
		listSites.add(index + 1, a);
		fireTableRowsInserted(0, index);

		//Creates an instance of tent
		Tent t = new Tent();
		
		//Creates an instance of RV
		RV r = new RV();

		Calendar calendar = Calendar.getInstance();
		int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

		if (a instanceof Tent) {
			t = (Tent) a;
			// 1 for add, -1 for delete
			undo.add(new UndoSite(t, index, 1));
			JOptionPane.showMessageDialog(null,
					"Estimated Cost: $" + t.cost(), "MONEY NEEDED",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			r = (RV) a;
			// 1 for add, -1 for delete
			undo.add(new UndoSite(r, index, 1));
			JOptionPane.showMessageDialog(null,
					"Estimated Cost: $" + r.cost(), "MONEY NEEDED",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	/******************************************************************
	 *
	 * This method will check the site and date that the user wishes
	 * to reserve. The application will check if each day is open
	 * for the desired site.
	 * 
	 * @param site Holds the site that the user wishes to reserve.
	 * @return true Returns true if the site is NOT occupied.
	 * @return false Returns false if the site IS occupied.
	 * 
	 *****************************************************************/
	public boolean reserve(Site site) {
		int totalDays = 0;
		GregorianCalendar tempDate = site.getCheckIn();
		int days = tempDate.get(Calendar.DAY_OF_YEAR);
		if ((tempDate.get(Calendar.YEAR) - 2017) > 0)
			totalDays = 366 * (tempDate.get(Calendar.YEAR) - 2017);

		// No index needed
		if (site.getDaysStaying() == 1) {
			siteDate tempSiteDate = datesOccupied.get(days + totalDays);
			if (tempSiteDate.getIndex(site.getSiteNumber()) == false)
				return false;
			else
				tempSiteDate.setIndex(site.getSiteNumber());
			// Index needed
		} else {
			for (int i = 0; i < site.getDaysStaying() - 1; i++) {
				siteDate tempSiteDate = datesOccupied
						.get(days + i + totalDays);
				if (tempSiteDate
						.getIndex(site.getSiteNumber()) == false)
					return false;
				else
					tempSiteDate.setIndex(site.getSiteNumber());
			}
		}
		return true;
	}

	/******************************************************************
	 *
	 * This method will delete the row for the site the user wishes
	 * to have unreserved.
	 * 
	 * @param site Holds the site that the user wishes to unreserve.
	 * @return none
	 * 
	 *****************************************************************/
	public void unReserve(Site site) {
		int totalDays = 0;
		GregorianCalendar tempDate = site.getCheckIn();
		int days = tempDate.get(Calendar.DAY_OF_YEAR);
		if ((tempDate.get(Calendar.YEAR) - 2017) != 0)
			totalDays = 366 * (tempDate.get(Calendar.YEAR) - 2017);
		for (int i = 0; i < site.getDaysStaying(); i++) {
			siteDate tempSiteDate = datesOccupied
					.get(days + i + totalDays);
			tempSiteDate.removeIndex(site.getSiteNumber());
		}
	}

	/******************************************************************
	 *
	 * This method will delete the row for the site the user wishes
	 * to have deleted.
	 * 
	 * @param site Holds the site that the user wishes to delete.
	 * @return none
	 * 
	 *****************************************************************/
	public void delete(Site site) {
		try {
			int totalDays = 0;
			GregorianCalendar tempDate = site.getCheckIn();
			int days = tempDate.get(Calendar.DAY_OF_YEAR);
			if ((tempDate.get(Calendar.YEAR) - 2017) != 0)
				totalDays = 366 * (tempDate.get(Calendar.YEAR) - 2017);
			for (int i = 0; i < site.getDaysStaying(); i++) {
				siteDate tempSiteDate = datesOccupied
						.get(days + i + totalDays);
				tempSiteDate.removeIndex(site.getSiteNumber());
			}
		} catch (RuntimeException e) {
			// do nothing
		}
	}

	/******************************************************************
	 *
	 * This method handles all of the undo operations. It uses an array
	 * to store what order to go into for undoing an operation. The
	 * method will then determine if it needs to execute an undo
	 * operation or store an operation to potentially be undone.
	 * 
	 * @param index An integer that holds the location of the current
	 * 	operation to be undone.
	 * @return none
	 * 
	 *****************************************************************/
	public void undoLastAction(int index) {
		UndoSite undoSite = undo.get(index);

		// If the last action was added
		if (undoSite.getAddOrDelete() == 1) {

			Site tempSite = undoSite.getSite();
			unReserve(tempSite);
			removeSite(index);

			// If the last action was deleted
		} else if (undoSite.getAddOrDelete() == -1) {
			Site tempSite = undoSite.getSite();
			reserve(tempSite);
			addSite(tempSite, undoSite.getIndex());
		}

	}
	
	/******************************************************************
	 *
	 * This method is used to remove row for a site that the user wants
	 * to remove.
	 * 
	 * @param i Holds integer value of which row that the user wishes 
	 * 	to have removed from the table.
	 * @return none
	 * 
	 *****************************************************************/
	public void removeSite(int i) {
		listSites.remove(i);
		fireTableRowsDeleted(0, listSites.size());
	}

	/******************************************************************
	 *
	 * This method exports data from the table into a .txt file.
	 * 
	 * @param fileName Used to hold what file the method will output
	 * 	to.
	 * @return none
	 * 
	 *****************************************************************/
	public void saveText(File fileName) {
		try {

			// Writes to the file
			PrintWriter out = new PrintWriter(
					new BufferedWriter(new FileWriter(fileName)));
			out.println(listSites.size());

			// Loops for each row of sites.
			for (int i = 0; i < listSites.size(); i++) {

				//Creates an instance of site.
				Site site = listSites.get(i);
				GregorianCalendar date = site.getCheckIn();

				//Outputs the name of the reserver.
				out.println(site.getNameReserving());
				
				//Outputs the date (mm/dd/yyyy).
				out.println((date.get(Calendar.MONTH) + 1) + "/"
						+ date.get(Calendar.DAY_OF_MONTH) + "/"
						+ date.get(Calendar.YEAR));

				//Outputs the number of days staying.
				out.println(site.getDaysStaying());
				
				//Outputs the site number.
				out.println(site.getSiteNumber());

				/* Checks what type of site each row is. It then adds 
				   an identifier to be used later when loading. */
				//If site is a tent.
				if (site instanceof Tent) {
					out.println("Tent ");
					out.println(((Tent) site).getNumOfTenters());
					
				//If site is a RV.
				} else {
					out.println("RV ");
					out.println(((RV) site).getPower());
				}

			}

			// Closes file
			out.close();

		} catch (IOException error) {
			
			//Error message to user.
			JOptionPane.showMessageDialog(null,
					"Text error while saving!", "IO ERROR",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/******************************************************************
	 *
	 * This method exports data from the table into a .dat file.
	 * 
	 * @param fileName Used to hold what file the method will output
	 * 	to.
	 * @return none
	 * 
	 *****************************************************************/
	public void saveSerial(File fileName) {
		try {
			//Writes to the file
			FileOutputStream fStream = new FileOutputStream(fileName);
			ObjectOutputStream output = new ObjectOutputStream(fStream);
			output.writeObject(listSites);

			//Closes file
			output.close();

		} catch (IOException error) {
			
			//Error message to the user.
			JOptionPane.showMessageDialog(null,
					"Serial error while saving!", "IO ERROR",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/******************************************************************
	 *
	 * This method uses a .txt file to add sites into the table.
	 * 
	 * @param fileName Used to hold what file the method will receive
	 * 	input from.
	 * @return none
	 * 
	 *****************************************************************/
	public void loadText(File fileName) {

		// Adds items to the table
		try {
			//Clears the current table
			listSites.clear();
			fireTableRowsDeleted(0, listSites.size());
			
			datesOccupied = new ArrayList<siteDate>();
			for (int x = 0; x < 3600; x++) {
				datesOccupied.add(x, new siteDate());
			}

			// Creates local variables
			GregorianCalendar dateCheckedIn = null;
			String nameReserved = "";
			Scanner scanner = new Scanner(fileName);

			//Finds the number of rows needed.
			int count = Integer.parseInt(scanner.nextLine().trim());
			
			// Loops for each row of sites.
			for (int i = 0; i < count; i++) {

				// Finds name of reserver.
				nameReserved = scanner.nextLine().trim();

				// Finds date for reservation.
				DateFormat formatter = new SimpleDateFormat(
						"MM/dd/yyyy");
				String tempDate = scanner.nextLine().trim();
					
					//Checks if valid date
					String[] checkDates = tempDate.split("/");
					if (12 < Integer.parseInt(checkDates[0]) ||
							1 > Integer.parseInt(checkDates[0]))
							throw new Exception();
					if (31 < Integer.parseInt(checkDates[1]) || 
							1 > Integer.parseInt(checkDates[1]))
							throw new Exception();
					if (2017 > Integer.parseInt(checkDates[2]))
							throw new Exception();
						
				Date date = formatter.parse(tempDate);
				dateCheckedIn = new GregorianCalendar();
				dateCheckedIn.setTime(date);

				// Finds number of days staying.
				int daysStaying = Integer
						.parseInt(scanner.nextLine().trim());
				if (daysStaying < 1) throw new Exception();

				// Finds which site is being reserved.
				int siteNumber = Integer
						.parseInt(scanner.nextLine().trim());					
				
				// Checks for identifier in text file to determine what
				// type of site the row is.
				// If - site is a tent
				if (scanner.nextLine().trim().contains("Tent")) {
					int tenters = Integer
							.parseInt(scanner.nextLine().trim());
						//Checks if valid number of tenters
						if (tenters > 10 || tenters < 1) 
							throw new Exception();
						
					Tent tent;
					tent = new Tent(nameReserved, dateCheckedIn,
							daysStaying, siteNumber, tenters);
					
					//Checks if valid tent site
					if (!reserve(tent)) throw new Exception();

					// Adds site to the JTable
					listSites.add(tent);
					fireTableRowsInserted(listSites.size() - 1,
							listSites.size() - 1);

				}
				// Else - site is a RV
				else {
					int power = Integer
							.parseInt(scanner.nextLine().trim());
						//Checks for a valid power integer
						if (power != 30 && power != 40 && power != 50)
							throw new Exception();
						
					RV rv;
					rv = new RV(nameReserved, dateCheckedIn,
							daysStaying, siteNumber, power);
					
					//Checks if valid site
					if (!reserve(rv)) throw new Exception();

					// Adds site to the JTable
					listSites.add(rv);
					fireTableRowsInserted(listSites.size() - 1,
							listSites.size() - 1);
				}
				
			}

			// Closes file
			scanner.close();

		} catch (Exception ex) {
			//Clears table
			listSites.clear();
			fireTableRowsDeleted(0, listSites.size());
			
			//Message dialog to user.
			JOptionPane.showMessageDialog(null,
					"Text error while loading!\nTable Cleared!",
					"IO ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	/******************************************************************
	 *
	 * This method uses a .dat file to add sites into the table.
	 * 
	 * @param fileName Used to hold what file the method will receive
	 * 	input from.
	 * @return none
	 * 
	 *****************************************************************/
	public void loadSerial(File fileName) {

		// Clears the table
		listSites.clear();
		fireTableRowsDeleted(0, listSites.size());

		// Adds items to the table
		try {
			FileInputStream fStream = new FileInputStream(fileName);
			ObjectInputStream inputFile = new ObjectInputStream(
					fStream);

			listSites = (ArrayList<Site>) inputFile.readObject();
			fireTableRowsInserted(0, listSites.size());

			// Closes file
			inputFile.close();

		} catch (Exception ex) {
			
			//Clears table
			listSites.clear();
			fireTableRowsDeleted(0, listSites.size());
			
			//Message to user.
			JOptionPane.showMessageDialog(null,
					"Serial error while loading!\nTable Cleared!", 
					"IO ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	/******************************************************************
	 *
	 * This method is used to find the value of information on a row
	 * in different columns. The row will host information for the same
	 * site. Columns will host all the values that belong under their
	 * respective header.
	 * 
	 * @param row Holds the integer value for the location of the row
	 * 	for the cell.
	 * @param col Holds the integer value for the location of the
	 * 	column for the cell.
	 * @return Returns the name of the reserver.
	 * @return Returns the date of the first date of reservation.
	 * @return Returns the number of days staying.
	 * @return Returns the site number for the reservation.
	 * @return Returns the amount of power needed IF RV site.
	 * @return Returns the amount of people IF a tent site.
	 * @return null.
	 * 
	 *****************************************************************/
	public Object getValueAt(int row, int col) {

		switch (col) {
		case 0:	//Name of reserver
			return (listSites.get(row).getNameReserving());
		case 1:	//Date (mm/dd/yyyy)
			return (DateFormat.getDateInstance(DateFormat.SHORT)
					.format(listSites.get(row).getCheckIn().getTime()));
		case 2:	//Numbers of days staying
			return (listSites.get(row).getDaysStaying());
		case 3:	//Site number selected
			return (listSites.get(row).getSiteNumber());
		case 4:	//If RV, then returns the number of amps needed.
				//If Tent, then returns the number of tenters.
			if (listSites.get(row) instanceof RV)
				return ((RV) listSites.get(row)).getPower();
			if (listSites.get(row) instanceof Tent)
				return ((Tent) listSites.get(row)).getNumOfTenters();
		default:
			return null;
		}
	}

	/******************************************************************
	 *
	 * This method is used to initialize this.listSites to the current
	 * listSites.
	 * 
	 * @param listSites Holds the current site.
	 * @return none
	 * 
	 *****************************************************************/
	public void setListSite(ArrayList<Site> listSites) {
		this.listSites = listSites;
	}

	/******************************************************************
	 *
	 * This method is used to retrieve the current site from listSites.
	 * 
	 * @param none
	 * @return listSites Returns the listSites for the current site in
	 * 	the ArrayList<Site>.
	 * 
	 *****************************************************************/
	public ArrayList<Site> getListSite() {
		return listSites;
	}

	/******************************************************************
	 *
	 * This method is used to find the number of columns that the table
	 * uses.
	 * 
	 * @param none
	 * @return columnNames.length Returns the integer value of the
	 * 	number of columns the table has.
	 * 
	 *****************************************************************/
	public String getColumnName(int col) {
		return columnNames[col];
	}

	/******************************************************************
	 *
	 * This method is used to find the number of columns that the table
	 * uses.
	 * 
	 * @param none
	 * @return columnNames.length Returns the integer value of the
	 * 	number of columns the table has.
	 * 
	 *****************************************************************/
	public int getColumnCount() {
		return columnNames.length;
	}

	/******************************************************************
	 *
	 * This method is used to find the size of the table in terms of
	 * the number of rows it has of sites.
	 * 
	 * @param none
	 * @return listSites.size() Returns the integer value of the
	 * 	number of rows of sites the table has.
	 * 
	 *****************************************************************/
	public int getRowCount() {
		return listSites.size();
	}

	/******************************************************************
	 *
	 * This method is used to find the size of the table in terms of
	 * its column count.
	 * 
	 * @param none
	 * @return getColumnCount() Returns the integer value of the number 
	 * 	of columns.
	 * 
	 *****************************************************************/
	public int getSize() {
		return getColumnCount();
	}
	
	/******************************************************************
	 * 
	 * This method is used for finding which dates are occupied.
	 * 
	 * @return the datesOccupied
	 *
	 *****************************************************************/
	public ArrayList<siteDate> getDatesOccupied() {
		return datesOccupied;
	}

	/******************************************************************
	 * 
	 * This method is used for setting which dates are occupied.
	 * 
	 * @param datesOccupied the datesOccupied to set
	 * 
	 *****************************************************************/
	public void setDatesOccupied(ArrayList<siteDate> datesOccupied) {
		this.datesOccupied = datesOccupied;
	}
}