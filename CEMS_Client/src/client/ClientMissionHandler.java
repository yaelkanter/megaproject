package client;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import common.*;
import gui.ClientUI;
import gui.GetQuestionController;
import gui.LecturerController;
import gui.LoginController;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ClientMissionHandler {

	public static void CONNECT_TO_SERVER(final MouseEvent event, final String ip, final String port) throws Exception {
		ClientUI.chat = new ClientController(ip, Integer.parseInt(port));
		MissionPack obj = new MissionPack(Mission.SEND_CONNECTION_DETAILS, null, null);
		final ArrayList<String> details = new ArrayList<String>();
		details.add(InetAddress.getLocalHost().getHostAddress());
		details.add(InetAddress.getLocalHost().getHostName());
		obj.setInformation(details);
		System.out.println("in client");
		ClientUI.chat.accept(obj);
		obj = ClientUI.chat.getResponseFromServer();
		if (obj.getResponse().equals(Response.UPDATE_CONNECTION_SUCCESS)) {
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			LoginController clientController = new LoginController();
			clientController.start(primaryStage);
		} else {
			System.out.println("did not connect to server");
		}
	}

	public static void GET_QUESTIONS(final ObservableList<Question> listView, final TableView<Question> table,
			final Label statusLabel) {
		MissionPack obj = new MissionPack(Mission.GET_QUESTIONS, null, null);
		ClientUI.chat.accept(obj);
		obj = ClientUI.chat.getResponseFromServer();
		if (obj.getResponse() == Response.FOUND_QUESTIONS) {
			listView.clear();
			List<?> list = (List<?>) obj.getInformation();
			for (int i = 0; i < list.size(); ++i) {
				listView.add((Question) list.get(i));
				System.out.println(list.get(i));
			}
			table.setItems(listView);
			statusLabel.setText("Upload Success");
		} else {
			statusLabel.setText("Upload Failed");
		}
	}

	public static void DISCONNECT_FROM_SERVER() {
		final MissionPack obj = new MissionPack(Mission.SEND_DISCONNECTION_DETAILS, null, null);
		final ArrayList<String> details = new ArrayList<String>();
		try {
			details.add(InetAddress.getLocalHost().getHostAddress());
			details.add(InetAddress.getLocalHost().getHostName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		obj.setInformation(details);
		ClientUI.chat.accept(obj);
	}

	public static void EDIT_QUESTIONS(final Label statusLabel, final TextField editQuestionText,
			final TextField editQuestionNumber, final TextField idInsert) {
		final Question Question = new Question();
		Question.setQuestionText(editQuestionText.getText());
		Question.setQuestionNumber(editQuestionNumber.getText());
		Question.setId(idInsert.getText());
		MissionPack obj = new MissionPack(Mission.EDIT_QUESTIONS_DATA, null, Question);
		ClientUI.chat.accept(obj);
		obj = ClientUI.chat.getResponseFromServer();
		if (obj.getResponse() == Response.EDIT_QUESTIONS_FAILD) {
			statusLabel.setText("Edit Failed");
		} else {
			statusLabel.setText("Edit in progress");
		}
	}

	public static void LOGIN(String username, String password) throws Exception {
		UserCredentials uc = new UserCredentials(username, password, null);
		MissionPack obj = new MissionPack(Mission.LOGIN, null, (Object)uc);
		ClientUI.chat.accept(obj);// Send the login request to the server
		obj = ClientUI.chat.getResponseFromServer(); // Receive the response from the server
		LoginController LG = new LoginController();
		if (obj.getResponse() == Response.LOGIN_SUCCESS) {
			uc = (UserCredentials) obj.getInformation();
			LG.setPage(uc);
		} else {
			LG.userNotExistsError();
			System.out.println("user info not in DB");
		}
	}

	//get the test data from the DB and shoe it in the screen 
	public static void GET_TESTS(ObservableList<Test> listView, TableView<Test> table) {
			MissionPack obj = new MissionPack(Mission.GET_TESTS, null, null);
			ClientUI.chat.accept(obj);
			obj = ClientUI.chat.getResponseFromServer();
			if (obj.getResponse() == Response.FOUND_TESTS) {
				listView.clear();
				List<?> list = (List<?>) obj.getInformation();
				for (int i = 0; i < list.size(); ++i) {
					listView.add((Test) list.get(i));
					System.out.println(list.get(i).toString());
					
				}
				table.setItems(listView);
				
			} else {
				System.out.println("No tests found.");
			}
		}
	
	
	
	// the download file operation after enter the code 
	public static void DOWNLOAD_TEST( TextField codeField) {
        String testCode = codeField.getText(); // Get the test code from the text field

        // Check if the test code is valid
        if (isValidTestCode(testCode)) {
            // Perform the necessary steps to download the file
            try {
                // Retrieve the file based on the test code
                File file = retrieveFileFromServer(testCode);

                // Specify the file download location
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save File");
                fileChooser.setInitialFileName(file.getName());

                // Show the file save dialog
                Stage stage = (Stage) codeField.getScene().getWindow();
                File selectedFile = fileChooser.showSaveDialog(stage);

                if (selectedFile != null) {
                    // Copy the file to the selected location
                    Files.copy(file.toPath(), selectedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    // Display a success message
                    //statusLabel.setText("File downloaded successfully.");
                } else {
                    // Display a message if the user cancels the file save dialog
                    //statusLabel.setText("File download cancelled.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                // Display an error message if an exception occurs during file download
                //statusLabel.setText("Error occurred during file download.");
            }
        } else {
            // Display an error message if the test code is invalid
           // statusLabel.setText("Invalid test code.");
        }
    }

    private static boolean isValidTestCode(String testCode) {
        // Implement the logic to validate the test code
        // Return true if the test code is valid, otherwise return false
        // You can use regular expressions, database lookups, or any other validation mechanism
        // based on your requirements
        return true; // Placeholder, replace with your validation logic
    }

    private static File retrieveFileFromServer(String testCode) {
        // Implement the logic to retrieve the file based on the test code from the server
        // Return the File object representing the file to be downloaded
        // You can fetch the file from a database, a file system, or any other storage mechanism
        // based on your server implementation
        // Replace the return statement with your retrieval logic
        return new File("path/to/your/file"); // Placeholder, replace with your retrieval logic
    }

    // ...
}
		
	


