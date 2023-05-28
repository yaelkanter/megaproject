package common;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserCredentials implements Serializable{
	private String userName;
	private String Password;
	private String UserType;

	public String getUserType() {
		return UserType;
	}

	public void setUserType(String userType) {
		UserType = userType;
	}

	public UserCredentials(String userName, String password, String userType) {
		super();
		this.userName = userName;
		Password = password;
		UserType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

}
