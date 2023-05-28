package gui;

//import client.ClientController;
import client.ClientMissionHandler;
import common.UserCredentials;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoginController implements EventHandler<WindowEvent> {

	public LoginController() {
		super();
	}

	@FXML
	private TextField usernameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Button loginButton;

	@FXML
	private Label ErrorLabel = null;

	private String userName = null;
	private String Password = null;

	private double xoffset;
	private double yoffset;
	
	public void setErrorLabel(Label errorLabel) {
		this.ErrorLabel = errorLabel;
	}

	// UserCredentials uc = new UserCredentials(username, password, null);
	public void initialize() {
		// Set the initial style for the error label
		ErrorLabel.setTextAlignment(TextAlignment.CENTER);
		ErrorLabel.setTextFill(Color.RED);
		ErrorLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
	}

	public void start(final Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("/gui/login.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("CEMS Login");
		primaryStage.setScene(scene);
		//event handler for when the mouse is pressed AND dragged to move the window
				root.setOnMousePressed(event1 -> {
		        xoffset = event1.getSceneX();
		        yoffset = event1.getSceneY();
		        });
				 // event handler for when the mouse is pressed AND dragged to move the window
		        root.setOnMouseDragged(event1 -> {
		        primaryStage.setX(event1.getScreenX()-xoffset);
		        primaryStage.setY(event1.getScreenY()-yoffset);
		        });
		
		primaryStage.show();
		// primaryStage.setOnCloseRequest(this);
	}

	@FXML
	void handleLoginButtonClicked(ActionEvent event) throws Exception {
		usernameField.getStyleClass().remove("text-field-error");
		passwordField.getStyleClass().remove("text-field-error");
		userName = usernameField.getText();
		Password = passwordField.getText();
		if (userName.equals("") && Password.equals("")) {
			missingDetailsError();
		} else if (Password.equals(""))
			missingPasswordError();
		else if (userName.equals(""))
			missingUserNameError();
		else
			ClientMissionHandler.LOGIN(this.userName, this.Password);
	}

	public void setPage(UserCredentials userCredentials) throws Exception {

		if (userCredentials.getUserType().equals("lecturer")) {
			Stage primaryStage = new Stage();
			LecturerController clientController = new LecturerController();
			clientController.start(primaryStage);
		} else if (userCredentials.getUserType().equals("student")) {
			Stage primaryStage = new Stage();
			studentMenuController clientController = new studentMenuController();
			clientController.start(primaryStage);
		} else if (userCredentials.getUserType().equals("dep_head")) {
			Stage primaryStage = new Stage();
			DepHeadMenuController clientController = new DepHeadMenuController();
			clientController.start(primaryStage);
		} else {
			System.out.println("Invalid user type");
		}
	}

	public void userNotExistsError() {
		if (ErrorLabel != null)
			ErrorLabel.setText("User Not Found!");
	}

	public void missingUserNameError() {
		usernameField.getStyleClass().add("text-field-error");
		usernameField.setStyle("-fx-border-color: red;");
		ErrorLabel.setText("UserName field is Empty!");
	}

	public void missingPasswordError() {
		passwordField.getStyleClass().add("text-field-error");
		passwordField.setStyle("-fx-border-color: red;");
		ErrorLabel.setText("Password field is Empty!");

	}

	public void missingDetailsError() {
		usernameField.getStyleClass().add("text-field-error");
		usernameField.setStyle("-fx-border-color: red;");
		passwordField.getStyleClass().add("text-field-error");
		passwordField.setStyle("-fx-border-color: red;");
		ErrorLabel.setText("UserName & Password fields are Empty!");
	}

	@Override
	public void handle(WindowEvent event) {
		// TODO Auto-generated method stub

	}

}
