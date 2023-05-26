package server;

import java.io.IOException;
import common.ClientStatus;
import common.MissionPack;
import entities.ConnectedClient;
import entities.DatabaseConnector;
import javafx.collections.ObservableList;
import ocsf.server.ConnectionToClient;

public class ServerMessageHandler {
	/**
	 * The response the server make
	 */

	private static ServerMessageHandler messageHandler = null;

	private ServerMessageHandler() {
	}

	public static ServerMessageHandler getMessageHandlerInstance() {
		if (messageHandler == null) {
			messageHandler = new ServerMessageHandler();
		}
		return messageHandler;
	}

	public void handleMessages(Object msg, ConnectionToClient client) {
		final MissionPack mission = (MissionPack) msg;
		System.out.println("Message received: " + mission + " from " + client);
		DatabaseConnector.parsingToData(mission, client);
		try {
			System.out.println(mission);
			client.sendToClient(mission);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void updateClientList(ConnectionToClient client, ClientStatus connectionStatus) {
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
		clientList.add(new ConnectedClient(client.getInetAddress().getHostAddress(),
				client.getInetAddress().getHostName(), connectionStatus));
		ServerConfiguration.setClientList(clientList);
	}

}
