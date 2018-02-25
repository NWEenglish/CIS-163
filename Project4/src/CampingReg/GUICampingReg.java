package CampingReg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/***********************************************************************
 * GUICampingReg is the main view of this application
 ***********************************************************************/
public class GUICampingReg extends JFrame implements ActionListener {
	private JPanel mainPanel;
	public JPanel messagePanel;

	private JLabel fullLabel;

	private JMenuBar menu;
	private JMenu file;
	private JMenu checkIn;
	private JMenu checkOutMenu;
	private JMenu sort;
	private JMenuItem saveSerial;
	private JMenuItem saveText;
	private JMenuItem loadSerial;
	private JMenuItem loadText;
	private JMenuItem checkInRv;
	private JMenuItem checkInTent;
	private JMenuItem exit;
	private JMenuItem checkOutItem;
	private JMenuItem sortName;
	private JMenuItem sortDate;
	private JMenuItem sortDays;
	private JMenuItem sort3Days;

	private JTable table;

	private SiteModel sModel;

	private Key keyPressedListener;

	/*******************************************************************
	 * Initialize all class level variables
	 ******************************************************************/
	public GUICampingReg() {
		keyPressedListener = new Key();

		mainPanel = new JPanel(new BorderLayout());
		messagePanel = new JPanel();

		fullLabel = new JLabel();

		menu = new JMenuBar();
		file = new JMenu("File");
		checkIn = new JMenu("Checking In");
		sort = new JMenu("Sort");
		saveSerial = new JMenuItem("Save Serial...");
		saveText = new JMenuItem("Save Text...");
		loadSerial = new JMenuItem("Open Serial...");
		loadText = new JMenuItem("Open Text...");
		checkInRv = new JMenuItem("Check-in RV Site");
		checkInTent = new JMenuItem("Check-in Tent Site");
		exit = new JMenuItem("Exit");
		checkOutItem = new JMenuItem("Check Out");
		checkOutMenu = new JMenu("Check Out");
		sortName = new JMenuItem("Sort by Name");
		sortDate = new JMenuItem("Sort by Date");
		sortDays = new JMenuItem("Sort by Days Staying");
		sort3Days = new JMenuItem("Sort by Three+ Days Staying");

		sModel = new SiteModel();

		table = new JTable(sModel);
		table.addKeyListener(keyPressedListener);
	}

