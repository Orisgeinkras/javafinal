package org.openjfx.javaproject;
class Reply extends Message {
	Message replyMessage;
	int replyMessageID;
	Reply(int userID, String contents, Message replyMessage) {
		super(userID, contents);
		this.replyMessage = replyMessage;
		this.replyMessageID = replyMessage.getMessageID();
	}
	public Message getReplyMessage() {
		return replyMessage;
	}
	public int getReplyMessageID() {
		return replyMessageID;
	}
}