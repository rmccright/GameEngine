package gameEngine.Core;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import gameEngine.Data.Model;
import gameEngine.Data.VectorField;
import gameEngine.Data.vertexAtribute;
import gameEngine.Utilities.Vector3f;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.util.vector.Vector4f;


/**
 *
 * @author Randoph
 */


public class VectorFields {
    

    public static VectorField loadVectorField(String filePath){
       
        try{
             File tempFile = new File(filePath);
             if(!tempFile.isFile())
                 return null;
             
             InputStream iStream = new FileInputStream(tempFile);
             
             byte[] tempBytes = new byte[8];
             iStream.read(tempBytes);
             int width = (tempBytes[0] & 0x000000ff) | ((tempBytes[1] & 0x000000ff) << 8) | ((tempBytes[2] & 0x000000ff) << 16) | ((tempBytes[3] & 0x000000ff) << 24);
             int length = (tempBytes[4] & 0x000000ff) | ((tempBytes[5] & 0x000000ff) << 8) | ((tempBytes[6] & 0x000000ff) << 16) | ((tempBytes[7] & 0x000000ff) << 24);
             int numVerticies = (width*length);
             int numBytes = numVerticies*3*4;
             
             System.out.println("Width: " + width + " Length: " + length + " Num Verticies: " + numVerticies + " numBytes: " + numBytes);
             
             tempBytes = new byte[numBytes];
             iStream.read(tempBytes);
             
             VectorField tempVF = new VectorField();
             tempVF.width = width;
             tempVF.length = length;
             for(int a = 0, b = numVerticies; a < b; a++){
                 tempVF.field.add(new Vector3f(
                         Float.intBitsToFloat((tempBytes[0 + (12*a)] & 0x000000ff) | ((tempBytes[1 + (12*a)] & 0x000000ff) << 8) | ((tempBytes[2 + (12*a)] & 0x000000ff) << 16) | ((tempBytes[3 + (12*a)] & 0x000000ff) << 24)),
                         Float.intBitsToFloat((tempBytes[4 + (12*a)] & 0x000000ff) | ((tempBytes[5 + (12*a)] & 0x000000ff) << 8) | ((tempBytes[6 + (12*a)] & 0x000000ff) << 16) | ((tempBytes[7 + (12*a)] & 0x000000ff) << 24)),
                         Float.intBitsToFloat((tempBytes[8 + (12*a)] & 0x000000ff) | ((tempBytes[9 + (12*a)] & 0x000000ff) << 8) | ((tempBytes[10 + (12*a)] & 0x000000ff) << 16) | ((tempBytes[11 + (12*a)] & 0x000000ff) << 24))
                 ));
             }
             
             
             iStream.close();
             
             return tempVF;
        }catch(Exception e){
             return null;
        }
        
        
        
    }
    
