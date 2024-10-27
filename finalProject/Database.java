package finalProject;

import java.io.*;

public class Database {//maybe implement serializable idk maybe not

    private static BufferedReader br;
    private static BufferedWriter wr;
   
    public static boolean createDataFile(String filePath){
        try {
            File file = new File(filePath);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
                return true;
            } else {
                System.out.println("File already exists.");
                return true;
            }
        } catch (Exception e) {
            System.out.println("error");
            return false;
        }
    }
    //write score, gametype, timeStamp, and given name/username in the form of a Data Obj to a file 
    public static boolean writeDataToFile(String filePath, Data data){
        
        return false;
    }

    //might want this to return like an arrayList of the top 10 Data high scores instead.
    public static Data readDataFromFile(String filePath){
        return null;
    }
    
}

