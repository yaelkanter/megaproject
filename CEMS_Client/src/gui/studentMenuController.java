package gui;

import client.ClientMissionHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class studentMenuController implements EventHandler<WindowEvent> {
	@FXML
	private Button MenualtestButton;
	
	@FXML
	private Button OnlinetestsButton;
	
	@FXML
	private Button GradesButton;
	@FXML
	private Button LogoutButton;

	public void start(final Stage primaryStage) throws Exception {
		System.out.println("check1");
		Parent root = FXMLLoader.load(this.getClass().getResource("/gui/student MenuPage.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("CEMS Student");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(this);
	}
	
	@FXML
	public void handleManualtestClick(ActionEvent event) throws Exception {
		
		Stage primaryStage = new Stage();
		ManualTestController clientController = new ManualTestController();
		clientController.start(primaryStage);
		((Node) event.getSource()).getScene().getWindow().hide();//hide the screen 
	}	
		
	@Override
	public void handle(WindowEvent event) {
		

	}
}