    public static void saveVectorField(String filePath, VectorField output){
       
        
        int numBytes = (output.width * output.length)*3*4;
        byte[] convertedField = new byte[numBytes + 8];
        
        convertedField[0] = (byte)((output.width) & 0xff);
        convertedField[1] = (byte)((output.width >> 8) & 0xff);
        convertedField[2] = (byte)((output.width >> 16) & 0xff);
        convertedField[3] = (byte)((output.width >> 24) & 0xff);
        convertedField[4] = (byte)((output.length) & 0xff);
        convertedField[5] = (byte)((output.length >> 8) & 0xff);
        convertedField[6] = (byte)((output.length >> 16) & 0xff);
        convertedField[7] = (byte)((output.length >> 24) & 0xff);
        
        for(int a = 0, b = output.field.size(); a < b ; a++){
            
            convertedField[8+(12*a)] = (byte)(Float.floatToIntBits(output.field.get(a).GetX()) & 0xff);
            convertedField[9+(12*a)] = (byte)((Float.floatToIntBits(output.field.get(a).GetX()) >> 8) & 0xff);
            convertedField[10+(12*a)] = (byte)((Float.floatToIntBits(output.field.get(a).GetX()) >> 16) & 0xff);
            convertedField[11+(12*a)] = (byte)((Float.floatToIntBits(output.field.get(a).GetX()) >> 24) & 0xff);
            
            convertedField[12+(12*a)] = (byte)(Float.floatToIntBits(output.field.get(a).GetY()) & 0xff);
            convertedField[13+(12*a)] = (byte)((Float.floatToIntBits(output.field.get(a).GetY()) >> 8) & 0xff);
            convertedField[14+(12*a)] = (byte)((Float.floatToIntBits(output.field.get(a).GetY()) >> 16) & 0xff);
            convertedField[15+(12*a)] = (byte)((Float.floatToIntBits(output.field.get(a).GetY()) >> 24) & 0xff);
           
            convertedField[16+(12*a)] = (byte)(Float.floatToIntBits(output.field.get(a).GetZ()) & 0xff);
            convertedField[17+(12*a)] = (byte)((Float.floatToIntBits(output.field.get(a).GetZ()) >> 8) & 0xff);
            convertedField[18+(12*a)] = (byte)((Float.floatToIntBits(output.field.get(a).GetZ()) >> 16) & 0xff);
            convertedField[19+(12*a)] = (byte)((Float.floatToIntBits(output.field.get(a).GetZ()) >> 24) & 0xff);
        }
        
        
        try {
            File tempFile = new File(filePath);
            if(!tempFile.isFile())
                tempFile.createNewFile();
        
            FileOutputStream fs;
            fs = new FileOutputStream(tempFile);
            BufferedOutputStream bs = new BufferedOutputStream(fs);
            bs.write(convertedField);
            bs.close();
            fs.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VectorFields.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VectorFields.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }
    
    public static VectorField createVectorField(int width, int length){
        VectorField tempVF = new VectorField();
        tempVF.width = width;
        tempVF.length = length;
        Random randomGenerator = new Random();
   
        for(int a = 0, b = length; a < b; a++ ){
            for(int c = 0, d = width; c < d; c++){
                tempVF.field.add(new Vector3f(0.0f, 0.0f, 0.0f));
            }
        }
        
        return tempVF; 
    }
    
    public static Model vectorFieldToModel(String name, VectorField fieldIN, int spacingW, int spacingL){
        Model tempModel = new Model();
        int numRect = (fieldIN.width - 1) * (fieldIN.length - 1);
        int numTri = numRect * 2;
        int numVer = numTri * 3;
        int numFloat = numVer * 3;
     //   System.out.println("NumTri: " + numTri + " NumVer: " + numVer + " NumFloat: " + numFloat);
        
        int[] tempVerticieIndicies = new int[numVer];
        int tempIT = 0;
        int first, last;
        for(int a = 0, b = fieldIN.length - 1; a < b; a++){
          for(int c = 0, d = fieldIN.width - 1; c < d; c++){
              first = (c+(a*fieldIN.width));
              last = first + fieldIN.width + 1;
              
              tempVerticieIndicies[tempIT++] = first;
              tempVerticieIndicies[tempIT++] = first + 1;
              tempVerticieIndicies[tempIT++] = last - 1;
              tempVerticieIndicies[tempIT++] = first + 1;
              tempVerticieIndicies[tempIT++] = last;
              tempVerticieIndicies[tempIT++] = last - 1;
          }            
        }
        
        float[] vertexBufferPrep = new float[numFloat];
        tempIT = 0;
        for(int a = 0, b = numVer; a < b; a ++){
            
             int row = (int)(tempVerticieIndicies[a]/fieldIN.width);
             int column = tempVerticieIndicies[a]  % fieldIN.width;  
                           
            
            vertexBufferPrep[tempIT++] = fieldIN.field.get(tempVerticieIndicies[a]).GetX()  + (spacingW * column);
            vertexBufferPrep[tempIT++] = fieldIN.field.get(tempVerticieIndicies[a]).GetY();
            vertexBufferPrep[tempIT++] = fieldIN.field.get(tempVerticieIndicies[a]).GetZ() - (spacingL * row);
        }
        
        
        
        tempModel.color = createColorBuffer(new Vector4f(0.7f, 0.2f, 0.3f, 1.0f), numVer);
        tempModel.fileName = name;
        tempModel.normals = calcNormals(vertexBufferPrep);
        tempModel.texture = null;
        tempModel.verticies = vertexBufferPrep;
     //   System.out.println("Color Size: " + tempModel.color.length/4 + " normals size: " + tempModel.normals.length/3 + " verticies size: " + vertexBufferPrep.length/3);
        return tempModel;
    }
    
    //Move this to a general area at somepoint
    public static float[] calcNormals(float[] verticiesIN){
        float[] tempNormals = new float[verticiesIN.length];
        
        int tempIT = 0;
        for(int a = 0, b = (verticiesIN.length/9); a < b; a++){
            Vector3f p1 = new Vector3f(verticiesIN[tempIT++] ,verticiesIN[tempIT++] ,verticiesIN[tempIT++] );
            Vector3f p2 = new Vector3f(verticiesIN[tempIT++] ,verticiesIN[tempIT++] ,verticiesIN[tempIT++] );
            Vector3f p3 = new Vector3f(verticiesIN[tempIT++] ,verticiesIN[tempIT++] ,verticiesIN[tempIT++] );
            Vector3f U = new Vector3f(p2.GetX() - p1.GetX(), p2.GetY() - p1.GetY(),p2.GetZ() - p1.GetZ());
            Vector3f V = new Vector3f(p3.GetX() - p1.GetX(), p3.GetY() - p1.GetY(),p3.GetZ() - p1.GetZ());
            Vector3f surfaceNormal = new Vector3f(((U.GetY()*V.GetZ())-(U.GetZ()*V.GetY())),((U.GetZ()*V.GetX())-(U.GetX()*V.GetZ())),((U.GetX()*V.GetY())-(U.GetY()*V.GetX()))).Normalized();  
        //    System.out.println("x: " + surfaceNormal.GetX() + " y: " + surfaceNormal.GetY() + "z: " + surfaceNormal.GetZ() );
            tempIT -= 9;
            tempNormals[tempIT++] = surfaceNormal.GetX();
            tempNormals[tempIT++] = surfaceNormal.GetY();
            tempNormals[tempIT++] = surfaceNormal.GetZ();
            
            tempNormals[tempIT++] = surfaceNormal.GetX();
            tempNormals[tempIT++] = surfaceNormal.GetY();
            tempNormals[tempIT++] = surfaceNormal.GetZ();
            
            tempNormals[tempIT++] = surfaceNormal.GetX();
            tempNormals[tempIT++] = surfaceNormal.GetY();
            tempNormals[tempIT++] = surfaceNormal.GetZ();
            
        }
            
        return tempNormals;
    }
    
    public static float[] createColorBuffer(Vector4f color, int numVerticies){
        float[] tempColors = new float[numVerticies*4];
        
        int tempIT = 0;
        for(int a = 0, b = numVerticies; a < b; a++){
            tempColors[tempIT++] = color.x;
            tempColors[tempIT++] = color.y;
            tempColors[tempIT++] = color.z;
            tempColors[tempIT++] = color.w;
        }
        
        return tempColors;
    }
}
