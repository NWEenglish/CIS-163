import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.*;

public class CampFun extends Application{

	protected Stage stage;
	
	public void start(Stage stage) throws Exception {

		this.stage = stage;
		
		java.net.URL url = getClass().getResource("CampFun.fxml");
		Parent root = FXMLLoader.load(url);
		
		Scene scene = new Scene(root);
		
		this.stage.setTitle("Show Camp Dates");
		this.stage.setScene(scene);
		this.stage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);

	}
	
	

}
