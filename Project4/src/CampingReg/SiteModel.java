package CampingReg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/***********************************************************************
 * The SiteModel class is the structure that all rows in the table will
 * follow
 **********************************************************************/
public class SiteModel extends AbstractTableModel {

	/** List of all the sites **/
	private LinkedList<Site> sites;

	/** Array of the column names **/
	private String[] columnNames;

	/** Autosave booleans **/
	private boolean textSave = false;
	private boolean serialSave = false;

	/** String that holds autosave filename **/
	private String saveName;

	/** String that holds previous edits **/
	private ArrayList<String> pastEdits;


	/*******************************************************************
	 * The default constructor that initializes the list of sites and
	 * sets the column names
	 ******************************************************************/
	public SiteModel() {
		pastEdits = new ArrayList<String>();
		sites = new LinkedList<Site>();
		columnNames = new String[] { "Name Reserving", 
				"Checked in", 
				"Days Staying", 
				"Site #", 
		"Tent/RV info" };
	}

	/*******************************************************************
	 * Gets the row count
	 * 
	 * @return returns the number of sites
	 ******************************************************************/
	public int getRowCount() {
		return sites.size();
	}

	/*******************************************************************
	 * Gets the column count
	 * 
	 * @return returns the number of columns
	 ******************************************************************/
	public int getColumnCount() {
		return columnNames.length;
	}

	/*******************************************************************
	 * Gets the column name at a specific index
	 * 
	 * @param col the column index you want the name of
	 * 
	 * @return returns the name of the specified column
	 ******************************************************************/
	public String getColumnName(int col) {
		return columnNames[col];
	}

