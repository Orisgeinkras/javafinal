package org.openjfx.javaproject;

import java.net.Socket;

//A class that allows you to communicate with a single client. 
public class Client {
	
		Socket socket;
		
		//create constructor
		public Client(Socket socket) {
			this.socket = socket;
			
			//Create a receive() function to receive messages from the client repeatedly.
			receive();
		}
		
		//A method that receives messages from the client.
		public void receive() {
			
		}
		
		//A method that sends a message to the client.
		public void send(String message) {
			
		}
		
}