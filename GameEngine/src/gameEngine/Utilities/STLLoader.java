package gameEngine.Utilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import gameEngine.Data.Model;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Randoph
 */
//THIS ONLY DOES VERTICIES CURRENTLY
public class STLLoader {

    public static Model loadSTL(String theName, String theFile) {
        File inv = new File(theFile);
        Model temp = new Model();
        temp.fileName = theName;
        String letter = "";
        try {
            InputStream inputstream = new FileInputStream(theFile);
           
            int bytes;
            byte[] b = new byte[4];
            byte[] a = new byte[2];

            inputstream.skip(80);

            bytes = inputstream.read(b);
            long newNum = (((short) (0x000000FF & (int) b[3]) << 24) | ((short) (0x000000FF & (int) b[2]) << 16) | ((short) (0x000000FF & (int) b[1]) << 8) | ((short) (0x000000FF & (int) b[0]))) & 0xFFFFFFFFL;
            System.out.println("Total nubmer of Faces: " + newNum);
            float[] Verticies = new float[(int) newNum * 9];
            float[] Normals = new float[(int) newNum * 9];
            
            for (long i = 0; i < newNum; i++) {
                float normals[] = new float[3];
                float vertex[] = new float[3];

              //    System.out.println();
                //   System.out.println("Face #" + (i+1));
                //  System.out.println("Normal: ");
                for (int j = 0; j < 3; j++) {
                    bytes = inputstream.read(b);
                    float newNumm = Float.intBitsToFloat(((short) (0x000000FF & (int) b[3]) << 24) | ((short) (0x000000FF & (int) b[2]) << 16) | ((short) (0x000000FF & (int) b[1]) << 8) | ((short) (0x000000FF & (int) b[0])));
                    switch (j) {
                        case 0:
                            letter = "x: ";
                            break;
                        case 1:
                            letter = "y: ";
                            break;
                        case 2:
                            letter = "z: ";
                            break;
                    }
                    normals[j] = newNumm;
                    
                 
                }

                    
                
                for (int x = 0; x < 3; x++) {
                    //   System.out.println("V" + (x+1) + ": ");
                    for (int j = 0; j < 3; j++) {
                        bytes = inputstream.read(b);
                        float newNumm = Float.intBitsToFloat(((short) (0x000000FF & (int) b[3]) << 24) | ((short) (0x000000FF & (int) b[2]) << 16) | ((short) (0x000000FF & (int) b[1]) << 8) | ((short) (0x000000FF & (int) b[0])));
                        switch (j) {
                            case 0:
                                letter = "x: ";
                                break;
                            case 1:
                                letter = "y: ";
                                break;
                            case 2:
                                letter = "z: ";
                                break;
                        }
                        vertex[j] = newNumm;
                        Verticies[(int) ((i * (9)) + (x * 3) + j)] = newNumm;
                        
                     //   System.out.println(letter + newNumm);
                      //  System.out.println("positions " + ((i * (9)) + (x * 3) + j) + " value: " + newNumm);
                    }
                          Normals[(int)((i * (9)) + (x * 3) + 0)] = normals[0];
                          Normals[(int)((i * (9)) + (x * 3) + 1)] = normals[1];
                          Normals[(int)((i * (9)) + (x * 3) + 2)] = normals[2];
                }

                bytes = inputstream.read(a);
                float newNumm = (((short) (0x000000FF & (int) b[1]) << 8) | ((short) (0x000000FF & (int) b[0]))) & 0xFFFFFFFFL;
            
            }
            System.out.println((Normals.length/3)*4);
              float[] c = new float[(Normals.length/3)*4];
             for(int ab = 0; ab < c.length/4; ab++){
                c[ab*4 + 0] = .3f;
                c[ab*4 + 1] = .3f;
                c[ab*4 + 2] = .3f; 
                c[ab*4 + 3] = 1.0f;
             }
            temp.normals = Normals;
            temp.verticies = Verticies;
            temp.texture = null;
            temp.color = c;
            
            inputstream.close();
            return temp;

        } catch (IOException e) {
            return null;
        }
    }

}
