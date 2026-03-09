package finalproject;

/**
 *
 * @author dtorallo
 */

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import java.io.*;
import java.util.*;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;



public class ownerManageUsers extends Application {
    
    
        
    private final TableView table = new TableView();
        
    ArrayList<User> users = new ArrayList<>();
       
    @Override
    public void start(Stage primaryStage){
           
        userList list = userList.getInstance();
        
        primaryStage.setTitle("Manage Users");
        primaryStage.setWidth(500);
        primaryStage.setHeight(600);
        
        //reading the file initially to have it display
        list.read();
        
        table.setEditable(true);
        
        TableColumn userCol = new TableColumn("Username");
        TableColumn passCol = new TableColumn("Password");
        TableColumn pointsCol = new TableColumn("Points");
        
        userCol.setMinWidth(100);
        passCol.setMinWidth(150);
        pointsCol.setMinWidth(100);
        
        userCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        passCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        pointsCol.setCellValueFactory(new PropertyValueFactory<>("points"));
        
        table.getColumns().addAll(userCol,passCol,pointsCol);
        
        for(User user : users){
            table.getItems().addAll(user);
        }
        
        TextField addUser = new TextField();
        addUser.setPromptText("Username");
        addUser.setMinWidth(100);
        
        TextField addPass = new TextField();
        addPass.setPromptText("Password");
        addPass.setMinWidth(150);
        
        Button addBt = new Button("Add");
        Button delBt = new Button("Delete");
        Button backBt = new Button("Back");
        
        addBt.setMinWidth(100);
        
        addBt.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                
                if(addUser.getText().trim().isEmpty() || addPass.getText().trim().isEmpty()){
                    System.out.println("Both fields must be filled.");
                }
                else{
                    boolean notInList = true;
                    for (User user : users){
                        if(user.getUsername().equals(addUser.getText())|| addUser.getText().equals("admin") || addUser.getText().contains(" ")){
                            notInList = false;
                            break;
                        }
                    }
                    
                    if(notInList){
                        users.add(new User(addUser.getText(), addPass.getText()));
                        table.getItems().addAll(users.get(users.size()- 1));
                        list.write("\n" + addUser.getText() + " " + addPass.getText() + " " + users.get(users.size() -1).getPoints());
                        
                        addUser.clear();
                        addPass.clear();
                    }
                    else{
                        System.out.println("This user is already in the list or contains an illegal character.");
                        
                    }
                }
            }
        });
        
        delBt.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                int index = table.getSelectionModel().getSelectedIndex();
                
                //check if a user is selected 
                if(index == -1){
                    System.out.println("Please select a user to delete");
                    return;
                }
                
                String selectedUser = (String) table.getVisibleLeafColumn(0).getCellData(index);
                
                boolean removed = false; 
                for(int i = 0; i < users.size(); i++){
                    if(users.get(i).getUsername().equals(selectedUser)){
                        users.remove(i);
                        removed = true;
                        break;
                    }
                }
                
                if(removed){
                    try(FileWriter w = new FileWriter(list.getName())){
                        for (User user : users){
                            w.write(user.getUsername() + " " + user.getPassword() + " " + user.getPoints());
                        }
                    }
                    catch(IOException io){
                        System.out.println("An error occured.");
                    }
                    
                    table.getItems().remove(index);
                }
                else{
                    System.out.println("The user was not found in the list.");
                }   
            }
        });
        
        backBt.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent e){
               OwnerSelectScreen OSS = new OwnerSelectScreen();
               OSS.start(primaryStage);
           }
        });
        
       
        HBox adding = new HBox();
        HBox buttons = new HBox();
        
        VBox pane = new VBox();
        
        adding.setSpacing(8);
        buttons.setSpacing(8);
        pane.setSpacing(5);
        pane.setPadding(new Insets(10));
        
        adding.getChildren().addAll(addUser, addPass, addBt);
        buttons.getChildren().addAll(addBt, delBt, backBt);
        pane.getChildren().addAll(table, adding, buttons);
        
        Scene scene = new Scene(new Group());
        ((Group) scene.getRoot()).getChildren().addAll(pane);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        
    }
}
