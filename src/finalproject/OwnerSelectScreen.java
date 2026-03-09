/**
 *
 * @author dtorallo
 */

package finalproject;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;

public class OwnerSelectScreen extends Application {
   
	@Override
	public void start(Stage primaryStage){
		
            Button users = new Button("Manage Users");
            Button books = new Button("Manage Books");
            Button logOut = new Button("Logout");
		
            users.setOnAction(new EventHandler<ActionEvent>(){
		@Override
                public void handle(ActionEvent e){
                    ownerManageUsers userM = new ownerManageUsers();
                    userM.start(primaryStage);
                    }
            });

            books.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent e){
                    ownerManageBooks booksM = new ownerManageBooks();
                    booksM.start(primaryStage);
                }
            });
            
            logOut.setOnAction(event -> {
                Stage newStage = new Stage();
                new LoginScreen().start(newStage);  // Start a fresh login screen
                primaryStage.close();  // Close the current stage
            });
            
            VBox OSS = new VBox();
            
            OSS.setAlignment(Pos.CENTER);
            OSS.getChildren().addAll(users, books, logOut);
            OSS.setSpacing(13);
            OSS.setPrefSize(400,300);
            
            Scene ownerScene = new Scene(OSS, 400, 300);
            primaryStage.setTitle("Owner Selection Screen");
            primaryStage.setScene(ownerScene);
            primaryStage.show();
            
        } 
}
