package gui;

import java.net.URL;
import java.util.ResourceBundle;

import common.Question;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Node;
import javafx.util.Callback;
import javafx.scene.control.cell.PropertyValueFactory;
import client.ClientMissionHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;

public class ManualTestController implements EventHandler<WindowEvent> {
	
		@FXML
		private Button LogoutButton;
		
		@FXML
		private Button submitButton;
		
		@FXML
		private Button downloadButtom;
		
		@FXML
		private Button uploadButtom;
		
		@FXML
		private TableColumn<Question, String> idcol;
		
		@FXML
		private TableColumn<Question, String> subjectcol;
		
		@FXML
		private TableColumn<Question, String> coursecol;
		
		@FXML
		private TableColumn<Question, String> authorcol;
		
		
	   @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	  
	


	public void start(final Stage primaryStage) throws Exception {
		System.out.println("check1");
		Parent root = FXMLLoader.load(this.getClass().getResource("/gui/ManuallTestController.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("CEMS Student");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(this);
	}
	
	void Back(final ActionEvent event) throws Exception {
		ClientMissionHandler.DISCONNECT_FROM_SERVER();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		ClientOpeningScreenController openScreen = new ClientOpeningScreenController();
		openScreen.start(primaryStage);
	}
	  @FXML
	    void GetTests(ActionEvent event) {

	    }

	    @FXML
	    void logout(ActionEvent event) {

	    }

	    @FXML
	    void submit(ActionEvent event) {

	    }

	    @FXML
	    void initialize() {

	    }

	@Override
	public void handle(WindowEvent event) {
		// TODO Auto-generated method stub

	}
}