	/*******************************************************************
	 * Setup the JFrame and all it's components
	 ******************************************************************/
	public void run() {
		setupMenu();
		setupActionListeners();

		messagePanel.add(fullLabel);
		messagePanel.setBackground(new Color(255, 204, 204));
		messagePanel.setVisible(false);

		mainPanel.add(menu, BorderLayout.NORTH);
		mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		mainPanel.add(messagePanel, BorderLayout.SOUTH);

		this.add(mainPanel);
		this.setSize(1000, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Camping Registration");
		this.setVisible(true);
	}

	/*******************************************************************
	 * Handles any actions that are performed
	 * 
	 * @param e has information about what action was performed
	 ******************************************************************/
	public void actionPerformed(ActionEvent e) {		
		if (e.getSource() == saveSerial) {
			JFileChooser chooser = new JFileChooser();
			int status = chooser.showSaveDialog(null);
			if (status == JFileChooser.APPROVE_OPTION) {
				String filename = chooser.getSelectedFile().getAbsolutePath();
				sModel.saveSerial(filename);
			}
		}
		else if (e.getSource() == loadSerial) {
			JFileChooser chooser = new JFileChooser();
			int status = chooser.showOpenDialog(null);
			if (status == JFileChooser.APPROVE_OPTION) {
				String filename = chooser.getSelectedFile().getAbsolutePath();
				sModel.loadSerial(filename);
				checkIfFull();
			}
		}
		else if (e.getSource() == saveText) {
			JFileChooser chooser = new JFileChooser();
			int status = chooser.showSaveDialog(null);
			if (status == JFileChooser.APPROVE_OPTION) {
				String filename = chooser.getSelectedFile().getAbsolutePath();
				sModel.saveText(filename);
			}
		}
		else if (e.getSource() == loadText) {
			JFileChooser chooser = new JFileChooser();
			int status = chooser.showOpenDialog(null);
			if (status == JFileChooser.APPROVE_OPTION) {
				String filename = chooser.getSelectedFile().getAbsolutePath();
				sModel.loadText(filename);
				checkIfFull();
			}
		}
		else if (e.getSource() == exit) {
			System.exit(1);
		}
		else if (e.getSource() == checkInTent) {
			Tent tent = new Tent();
			DialogCheckInTent dialog = new DialogCheckInTent(this, tent, sModel);
			if (dialog.getCloseStatus() == true && dialog.getTent() != null) {
				sModel.add(dialog.getTent());

				checkIfFull();
			}
		}
		else if (e.getSource() == checkInRv) {
			RV rv = new RV();
			DialogCheckInRv dialog = new DialogCheckInRv(this, rv, sModel);

			if (dialog.getCloseStatus() == true && dialog.getRV() != null) {
				sModel.add(dialog.getRV());

				checkIfFull();
			}
		}

		///////////////////////////////////////////////////////////////
		/////////////////// VVVV STEP 2 VVVV //////////////////////////

		else if ( e.getSource() == checkOutItem ) {
			
			// Gets the selected row
			int row = table.getSelectedRow();

			// Checks if a row was selected
			if ( row != -1 ) {

				// Creates the dialog box
				DialogCheckOut dialog = new DialogCheckOut( this, row, sModel );	


				if (dialog.getCloseStatus() == true) {
					
					// Removes the row from the 
					sModel.remove( row );
					
				}
			}
		}
		
		///////////////////////////////////////////////////////////////
		/////////////////// VVVV STEP 4 VVVV //////////////////////////
		else if ( e.getSource() == sortName ) {
			sModel.sortName();
			
		}
		
		else if ( e.getSource() == sortDate ) {
			sModel.sortDate();
			
		}
		
		else if ( e.getSource() == sortDays ) {
			sModel.sortDays();
			
		}
		
		///////////////////////////////////////////////////////////////
		/////////////////// VVVV STEP 5 VVVV //////////////////////////
		else if ( e.getSource() == sort3Days ) {
			JOptionPane.showMessageDialog(null, 
					"The names of the people that are staying 3+ days\n" 
					+ sModel.sort3Days() );
		}
	}


	/*******************************************************************
	 * Helper method for setting up the JMenu
	 ******************************************************************/
	private void setupMenu() {
		file.add(saveSerial);
		file.add(loadSerial);
		file.addSeparator();
		file.add(saveText);
		file.add(loadText);
		file.addSeparator();
		file.add(exit);

		checkOutMenu.add(checkOutItem);

		checkIn.add(checkInTent);
		checkIn.add(checkInRv);

		sort.add(sortName);
		sort.add(sortDate);
		sort.add(sortDays);
		sort.add(sort3Days);
		
		menu.add(file);
		menu.add(checkIn);
		menu.add(checkOutMenu);
		menu.add(sort);
	}

	/*******************************************************************
	 * Helper method for adding all action listeners
	 ******************************************************************/
	private void setupActionListeners() {
		saveSerial.addActionListener(this);
		loadSerial.addActionListener(this);
		saveText.addActionListener(this);
		loadText.addActionListener(this);
		exit.addActionListener(this);
		checkInTent.addActionListener(this);
		checkInRv.addActionListener(this);
		checkOutItem.addActionListener(this);
		sortName.addActionListener(this);
		sortDate.addActionListener(this);
		sortDays.addActionListener(this);
		sort3Days.addActionListener(this);
	}

	/*******************************************************************
	 * Checks to see if there are any sites that are full
	 ******************************************************************/
	private void checkIfFull() {
		LinkedList<Site> sites = sModel.getCurrentSites();
		ArrayList<GregorianCalendar> fullDays = new ArrayList<GregorianCalendar>();

		if (sites.size() > 0) {
			// Set the minDate and maxDate
			GregorianCalendar minDate = (GregorianCalendar)sites.get(0).getCheckIn().clone();
			GregorianCalendar maxDate = (GregorianCalendar)minDate.clone();
			maxDate.add(GregorianCalendar.DAY_OF_MONTH, sites.get(0).getDaysStaying());

			// Find the earliest and latest dates
			for (int i = 1; i < sites.size(); i++) {
				GregorianCalendar temp = (GregorianCalendar)sites.get(i).getCheckIn().clone();

				if (minDate.compareTo(temp) > 0) {
					minDate = (GregorianCalendar)sites.get(i).getCheckIn().clone();
				}

				temp.add(GregorianCalendar.DAY_OF_MONTH, sites.get(i).getDaysStaying());

				if (maxDate.compareTo(temp) < 0) {
					maxDate = (GregorianCalendar)sites.get(i).getCheckIn().clone();
				}
			}

			while (minDate.compareTo(maxDate) < 0) {
				boolean [] isOccupied = new boolean[5];

				for (int i = 0; i < sites.size(); i++) {
					GregorianCalendar localMin = (GregorianCalendar)sites.get(i).getCheckIn().clone();
					GregorianCalendar localMax = (GregorianCalendar)localMin.clone();
					localMax.add(GregorianCalendar.DAY_OF_MONTH, sites.get(i).getDaysStaying());

					if (minDate.compareTo(localMin) >= 0 && minDate.compareTo(localMax) < 0) {
						isOccupied[sites.get(i).getSiteNumber() - 1] = true;
					}
				}

				for (int i = 0; i < 5; i++) {
					if (!isOccupied[i]) {
						break;
					}
					else {
						if (i != 4) {
							continue;
						}

						fullDays.add((GregorianCalendar)minDate.clone());
					}
				}

				minDate.add(GregorianCalendar.DAY_OF_MONTH, 1);
			}

			if (fullDays.size() > 0) {
				String message = "The following days have no sites available: ";

				for (int i = 0; i < fullDays.size(); i++) {
					message += (fullDays.get(i).get(GregorianCalendar.MONTH) + 1) + "/" +
							fullDays.get(i).get(GregorianCalendar.DAY_OF_MONTH) + "/" +
							fullDays.get(i).get(GregorianCalendar.YEAR);

					if (!(i == (fullDays.size() - 1))) {
						message += ", ";
					}
				}

				fullLabel.setText(message);
				messagePanel.setVisible(true);
			}
			else {
				fullLabel.setText("");
				messagePanel.setVisible(false);
			}
		}
	}

	/*******************************************************************
	 * Key listener class used for catching keypresses on the table
	 ******************************************************************/
	/*?????????????????????????????????????????????????????????
	 * Currently this KeyListener calls 'undo()' when Ctrl-Z is
	 * pressed. Add in Ctrl-S to save serializable, and Ctrl-L
	 * for loading a serial file
	 */
	private class Key implements KeyListener {
		public void keyPressed(KeyEvent e) {
			// 8 is the backspace key
			if (e.getKeyCode() == 8) {
				int row = table.getSelectedRow();
				if (row != -1) {
					int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this entry?");

					// 0 means yes
					if (result == 0) {
						sModel.remove(row);

						checkIfFull();
					}
				}
			}

			if (e.getKeyCode() == KeyEvent.VK_Z) {

				sModel.undo();
				checkIfFull();
			}

			// Ctrl-S will save serializable
			if (e.getKeyCode() == KeyEvent.VK_S) {
				JFileChooser chooser = new JFileChooser();
				int status = chooser.showSaveDialog(null);
				if (status == JFileChooser.APPROVE_OPTION) {
					String filename = chooser.getSelectedFile().getAbsolutePath();
					sModel.saveSerial(filename);
				}

			}

			// Ctrl-L will load a serial file
			if (e.getKeyCode() == KeyEvent.VK_L) {
				JFileChooser chooser = new JFileChooser();
				int status = chooser.showOpenDialog(null);
				if (status == JFileChooser.APPROVE_OPTION) {
					String filename = chooser.getSelectedFile().getAbsolutePath();
					sModel.loadSerial(filename);
					checkIfFull();
				}
			}

		}

		public void keyTyped(KeyEvent e) {
			// Unused, but is abstract
		}


		public void keyReleased(KeyEvent e) {
			// Unused, but is abstract
		}

	}
}