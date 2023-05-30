package gui;

import java.net.URL;
import java.util.ResourceBundle;

import common.Question;
import common.Test;
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
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

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
		private TextField codeField;
		
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
	    
	    @FXML
		private TableView<Test> table;
	    
	    @FXML
	    private Label timerLabel;

	    private Timeline timeline;
	    private int secondsElapsed;

	  
	

	ObservableList<Test> Test = FXCollections.observableArrayList();
	public void start(final Stage primaryStage) throws Exception {
		System.out.println("check1");
		Parent root = FXMLLoader.load(this.getClass().getResource("/gui/ManuallTestController.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("CEMS Student");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(this);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setColumnsInTable() {
		idcol.setCellValueFactory((Callback) new PropertyValueFactory<Question, String>("id"));
		subjectcol.setCellValueFactory((Callback) new PropertyValueFactory<Question, String>("subject"));
		coursecol.setCellValueFactory((Callback) new PropertyValueFactory<Question, String>("courseName"));
		authorcol.setCellValueFactory((Callback) new PropertyValueFactory<Question, String>("questionText"));
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
		  ClientMissionHandler.GET_TESTS(this.Test, this.table);
	    }

	    @FXML
	    void logout(ActionEvent event) {

	    }

	    @FXML
	    void submit(ActionEvent event) {

	    }
	    @FXML
	    public void initialize() {
			setColumnsInTable();
			// This method is requesting data from the Server
			Test.clear();
			// Setting the Data to be displayed in the TableView
			table.setItems(Test);
			table.autosize();
			table.setEditable(true);
			timerLabel.setText("00:00");

	        // Create the  for the timer
	        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
	            secondsElapsed++;
	            updateTimerLabel();
	        }));

	        // Set the to repeat indefinitely
	        timeline.setCycleCount(Animation.INDEFINITE);
		}
	  
	  
	    
	    //
	    @FXML
	    void DownloadTest(final ActionEvent event) {
	    	String enteredCode = codeField.getText();
	    	codeField.clear();
	    	//check if the code field is empty and then send message
	    	  if (enteredCode.isEmpty()) {
	    		  codeField.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
	    		  downloadButtom.setDisable(true);
	    	        return;
	    	   }else {
	    		   // Reset the style to the default
	    	        codeField.setStyle("");
	    	        downloadButtom.setDisable(false); // Enable the download button
	    	    
	    	   }
	    	  
	        ClientMissionHandler.DOWNLOAD_TEST(codeField);

	        // Start the timer
	        timeline.play();
	    }
	    
	    @FXML
	    void startTimer() {
	        // Start the timer
	        timeline.play();
	    }

	    @FXML
	    void stopTimer() {
	        // Stop the timer
	        timeline.stop();
	    }
	    private void updateTimerLabel() {
	        // Convert seconds to minutes and seconds
	        int minutes = secondsElapsed / 60;
	        int seconds = secondsElapsed % 60;

	        // Format the time as "mm:ss"
	        String formattedTime = String.format("%02d:%02d", minutes, seconds);

	        // Update the label text
	        timerLabel.setText(formattedTime);
	    }
	    
	 


	@Override
	public void handle(WindowEvent event) {
		// TODO Auto-generated method stub

	}
}