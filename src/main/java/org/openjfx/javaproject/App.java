package org.openjfx.javaproject;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*Liz: please write your name in front of comments explaining your code 
 * so we know who is responsible for what code in case something breaks
 */
/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
    	//Liz: this creates layout
        VBox layout = new VBox();
        
        //Liz: scrollable area that contains the message history
        //Liz: message history should be rendered in this element
        StackPane messageHistory = new StackPane();
        messageHistory.setPrefSize(640, 375);
        layout.getChildren().add(messageHistory);
        renderThings(messageHistory);
        
        //Liz: box that new messages are typed in
        //Jorge: added an Event handler 
        TextField newMessageBox = new TextField("Message");
        newMessageBox.setOnAction(e -> new Message(org.openjfx.javaproject.UserStore.getUserName,newMessageBox.getText()));
        layout.getChildren().add(newMessageBox);
        
        //Jorge: adding a button that sends a message >:)
        Button btSend = new Button("Send");
	    btSend.setOnAction(new EventHandler<ActionEvent>() {
	    	
	    	@Override
	    	public void handle(ActionEvent onBtPress) {
	    		
	    		Message message = new Message(this.getUserName(),newMessageBox.getText());
	    	}
	    });
        layout.getChildren().add(btSend);
        
        //Liz: sets the stage and scene
        Scene scene = new Scene(layout, 640, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    
    //Liz: Sorry in advance for terrible unreadable code -- this should work, trust me.
    //Liz: If it doesn't work --  welp we're screwed.
    //Liz: (jk i'll just try 2 fix it)
    void renderThings(StackPane messageHistory) {
    	//Liz: clears already rendered messages
    	messageHistory.getChildren().clear();
    	
    	//Liz: stores HBoxes of messages to be added
    	ArrayList<VBox> elementList = new ArrayList<>();
    	//Liz: this terribleness is necessary for my IDE to stop nagging me about messages being accessed in a static way
    	ArrayList<Message> messages = org.openjfx.javaproject.MessageStore.messages;
    	
    	//Liz: for each message: makes an HBox and adds to the ArrayList
    	try {
			for(int i = 0; i<messages.size(); i++) { 
				VBox thisElement = new VBox();
				thisElement.setPrefWidth(360);
				Label nameLabel = new Label(org.openjfx.javaproject.UserStore.getUsernameByID(messages.get(i).getAuthor()));
				Label contents = new Label(messages.get(i).getContents());
				thisElement.getChildren().add(nameLabel);
				thisElement.getChildren().add(contents);
				elementList.add(thisElement);
			}
		} catch (Exception e) {
			
		}
    	
    	//Liz: adds contents of arrayList to the Layout -- see line 27.
    	for(int i = 0; i<elementList.size(); i++) {
    		messageHistory.getChildren().add(elementList.get(i));
    	}
    }
}