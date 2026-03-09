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


public class ownerManageBooks extends Application {
    
    private final TableView table = new TableView();
    
    ArrayList<Book> books = new ArrayList<>();
    
    @Override
    public void start(Stage primaryStage){
        
        bookList list = bookList.getInstance();
        
        primaryStage.setTitle("Manage Books");
        primaryStage.setWidth(500);
        primaryStage.setHeight(600);
        
        books = list.read();
        
        table.setEditable(true);
        
        TableColumn titleCol = new TableColumn("Title");
        TableColumn priceCol = new TableColumn("Price");
        
        titleCol.setMinWidth(150);
        priceCol.setMinWidth(100);
        
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        table.getColumns().addAll(titleCol,priceCol);
        
        for(Book book: books){
            table.getItems().add(book);
        }
        
        TextField addTitle = new TextField();
        addTitle.setPromptText("Title");
        addTitle.setMinWidth(150);

        TextField addPrice = new TextField();
        addPrice.setPromptText("Price");
        addPrice.setMinWidth(100);
        
        Button addBt = new Button("Add");
        Button delBt = new Button("Delete");
        Button backBt = new Button("Back");
        
        addBt.setMinWidth(150);
        addBt.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                
                if(addTitle.getText().trim().isEmpty() || addPrice.getText().trim().isEmpty()){
                    System.out.println("Both fields must be filled.");
                }
                else{
                    boolean notInList = true;
                    for(Book book: books){
                        if(book.getTitle().equals(addTitle.getText())){
                            notInList = false;
                            break;
                        }
                    }
                    
                    if(notInList){
                        books.add(new Book(addTitle.getText(), Double.parseDouble(addPrice.getText())));
                        table.getItems().addAll(books.get(books.size()-1));
                        list.write(addTitle.getText() + " " + addPrice.getText() + "\n" );
                        
                        addTitle.clear();
                        addPrice.clear();
                    }
                    else{
                        System.out.println("This book is already in the list or contains an invalid character");
                    }
                }
            }
        });
        
        delBt.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                int index = table.getSelectionModel().getSelectedIndex();
                
                if(index == -1){
                    System.out.println("Please select a book to delete");
                    return; 
                }
                
                String selectedBook = (String) table.getVisibleLeafColumn(0).getCellData(index);
                boolean removed = false;
                for(int i = 0; i < books.size(); i++){
                    if(books.get(i).getTitle().equals(selectedBook)){
                        books.remove(i);
                        removed = true;
                        break;
                    }
                }
                
                if(removed){
                    try(FileWriter w = new FileWriter(list.getName())){
                        for(Book book: books){
                            w.write(book.getTitle()+ " " + book.getPrice());
                        }
                    }
                    catch(IOException io){
                        System.out.println("An error occured.");
                    }
                    
                    table.getItems().remove(index);
                }
                else{
                    System.out.println("Book not found in the list.");
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
        
        adding.getChildren().addAll(addTitle, addPrice);
        buttons.getChildren().addAll(addBt, delBt, backBt);
        pane.getChildren().addAll(table, adding, buttons);
        
        Scene scene = new Scene(new Group());
        ((Group) scene.getRoot()).getChildren().addAll(pane);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
    }
    
    
}
