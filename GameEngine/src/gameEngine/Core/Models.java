package gameEngine.Core;

import gameEngine.Data.Model;
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
                models.add(newModel);
                return newModel;
           
            case ".obj":
                newModel = gameEngine.Utilities.OBJLoader.loadOBJ(fileName.replace(fileType, ""), fileName);
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
         
    public static  Model getBoundingBox(Model modelIN){
        float xMin = modelIN.verticies[0], xMax = xMin, yMin = modelIN.verticies[1],yMax = yMin, zMin = modelIN.verticies[2],zMax = zMin;
        for(int a = 3, b = modelIN.verticies.length/3; a < b; a++){
            switch(a%3){
                case 1:
                case 2:
                case 3:
                    
            }
        }
        
        Model boundingBox = new Model();
        return boundingBox;
    }
}
