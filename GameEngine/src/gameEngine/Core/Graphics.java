package gameEngine.Core;

import gameEngine.Data.Model;
import gameEngine.Data.ShaderProgram;
import gameEngine.Data.VAO;
import gameEngine.Data.VBO;
import gameEngine.Data.vertexAtribute;

import gameEngine.Utilities.Matrix4f;
import gameEngine.Utilities.Vector3f;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.lwjgl.BufferUtils;
import static org.lwjgl.BufferUtils.createFloatBuffer;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import org.lwjgl.opengl.GL30;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.PNGDecoder;

/**
 *
 * @author Randoph
 */
public class Graphics {

    public Graphics() {

    }

    public static List<VAO> vaos = new ArrayList();
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

    public static VAO createVAOFromModel(Model data) {
   
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
        return newVAO;
    }
    
   

    
    
    //////////////////
    //Data Operators//
    //////////////////
    //VAO Managment
    
     public static void printVAOs(){
        for (int a = 0; a < vaos.size(); a++) {
            System.out.println("VAO List: " + vaos.get(a).modelName);

        }
    }
     
    public static synchronized void subVBOData(VBO theVBO) {
        glBindBuffer(theVBO.target, theVBO.ID);
        glBufferSubData(theVBO.target, theVBO.offset, theVBO.buffer);
        glBindBuffer(theVBO.target, 0);
    }

    public static synchronized void renderVAO(VAO theVAO) {
        
        glBindVertexArray(theVAO.ID);

        for (int a = 0; a < theVAO.vbos.size(); a++) {
            for (int b = 0; b < theVAO.vbos.get(a).vertexAtributes.size(); b++) {
                glEnableVertexAttribArray(theVAO.vbos.get(a).vertexAtributes.get(b).index);

            }
        }
      glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
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

    //SHADERS
    public static void bindShaderProgram(ShaderProgram p) {
        glUseProgram(p.ID);
    }

    public static void unbindShaderProgram() {
        glUseProgram(0);
    }

    public static void createShaderProgram(ShaderProgram p) {
        p.ID = glCreateProgram();
        p.uniforms = new HashMap<>();

        if (p.ID == 0) {
            System.err.println("Shader program craetion failed");
            System.exit(1);
        }
    }

    public static void addUniform(ShaderProgram p, String uniform) {
        int uniformLocation = glGetUniformLocation(p.ID, uniform);

        if (uniformLocation == 0xFFFFFFFF) {
            System.err.println("Uniform Location does not exist: " + uniform);
            new Exception().printStackTrace();
            System.exit(1);
        }

        p.uniforms.put(uniform, uniformLocation);
    }

    public static void addVertexShaderToProgram(ShaderProgram p, String file) {
        addShaderToProgram(p, file, GL_VERTEX_SHADER);
    }

    public static void addFragmentShaderToProgram(ShaderProgram p, String file) {
        addShaderToProgram(p, file, GL_FRAGMENT_SHADER);
    }

    public static void addGeometryShaderToProgram(ShaderProgram p, String file) {
        addShaderToProgram(p, file, GL_GEOMETRY_SHADER);
    }

    public static void addShaderToProgram(ShaderProgram p, String file, int type) {
        int shader = glCreateShader(type);
        if (shader == 0) {
            System.err.println("Shader creation failed");
            System.exit(1);
        }

        glShaderSource(shader, file);
        glCompileShader(shader);

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
            System.err.println(glGetShaderInfoLog(shader, 1024));
            System.exit(1);
        }

        glAttachShader(p.ID, shader);
    }

    public static void compileShaderProgram(ShaderProgram p) {
        glLinkProgram(p.ID);
       
        if (glGetProgrami(p.ID, GL_LINK_STATUS) == 0) {
            System.err.println(glGetShaderInfoLog(p.ID, 1024));
            System.exit(1);
        }

        glValidateProgram(p.ID);
        if (glGetProgrami(p.ID, GL_VALIDATE_STATUS) == 0) {
            System.err.println(glGetShaderInfoLog(p.ID, 1024));
            System.exit(1);
        }
    }

    public static void setShaderUniformi(ShaderProgram p, String uniformName, int value) {
        glUniform1i(p.uniforms.get(uniformName), value);
    }

    public static void setShaderUniformf(ShaderProgram p, String uniformName, float value) {
        glUniform1f(p.uniforms.get(uniformName), value);
    }

    public static void setShaderUniform(ShaderProgram p, String uniformName, Vector3f value) {
        glUniform3f(p.uniforms.get(uniformName), value.GetX(), value.GetY(), value.GetZ());
    }
    
    public static void setShaderUniform(ShaderProgram p, String uniformName, Matrix4f value) {
        glUniformMatrix4(p.uniforms.get(uniformName), true, createFlippedBuffer(value));
      
    }

    public void getShaderUniform() {

    }

    public static FloatBuffer createFlippedBuffer(Matrix4f value) {
        FloatBuffer buffer = createFloatBuffer(4 * 4);
        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
                buffer.put(value.Get(a, b));
            }
        }
        buffer.flip();

        return buffer;
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
