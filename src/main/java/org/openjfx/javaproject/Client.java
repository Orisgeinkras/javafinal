package org.openjfx.javaproject;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;

//A class that allows you to communicate with the server. 
public class Client {
	
	private Socket socket;
	private BufferedWriter bufferedWriter;
	private BufferedReader bufferedReader;
	private String userName;
	private static String messageToSend;
	
	private BufferedWriter authorUser;
	private BufferedReader authorUserReader;
	
	//create constructor
	public Client(Socket socket,String userName) {
		try {
			this.socket = socket;
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.userName = userName;
		}catch(IOException e) {
			closeEverything(socket,bufferedReader,bufferedWriter);
			closeEverything(socket, authorUserReader, authorUser);
		}
	}
	//A method that receives messages from the client.
	//This method starts a new thread and allows Users to recieve and send messages simultaneously
	//This method should also display messages from other users
	public void receiveMessages() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				//TODO: Liz: I don't know why, I fear the answer, but this code isn't working.
				String messageFromChat;
				Message recievedMessage;
				String authorUserName;
				try {
					while(socket.isConnected()) {
						messageFromChat = bufferedReader.readLine();
						authorUserName = authorUserReader.readLine();
						recievedMessage = new Message(authorUserName, messageFromChat);
						MessageStore.addMessage(recievedMessage);
						App.renderThings(App.messageHistory);
					}
				}catch(IOException e) {
					closeEverything(socket,bufferedReader,bufferedWriter);
					closeEverything(socket, authorUserReader, authorUser);
				}catch(NullPointerException i) {
					System.out.println(" ");
				}
			}
		}).start();
	}
		
	//A method that sends a message to the client.
	public void send() {
		try {
			while(socket.isConnected()) {	
				bufferedWriter.write(messageToSend);
				bufferedWriter.newLine();
				bufferedWriter.flush();
				authorUser.write(userName);
				authorUser.newLine();
				authorUser.flush();
			}
		}catch(IOException e) {
			closeEverything(socket,bufferedReader,bufferedWriter);
			closeEverything(socket, authorUserReader, authorUser);
		}catch(NullPointerException i) {
			System.out.println(" ");
		}
	}
	//this method creates a new Client and should be executed on Login
	public static void newClient(String userName) throws IOException,ConnectException{

		Socket socket = new Socket(App.connectionIP, App.connectionPort);	//this will only run on your own computer!!
		if(!socket.isConnected()) {
			socket.close();
			try {
				System.out.println("Connection Failed. Retrying connection...");
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			newClient(userName);
		}
		Client client = new Client(socket,userName);
		client.receiveMessages();
		//client.sendMessage();
	
	}
	//Updates newMessage to send to the socket
	public static void newMessage(String message) {
		Client.messageToSend = message;
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