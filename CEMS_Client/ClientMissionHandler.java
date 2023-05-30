package client;

import java.net.InetAddress;
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

	//get the test data from the Db and shoe it in the screen 
	public static void GET_TESTS(ObservableList<Test> test, TableView<Test> table) {
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
		
	}

}
