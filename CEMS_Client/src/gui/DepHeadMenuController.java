package gui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class DepHeadMenuController implements EventHandler<WindowEvent> {
	@FXML
	private Button RequestsButton;
	@FXML
	private Button statsButton;
	@FXML
	private Button reportsButton;
	@FXML
	private Button LogoutButton;

	public void start(final Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("/gui/DepHeadMenuPage.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("CEMS Student");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(this);
	}

	@Override
	public void handle(WindowEvent event) {
		// TODO Auto-generated method stub
	}

}
