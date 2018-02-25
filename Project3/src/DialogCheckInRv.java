import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DialogCheckInRv extends JDialog {

	private JTextField nameTxt;
	private JTextField occupyOnDateText;
	private JTextField stayingTxt;
	private JComboBox siteNumberTxt;
	private Integer[] siteNumbers;
	private JComboBox powerTxt;
	private JButton okButton;
	private JButton cancelButton;
	protected boolean closeStatus;
	private Site unit;
	private JLabel nameTextLabel;
	private JLabel siteNumberTxtLabel;
	private JLabel occupyOnDateTextLabel;
	private JLabel stayingTxtLabel;
	private JLabel powerTxtLabel;
	private Integer[] powerAmps;

	private JPanel main;
	private JPanel userInput;
	private JPanel buttonInput;

	private JPanel[] userInputArray;;
	private JPanel[] buttonInputArray;

	private RV rv;

	private String defaultName;
	private String defaultDays;
	
	private int daysStaying;

	public DialogCheckInRv(JFrame paOccupy, RV r) {

		super(paOccupy, "Check in RV", true);
		this.rv = r;
		
		defaultName = r.getNameReserving();
		defaultDays = Integer.toString(r.getDaysStaying());
		
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
		powerTxtLabel = new JLabel("Power in Amps");

		// JTextFields
		nameTxt = new JTextField(10);
		siteNumbers = new Integer[] { 1, 2, 3, 4, 5 };
		siteNumberTxt = new JComboBox(siteNumbers);
		occupyOnDateText = new JTextField(10);
		nameTxt.setText(defaultName);

		// Change for later
		DateFormat showDate = new SimpleDateFormat("MM/dd/yyyy");
		Date today = new Date();
		occupyOnDateText.setText(showDate.format(today));
		// https://stackoverflow.com/questions/12592600/printing-the-correct-date-with-java-gregoriancalendar-class

		stayingTxt = new JTextField(10);
		powerAmps = new Integer[] { 30, 40, 50 };
		powerTxt = new JComboBox(powerAmps);
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
		userInputArray[8].add(powerTxtLabel);
		userInputArray[9].add(powerTxt);

		buttonInputArray[0].add(okButton);
		okButton.addActionListener(new okButtonListener());

		buttonInputArray[1].add(cancelButton);
		cancelButton.addActionListener(new cancelButtonListener());

		main.add(userInput, BorderLayout.CENTER);
		main.add(buttonInput, BorderLayout.SOUTH);

		add(main);

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
					rv.setCheckIn(checkIn);

				} catch (ParseException er) {
					closeStatus = false;
					JOptionPane.showMessageDialog(null,
							"Invalid Check-in Date. \nUse format: mm/dd/yyyy");
				}

				int power = 0;
				power = (Integer) powerTxt.getSelectedItem();

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
					closeStatus = false;
				}

				rv.setNameReserving(nameTxt.getText());
				rv.setPower(power);
				rv.setSiteNumber(siteNum);
				rv.setDaysStaying(daysStaying);

			} catch (RuntimeException er) {
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

	public boolean isCloseStatus() {
		return closeStatus;
	}

	public void setCloseStatus(boolean closeStatus) {
		this.closeStatus = closeStatus;
	}

	public Site getUnit() {
		return unit;
	}

	public void setUnit(Site unit) {
		this.unit = unit;
	}

	public JLabel getNameTextLabel() {
		return nameTextLabel;
	}

	public void setNameTextLabel(JLabel nameTextLabel) {
		this.nameTextLabel = nameTextLabel;
	}

	public JLabel getSiteNumberTxtLabel() {
		return siteNumberTxtLabel;
	}

	public void setSiteNumberTxtLabel(JLabel siteNumberTxtLabel) {
		this.siteNumberTxtLabel = siteNumberTxtLabel;
	}

	public JLabel getOccupyOnDateTextLabel() {
		return occupyOnDateTextLabel;
	}

	public void setOccupyOnDateTextLabel(JLabel occupyOnDateTextLabel) {
		this.occupyOnDateTextLabel = occupyOnDateTextLabel;
	}

	public JLabel getStayingTxtLabel() {
		return stayingTxtLabel;
	}

	public void setStayingTxtLabel(JLabel stayingTxtLabel) {
		this.stayingTxtLabel = stayingTxtLabel;
	}

	public JLabel getPowerTxtLabel() {
		return powerTxtLabel;
	}

	public void setPowerTxtLabel(JLabel powerTxtLabel) {
		this.powerTxtLabel = powerTxtLabel;
	}

	public Integer[] getPowerAmps() {
		return powerAmps;
	}

	public void setPowerAmps(Integer[] powerAmps) {
		this.powerAmps = powerAmps;
	}

	public JPanel getMain() {
		return main;
	}

	public void setMain(JPanel main) {
		this.main = main;
	}

	public JPanel getUserInput() {
		return userInput;
	}

	public void setUserInput(JPanel userInput) {
		this.userInput = userInput;
	}

	public JPanel getButtonInput() {
		return buttonInput;
	}

	public void setButtonInput(JPanel buttonInput) {
		this.buttonInput = buttonInput;
	}

	public JPanel[] getUserInputArray() {
		return userInputArray;
	}

	public void setUserInputArray(JPanel[] userInputArray) {
		this.userInputArray = userInputArray;
	}

	public JPanel[] getButtonInputArray() {
		return buttonInputArray;
	}

	public void setButtonInputArray(JPanel[] buttonInputArray) {
		this.buttonInputArray = buttonInputArray;
	}

	/**
	 * @return the daysStaying
	 */
	public int getDaysStaying() {
		return daysStaying;
	}

	/**
	 * @param daysStaying the daysStaying to set
	 */
	public void setDaysStaying(int daysStaying) {
		this.daysStaying = daysStaying;
	}

}
