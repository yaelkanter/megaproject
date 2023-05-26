package common;

public enum Mission {
	GET_QUESTIONS("GET_QUESTIONS", 0), 
	EDIT_QUESTIONS_DATA("EDIT_QUESTIONS_DATA", 1),
	SEND_CONNECTION_DETAILS("SEND_CONNECTION_DETAILS", 2),
	SEND_DISCONNECTION_DETAILS("SEND_DISCONNECTION_DETAILS", 3);

	private Mission(final String mission, final int serialNumber) {
	}
}
