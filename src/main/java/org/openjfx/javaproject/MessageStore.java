package org.openjfx.javaproject;
import java.util.ArrayList;

final class MessageStore {
	//everything is static because this class should not be instantiated. ever.
	//private constructor to prevent instantiation.
	private MessageStore() {
		
	}
	public static ArrayList<Message> messages;
	public static void addMessage(Message message) {
		messages.add(message);
	}
	Message findMessage(int messageID) {
		for(int i = 0; i < messages.size(); i++) {
			if(messages.get(i).getMessageID() == messageID) {
				return messages.get(i);
			}
		}
		return null;
	}
}