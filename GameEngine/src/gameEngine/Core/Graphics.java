package gameEngine.Core;

import gameEngine.Data.VAO;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import org.lwjgl.opengl.GL13;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import org.lwjgl.opengl.GL30;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.PNGDecoder;

/**
 *
 * @author Randoph
 */
public class Graphics {

    public Graphics() {

    }

   
  


    
    
    //////////////////
    //Data Operators//
    //////////////////
    //VAO Managment
    
   
    public static synchronized void renderVAO(int theVAOID) {
        VAO theVAO = VOs.getVAO(theVAOID);
        glBindVertexArray(theVAO.ID);

        for (int a = 0; a < theVAO.vbos.size(); a++) {
            for (int b = 0; b < theVAO.vbos.get(a).vertexAtributes.size(); b++) {
                glEnableVertexAttribArray(theVAO.vbos.get(a).vertexAtributes.get(b).index);

            }
        }
    
           glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
        glEnable (GL_BLEND);
glBlendFunc (GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glDrawArrays(theVAO.mode, theVAO.first, theVAO.count);

        for (int a = 0; a < theVAO.vbos.size(); a++) {
            for (int b = 0; b < theVAO.vbos.get(a).vertexAtributes.size(); b++) {
                glDisableVertexAttribArray(theVAO.vbos.get(a).vertexAtributes.get(b).index);
            }
        }

        glBindVertexArray(0);
    }

    public static synchronized void placeVAOOnGraphicsCard(VAO theVAO) {
        theVAO.ID = glGenVertexArrays();
        glBindVertexArray(theVAO.ID);

        for (int a = 0; a < theVAO.vbos.size(); a++) {
            theVAO.vbos.get(a).ID = glGenBuffers();
            glBindBuffer(theVAO.vbos.get(a).target, theVAO.vbos.get(a).ID);
            glBufferData(theVAO.vbos.get(a).target, theVAO.vbos.get(a).buffer, theVAO.vbos.get(a).usage);

            for (int b = 0; b < theVAO.vbos.get(a).vertexAtributes.size(); b++) {
                glVertexAttribPointer(theVAO.vbos.get(a).vertexAtributes.get(b).index,
                        theVAO.vbos.get(a).vertexAtributes.get(b).size,
                        theVAO.vbos.get(a).type,
                        theVAO.vbos.get(a).vertexAtributes.get(b).normalized,
                        theVAO.vbos.get(a).vertexAtributes.get(b).stride,
                        theVAO.vbos.get(a).vertexAtributes.get(b).pointer);
            }
            glBindBuffer(theVAO.vbos.get(a).target, 0);
        }
        glBindVertexArray(0);
    }

    public static synchronized void removeVAOFromGraphicsCard(VAO theVAO) {
        glBindVertexArray(theVAO.ID);
        for (int a = 0; a < theVAO.vbos.size(); a++) {
            glBindBuffer(theVAO.vbos.get(a).target, theVAO.vbos.get(a).ID);
            for (int b = 0; b < theVAO.vbos.get(a).vertexAtributes.size(); b++) {
                glDisableVertexAttribArray(theVAO.vbos.get(a).vertexAtributes.get(b).index);
            }
            glDeleteBuffers(theVAO.vbos.get(a).ID);
            glBindBuffer(theVAO.vbos.get(a).target, 0);
        }
        glBindVertexArray(0);
        glDeleteVertexArrays(theVAO.ID);
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //TEXTURES
    public static int loadPNGTexture(String filename, int textureUnit) {
        ByteBuffer buf = null;
        int tWidth = 0;
        int tHeight = 0;

        try {
            // Open the PNG file as an InputStream
            InputStream in = new FileInputStream(filename);
            // Link the PNG decoder to this stream
            PNGDecoder decoder = new PNGDecoder(in);

            // Get the width and height of the texture
            tWidth = decoder.getWidth();
            tHeight = decoder.getHeight();

            // Decode the PNG file in a ByteBuffer
            buf = ByteBuffer.allocateDirect(
                    4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.RGBA);
            buf.flip();

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // Create a new texture object in memory and bind it
        int texId = GL11.glGenTextures();
        GL13.glActiveTexture(textureUnit);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);

        // All RGB bytes are aligned to each other and each component is 1 byte
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

        // Upload the texture data and generate mip maps (for scaling)
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, tWidth, tHeight, 0,
                GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

        // Setup the ST coordinate system
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

        // Setup what to do when the texture has to be scaled
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
                GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
                GL11.GL_LINEAR_MIPMAP_LINEAR);

        exitOnGLError("loadPNGTexture");

        return texId;
    }

    private static void exitOnGLError(String errorMessage) {
        int errorValue = GL11.glGetError();

        if (errorValue != GL11.GL_NO_ERROR) {
            String errorString = GLU.gluErrorString(errorValue);
            System.err.println("ERROR - " + errorMessage + ": " + errorString);

            if (Display.isCreated()) {
                Display.destroy();
            }
            System.exit(-1);
        }
    }

    public static void bindTexture(int id) {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public static void unbindTexture() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

   
    ///////////////////
    //Data Structures//
    ///////////////////
    class Texture {

        int Id;
        public String name, file;

        public Texture() {

        }
    }

}
