package org.openjfx.javaproject;
import java.util.ArrayList;
import java.io.IOException;

import org.server.Server;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	//made some things class variables instead of clogging up the main/start method
	
	//this creates layout
	VBox layout = new VBox();
    
    //sign-up screen
    VBox signupScreen = new VBox();
    Label signupTitle = new Label("Sign up:");
    Label signupUsernameLabel = new Label("Username: ");
    TextField signupUsernameBox = new TextField();
    Label signupPasswordLabel = new Label("Password: ");
    PasswordField signupPasswordBox = new PasswordField();
    Label signupErrorLabel = new Label("");
    Button signUpSubmit = new Button("Sign Up");
    Button backToLogin = new Button("Back to Login");
    

    //login screen
    VBox loginScreen = new VBox();
    Label loginTitle = new Label("Login:");
    Label usernameLabel = new Label("Username: ");
    TextField usernameBox = new TextField();
    Label passwordLabel = new Label("Password: ");
    PasswordField passwordBox = new PasswordField();
    Label errorLabel = new Label("");
    Label connectionLabel = new Label("Server IP: ");
    TextField connectionBox = new TextField();
    Label portLabel = new Label("Port: ");
    TextField portBox = new TextField();
    Button loginSend = new Button("Login");
    Button signUpButton = new Button("Sign Up");
    Label successLabel = new Label("");
    
    //messaging screen
    ScrollPane mainScreen = new ScrollPane();			//NEW: added a scrollable interface
    VBox messageScreen = new VBox();					//mainScreen takes in messageScreen which holds all messages
    static VBox messageHistory = new VBox();					//messageHistory is now VBox from StackPane to prevent overlaying messages
    HBox messageLine = new HBox();
    TextField newMessageBox = new TextField("Message");
    Button btSend = new Button("Send");
    
    //Current user variables:
    public static User sessionUser = null;
    public static String sessionUserName = " ";
    public static String connectionIP = " ";
    public static int connectionPort = 0;
    
    
    
    
    
    
    
    
    //START METHOD!
    @Override
    public void start(Stage stage) {
    	//construct screen layouts
    	loginScreen.getChildren().add(loginTitle);
    	loginScreen.getChildren().add(usernameLabel);
    	loginScreen.getChildren().add(usernameBox);
    	loginScreen.getChildren().add(passwordLabel);
    	loginScreen.getChildren().add(passwordBox);
    	loginScreen.getChildren().add(errorLabel);
    	loginScreen.getChildren().add(connectionLabel);
    	loginScreen.getChildren().add(connectionBox);
    	loginScreen.getChildren().add(portLabel);
    	loginScreen.getChildren().add(portBox);
    	loginScreen.getChildren().add(loginSend);
    	loginScreen.getChildren().add(signUpButton);
		loginScreen.getChildren().add(successLabel);
    	    
    	signupScreen.getChildren().add(signupTitle);
    	signupScreen.getChildren().add(signupUsernameLabel);
    	signupScreen.getChildren().add(signupUsernameBox);
    	signupScreen.getChildren().add(signupPasswordLabel);
    	signupScreen.getChildren().add(signupPasswordBox);
    	signupScreen.getChildren().add(signupErrorLabel);
    	signupScreen.getChildren().add(signUpSubmit);
    	signupScreen.getChildren().add(backToLogin);
    	

    	messageScreen.getChildren().add(messageHistory);
    	messageLine.getChildren().add(newMessageBox);
        //Jorge: adding a button that sends a message >:)
        messageLine.getChildren().add(btSend);
        messageScreen.getChildren().add(messageLine);
        

    	
    	//button event listeners
        loginSend.setOnAction(loggingInEvent);
    	signUpSubmit.setOnAction(signingUpEvent);
        signUpButton.setOnAction(toSignUpScreenEvent);
    	backToLogin.setOnAction(toLoginScreenEvent);
	    btSend.setOnAction(newMessageEvent);
	    newMessageBox.setOnAction(newMessageEvent);


    	    
    	//Initialize app.
    	layout.getChildren().add(loginScreen);	
    	messageHistory.setPrefSize(640, 375);
    	newMessageBox.setPrefWidth(580);
    	mainScreen.setContent(messageScreen);				//mainScreen takes in messageScreen
        Scene scene = new Scene(layout, 640, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
    //END OF START METHOD!!!! WARY OF CODE BELOW!!!!
    
    
    
    
    
    
    
    //Sorry in advance for terrible unreadable code -- this should work, trust me.
    //If it doesn't work --  welp we're screwed.
    //(jk i'll just try 2 fix it)

    public static void renderThings(VBox messageHistory) {
    	//clears already rendered messages
    	messageHistory.getChildren().clear();
    	
    	//stores HBoxes of messages to be added
    	ArrayList<VBox> elementList = new ArrayList<>();
    	//this terribleness is necessary for my IDE to stop nagging me about messages being accessed in a static way
    	ArrayList<Message> messages = MessageStore.messages;
    	
    	//for each message: makes an HBox and adds to the ArrayList
    	try {
			for(int i = 0; i<messages.size(); i++) { 
				VBox thisElement = new VBox();
				thisElement.setPrefWidth(360);
				Label nameLabel = new Label(UserStore.getUsernameByID(messages.get(i).getAuthor()));
				Label contents = new Label(messages.get(i).getContents());
				thisElement.getChildren().add(nameLabel);
				thisElement.getChildren().add(contents);
				elementList.add(thisElement);
			}
		} catch (Exception e) {
			
		}
    	
    	//adds contents of arrayList to the Layout -- see line 27.
    	for(int i = 0; i<elementList.size(); i++) {
    		messageHistory.getChildren().add(elementList.get(i));
    	}
    }


    
    
    //event handling methods
    EventHandler<ActionEvent> signingUpEvent = new EventHandler<>() {
    	@Override public void handle(ActionEvent e) {
    		
    		//checks if user w/ name exists, if not, makes new user by that name
    		boolean exists = UserStore.userExists(signupUsernameBox.getText());
    		if(!exists) {
    			UserStore.addUser(new User(signupUsernameBox.getText(), signupPasswordBox.getText()));
    			successLabel.setText("Sign-up successful! Please log in!");
    			layout.getChildren().clear();
    			layout.getChildren().add(loginScreen);
    			signupErrorLabel.setText("");
    			errorLabel.setText("");
    		} else{																																																							
    			if(signupErrorLabel.getText() == "") {
    				signupErrorLabel.setText("Error: User already exists.");
    				successLabel.setText("");
    			}
    		}
    	}
    };
    EventHandler<ActionEvent> loggingInEvent = new EventHandler<>() {
    	@Override public void handle(ActionEvent e) {
    		
    		//checks if login details are valid
    		boolean loginVerified = Login.validateLogin(usernameBox.getText(), passwordBox.getText());
    		try {
    			connectionPort = Integer.parseInt(portBox.getText());
    		} catch (NumberFormatException ex) {
    			loginVerified = false;
    		}
    		if(loginVerified) {
    			connectionIP = connectionBox.getText();
    			connectionPort = Integer.parseInt(portBox.getText());
    			sessionUserName = usernameBox.getText();
    			sessionUser = UserStore.findUserbyName(sessionUserName);
    			layout.getChildren().clear();
    	    	renderThings(messageHistory);
    			layout.getChildren().add(mainScreen);		
    			Server.clientConnects(messageHistory);		//Displays User has Connected message
    			try {
    				Client.newClient(sessionUserName);
    			}catch(IOException ex) {
    				ex.printStackTrace();
    			}
    		} else {
    			usernameBox.clear();
    			passwordBox.clear();
    			if(errorLabel.getText() == "") {
    				errorLabel.setText("Invalid Username, Password, or Port.");
    				successLabel.setText("");
    			}
    		}
    	}
    };
    EventHandler<ActionEvent> toSignUpScreenEvent = new EventHandler<>() {
        	@Override public void handle(ActionEvent e) {
        		layout.getChildren().clear();
        		layout.getChildren().add(signupScreen);
        	}
    };
    EventHandler<ActionEvent> toLoginScreenEvent = new EventHandler<>() {
    	@Override public void handle(ActionEvent e) {
    		layout.getChildren().clear();
    		layout.getChildren().add(loginScreen);
    	}
    };
    EventHandler<ActionEvent> newMessageEvent = new EventHandler<>() {
    	@Override public void handle(ActionEvent e) {	    			
    		Message message = new Message(sessionUserName,newMessageBox.getText());
	    	MessageStore.addMessage(message);
	    	System.out.println(newMessageBox.getText());	//This is to check if it works, will delete later
	    	Client.newMessage(newMessageBox.getText());
	    	renderThings(messageHistory);
	    	newMessageBox.clear();
	    }
    };
}