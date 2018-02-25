package CampingReg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/***********************************************************************
 * Class that creates the JDialog Box when checking out
 **********************************************************************/

public class DialogCheckOut extends JDialog implements ActionListener {

	/* JTextFields */
	private JTextField checkOutText;

	/* JButtons */
	private JButton okButton, cancelButton;

	/* Close Status */
	private boolean closeStatus;

	/* JLabels */
	private JLabel checkOutLabel;

	/* Gregorian Calendar */
	private GregorianCalendar gCalendarCheckOut;

	/* JDialog */
	private JDialog dialog;

	/* JFrame */
	private JFrame parentFrame;

	/* JPanel */
	private JPanel panel;

	/* Month, Day, and Year */
	private int month, day, year;

	/* SiteModel */
	private SiteModel sModel;

	/* Row selected */
	private int row;

	/**********************************************************************
	 * Constructor that sets up the Dialog with given parameters
	 * 
	 * @param paOccupy is the frame for the JDialog
	 * @param row is the row that was selected in the table
	 *********************************************************************/

	public DialogCheckOut( JFrame paOccupy, int row, SiteModel sModel) {

		this.sModel = sModel;

		setRow( row );

		//Creates Gregorian Calendar
		gCalendarCheckOut = new GregorianCalendar();
		gCalendarCheckOut.setLenient(false);
		month = gCalendarCheckOut.get(GregorianCalendar.MONTH) + 1;
		day = gCalendarCheckOut.get(GregorianCalendar.DAY_OF_MONTH);
		year = gCalendarCheckOut.get(GregorianCalendar.YEAR);

		//Creates JDialog
		dialog = new JDialog();

		//Creates JTextFields
		checkOutText = new JTextField(month + "/" + day + "/" + year);

		//Creates JButtons
		okButton = new JButton("Ok");
		cancelButton = new JButton("Cancel");

		//Creates JLabels
		checkOutLabel = new JLabel("Checking out on (Date): ");

		//Creates Frame
		parentFrame = paOccupy;

		//Sets closeStatus to false
		closeStatus = false;

		//Instantiate Buttons
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		//Creates JPanel and adds on items
		panel = new JPanel();
		panel.add(checkOutLabel);
		panel.add(checkOutText);
		panel.add(okButton);
		panel.add(cancelButton);

		//Adds the panel to the JDialog
		dialog.add(panel);

		//Sets specifics for JDialog
		dialog.setLocationRelativeTo(parentFrame);
		dialog.setModal(true);
		dialog.setTitle("Checking Out");
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setSize(250, 110);
		dialog.setResizable(false);
		dialog.setVisible(true);

	}

