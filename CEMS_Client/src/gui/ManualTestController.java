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
import javafx.scene.control.TreeTableView;


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

public  class ManualTestController implements EventHandler<WindowEvent> {
	
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
		private TableColumn<Test, String> idcol;
		
		@FXML
		private TableColumn<Test, String> subjectcol;
		
		@FXML
		private TableColumn<Test, String> coursecol;
		
		@FXML
		private TableColumn<Test, String> authorcol;
		
		@FXML
		private TableColumn<Test, Integer> duration;
		
		@FXML
		private TableColumn<Test, String> codeCol;
		
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
		idcol.setCellValueFactory((Callback) new PropertyValueFactory<Test, String>("Id"));
		subjectcol.setCellValueFactory((Callback) new PropertyValueFactory<Test, String>("Subject"));
		coursecol.setCellValueFactory((Callback) new PropertyValueFactory<Test, String>("Course"));
		authorcol.setCellValueFactory((Callback) new PropertyValueFactory<Test, String>("Author"));
		duration.setCellValueFactory((Callback) new PropertyValueFactory<Test, Integer>("Duration"));
		codeCol.setCellValueFactory((Callback) new PropertyValueFactory<Test, String>("TestCode"));
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
		ClientMissionHandler.GET_TESTS(Test, this.table);
		//timerLabel.setText("00:00");

        // Create the  for the timer
        //timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
           // secondsElapsed++;
           // updateTimerLabel();
       // }));

        // Set the to repeat indefinitely
       // timeline.setCycleCount(Animation.INDEFINITE);
      
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

	    // Set the initial directory for file selection
	    String userHomeDirectory = System.getProperty("user.home");
	    fileChooser.setInitialDirectory(new File(userHomeDirectory));

	    // Add filters to specify the allowed file types (optional)
	    fileChooser.getExtensionFilters().add(new ExtensionFilter("Word Files", "*.doc", "*.docx"));

	    // Show the file chooser dialog and get the selected file
	    File selectedFile = fileChooser.showOpenDialog(null);

	    if (selectedFile != null) {
	        // Save the file to the "uploads" folder in the current directory
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
	            //outputTextArea.appendText(message);
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

    

	@Override
	public void handle(WindowEvent event) {
		// TODO Auto-generated method stub
		
	}
}
