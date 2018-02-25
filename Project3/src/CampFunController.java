
/**
 * Sample Skeleton for 'CampFun.fxml' Controller Class
 */

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class CampFunController extends GUICampingReg {

	private int total;
	private siteDate siteDateObject;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the
			// FXMLLoader
	private URL location;

	@FXML // fx:id="image"
	private ImageView image; // Value injected by FXMLLoader

	@FXML // fx:id="dateSelect"
	private DatePicker dateSelect; // Value injected by FXMLLoader

	@FXML // fx:id="okButton"
	private Button okButton; // Value injected by FXMLLoader

	@FXML // fx:id="site2"
	private Label site2; // Value injected by FXMLLoader

	@FXML // fx:id="site1"
	private Label site1; // Value injected by FXMLLoader

	@FXML // fx:id="site3"
	private Label site3; // Value injected by FXMLLoader

	@FXML // fx:id="site4"
	private Label site4; // Value injected by FXMLLoader

	@FXML // fx:id="site5"
	private Label site5; // Value injected by FXMLLoader

	@FXML
	void okButtonListener(ActionEvent event) {
		try {
		total = 0;
		
		site1.setTextFill(Color.GREEN);
		site2.setTextFill(Color.GREEN);
		site3.setTextFill(Color.GREEN);
		site4.setTextFill(Color.GREEN);
		site5.setTextFill(Color.GREEN);

		String date = dateSelect.getValue()
				.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		String[] checkDate = date.split("/");
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(GregorianCalendar.YEAR, Integer.parseInt(checkDate[2]));
		gc.set(GregorianCalendar.MONTH, Integer.parseInt(checkDate[0]) - 1);
		gc.set(GregorianCalendar.DATE, Integer.parseInt(checkDate[1]));

		total = gc.get(Calendar.DAY_OF_YEAR);

		if(Integer.parseInt(checkDate[2]) > 2017) {
			total += 366 * (Integer.parseInt(checkDate[2]) - 2017);
		}
		
		siteDateObject = siteController.datesOccupied.get(total);
		
		System.out.println("Implied "+total);
		
		if(siteDateObject.getIndex(1) == true){
		site1.setTextFill(Color.GREEN);
		site1.setText("Available");
		} else {
			site1.setTextFill(Color.RED);
			site1.setText("Taken");
		}
		
		if(siteDateObject.getIndex(2) == true){
			site2.setTextFill(Color.GREEN);
			site2.setText("Available");
			} else {
				site2.setTextFill(Color.RED);
				site2.setText("Taken");
			}
		
		if(siteDateObject.getIndex(3) == true){
			site3.setTextFill(Color.GREEN);
			site3.setText("Available");
			} else {
				site3.setTextFill(Color.RED);
				site3.setText("Taken");
			}
		
		if(siteDateObject.getIndex(4) == true){
			site4.setTextFill(Color.GREEN);
			site4.setText("Available");
			} else {
				site4.setTextFill(Color.RED);
				site4.setText("Taken");
			}
		
		if(siteDateObject.getIndex(5) == true){
			site5.setTextFill(Color.GREEN);
			site5.setText("Available");
			} else {
				site5.setTextFill(Color.RED);
				site5.setText("Taken");
			}
		
		}catch(RuntimeException e) {
			JOptionPane.showMessageDialog(null, "Please Pick a Date");
		}
		

	}

	@FXML // This method is called by the FXMLLoader when initialization
			// is complete
	void initialize() {
		// Load image to display
		Image campground = new Image("Camp Fun.png");
		image.setImage(campground);

		assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'CampFun.fxml'.";
		assert dateSelect != null : "fx:id=\"dateSelect\" was not injected: check your FXML file 'CampFun.fxml'.";
		assert okButton != null : "fx:id=\"okButton\" was not injected: check your FXML file 'CampFun.fxml'.";
		assert site2 != null : "fx:id=\"site2\" was not injected: check your FXML file 'CampFun.fxml'.";
		assert site1 != null : "fx:id=\"site1\" was not injected: check your FXML file 'CampFun.fxml'.";
		assert site3 != null : "fx:id=\"site3\" was not injected: check your FXML file 'CampFun.fxml'.";
		assert site4 != null : "fx:id=\"site4\" was not injected: check your FXML file 'CampFun.fxml'.";
		assert site5 != null : "fx:id=\"site5\" was not injected: check your FXML file 'CampFun.fxml'.";

	}
}
