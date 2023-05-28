package gui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LecturerController implements EventHandler<WindowEvent> {
	@FXML
	private Button TestsButton;
	@FXML
	private Button QuestionsButton;
	@FXML
	private Button ReportsButton;
	@FXML
	private Button LogoutButton;

	public void start(final Stage primaryStage) throws Exception {
		System.out.println("check1");
		Parent root = FXMLLoader.load(this.getClass().getResource("/gui/LecturerMenuPage.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("CEMS Lecturer");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(this);
	}

	@Override
	public void handle(WindowEvent event) {
		// TODO Auto-generated method stub

	}
}
