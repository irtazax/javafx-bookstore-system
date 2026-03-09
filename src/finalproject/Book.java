package finalproject;

/**
 *
 * @author dtorallo
 */

import javafx.scene.control.CheckBox;

public class Book {
    
    
    private String title;
    private double price;
    private CheckBox selected;
    
    public Book(String title, double price){
        this.title = title;
        this.price = price;
        this.selected = new CheckBox();
        
    }
    
    public String getTitle(){
        return this.title;
    }
    public double getPrice(){
        return this.price;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    public void setPrice(double price){
        this.price = price;
    }
    
    public CheckBox getSelected(){
        return selected;
    }
    
    public void setSelected(CheckBox selected){
        this.selected = selected;
    }
    
}
