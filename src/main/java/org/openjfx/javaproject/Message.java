package org.openjfx.javaproject;
import java.sql.Timestamp;

class Message {
	int author;
	String contents;
	Timestamp timestamp;
	static int totalMessages = 0; //Liz: counts number of instances of this class
	int messageID;
	//constructor
	Message(int userID, String contents) {
		this.author = userID;
		this.contents = contents;
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.messageID = totalMessages; //Liz: uses total number of messages to uniquely ID them
		totalMessages += 1; //Liz: increments number of total messages -- a static variable so shared by all instances
	}
	
	//Liz: these are getters: self-explanatory
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