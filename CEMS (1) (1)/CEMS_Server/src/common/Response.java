package common;

public enum Response {
	FOUND_QUESTIONS("FOUND_QUESTIONS", 0), DIDNT_FOUND_QUESTIONS("DIDNT_FOUND_QUESTIONS", 1),
	EDIT_QUESTIONS_SUCCESS("EDIT_QUESTIONS_SUCCESS", 2), EDIT_SQUESTIONS_FAILD("EDIT_QUESTIONS_FAILD", 3),
	UPDATE_CONNECTION_SUCCESS("UPDATE_CONNECTION_SUCCESS", 4), UPDATE_CONNECTION_FAILD("UPDATE_CONNECTION_FAILD", 5);

	
	private String responeName;
	private int responeNumber;
	

	private Response(final String responeName, final int responeNumber) {
		this.responeName = responeName;
		this.setResponeNumber(responeNumber);
	}

	public String toString() {
		return this.responeName;
	}

	public int getResponeNumber() {
		return responeNumber;
	}

	public void setResponeNumber(int responeNumber) {
		this.responeNumber = responeNumber;
	}
}
