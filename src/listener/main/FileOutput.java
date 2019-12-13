package listener.main;

import java.io.FileWriter;
import java.io.IOException;

public class FileOutput {
    public void fileWrite(String fileName, String str){
        try (FileWriter fw = new FileWriter(fileName);){
            fw.write(str);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
