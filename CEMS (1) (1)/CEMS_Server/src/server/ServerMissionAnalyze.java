package server;

import java.sql.SQLException;

import common.ClientStatus;
import common.MissionPack;
import entities.DatabaseConnector;
import ocsf.server.ConnectionToClient;

public class ServerMissionAnalyze {
	
	public static void MissionsAnalyze(MissionPack obj,ConnectionToClient client) throws SQLException {
		switch (obj.getMission()) {
		case GET_QUESTIONS: {
			QueryExecutor.getQuestionsData(obj, DatabaseConnector.getDatabaseConnectorInstance().getConnection());
			break;
		}

		case EDIT_QUESTIONS_DATA: {
			QueryExecutor.updateClientInDatabase(obj, DatabaseConnector.getDatabaseConnectorInstance().getConnection());
			break;
		}

		case SEND_CONNECTION_DETAILS: {
			QueryExecutor.updateClientList(obj, client, ClientStatus.CONNECTED);
			break;
		}

		case SEND_DISCONNECTION_DETAILS: {
			QueryExecutor.updateClientList(obj, client, ClientStatus.DISCONNECTED);
		}

		}
	}
}
