package org.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import org.openjfx.javaproject.User;

import org.openjfx.javaproject.App;

/*	ClientHandler is used by the Server to keep track of all the Clients currently in the chat room
 * 	ClientHandler has similar functions to Client
 * 	ClientHandler allows server to communicate to Users using their own Client 
*/
public class ClientHandler implements Runnable{

	//This allows multiple users to join the chat room
	public static ArrayList<ClientHandler> clientList = new ArrayList<>();
	
	private Socket socket;
	private BufferedWriter bufferedWriter;
	private BufferedReader bufferedReader;
	private String userName;
	private User user;

	//This creates a client for the server
	ClientHandler(Socket socket) {
		try {
		this.socket = socket;
		this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		this.userName = App.sessionUserName;
		this.user = App.sessionUser;
		clientList.add(this);
		System.out.println(this.userName + " has joined the Chat!");
		
		} catch(IOException e) {
			closeEverything(socket, bufferedReader,bufferedWriter);
		}
	}
	//This essentially reads whatever is input into the console
	@Override
	public void run() {	
		String message;
		while(socket.isConnected()){
			try {
				message = bufferedReader.readLine();
				broadcastMessage(message);
				
			}catch(IOException e) {
				closeEverything(socket, bufferedReader,bufferedWriter);
				break;
			}catch(NullPointerException i) {
				System.out.println(" ");
			}
		}
	}
	//This will display any message entered to all the clients connected to the server
	public void broadcastMessage(String message) {
		for(int i = 0; i < clientList.size(); i++) {
			try {
				if(!clientList.get(i).user.equals(user)) {
					clientList.get(i).bufferedWriter.write(message);
					clientList.get(i).bufferedWriter.newLine();
					clientList.get(i).bufferedWriter.flush();
					System.out.println(userName + " : ");
					System.out.println(message);
				}
			}catch(IOException e) {
				closeEverything(socket, bufferedReader,bufferedWriter);
			}
		}
	}	
	//Self explanatory
	public void removeClient() {
		clientList.remove(this);
		broadcastMessage(userName + "has left the chat");
	}
	//Exception Handling
	void closeEverything(Socket socket, BufferedReader bufferedReader,BufferedWriter bufferedWriter){
		removeClient();
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
