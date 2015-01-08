/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameEngine.Core;

import gameEngine.Data.Model;
import gameEngine.Data.VAO;
import gameEngine.Data.VBO;
import gameEngine.Data.vertexAtribute;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import org.lwjgl.opengl.GL15;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferSubData;

/**
 *
 * @author Randoph
 */
public class VOs {
     public static List<VAO> vaos = new ArrayList();
     public static List<Integer> ID = new ArrayList();
     private static Integer nextID = 0;
     
    public static int createVAOFromModel(Model data) {
   
        VAO newVAO = new VAO();
        VBO newVBO = new VBO();
        List<vertexAtribute> vertexAtributes = new ArrayList();
        vertexAtribute newVertexAtrib = new vertexAtribute();
        vertexAtribute newColorAtrib = new vertexAtribute();
        vertexAtribute newNormalAtrib = new vertexAtribute();
        
        FloatBuffer newBuffer = BufferUtils.createFloatBuffer(data.verticies.length + data.color.length + data.normals.length);
        newBuffer.put(data.verticies);
        newBuffer.put(data.color);
        newBuffer.put(data.normals);
        newBuffer.flip();
        
      
        newVertexAtrib.setAtributes(0, 3, 0, 0, false);
        newColorAtrib.setAtributes(1, 4, 0, data.verticies.length*4, false);
        newNormalAtrib.setAtributes(2, 3, 0, data.verticies.length*4 + data.color.length*4, false);
        
        vertexAtributes.add(newVertexAtrib);
        vertexAtributes.add(newColorAtrib);
        vertexAtributes.add(newNormalAtrib);
        
        newVBO.setAttributes(0, GL15.GL_ARRAY_BUFFER, GL15.GL_STATIC_DRAW, GL11.GL_FLOAT, 0, newBuffer,vertexAtributes );
        
        List<VBO> vbos = new ArrayList();
        vbos.add(newVBO);
        
        newVAO.setAtributes(data.fileName, GL_TRIANGLES, 0, data.verticies.length/3, vbos);
        
       vaos.add(newVAO);
       Integer theID = nextID;
       nextID ++;
       ID.add(theID);
       return theID;
    }
    
      public static void printVAOs(){
        for (int a = 0; a < vaos.size(); a++) {
            System.out.println("VAO List: " + vaos.get(a).modelName + " ID: " + ID.get(a));

        }
    }
      
      public static VAO getVAO(int VAOID){
           return vaos.get(ID.indexOf(VAOID));
      }
     
    public static synchronized void subVBOData(VBO theVBO) {
        glBindBuffer(theVBO.target, theVBO.ID);
        glBufferSubData(theVBO.target, theVBO.offset, theVBO.buffer);
        glBindBuffer(theVBO.target, 0);
    }

}