	/*******************************************************************
	 * Handles the actions of the buttons
	 * 
	 * @param e is an ActionEvent that determines the action
	 ******************************************************************/
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okButton) {

			if (check()) {
				getCheckOutDate();
				closeStatus = true;

				// Prints to the user the final cost
				JOptionPane.showMessageDialog( null, 
						"Checking out final cost is $" + 
						getCost( getRow() ) );

				dialog.dispose();

			}
			else {
				JOptionPane.showMessageDialog(null, "There are empty" + 
					" fields. Please populate all fields with values");
			}
		}
		else if (e.getSource() == cancelButton) {
			dialog.dispose();
		}
	}

	/*******************************************************************
	 * Private helper method that checks if every text field has
	 * input
	 * 
	 * @return check is true if all textfields have input, false if
	 * not
	 ******************************************************************/
	private boolean check() {
		if (checkOutText.getText().length() > 0 ) {
			return true;
		}

		return false;
	}

	/*******************************************************************
	 * Private helper method that converts the text in occupyedOnTxt 
	 * into a GregorianCalender 
	 * 
	 * @return GregorianCalendar that matches what was typed in
	 ******************************************************************/
	private GregorianCalendar getCheckOutDate() {
		String input[] = checkOutText.getText().split("/");
		int inputInt[] = new int[input.length];

		if (input.length == 3) {
			try {
				for (int i = 0; i < input.length; i++) {
					inputInt[i] = Integer.parseInt(input[i].trim());
				}
			}
			catch (NumberFormatException ex) {
				return null;
			}

			if (checkDates(inputInt[0], inputInt[1], inputInt[2])) {
				return new GregorianCalendar(inputInt[2], inputInt[0] - 
						1, inputInt[1]);
			}
		}

		return null;
	}

	/******************************************************************
	 * Private helper method that checks that dates are valid
	 *****************************************************************/
	private boolean checkDates(int month, int day, int year) {
		String[] months = {null, "January", "February", "March",
				"April", "May", "June", "July", "August", 
				"September", "October", "November", 
		"December"};

		if (year < 2017 || year >= 2099) {
			JOptionPane.showMessageDialog(null, "Please choose a year"+
					" from 2017 to 2099.");

			return false;
		}

		if (month <= 0 || month > 12) {
			JOptionPane.showMessageDialog(null, "Please choose a month"+
					"from 1 to 12.");

			return false;
		}

		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			if (day < 1 || day > 31) {
				JOptionPane.showMessageDialog(null, "For " + 
						months[month] + 
						", please choose a day from 1 to 31.");

				return false;
			}

			return true;
		case 4:
		case 6:
		case 9:
		case 11:
			if (day < 1 || day > 30) {
				JOptionPane.showMessageDialog(null, "For " + 
						months[month] + 
						", please choose a day from 1 to 30.");

				return false;
			}

			return true;
		case 2:
			if (day < 1 || day > 28) {
				JOptionPane.showMessageDialog(null, "For " + 
						months[month] + 
						", please choose a day from 1 to 28.");

				return false;
			}

			return true;
		default:
			return false;
		}
	}

	/*******************************************************************
	 * Setter method for setting the row selected
	 * 
	 * @param r is the row being set
	 ******************************************************************/
	private void setRow( int r ) {
		this.row = r;

	}

	/*******************************************************************
	 * Getter method for getting the row selected
	 * @return 
	 * 
	 * @return Returned is the row that was set
	 ******************************************************************/
	private int getRow() {
		return row;

	}

	/*******************************************************************
	 * Getter method for closeStatus
	 * 
	 * @return closeStatus is a boolean that changes when the 
	 * JDialog is finished
	 ******************************************************************/
	public boolean getCloseStatus() {
		return closeStatus;
	}

	/*******************************************************************
	 * Method that sets the cost
	 * 
	 * @param row is the row the user selected
	 ******************************************************************/
	public int getCost( int row ) {

		int cost = 0;

		// Runs if an RV
		if ( sModel.getAtIndex( row ) instanceof RV ) {

			// Checks if leaving early or later
			if (getLengthOfStay(row) > (int) sModel.getValueAt(row, 2))

				// Runs if a RV
				if ( sModel.getAtIndex( row ) instanceof RV ) {

					// Runs if leaving late
					if ( getLengthOfStay( row ) > 
					(int) sModel.getValueAt( row, 2 ) ) {

						// Finds the cost
						cost = getCostRV( getLengthOfStay( row ) );
						cost = cost + ( 10 * ( getLengthOfStay( row ) - 
								(int) sModel.getValueAt( row, 2 ) ) );

						// Runs if site uses 50 amps
						char charAmps =  ( (String) sModel.getValueAt
								( row, 4 ) ).charAt(0);
						
						int numAmps = (Integer.parseInt("" + 
								charAmps)) * 10;
						
						if ( 50 ==  numAmps )
							cost = cost + ( 5 * ( getLengthOfStay( row ) 
									- (int) sModel.getValueAt(row, 2)));

					}

					// Runs if leaving early
					if ( getLengthOfStay(row) < (int)sModel.getValueAt
							( row, 2 ) )

						// Finds the cost
						cost = getCostRV( getLengthOfStay( row ) );
				}
		}

		// Runs if a tent
		if ( sModel.getAtIndex( row ) instanceof Tent ) {

			// Runs if leaving late
			if (getLengthOfStay(row) > (int)sModel.getValueAt(row,2)) {

				// Finds the cost
				char charNumOfTenters =  ( (String) sModel.getValueAt
						( row, 4 ) ).charAt(0);
				
				int numOfTenters = Integer.parseInt
						("" + charNumOfTenters);
				
				cost = getCostTent(row, numOfTenters);
				cost = cost + ( 5 * ( getLengthOfStay( row ) - 
						(int) sModel.getValueAt( row, 2) ) );

				// Prints to the user
				getCost( row );
				JOptionPane.showMessageDialog( null, 
						"Checking out final cost is $" + cost );

			}

			// Runs if leaving early
			if (getLengthOfStay(row) < (int)sModel.getValueAt(row, 2)) {

				// Finds the cost
				char charNumOfTenters =  ( ( String ) sModel.getValueAt
						( row, 4 ) ).charAt(0);
				
				int numOfTenters = Integer.parseInt
						( "" + charNumOfTenters );
				
				cost = getCostTent(getLengthOfStay(row), numOfTenters );
			}
		}

		return cost;

	}

	/*******************************************************************
	 * Method that calculates the default cost of the RV site
	 * 
	 * @return cost is the cost of the RV site
	 ******************************************************************/
	private int getCostRV( int days ) {
		return 30 * days;
	}

	/*******************************************************************
	 * Method that calculates the cost of the Tent site
	 * 
	 * @return cost is the cost of the Tent site
	 ******************************************************************/
	public int getCostTent( int days, int numOfTenters ) {
		return 3 * days * numOfTenters;
	}

	/*******************************************************************
	 * Method that gets the length of days staying
	 * 
	 * @param row is the row the user selected
	 * @return Returns the length of stay
	 ******************************************************************/
	public int getLengthOfStay( int row ) {
		
		/* Variables that hold dates */
		int day;
		int month;
		int year;

		// Sets the check in date
		GregorianCalendar checkInTime = getCheckInDate( row );

		// Sets the check out date
		GregorianCalendar checkOutTime = getCheckOutDate();

		// Finds the difference in time for check out time
		Calendar cal1 = new GregorianCalendar();
		Calendar cal2 = new GregorianCalendar();
		
		// Sets the check in time as a date
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime( checkInTime.getTime() );
		day = calendar1.get(Calendar.DAY_OF_YEAR);
		month = ( calendar1.get(Calendar.MONTH) ) + 1;
		year = calendar1.get(Calendar.YEAR);
		
		cal1.set( year, month, day);
		
		// Sets the check out time as a date
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime( checkOutTime.getTime() );
		day = calendar2.get(Calendar.DAY_OF_YEAR);
		month = ( calendar2.get(Calendar.MONTH) ) + 1;
		year = calendar2.get(Calendar.YEAR);
		
		cal2.set( year, month, day);

		int lengthOfStay = (int)( ( cal2.getTime().getTime() - 
				cal1.getTime().getTime() ) / (1000 * 60 * 60 * 24));

		return lengthOfStay;
	}

	/*******************************************************************
	 * Method that gets check in date
	 * 
	 * @param row is the row the user selected
	 * @return Returns the Gregorian check in date
	 ******************************************************************/

	private GregorianCalendar getCheckInDate(int row) {

		// Creates local variables
		GregorianCalendar dateCheckedIn = null;
		String stringDate = (String) sModel.getValueAt( row, 1 );
		Date date = null;

		// Sets the format for the date
		DateFormat formatter = new SimpleDateFormat( "MM/dd/yyyy" );

		// Splits the string into an array
		String[] inputDate = stringDate.split( "/" );

		// Converts the date checked in into a date
		try {
			date = formatter.parse( stringDate );

		} catch (ParseException e) {
			JOptionPane.showMessageDialog( null, 
				"A parsing error has occured. Program is closing..");

			// Exits the program
			System.exit(-1);

		}

		// Converts the date into the Gregorian variable
		dateCheckedIn = new GregorianCalendar();
		dateCheckedIn.setTime( date );

		return dateCheckedIn;
	}
}
