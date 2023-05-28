package gui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.*;
import client.ClientMissionHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ClientOpeningScreenController implements EventHandler<WindowEvent> {
	@FXML
	private TextField IpTxt;
	@FXML
	private TextField PortTxt;

	@FXML
	private Button loginButton;

	@FXML
	void clickLogin(MouseEvent event) throws Exception {
	    String ipAddress = IpTxt.getText();
	    String port = PortTxt.getText();
	    ClientMissionHandler.CONNECT_TO_SERVER(event, ipAddress, port);
	}
	

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ClientOpeningScreen.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("CEMS Connect To Server");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);

		primaryStage.setOnCloseRequest(this);
	}

	@Override
	public void handle(WindowEvent event) {
		// TODO Auto-generated method stub

	}

}
