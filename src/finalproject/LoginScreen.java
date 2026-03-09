/**
 *
 * @author dtorallo
 */

package finalproject;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.IOException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;

public class LoginScreen extends Application {
    
    static String usernameIn, passwordIn;
    static int pointsIn;
    
    ArrayList<User> users = new ArrayList<>();

    @Override
    public void start(Stage primaryStage){
        
        userList list = userList.getInstance();
       
        users = list.read();
        if(users == null){
            users = new ArrayList<>();
        }
        
        Label greeting = new Label("Welcome to our Bookstore.");
        
        Label userLabel = new Label("Username: ");
        TextField userField = new TextField();
        userLabel.setMaxWidth(200);
        userLabel.setAlignment(Pos.CENTER_LEFT);
        HBox user = new HBox(userLabel, userField);
        user.setAlignment(Pos.CENTER);
        user.setSpacing(10);
        
        Label passLabel = new Label("Password: ");
        PasswordField passField = new PasswordField();
        passLabel.setMaxWidth(200);
        passLabel.setAlignment(Pos.CENTER_LEFT);
        HBox pass = new HBox(passLabel, passField);
        pass.setAlignment(Pos.CENTER);
        pass.setSpacing(10);
        
        Button login = new Button();
        login.setText("Login");
        login.setMaxSize(100,10);
        
        login.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                LoginScreen.usernameIn = userField.getText();
                LoginScreen.passwordIn = passField.getText();
                
                if(LoginScreen.usernameIn.equals("admin") && LoginScreen.passwordIn.equals("admin")){
                        OwnerSelectScreen OSS = new OwnerSelectScreen();
                        OSS.start(primaryStage);
                        return;
                }
                
                boolean found = false;
                
                for(User user : users){
                    if(user.getUsername().equals(LoginScreen.usernameIn) && user.getPassword().equals(LoginScreen.passwordIn)){
                        LoginScreen.pointsIn = user.getPoints();
                        CustomerScreen CS = new CustomerScreen();
                        CS.start(primaryStage);
                        found = true;
                        break;
                    }
                }
                
                if (!found) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid username or password.");
                    alert.showAndWait();
                }
            }
        });
        
        VBox root = new VBox();
        
        root.getChildren().addAll(greeting, user, pass, login);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(15);
        
        Scene scene = new Scene(root, 500, 400);
       
        primaryStage.setTitle("Bookstore Login Screen");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
        
    }
}
 