package org.openjfx.javaproject;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

//A class that allows you to communicate with the server. 
public class Client {
	
	private Socket socket;
	private BufferedWriter bufferedWriter;
	private BufferedReader bufferedReader;
	private String userName;
	
	//create constructor
	public Client(Socket socket,String userName) {
		try {
			this.socket = socket;
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.userName = userName;
		}catch(IOException e) {
			closeEverything(socket,bufferedReader,bufferedWriter);
		}
	}
	//A method that receives messages from the client.
	//This method starts a new thread and allows Users to recieve and send messages simultaneously
	public void receiveMessages() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String messageFromChat;
				
				try {
					while(socket.isConnected()) {
						messageFromChat = bufferedReader.readLine();
						
					}
				}catch(IOException e) {
					closeEverything(socket,bufferedReader,bufferedWriter);
				}
			}
		}).start();
	}
		
	//A method that sends a message to the client.
	public void send(String message) {
		try {
			while(socket.isConnected()) {
				String messageToSend = message;
				bufferedWriter.write(messageToSend);
				bufferedWriter.newLine();
				bufferedWriter.flush();
			}
		}catch(IOException e) {
			closeEverything(socket,bufferedReader,bufferedWriter);
		}
	}
	//this method creates a new Client and should be executed on Login
	public static void newClient(String userName) throws IOException{

		Socket socket = new Socket("local host",4200);	//this will only run on your own computer!!
		Client client = new Client(socket,userName);
		client.receiveMessages();
		client.sendMessage(userName);
	
	}
	//Exception Handling
	void closeEverything(Socket socket, BufferedReader bufferedReader,BufferedWriter bufferedWriter){
		try {
			if(bufferedReader != null) {
				bufferedReader.close();
			}
			if(bufferedWriter != null) {
				bufferedWriter.close();
			}
			if(socket != null) {
				socket.close();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}	
	}
}