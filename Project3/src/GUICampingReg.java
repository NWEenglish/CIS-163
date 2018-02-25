
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class GUICampingReg extends JFrame {

	private JMenuBar menu;

	private JMenu fileMenu;
	private JMenuItem fileOpenSerial;
	private JMenuItem fileSaveSerial;
	private JMenuItem fileOpenText;
	private JMenuItem fileSaveText;
	private JMenuItem exit;

	private JMenu checkingIn;
	private JMenu checkOutMenu;
	private JMenuItem checkInTent;
	private JMenuItem checkInRV;
	private JMenuItem checkOut;
	private JMenuItem dateStatus;
	private JMenuItem undo;
	private JMenuItem change;
	private JMenuItem deleteRow;

	private JTable mainTable;
	private JScrollPane scrollPane;

	protected SiteModel siteController;

	protected JFileChooser fileChoose;
	protected File file;
	
	protected static CampFun campFun;

	public GUICampingReg() {

		super("Camping Reservation");

		menu = new JMenuBar();
		fileMenu = new JMenu("File");

		fileOpenSerial = new JMenuItem("Open Serial");
		fileOpenSerial.addActionListener(new fileOpenSerialListener());
		fileSaveText = new JMenuItem("Save Text");
		fileSaveText.addActionListener(new fileSaveTextListener());
		fileSaveSerial = new JMenuItem("Save Serial");
		fileSaveSerial.addActionListener(new fileSaveSerialListener());
		fileOpenText = new JMenuItem("Open Text");
		fileOpenText.addActionListener(new fileOpenTextListener());
		exit = new JMenuItem("Exit");
		exit.addActionListener(new exitListener());

		checkingIn = new JMenu("Checking In");
		checkInTent = new JMenuItem("Check-In Tent");
		checkInTent.addActionListener(new checkInTentListener());
		checkInRV = new JMenuItem("Check-In RV");
		checkInRV.addActionListener(new checkInRVListener());
		change = new JMenuItem("Change selected row");
		change.addActionListener(new changeListener());

		checkOutMenu = new JMenu("Check Out");
		checkOut = new JMenuItem("Check out selected row");
		checkOut.addActionListener(new checkOutListener());
		deleteRow = new JMenuItem("Delete selected row");
		deleteRow.addActionListener(new deleteListener());
		undo = new JMenuItem("Undo");
		undo.addActionListener(new undoListener());

		setLayout(new BorderLayout());

		fileMenu.add(fileOpenSerial);
		fileMenu.add(fileSaveSerial);
		fileMenu.add(fileOpenText);
		fileMenu.add(fileSaveText);
		fileMenu.add(undo);
		fileMenu.add(exit);
		menu.add(fileMenu);

		checkingIn.add(checkInTent);
		checkingIn.add(checkInRV);
		checkingIn.add(change);
		menu.add(checkingIn);

		checkOutMenu.add(checkOut);
		checkOutMenu.add(deleteRow);
		menu.add(checkOutMenu);

		// Add JTable
		siteController = new SiteModel();
		mainTable = new JTable(siteController);

		// File chooser
		fileChoose = new JFileChooser();
		file = new File("serial.dat");

		scrollPane = new JScrollPane(mainTable);

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 500);

		add(menu, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);

	}

	/******************************************************************/
	// LOAD SERIAL
	public class fileOpenSerialListener extends JFrame
			implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int ok = fileChoose.showOpenDialog(this);
			if (ok == JFileChooser.APPROVE_OPTION) {
				file = fileChoose.getSelectedFile();
			}

			siteController.loadSerial(file);
		}

	}

	/******************************************************************/
	// ----- UPDATED
	// SAVE TEXT
	public class fileSaveTextListener extends JFrame
			implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			int ok = fileChoose.showSaveDialog(this);
			if (ok == JFileChooser.APPROVE_OPTION) {
				file = fileChoose.getSelectedFile();
			}
			siteController.saveText(file);
		}

	}

	/******************************************************************/
	// SAVE SERIAL
	public class fileSaveSerialListener extends JFrame
			implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			int ok = fileChoose.showSaveDialog(this);
			if (ok == JFileChooser.APPROVE_OPTION) {
				file = fileChoose.getSelectedFile();
			}
			siteController.saveSerial(file);
		}

	}

	/******************************************************************/
	// LOAD TEXT
	public class fileOpenTextListener extends JFrame
			implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int ok = fileChoose.showOpenDialog(this);
			if (ok == JFileChooser.APPROVE_OPTION) {
				file = fileChoose.getSelectedFile();
			}

			siteController.loadText(file);
		}

	}

	/******************************************************************/

	public class exitListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			System.exit(0);

		}

	}

	public class checkInTentListener extends JFrame
			implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Tent t = new Tent();
			checkInTent(t);
		}

	}

	public class checkInRVListener extends JFrame
			implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			RV r = new RV();
			checkInRv(r);
		}

	}

	public class checkOutListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int index = mainTable.getSelectedRow();

			// gets checking out site number
			Site site = siteController.listSites.get(index);
			// makes site available to be filled again
			siteController.undo.add(new UndoSite(site, index, -1));
			siteController.unReserve(site);

			siteController.removeSite(index);
		}
	}


	public class undoListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int last = siteController.undo.size() - 1;
			UndoSite undo = siteController.undo.get(last);

			if (undo.getAddOrDelete() == 1) {
				int index = mainTable.getRowCount() - 1;
				siteController.undoLastAction(index);
			} else if (undo.getAddOrDelete() == -1) {
				Site tempSite = undo.getSite();
				siteController.addSite(tempSite, undo.getIndex());

			}

		}

	}

	public class changeListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int index = mainTable.getSelectedRow();
			// gets checking out site number
			Site site = siteController.listSites.get(index);

			siteController.unReserve(site);
			siteController.removeSite(index);

			// makes site available to be filled again
			siteController.undo.add(new UndoSite(site, index, -1));

			if (site instanceof Tent) {
				checkInTent((Tent) site);
			} else if (site instanceof RV) {
				checkInRv((RV) site);
			}

		}

	}

	public class deleteListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int index = mainTable.getSelectedRow();

			// gets checking out site number
			Site site = siteController.listSites.get(index);
			// makes site available to be filled again
			siteController.undo.add(new UndoSite(site, index, -1));
			siteController.unReserve(site);

			siteController.removeSite(index);
		}

	}

	public void checkInRv(RV r) {
		DialogCheckInRv x = new DialogCheckInRv(this, r);

		if (x.closeStatus == true) {
			try {
				if (siteController.reserve(r) == true)
					siteController.addSite(r,
							mainTable.getRowCount() - 1);
				else {
					JOptionPane.showMessageDialog(null,
							"That site has already been taken, try again.");
				}
			} catch (RuntimeException e) {
				JOptionPane.showMessageDialog(null,
						"Invalid data has been entered.\n"
								+ "Wrong Date\n"
								+ "Date Format:  mm/dd/yyyy");
			}
		}
	}

	public void checkInTent(Tent t) {
		DialogCheckInTent x = new DialogCheckInTent(this, t);

		if (x.closeStatus == true) {
			try {
				if (siteController.reserve(t) == true)
					siteController.addSite(t,
							mainTable.getRowCount() - 1);
				else {
					JOptionPane.showMessageDialog(null,
							"That site has already been taken, try again.");
				}
			} catch (RuntimeException e) {
				JOptionPane.showMessageDialog(null,
						"Invalid data has been entered.\n"
								+ "Wrong Date\n"
								+ "Date Format:  mm/dd/yyyy");
			}
		}
	}

	public static void main(String[] args) {
		new GUICampingReg();
	}

}
