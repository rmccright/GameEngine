package gameEngine.Core;

import gameEngine.Data.Model;
import gameEngine.Utilities.Vector3f;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Randoph
 */
public class Models {
      public static List<Model> models = new ArrayList();
      
      
      public static Model loadModel(String fileName) {
        String fileType = fileName.substring(fileName.indexOf("."));
        Model newModel;

        switch (fileType) {
            case ".stl":
                newModel = gameEngine.Utilities.STLLoader.loadSTL(fileName.replace(fileType, ""), fileName);
                newModel = setBounds(newModel);
                models.add(newModel);
                return newModel;
           
            case ".obj":
                newModel = gameEngine.Utilities.OBJLoader.loadOBJ(fileName.replace(fileType, ""), fileName);
                newModel = setBounds(newModel);
                models.add(newModel);
                
                  return newModel;
        }
        return null;
    }
      
         public static void printLoadedModels() {
        for (int a = 0; a < models.size(); a++) {
            System.out.println("Model List: " + models.get(a).fileName);

        }
    }
         
    public static  Model setBounds(Model in){
        in.max = new Vector3f(in.verticies[0], in.verticies[1], in.verticies[2]);
        in.min = new Vector3f(in.verticies[0], in.verticies[1], in.verticies[2]);
        
        for(int a = 0, b = in.verticies.length; a < b; a+=3){
            if(in.verticies[a] > in.max.GetX()){
                in.max.SetX(in.verticies[a]);
            }
            else if(in.verticies[a] < in.min.GetX()){
                in.min.SetX(in.verticies[a]);
            }
            
            if(in.verticies[a+1] > in.max.GetY()){
                in.max.SetY(in.verticies[a+1]);
            }
            else if(in.verticies[a+1] < in.min.GetY()){
                in.min.SetY(in.verticies[a+1]);
            }

            if(in.verticies[a+2] > in.max.GetZ()){
                in.max.SetZ(in.verticies[a+2]);
            }
            else if(in.verticies[a+2] < in.min.GetZ()){
                in.min.SetZ(in.verticies[a+2]);
            }
        }
        
        in.center = in.max.Add(in.min);
        in.center = in.center.Div(2);
        
        for(int a = 0, b = in.verticies.length; a < b; a+=3){
            in.verticies[a] = in.verticies[a] - in.center.GetX();
            in.verticies[a+1] = in.verticies[a+1] - in.center.GetY();
            in.verticies[a+2] = in.verticies[a+2] - in.center.GetZ();
        }
        
        
        return in;
    }
    
    
    
    
    
}
