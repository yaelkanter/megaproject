package gui;

import entities.*;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Callback;
import javafx.scene.control.cell.PropertyValueFactory;
import client.ClientMissionHandler;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class GetQuestionController implements EventHandler<WindowEvent> {
	
	@FXML
	private Button backBtn;

	@FXML
	private TableColumn<Question, String> lecturerColTbl;

	@FXML
	private Button submitBtn;

    @FXML
    private TableColumn<Question, String> questionNumberColTbl;

	@FXML
	private TableColumn<Question, String> subjectColTbl;

	@FXML
	private Button getQuestionsBtn;

	@FXML
	private TableColumn<Question, String> idColTbl;

	@FXML
	private TableColumn<Question, String> courseNameColTbl;

	@FXML
	private TextField editQuestionText;

	@FXML
	private TextField editQuestionNumber;

	@FXML
	private TextField idInsert;

	@FXML
	private Label statusLabel;

	@FXML
	private TableColumn<Question, String> questionTextColTbl;

	@FXML
	private TableView<Question> table;
	
	ObservableList<Question> Questions = FXCollections.observableArrayList();

	public void start(final Stage primaryStage) throws Exception {
		final Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/gui/GetQuestions.fxml"));
		final Scene scene = new Scene(root);
		primaryStage.setTitle("CEMS");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(this);
	}

	public void initialize() {
		setColumnsInTable();
		// This method is requesting data from the Server
		Questions.clear();
		// Setting the Data to be displayed in the TableView
		table.setItems(Questions);
		table.autosize();
		table.setEditable(true);
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setColumnsInTable() {
		idColTbl.setCellValueFactory((Callback) new PropertyValueFactory<Question, String>("id"));
		subjectColTbl.setCellValueFactory((Callback) new PropertyValueFactory<Question, String>("subject"));
		courseNameColTbl.setCellValueFactory((Callback) new PropertyValueFactory<Question, String>("courseName"));
		questionTextColTbl.setCellValueFactory((Callback) new PropertyValueFactory<Question, String>("questionText"));
		questionNumberColTbl.setCellValueFactory((Callback) new PropertyValueFactory<Question, String>("questionNumber"));
		lecturerColTbl.setCellValueFactory((Callback) new PropertyValueFactory<Question, String>("lecturer"));
	}

	@FXML
	void Back(final ActionEvent event) throws Exception {
		ClientMissionHandler.DISCONNECT_FROM_SERVER();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		ClientOpeningScreenController openScreen = new ClientOpeningScreenController();
		openScreen.start(primaryStage);
	}

	@FXML
	void GetQuestions(final ActionEvent event) {
		ClientMissionHandler.GET_QUESTIONS(this.Questions, this.table, this.statusLabel);
	}

	@FXML
	void EditQuestion(final ActionEvent event) {
		ClientMissionHandler.EDIT_QUESTIONS(this.statusLabel, this.editQuestionText, this.editQuestionNumber,
				this.idInsert);
	}

	@Override
	public void handle(WindowEvent event) {
		// TODO Auto-generated method stub
		
	}

}