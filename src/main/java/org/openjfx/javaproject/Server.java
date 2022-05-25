package org.openjfx.javaproject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


//This is a server that hosts the connection between Users. Users communicate to the Server and relays messages to other Users connected
public class Server {

	//Creates a single instance of serversocket
	private ServerSocket serverSocket;
	
	//Constructor to server object
	public Server(ServerSocket serversocket) {
		this.serverSocket = serversocket;
	}
	
	public void startServer() {
		try {	
			//Keeps Server Open until a client connects. Code is halted until client connects.
			//Once client connects, a socket is created and send into the Client Handler
			//A new Thread is created for each client that joins, keeping clients updated as server changes
			while(!serverSocket.isClosed()) {
				Socket socket = serverSocket.accept();
				ClientHandler clientHandler = new ClientHandler(socket);
				Thread thread = new Thread(clientHandler);
				thread.start();
			}
		}
		catch(IOException e) {
			closeServerSocket();
		}
	}
	
	//Incase Server Socket fails to open and Server Socket is equal to NULL
	public void closeServerSocket() {
		try {
			if(serverSocket != null) {
				serverSocket.close();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	//Displays a connection message to the Message Chat
	public static void clientConnects(VBox messageHistory){
		VBox client = new VBox();
		client.setPrefWidth(360);
		Label nameLabel = new Label(UserStore.getUsernameByID(MessageStore.messages.get(0).getAuthor()) + " has joined the chat!");
		client.getChildren().add(nameLabel);
		messageHistory.getChildren().add(client);  
	}
	
	//This should be a main method. It hosts the connection between clients and should only be called once
	public static void newServer(){
		try {
			ServerSocket serverSocket = new ServerSocket(4200);
			Server server = new Server(serverSocket);
			server.startServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
} //Nice