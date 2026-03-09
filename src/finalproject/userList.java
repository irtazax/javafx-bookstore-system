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

public class userList {
    
    private String filename;
    private static userList list;
    private File listFile;
    
    //singleton design pattern implementation (only one copy of the userList)
    private userList(String filename){
        this.filename = filename;
        this.listFile = new File(filename);
        
        try{
            if(this.listFile.createNewFile()){
                System.out.println("File created: " + filename);
            }
        }
        catch(IOException e){
            System.out.println("Error creating file: " + filename);
        }
    }
    
    public String getName(){
        return this.filename;
    }
    
    public static userList getInstance(){
        if(list == null){
            list = new userList("customerList.txt");
        }
        
        return list;
    }
    
    public void write(String message){
        try (FileWriter writer = new FileWriter(this.listFile, true)){
            writer.write(message);
            System.out.println("Wrote message to file.");
            writer.close();
        } 
        catch(IOException e){
            System.out.println("Failed writing to file.");
        }
    }
    
    //reads a file and returns a list of User objects
    public ArrayList<User> read(){
        
        ArrayList<User> users = new ArrayList<>();
       
        
        if (!this.listFile.exists()){
            System.out.println("Error. File does not exist.");
            return users; //if the file is empty, returns an empty list
        }
        
        try{
            Scanner reader = new Scanner(this.listFile);
            
            System.out.println("\nReading file:");
            while(reader.hasNextLine()){
                String nextLine = reader.nextLine().trim();
                
                if(!nextLine.isEmpty()){
                    String[] str = nextLine.split(" ");
                    if (str.length == 3) {
                        try {
                            users.add(new User(str[0], str[1], Integer.parseInt(str[2])));
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
            System.out.println("Failed to read file");
        }
        return users;
    }
}
