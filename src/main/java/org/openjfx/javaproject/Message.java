package org.openjfx.javaproject;
import java.sql.Timestamp;

class Message {
	int author;
	String contents;
	Timestamp timestamp;
	static int totalMessages = 0; //counts number of instances of this class
	int messageID;
	//constructor
	Message(int userID, String contents) {
		this.author = userID;
		this.contents = contents;
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.messageID = totalMessages; //uses total number of messages to uniquely ID them
		totalMessages += 1; //increments number of total messages -- a static variable so shared by all instances
	}
	Message(String userName, String contents) {
		this.author = UserStore.findUserbyName(userName).getUserID();
		this.contents = contents;
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.messageID = totalMessages;
		totalMessages += 1;
	}
	
	//these are getters: self-explanatory
	public int getAuthor() {
		return author;
	}
	public String getContents() {
		return contents;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}	
	public int getMessageID() {
		return messageID;
	}
}