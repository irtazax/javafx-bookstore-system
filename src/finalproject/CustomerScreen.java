package finalproject;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerScreen extends Application {
    
    static double totalCost;
    private final TableView table = new TableView();
    
    ArrayList<Book> books = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();
    
    @Override
    public void start(Stage primaryStage){
        
        userList u = userList.getInstance();
        bookList b = bookList.getInstance();
        
        Scene scene = new Scene(new Group());
        primaryStage.setTitle("Bookstore App");
        primaryStage.setWidth(700);
        primaryStage.setHeight(600);
        
        books = b.read();
        users = u.read();
        
        // STATE PATTERN UPDATE - Replaces old status check
        User currentUser = findCurrentUser();
        Label heading = new Label("Hello " + LoginScreen.usernameIn + " you have " + 
            LoginScreen.pointsIn + " points. You are a " + 
            currentUser.getMembershipStatus() + " member.");
        
        table.setEditable(true);
        
        TableColumn bookCol = new TableColumn("Book Title");
        TableColumn priceCol = new TableColumn("Price");
        TableColumn selectCol = new TableColumn("Select");
        
        bookCol.setMinWidth(200);
        priceCol.setMinWidth(200);
        selectCol.setMinWidth(200);
        
        bookCol.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));
        selectCol.setCellValueFactory(new PropertyValueFactory<>("select"));
        
        table.getColumns().addAll(bookCol, priceCol, selectCol);
        table.setSelectionModel(null);
        selectCol.setStyle("-fx-alignment: CENTER;");
        
        for(Book book : books){
            table.getItems().add(book);
        }
        
        Button buyBt = new Button("Buy");
        Button redeemBt = new Button("Redeem points and buy");
        Button logoutBt = new Button("Logout");
        
        buyBt.setMinWidth(100);
        redeemBt.setMinWidth(200);
        logoutBt.setMinWidth(100);
        
        buyBt.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                processPurchase(currentUser, false);
            }
        });
        
        redeemBt.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                processPurchase(currentUser, true);
            }
        });
        
        logoutBt.setOnAction(event -> {
            Stage newStage = new Stage();
            new LoginScreen().start(newStage);  
            primaryStage.close();  
        });
        
        HBox buttons = new HBox();
        VBox pane = new VBox();
        
        buttons.setSpacing(10);
        pane.setSpacing(5);
        pane.setPadding(new Insets(10));
        
        buttons.getChildren().addAll(buyBt, redeemBt, logoutBt);
        pane.getChildren().addAll(heading, table, buttons);
        
        ((Group) scene.getRoot()).getChildren().addAll(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    // STATE PATTERN HELPER METHODS
    
    /**
     * Finds the current logged-in user
     */
    private User findCurrentUser() {
        for (User user : users) {
            if (user.getUsername().equals(LoginScreen.usernameIn)) {
                return user;
            }
        }
        return new User(); // Fallback
    }
    
    /**
     * Handles purchase logic for both regular and point redemption
     */
    private void processPurchase(User currentUser, boolean usePoints) {
        totalCost = 0;
        ArrayList<Book> selectedBooks = new ArrayList<>();
        
        // Calculate total cost
        for(Book book: books) {
            if(book.getSelected().isSelected()) {
                totalCost += book.getPrice();
                selectedBooks.add(book);
            }
        }
        
        // Apply state-specific behavior
        if (usePoints) {
            totalCost = currentUser.applyDiscount(totalCost);
            LoginScreen.pointsIn = currentUser.redeemPoints();
        } else {
            LoginScreen.pointsIn += (int) (totalCost * 10);
        }
        
        currentUser.setPoints(LoginScreen.pointsIn);
        updateUserFile(currentUser);
        updateBookFile(selectedBooks);
        
        CustomerCostScreen CCS = new CustomerCostScreen();
        CCS.start(new Stage());
    }
    
    private void updateUserFile(User currentUser) {
        try (FileWriter w = new FileWriter(userList.getInstance().getName())) {
            for (User user : users) {
                w.write(user.getUsername() + " " + user.getPassword() + " " + user.getPoints() + "\n");
            }
        } catch (IOException io) {
            System.out.println("A write error occurred.");
        }
    }
    
    private void updateBookFile(ArrayList<Book> selectedBooks) {
        try (FileWriter writer = new FileWriter(bookList.getInstance().getName())) {
            for (Book book : books) {
                if (!selectedBooks.contains(book)) {
                    writer.write(book.getTitle() + " " + book.getPrice() + "\n");
                }
            }
        } catch (IOException io) {
            System.out.println("A write error occurred");
        }
    }
}