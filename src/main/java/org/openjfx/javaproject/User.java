package org.openjfx.javaproject;

class User {
	int userID;
	String userName;
	private String password;
	public static int numUsers = 0;
	public boolean checkPassword(String password) {
		if(password == this.password) {
			return true;
		} else {
			return false;
		}
	}
	User(String userName, String password) {
		this.userName = userName;
		this.password = password;
		this.userID = numUsers;
		numUsers += 1;
		
		//Liz: not sure if this is correct syntax but the IDE isn't complaining.
		org.openjfx.javaproject.UserStore.addUser(this);
	}
	public int getUserID() {
		return userID;
	}
	public String getUserName() {
		return userName;
	}
}