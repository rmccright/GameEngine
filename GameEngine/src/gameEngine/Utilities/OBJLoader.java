package gameEngine.Utilities;

import gameEngine.Data.Model;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Randolph
 */
public class OBJLoader {
        
    
    
    
    public static Model loadOBJ(String theName, String theFile){
        File inv = new File(theFile);
        
        List<Vector3f> verticies = new ArrayList();
        List<Vector3f> normals = new ArrayList();
        List<Vector2f> texture = new ArrayList();
         List<Vector3f> vI = new ArrayList();
        List<Vector3f> nI = new ArrayList();
        List<Vector3f> tI = new ArrayList();
        Model temp = new Model();
        temp.fileName = theName;
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(inv));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
              if(line.startsWith("v ")){
                  String[] result = line.split(" ");
                  float x = Float.valueOf(result[1]);
                  float y = Float.valueOf(result[2]);
                  float z = Float.valueOf(result[3]);
                  verticies.add(new Vector3f(x,y,z));
              }
              else if(line.startsWith("vn ")){
                  String[] result = line.split(" ");
                  float x = Float.valueOf(result[1]);
                  float y = Float.valueOf(result[2]);
                  float z = Float.valueOf(result[3]);
                  normals.add(new Vector3f(x,y,z));
             
              }
              else if(line.startsWith("vt ")){
                  String[] result = line.split(" ");
                  float x = Float.valueOf(result[1]);
                  float y = Float.valueOf(result[2]);
                  texture.add(new Vector2f(x, y));
              }
              else if(line.startsWith("f ")){
                  String[] result = line.split(" ");
                  String[] result1 = result[1].split("/");
                  String[] result2 = result[2].split("/");
                  String[] result3 = result[3].split("/");
                  
                  if(result1[0].isEmpty())
                    result1[0] = "0";
                  
                  if(result1[1].isEmpty())
                    result1[1] = "0";
                  
                  if(result1[2].isEmpty())
                    result1[2] = "0";
                  
                  
                  if(result2[0].isEmpty())
                    result2[0] = "0";
                  
                  if(result2[1].isEmpty())
                    result2[1] = "0";
                  
                  if(result2[2].isEmpty())
                    result2[2] = "0";
                  
                  
                  if(result3[0].isEmpty())
                    result3[0] = "0";
                  
                  if(result3[1].isEmpty())
                    result3[1] = "0";
                  
                  if(result3[2].isEmpty())
                    result3[2] = "0";
                  
                  vI.add(new Vector3f(Float.parseFloat(result1[0])-1,Float.parseFloat(result2[0])-1,Float.parseFloat(result3[0])-1));
                  
                  if(Float.parseFloat(result1[1]) != 0)
                  tI.add(new Vector3f(Float.parseFloat(result1[1])-1,Float.parseFloat(result2[1])-1,Float.parseFloat(result3[1])-1));
                  nI.add(new Vector3f(Float.parseFloat(result1[2])-1,Float.parseFloat(result2[2])-1,Float.parseFloat(result3[2])-1));
       
              }
              
            }
            float[] c = new float[vI.size()* 12];
             for(int a = 0; a < c.length/4; a++){
                c[a*4 + 0] = .25f;
                c[a*4 + 1] = .7f;
                c[a*4 + 2] = .35f; 
                c[a*4 + 3] = 1.0f;
             }
             
         float[] v = new float[vI.size()*9];
         
          for(int a = 0; a < vI.size(); a++){
              
               v[(9*a)+0] = verticies.get((int)vI.get(a).GetX()).GetX();
               v[(9*a)+1] = verticies.get((int)vI.get(a).GetX()).GetY();
               v[(9*a)+2] = verticies.get((int)vI.get(a).GetX()).GetZ();
               
               v[(9*a)+3] = verticies.get((int)vI.get(a).GetY()).GetX();
               v[(9*a)+4] = verticies.get((int)vI.get(a).GetY()).GetY();
               v[(9*a)+5] = verticies.get((int)vI.get(a).GetY()).GetZ();

               v[(9*a)+6] = verticies.get((int)vI.get(a).GetZ()).GetX();
               v[(9*a)+7] = verticies.get((int)vI.get(a).GetZ()).GetY();
               v[(9*a)+8] = verticies.get((int)vI.get(a).GetZ()).GetZ();
               
            }
          
          float[] t = null;
          
          if(texture.size() > 0){
            t = new float[tI.size()*6];
          for(int a = 0; a < tI.size(); a++){
             
               t[(6*a)+0] = texture.get((int)tI.get(a).GetX()).GetX();
               t[(6*a)+1] = texture.get((int)tI.get(a).GetX()).GetY();
               
               t[(6*a)+2] = texture.get((int)tI.get(a).GetY()).GetX();
               t[(6*a)+3] = texture.get((int)tI.get(a).GetY()).GetY();
               
               t[(6*a)+4] = texture.get((int)tI.get(a).GetZ()).GetX();
               t[(6*a)+5] = texture.get((int)tI.get(a).GetZ()).GetY();
            }
          }
                   float[] n = new float[nI.size()*9];
          for(int a = 0; a < nI.size(); a++){
               n[(9*a)+0] = normals.get((int)nI.get(a).GetX()).GetX();
               n[(9*a)+1] = normals.get((int)nI.get(a).GetX()).GetY();
               n[(9*a)+2] = normals.get((int)nI.get(a).GetX()).GetZ();
               
               n[(9*a)+3] = normals.get((int)nI.get(a).GetY()).GetX();
               n[(9*a)+4] = normals.get((int)nI.get(a).GetY()).GetY();
               n[(9*a)+5] = normals.get((int)nI.get(a).GetY()).GetZ();
               
               n[(9*a)+6] = normals.get((int)nI.get(a).GetZ()).GetX();
               n[(9*a)+7] = normals.get((int)nI.get(a).GetZ()).GetY();
               n[(9*a)+8] = normals.get((int)nI.get(a).GetZ()).GetZ();
            }
          
          temp.verticies = v;
          temp.normals = n;
          temp.texture = t;
          temp.color = c;
         
         
           
            
           br.close();
           return temp;
        } catch (IOException e) {
            return null;
        }   
    }
    
    
    
}