	/*******************************************************************
	 * Gets the value at a specific row and column
	 * 
	 * @param rowIndex the specified row
	 * @param columnIndex the specified column
	 * 
	 * @return returns the object at the specified row and column
	 ******************************************************************/
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return sites.get(rowIndex).getNameReserving();
		case 1:
			return sites.get(rowIndex).getCheckInAsString();
		case 2:
			return sites.get(rowIndex).getDaysStaying();
		case 3:
			return sites.get(rowIndex).getSiteNumber();
		case 4:
			if (sites.get(rowIndex) instanceof Tent) {
				String text = ((Tent)sites.get(rowIndex)).getNumOfTenters() != 1 ? " tenters" : " tenter";

				return ((Tent)sites.get(rowIndex)).getNumOfTenters() + text;
			}
			else if (sites.get(rowIndex) instanceof RV) {
				return ((RV)sites.get(rowIndex)).getPower() + " amps";
			}
			break;
		}

		return null;
	}

	/*******************************************************************
	 * Gets the list of sites
	 * 
	 * @return an ArrayList of all sites
	 ******************************************************************/
	public LinkedList<Site> getCurrentSites() {
		return sites;
	}

	/*******************************************************************
	 * Adds a site to the list (and updates the view)
	 * 
	 * @param site the site to be added to the list (and view)
	 ******************************************************************/
	public void add(Site site) {
		sites.add(site);

		refresh();

		if(serialSave == true) {
			saveSerial(saveName);
		}else if(textSave == true) {
			saveText(saveName);
		}

		pastEdits.add("D," + (getRowCount() - 1));
	}

	/*******************************************************************
	 * Removes a site from the list (and updates the view)
	 * 
	 * @param index the index in the list to be removed
	 ******************************************************************/
	public void remove(int index) {
		pastEdits.add("I," + getAtIndex(index));
		sites.remove( index );

		refresh();

		if(serialSave == true) {
			saveSerial(saveName);
		}else if(textSave == true) {
			saveText(saveName);
		}
	}

	/*******************************************************************
	 * Gets the site at the requested index
	 * 
	 * @param index the index of the requested site
	 * @return the site at the specified index
	 ******************************************************************/
	public Site getAtIndex(int index) {
		return sites.get(index);
	}

	/*******************************************************************
	 * Saves the current table as a serializable object
	 * 
	 * @param filename the filename of the file to be saved
	 ******************************************************************/
	public void saveSerial(String filename) {
		try {
			FileOutputStream f = new FileOutputStream(new File(filename));
			ObjectOutputStream o = new ObjectOutputStream(f);

			o.writeObject(sites);

			o.close();
			f.close();

			serialSave = true;
			saveName = filename;
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	/*******************************************************************
	 * Loads a file that was saved as a serializable object
	 * 
	 * @param filename the filename of the file to be loaded
	 ******************************************************************/
	public void loadSerial(String filename) {
		try {
			FileInputStream f = new FileInputStream(new File(filename));
			ObjectInputStream o = new ObjectInputStream(f);

			sites = (LinkedList<Site>) o.readObject();

			refresh();

			o.close();
			f.close();

			serialSave = true;
			saveName = filename;
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	/*******************************************************************
	 * Saves a file as a text file
	 * 
	 * @param filename the filename of the file to be saved as text
	 ******************************************************************/
	public void saveText(String filename) {
		try {
			File file = new File(filename);
			PrintWriter pw = new PrintWriter(file);

			for (int i = 0; i < sites.size(); i++) {
				pw.write(sites.get(i) + "\n");
			}

			pw.close();
			textSave = true;
			saveName = filename;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	/*******************************************************************
	 * Loads a file that was saved as a text file
	 * 
	 * @param filename the filename of the file to be loaded as a text
	 * file
	 ******************************************************************/
	public void loadText(String filename) {
		try {
			File file = new File(filename);
			BufferedReader br = new BufferedReader(new FileReader(file));

			sites.clear();

			String line;
			while ((line = br.readLine()) != null) {
				Site retVal = stringToSite(line);

				if (retVal != null) {
					sites.add(retVal);
				}
			}

			refresh();

			textSave = true;
			saveName = filename;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	/*******************************************************************
	 * Converts a string input into a Site object
	 * 
	 * @param input the values to be converted into a Site object
	 * @return a Site object that holds the same information as the 
	 * passed in values
	 * @throws Exception when there are incorrect values
	 ******************************************************************/
	public Site stringToSite(String input) throws Exception {
		String[] items = input.split(",");

		if (items.length == 6) {
			GregorianCalendar date = new GregorianCalendar();
			String[] parsedDate = items[2].split("/");
			date.set(GregorianCalendar.MONTH, Integer.parseInt(parsedDate[0]) - 1);
			date.set(GregorianCalendar.DAY_OF_MONTH, Integer.parseInt(parsedDate[1]));
			date.set(GregorianCalendar.YEAR, Integer.parseInt(parsedDate[2]));

			try {
				if (items[0].indexOf("RV") > -1) {
					return new RV(items[1], 
							date, 
							Integer.parseInt(items[3]), 
							Integer.parseInt(items[4]), 
							Integer.parseInt(items[5]));
				}
				else if (items[0].indexOf("Tent") > -1) {
					return new Tent(items[1], 
							date, 
							Integer.parseInt(items[3]), 
							Integer.parseInt(items[4]), 
							Integer.parseInt(items[5]));
				}
			}
			catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
		else {
			throw new Exception("File was corrupted and could not be loaded");
		}

		return null;
	}

	/*******************************************************************
	 * Private helper method that calls fireTableRowsInserted which
	 * refreshes the table
	 ******************************************************************/
	private void refresh() {
		fireTableRowsInserted(0, getRowCount() - 1);
	}

	/*******************************************************************
	 * Private helper method that undo's previous operations
	 ******************************************************************/

	/*???????????????????????????????????????????????????????????????
	 *	Having the undo function in the GUI class breaks MVC standards.
	 *	Move this and any corresponding code to SiteModel to adhere to
	 *	MVC
	 */ 	
	public void undo() {

		if (pastEdits.size() > 0) {
			String execute = pastEdits.get(pastEdits.size() - 1);
			String[] items = execute.split(",");

			switch (items[0]) {
			case "D":
				remove(Integer.parseInt(items[1]));
				pastEdits.remove(pastEdits.size() - 1);

				break;
			case "I":
				String input = "";

				for (int i = 1; i < items.length; i++) {
					input += (items[i] + ",");
				}

				try {
					Site retVal = stringToSite(input);
					add(retVal);
					pastEdits.remove(pastEdits.size() - 1);
				}
				catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}

				break;
			}
		}
	}

	/*******************************************************************
	 * Private helper method that checks if the site info entered will
	 * work with what's currently occupied
	 * @return 
	 * 
	 * @throws Exception when the requested dates conflict with already
	 * reserved dates
	 ******************************************************************/

	/*??????????????????????? TO DO ??????????????????????????????
	 * This logic is needed by both dialog classes, so instead of 
	 * having it duplicated in both classes, move it to 
	 * SiteModel so 1) conforms to MVC (model, view, control) design
	 * and 2) can be shared and used by both dialogs
	 */
	public boolean checkOtherSites(Site unit) {
		LinkedList<Site> otherSites = getCurrentSites();
		boolean temp = true;


		for (int i = 0; i < otherSites.size(); i++) {
			// Find the other sites that have the same site number as
			// the new site to be added

			if (otherSites.get(i).getSiteNumber() == unit.getSiteNumber()) {
				GregorianCalendar otherClone = (GregorianCalendar)otherSites.get(i).getCheckIn().clone();
				GregorianCalendar unitClone = (GregorianCalendar)unit.getCheckIn().clone();

				for (int j = 0; j < unit.getDaysStaying(); j++) {
					for (int k = 0; k < otherSites.get(i).getDaysStaying(); k++) {
						if (unitClone.get(GregorianCalendar.MONTH) == otherClone.get(GregorianCalendar.MONTH) &&
								unitClone.get(GregorianCalendar.DAY_OF_MONTH) == otherClone.get(GregorianCalendar.DAY_OF_MONTH) &&
								unitClone.get(GregorianCalendar.YEAR) == otherClone.get(GregorianCalendar.YEAR)) {
							temp = false;
						}

						otherClone.add(GregorianCalendar.DAY_OF_MONTH, 1);
					}

					unitClone.add(GregorianCalendar.DAY_OF_MONTH, 1);
					otherClone.add(GregorianCalendar.DAY_OF_MONTH, otherSites.get(i).getDaysStaying() * -1);
				}
			}
		}
		return temp;
	}

	/******************************************************************
	 * Method used to sort the table by the name
	 *****************************************************************/
	public void sortName() {
		
		// Sorts the sites
		Collections.sort( sites, new SortByName() );
		
		// Updates the table
		for ( int i = 1; i <= ( sites.size()  ); i++)
			fireTableRowsInserted(sites.size() - i, sites.size() - i);

	}
	
	/******************************************************************
	 * Method used to sort the table by the days staying
	 *****************************************************************/
	public void sortDate() {
		
		// Lambda function that sorts by dates
		Comparator<Site> SortByDate = ( Site o1, Site o2 ) ->
			o1.getCheckIn().compareTo( o2.getCheckIn() );
		
		// Calls the lambda function to sort
		Collections.sort( sites, SortByDate );
		
		// Updates the table
		for ( int i = 1; i <= ( sites.size()  ); i++)
			fireTableRowsInserted(sites.size() - i, sites.size() - i);

	}
	
	/******************************************************************
	 * Method used to sort the table by the check in date
	 *****************************************************************/
	public void sortDays() {
		
		// An anonymous class to sort the site by days
		class SortByDays implements Comparator<Site> {
			@Override
			
			public int compare(Site o1, Site o2) {
				 if ( o1.getDaysStaying() == o2.getDaysStaying() )
					 return 0;
				 else if ( o1.getDaysStaying() < o2.getDaysStaying() )
					 return -1;
				 else if ( o1.getDaysStaying() > o2.getDaysStaying() )
					 return 1;
				 return 0;
			} 
		}
		
		// Sorts the sites
		Collections.sort( sites, new SortByDays() );
		
		// Updates the table
		for ( int i = 1; i <= ( sites.size()  ); i++)
			fireTableRowsInserted(sites.size() - i, sites.size() - i);
	}
	
	/******************************************************************
	 * Method used to sort the table by sites with days staying is
	 * 	three or greater.
	 * 
	 * @return Returns the entire LinkedList<String>
	 *****************************************************************/
	public LinkedList<String> sort3Days() {
		
		// Creates a new Linked List
		LinkedList<String> specSites = new LinkedList<String>();
		
		/* Stream function that finds and stores each site with days
		 * staying is 3 or greater.
		 */
		sites.stream().filter(u -> u.getDaysStaying() >= 3).forEach(u -> 
			specSites.add( "Name: " + u.getNameReserving() + 
			", Days Staying: " + u.getDaysStaying() + 
			", Site Number: " + u.getSiteNumber() + "\n" ) );
		
		return specSites;
	}
}
