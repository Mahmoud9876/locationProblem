package Utils;

import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    public void writeIntances(String text){
        try {
            FileWriter myWriter = new FileWriter("instances.txt", true);
            myWriter.write(text + '\n');
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
