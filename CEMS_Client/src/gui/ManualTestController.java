package gui;

import javafx.stage.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

import common.Question;
import common.Test;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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
import javafx.scene.control.TextArea;
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
		private TextArea outputTextArea;
		
		@FXML
		private TableColumn<Question, String> idcol;
		
		@FXML
		private TableColumn<Question, String> subjectcol;
		
		@FXML
		private TableColumn<Question, String> coursecol;
		
		@FXML
		private TableColumn<Question, String> authorcol;
		
		@FXML
		private TableColumn<Question, String> duration;
		
		
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
		System.out.println("ManuallTestController");
		Parent root = FXMLLoader.load(this.getClass().getResource("/gui/manuallTestController.fxml"));
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
		authorcol.setCellValueFactory((Callback) new PropertyValueFactory<Question, String>("authorcol"));
		duration.setCellValueFactory((Callback) new PropertyValueFactory<Question, String>("duration"));
	}

	void Back(final ActionEvent event) throws Exception {
		ClientMissionHandler.DISCONNECT_FROM_SERVER();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		ClientOpeningScreenController openScreen = new ClientOpeningScreenController();
		openScreen.start(primaryStage);
	}
	
	
	    @FXML
	    void logout(ActionEvent event) {

	    }

	    //submit the test after uploading the file 
	    @FXML
	    void submit(ActionEvent event) throws IOException {
	    	
	    	 // Load the FXML file for the student menu screen
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/StudentMenuPage.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        
	     // Create a new stage for the student menu screen
	        Stage studentMenuStage = new Stage();
	        studentMenuStage.setTitle("Student Menu");
	        studentMenuStage.setScene(scene);
	        studentMenuStage.show();
	        
	     // Close the current screen
	        ((Node) event.getSource()).getScene().getWindow().hide();
	        
	    }
	    
	    //upload the test file 
	    @SuppressWarnings("deprecation")
		@FXML
	    void upload(ActionEvent event) {
	    	FileChooser fileChooser = new FileChooser();
	        fileChooser.setTitle("Select Test File");
	        
	        // Set the initial directory for file selection (optional)
	        fileChooser.setInitialDirectory(new File("C:/Users/Username/Documents"));
	        
	        // Add filters to specify the allowed file types (optional)
	        fileChooser.getExtensionFilters().add(new ExtensionFilter("Word Files", "*.doc", "*.docx"));
	        
	        // Show the file chooser dialog and get the selected file
	        File selectedFile = fileChooser.showOpenDialog(null);
	        
	        if (selectedFile != null) {
	           
	            
	            //Save the file to the "uploads" folder in the current directory
	            Path uploadsFolder = Paths.get("uploads");
	            if (!Files.exists(uploadsFolder)) {
	                try {
	                    Files.createDirectory(uploadsFolder);
	                } catch (IOException e) {
	                    e.printStackTrace();
	                    // Handle directory creation error
	                }
	            }
	            
	            // Define the destination file path in the uploads folder
	            String fileName = selectedFile.getName();
	            Path destination = uploadsFolder.resolve(fileName);
	            
	            try {
	                // Copy the selected file to the destination folder
	                Files.copy(selectedFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
	                // File uploaded successfully
	                System.out.println("File uploaded: " + destination);
	                String message = "File uploaded: " + destination;
	                outputTextArea.appendText(message);
	                // Show a pop-up dialog
	                Alert alert = new Alert(Alert.AlertType.INFORMATION);
	                alert.setTitle("Upload Successful");
	                alert.setHeaderText(null);
	                alert.setContentText("The test file has been uploaded successfully.");
	                alert.showAndWait();
	            } catch (IOException e) {
	                e.printStackTrace();
	                // Handle file upload error
	            }
	        }
	    }
	    
	    //initialize the table and the columns and the timer 
	    
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
	    
	    
	    void startTimer() {
	    	Test selectedTest = table.getSelectionModel().getSelectedItem();
	        if (selectedTest != null) {
	            int testDuration = selectedTest.getDuration(); // Assuming 'duration' is an integer field in the 'Test' class
	            secondsElapsed = 0;
	            timeline.stop();
	            timeline.getKeyFrames().setAll(new KeyFrame(Duration.seconds(1), event -> {
	                secondsElapsed++;
	                updateTimerLabel();
	                if (secondsElapsed >= testDuration) {
	                    timeline.stop();
	                    // Perform any action you want when the timer reaches the test duration
	                }
	            }));
	            timeline.play();
	    }
	    }

	    
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