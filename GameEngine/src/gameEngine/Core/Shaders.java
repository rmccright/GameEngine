package gameEngine.Core;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author Randolph
 */
public class Shaders {
    public static String loadShader(String fileName){
        StringBuilder shaderSource = new StringBuilder();
        BufferedReader fileReader = null;
        try{
            fileReader = new BufferedReader(new FileReader(fileName));
            String line;
            while((line = fileReader.readLine()) != null){
                shaderSource.append(line).append("\n");
            }
            fileReader.close();
        }catch(Exception e){
            System.exit(1);
        }
        
        return shaderSource.toString();
    }
    
    
}
