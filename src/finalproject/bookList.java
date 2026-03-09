package finalproject;

/**
 *
 * @author dtorallo
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class bookList {
    
    private String filename;
    private static bookList bList;
    private File bListFile;
    
    private bookList(String filename) {
        this.filename = filename;
        this.bListFile = new File(filename);

        try {
            if (this.bListFile.createNewFile()) {
                System.out.println("File created: " + filename);
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + filename);
        }
    }
    
    public static bookList getInstance(){
        if(bList == null){
            bList = new bookList("bookList.txt");
        }
        
        return bList;
    }
    
    public void write(String message){
        try(FileWriter writer = new FileWriter(this.bListFile, true)){
            writer.write(message);
            writer.close();
            System.out.println("Wrote message to file.");
        }
        catch(IOException e){
            System.out.println("Failed writing to file.");
        }
    }
    
    public ArrayList<Book> read(){
        
        ArrayList<Book> books = new ArrayList<>();
        
        if(!(this.bListFile.exists())){
            System.out.println("Error. File does not exist.");
            return books;
        }
        
        try (Scanner reader = new Scanner(this.bListFile)){
            
            System.out.println("Reading file: ");
            while(reader.hasNextLine()){
                String nextLine = reader.nextLine().trim();
                
                if (!nextLine.isEmpty()) {
                    String[] str = nextLine.split(" ");

                    if (str.length == 2) {
                        try {
                            books.add(new Book(str[0], Double.parseDouble(str[1])));
                        } catch (NumberFormatException e) {
                            System.out.println("Skipping invalid entry: " + nextLine);
                        }
                    } else {
                        System.out.println("Skipping improperly formatted line: " + nextLine);
                    }
                }
            }
            reader.close();
        }
        catch(IOException e){
            System.out.println("Failed to read file.");
        }
        return books;
    }
    
    public String getName(){
        return this.filename;
        
    }
    
}
