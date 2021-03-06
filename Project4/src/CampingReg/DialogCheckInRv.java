package CampingReg;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.*;

/***********************************************************************
 * Class that creates the JDialog Box when checking in to an RV site
 **********************************************************************/
public class DialogCheckInRv extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;

	/* JTextFields */
	private JTextField nameTxt, occupyedOnTxt, stayingTxt, siteNumberTxt;

	/* JComboBox */
	private JComboBox<String> powerBox;

	/* JButtons */
	private JButton okButton, cancelButton;

	/* Close Status */
	private boolean closeStatus;

	/* RV object */
	public RV unit;  

	/* JLabels */
	private JLabel nameLabel, occupyingLabel, stayingLabel, siteLabel, powerLabel;

	/* Gregorian Calendar */
	private GregorianCalendar gCalendarCheckIn;

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

	/**********************************************************************
	 * Constructor that sets up the Dialog with given parameters
	 * 
	 * @param paOccupy is the frame for the JDialog
	 * @param d is the RV being checked in
	 *********************************************************************/
	public DialogCheckInRv(JFrame paOccupy, RV d, SiteModel sModel) {	
		unit = d;
		this.sModel = sModel;

		//Creates Gregorian Calendar
		gCalendarCheckIn = new GregorianCalendar();
		gCalendarCheckIn.setLenient(false);
		month = gCalendarCheckIn.get(GregorianCalendar.MONTH) + 1;
		day = gCalendarCheckIn.get(GregorianCalendar.DAY_OF_MONTH);
		year = gCalendarCheckIn.get(GregorianCalendar.YEAR);

		//Creates JDialog
		dialog = new JDialog();

		//Creates JTextFields
		nameTxt = new JTextField(d.getNameReserving());
		occupyedOnTxt = new JTextField(month + "/" + day + "/" + year);
		stayingTxt = new JTextField(d.getDaysStaying());
		siteNumberTxt = new JTextField(d.getSiteNumber());

		//Creates JComboBox
		powerBox = new JComboBox<String>();

		//Creates JButtons
		okButton = new JButton("Ok");
		cancelButton = new JButton("Cancel");

		//Creates JLabels
		nameLabel = new JLabel("Name of Reserver:");
		occupyingLabel = new JLabel("Occupied On Date:");
		stayingLabel = new JLabel("Days Staying:");
		siteLabel = new JLabel("Requested Site Number:");
		powerLabel = new JLabel("Power in AMPS:");

		//Creates Frame
		parentFrame = paOccupy;

		//Sets closeStatus to false
		closeStatus = false;

		//Instantiate Buttons
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		//Adds Items to powerBox
		powerBox.addItem("30");
		powerBox.addItem("40");
		powerBox.addItem("50");

		//Creates JPanel and adds on items
		panel = new JPanel();
		panel.add(nameLabel);
		panel.add(nameTxt);
		panel.add(siteLabel);
		panel.add(siteNumberTxt);
		panel.add(occupyingLabel);
		panel.add(occupyedOnTxt);
		panel.add(stayingLabel);
		panel.add(stayingTxt);
		panel.add(powerLabel);
		panel.add(powerBox);
		panel.add(Box.createVerticalStrut(5));
		panel.add(Box.createVerticalStrut(5));
		panel.add(okButton);
		panel.add(cancelButton);

		//Sets the layout of panel
		panel.setLayout(new GridLayout(7, 2));

		//Adds the panel to the JDialog
		dialog.add(panel);

		//Sets specifics for JDialog
		dialog.setLocationRelativeTo(parentFrame);
		dialog.setModal(true);
		dialog.setTitle("Reserve An RV Site");
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setSize(350, 250);
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
				checkFields();
				closeStatus = true;
			}
			else {
				JOptionPane.showMessageDialog(null, "There are empty fields. Please populate all fields with values");
			}
		}
		else if (e.getSource() == cancelButton) {
			unit = null;
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
		if (nameTxt.getText().length() > 0 &&
				siteNumberTxt.getText().length() > 0 && 
				occupyedOnTxt.getText().length() > 0 && 
				stayingTxt.getText().length() > 0) {
			return true;
		}

		return false;
	}

	/*******************************************************************
	 * Private helper method that checks if textfields that require
	 * an int, have them. If this is the case, it closes the dialog
	 ******************************************************************/
	private void checkFields() {
		boolean isValid = true;
		int siteNum = 0, daysStaying = 0;

		try {
			siteNum = Integer.parseInt(siteNumberTxt.getText());
		} catch (NumberFormatException ex){
			JOptionPane.showMessageDialog(null, "Site number must be an integer. Please enter an integer.");
			isValid = false;
		}

		try {
			daysStaying = Integer.parseInt(stayingTxt.getText());
		} catch (NumberFormatException ex){
			JOptionPane.showMessageDialog(null, "Days staying must be an integer. Please enter an integer.");
			isValid = false;
		}

		if (isValid) {
			try {
				unit = new RV(nameTxt.getText(),
						getCheckInDate(),
						daysStaying,
						siteNum,
						getPowerFromView());
				
				sModel.checkOtherSites(unit);

				JOptionPane.showMessageDialog(null, "You Owe: $" + unit.calcCost());

				dialog.dispose();
			}
			catch (Exception ex) {
				if (!ex.getMessage().equals("Don't show")) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		}	
	}


	/*******************************************************************
	 * Private helper method that converts the text in occupyedOnTxt 
	 * into a GregorianCalender 
	 * 
	 * @return GregorianCalendar that matches what was typed in
	 ******************************************************************/
	private GregorianCalendar getCheckInDate() {
		String input[] = occupyedOnTxt.getText().split("/");
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
				return new GregorianCalendar(inputInt[2], inputInt[0] - 1, inputInt[1]);
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
			JOptionPane.showMessageDialog(null, "Please choose a year from 2017 to 2099.");

			return false;
		}

		if (month <= 0 || month > 12) {
			JOptionPane.showMessageDialog(null, "Please choose a month from 1 to 12.");

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
				JOptionPane.showMessageDialog(null, "For " + months[month] + ", please choose a day from 1 to 31.");

				return false;
			}

			return true;
		case 4:
		case 6:
		case 9:
		case 11:
			if (day < 1 || day > 30) {
				JOptionPane.showMessageDialog(null, "For " + months[month] + ", please choose a day from 1 to 30.");

				return false;
			}

			return true;
		case 2:
			if (day < 1 || day > 28) {
				JOptionPane.showMessageDialog(null, "For " + months[month] + ", please choose a day from 1 to 28.");

				return false;
			}

			return true;
		default:
			return false;
		}
	}

	/*******************************************************************
	 * Private helper method that gets the power from the view
	 ******************************************************************/
	private int getPowerFromView() {
		return Integer.parseInt(powerBox.getSelectedItem().toString());
	}


	/*******************************************************************
	 * Getter method for unit
	 * 
	 * @return unit is the current RV
	 ******************************************************************/
	public RV getRV() {
		return unit;
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
}