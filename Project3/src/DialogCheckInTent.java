import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.*;

public class DialogCheckInTent extends JDialog {

	private JTextField nameTxt;
	private JTextField occupyOnDateText;
	private JTextField stayingTxt;
	private JComboBox siteNumberTxt;
	private JComboBox powerTxt;
	private JButton okButton;
	private JButton cancelButton;
	private Site unit;
	private JLabel nameTextLabel;
	private JLabel siteNumberTxtLabel;
	private JLabel occupyOnDateTextLabel;
	private JLabel stayingTxtLabel;
	private JLabel tentersTxtLabel;

	private Integer[] siteNumbers;
	private Integer[] tentersOcc;

	private JPanel main;
	private JPanel userInput;
	private JPanel buttonInput;

	private JPanel[] userInputArray;
	private JPanel[] buttonInputArray;
	
	private String defaultName;
	private String defaultDays;
	
	private int daysStaying;

	protected static boolean add = true;

	protected boolean closeStatus;

	private Tent tent;

	public DialogCheckInTent(JFrame paOccupy, Tent t) {

		super(paOccupy, "Check in Tent", true);
		this.tent = t;

		defaultName = t.getNameReserving();
		defaultDays = Integer.toString(t.getDaysStaying());
		
		closeStatus = false;
		buildPanel();
		setSize(300, 300);
		setVisible(true);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void buildPanel() {

		// JPanel arrays to make things look nice
		userInputArray = new JPanel[10];
		buttonInputArray = new JPanel[2];

		// JLabels
		nameTextLabel = new JLabel("Name of Reserver");
		siteNumberTxtLabel = new JLabel("Requested site Number");
		occupyOnDateTextLabel = new JLabel("Occupied on Date");
		stayingTxtLabel = new JLabel("Days staying");
		tentersTxtLabel = new JLabel("Number of Tenters");

		// JTextFields
		nameTxt = new JTextField(10);
		siteNumbers = new Integer[] { 1, 2, 3, 4, 5 };
		siteNumberTxt = new JComboBox(siteNumbers);
		nameTxt.setText(defaultName);

		occupyOnDateText = new JTextField(10);

		// Change for later
		DateFormat showDate = new SimpleDateFormat("MM/dd/yyyy");
		Date today = new Date();
		occupyOnDateText.setText(showDate.format(today));
		// https://stackoverflow.com/questions/12592600/printing-the-correct-date-with-java-gregoriancalendar-class

		stayingTxt = new JTextField(10);
		tentersOcc = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		powerTxt = new JComboBox(tentersOcc);
		stayingTxt.setText(defaultDays);

		// Buttons
		cancelButton = new JButton("Cancel");
		okButton = new JButton("Ok");

		// Panels
		main = new JPanel();
		main.setLayout(new BorderLayout());

		userInput = new JPanel();
		userInput.setLayout(new GridLayout(5, 2));

		// Loop to add JPanels
		for (int i = 0; i < userInputArray.length; i++) {
			userInputArray[i] = new JPanel();
			userInputArray[i]
					.setLayout(new FlowLayout(FlowLayout.LEFT));
			userInput.add(userInputArray[i]);
		}

		buttonInput = new JPanel();
		buttonInput.setLayout(new GridLayout(1, 2));

		// Loop to add JPanels for buttons
		for (int i = 0; i < buttonInputArray.length; i++) {
			buttonInputArray[i] = new JPanel();
			buttonInput.add(buttonInputArray[i]);
		}

		userInputArray[0].add(nameTextLabel);
		userInputArray[1].add(nameTxt);
		userInputArray[2].add(siteNumberTxtLabel);
		userInputArray[3].add(siteNumberTxt);
		userInputArray[4].add(occupyOnDateTextLabel);
		userInputArray[5].add(occupyOnDateText);
		userInputArray[6].add(stayingTxtLabel);
		userInputArray[7].add(stayingTxt);
		userInputArray[8].add(tentersTxtLabel);
		userInputArray[9].add(powerTxt);

		buttonInputArray[0].add(okButton);
		okButton.addActionListener(new okButtonListener());

		buttonInputArray[1].add(cancelButton);
		cancelButton.addActionListener(new cancelButtonListener());

		main.add(userInput, BorderLayout.CENTER);
		main.add(buttonInput, BorderLayout.SOUTH);

		add(main);

	}

	public JTextField getNameTxt() {
		return nameTxt;
	}

	public void setNameTxt(JTextField nameTxt) {
		this.nameTxt = nameTxt;
	}

	public JTextField getOccupyOnDateText() {
		return occupyOnDateText;
	}

	public void setOccupyOnDateText(JTextField occupyOnDateText) {
		this.occupyOnDateText = occupyOnDateText;
	}

	public JTextField getStayingTxt() {
		return stayingTxt;
	}

	public void setStayingTxt(JTextField stayingTxt) {
		this.stayingTxt = stayingTxt;
	}

	public JComboBox getPowerTxt() {
		return powerTxt;
	}

	public void setPowerTxt(JComboBox powerTxt) {
		this.powerTxt = powerTxt;
	}

	public Integer[] getTentersOcc() {
		return tentersOcc;
	}

	public void setTentersOcc(Integer[] tentersOcc) {
		this.tentersOcc = tentersOcc;
	}

	public class okButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			closeStatus = true;

			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			Date d;
			try {
				try {
					d = df.parse(occupyOnDateText.getText());
					GregorianCalendar checkIn = new GregorianCalendar();
					checkIn.setTime(d);
					tent.setCheckIn(checkIn);

				} catch (ParseException er) {
					closeStatus = false;
					JOptionPane.showMessageDialog(null,
							"Invalid Check-in Date");
				}

				int numTenters = 0;
				numTenters = powerTxt.getSelectedIndex() + 1;

				int siteNum = 0;
				siteNum = siteNumberTxt.getSelectedIndex() + 1;


				try {
					daysStaying = Integer
							.parseInt(stayingTxt.getText());
					if (daysStaying < 1 || daysStaying > 30) {
						throw new IllegalArgumentException();
					}
				} catch (IllegalArgumentException b) {
					JOptionPane.showMessageDialog(null,
							"Invalid days. Enter 1 - 30");
					add = false;
				}

				tent.setNameReserving(nameTxt.getText());
				tent.setNumOfTenters(numTenters);
				tent.setSiteNumber(siteNum);
				tent.setDaysStaying(daysStaying);

			} catch (RuntimeException ex) {
				throw new RuntimeException();
			}

			finally {
				dispose();
			}

		}

	}

	public class cancelButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int confirm = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to cancel?\n"
							+ "All information will be deleted");
			if (confirm == JOptionPane.YES_OPTION)
				dispose();
		}
	}

	public void actionPerformed(ActionEvent e) {

	}
}
