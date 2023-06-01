package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import common.*;
import entities.ConnectedClient;
import javafx.collections.ObservableList;
import ocsf.server.ConnectionToClient;

public class QueryExecutor {
	public static void getQuestionsData(MissionPack obj, Connection con) {

		List<Question> questions = new ArrayList<Question>();
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM cems.question");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				questions.add(new Question(rs.getString("id"), rs.getString("subject"), rs.getString("courseName"),
						rs.getString("questionText"), rs.getString("questionNumber"), rs.getString("lecturer")));
			}
			System.out.println("Retrieved " + questions.size() + " questions from the database.");
			rs.close();
			if (questions.size() > 0) {
				obj.setResponse(Response.FOUND_QUESTIONS);
				obj.setInformation(questions);
				System.out.println(questions.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Importing orders from cems.question failed!");
			obj.setResponse(Response.DIDNT_FOUND_QUESTIONS);
		}
	}
	//Tests query
	public static void getTestsData(MissionPack obj, Connection con) {
		System.out.println("getTestsData");
		List<Test> tests = new ArrayList<Test>();
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM cems.tests");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				tests.add(new Test(rs.getString("Select"),rs.getString("ID"), rs.getString("Subject"), rs.getString("Course"),
						rs.getString("Author"), rs.getInt("Duration"),rs.getString("TestCode")));
			}
			System.out.println("Retrieved " + tests.size() + " tests from the database.");
			rs.close();
			if (tests.size() > 0) {
				obj.setResponse(Response.FOUND_TESTS);
				obj.setInformation(tests);
				System.out.println(tests.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Importing orders from cems.Test failed!");
			obj.setResponse(Response.DIDNT_FOUND_TESTS);
		}
	}

	public static void updateClientInDatabase(MissionPack obj, Connection con) {
		Question question = (Question) obj.getInformation();
		PreparedStatement ps = null;
		boolean idExists = false;
		try {
			ps = con.prepareStatement("SELECT * FROM cems.question");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getString("id").equals(question.getId())) {
					idExists = true;
					break;
				}
			}

		} catch (SQLException sqlException) {
			System.out.println("Statement failure");
		}
		if ((idExists == false)) {
			obj.setResponse(Response.EDIT_QUESTIONS_FAILD);
			return;
		} else {
			try {
				ps = con.prepareStatement(
						"UPDATE cems.question SET id = ?, questionText = ?, questionNumber = ? WHERE id = ?");
			} catch (SQLException sqlException) {
				System.out.println("Statement failure");
			}

			System.out
					.println(question.getQuestionText() + " " + question.getQuestionNumber() + " " + question.getId());
			try {
				String subject = question.getId().substring(0, 2);
				ps.setString(1, subject + question.getQuestionNumber());
				ps.setString(2, question.getQuestionText());
				ps.setString(3, question.getQuestionNumber());
				ps.setString(4, question.getId());
				ps.executeUpdate();
				obj.setInformation(question);
				obj.setResponse(Response.EDIT_QUESTIONS_SUCCESS);

			} catch (Exception exception) {
				System.out.println("Executing statement-Updating question text and question number has failed");
				obj.setResponse(Response.EDIT_QUESTIONS_FAILD);

				exception.printStackTrace();

			}
		}
	}

	public static void updateClientList(MissionPack obj, ConnectionToClient client, ClientStatus connectionStatus) {
		ObservableList<ConnectedClient> clientList = ServerConfiguration.getClientList();

		for (int i = 0; i < clientList.size(); i++) {
			/* Comparing clients by IP addresses */
			if (clientList.get(i).getIp().equals(client.getInetAddress().getHostAddress()))
				clientList.remove(i);
		}

		/*
		 * In both cases of Connect and Disconnected we will need to add Client into the
		 * list so this function covers both of them simultaneously
		 */

		boolean hasAdd = clientList.add(new ConnectedClient(client.getInetAddress().getHostAddress(),
				client.getInetAddress().getHostName(), connectionStatus));
		if (hasAdd)
			obj.setResponse(Response.UPDATE_CONNECTION_SUCCESS);
		else
			obj.setResponse(Response.UPDATE_CONNECTION_FAILD);
		ServerConfiguration.setClientList(clientList);

	}

	public static void getLoginInfo(MissionPack obj, Connection con) {
		// Extract user credentials from the MissionPack object
		UserCredentials credentials = (UserCredentials) obj.getInformation();
		String username = credentials.getUserName();
		String password = credentials.getPassword();
		PreparedStatement ps = null;
		// Execute the SQL query to search for user credentials
		try {
			String query = "SELECT * FROM user WHERE username = ? AND password = ?";
			ps = con.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet resultSet = ps.executeQuery();

			// Check if the user credentials are found in the database
			if (resultSet.next()) {
				// User credentials found, send a success response to the client
				credentials.setUserType(resultSet.getString(3));
				obj.setInformation(credentials);
				obj.setResponse(Response.LOGIN_SUCCESS);
			} else {
				// User credentials not found, send a failure response to the client
				obj.setResponse(Response.LOGIN_FAILURE);
			}
		} catch (SQLException e) {
			// Handle any exceptions that occur during the database operation
			e.printStackTrace();
			// Send an error response to the client
			obj.setResponse(Response.LOGIN_FAILURE);
		}
	}

}