package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import common.*;
import entities.ConnectedClient;
import entities.Question;
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

	public static void updateClientInDatabase(MissionPack obj, Connection con) {
		Question question = (Question) obj.getInformation();
		PreparedStatement ps = null;
		boolean idExists=false;
		
		try {
			ps = con.prepareStatement("SELECT * FROM cems.question");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				if(rs.getString("id").equals(question.getId()))
				{
						idExists=true;
						break;
				}
						
			}
		} catch (SQLException sqlException) {
			System.out.println("Statement failure");
		}
		
		
		if(question.getId().isBlank()||!idExists)
		{
			obj.setResponse(Response.EDIT_SQUESTIONS_FAILD);
			return;
		}
			
		try {
			ps = con.prepareStatement(
					"UPDATE cems.question SET questionText = ?, questionNumber = ? WHERE id = ?");
		} catch (SQLException sqlException) {
			System.out.println("Statement failure");
		}
		
		System.out.println(question.getQuestionText()+" "+question.getQuestionNumber()+" "+question.getId());

		try {
			ps.setString(1, question.getQuestionText());
			ps.setString(2, question.getQuestionNumber());
			ps.setString(3, question.getId());
			ps.executeUpdate();
			
			obj.setInformation(question);
			obj.setResponse(Response.EDIT_QUESTIONS_SUCCESS);
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Executing statement-Updating question text and question number has failed");
			obj.setResponse(Response.EDIT_SQUESTIONS_FAILD);
			return;
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

}
