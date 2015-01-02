package gameEngine.Core;

import gameEngine.Data.ShaderProgram;
import gameEngine.Utilities.Matrix4f;
import gameEngine.Utilities.Vector3f;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;
import java.util.HashMap;
import static org.lwjgl.BufferUtils.createFloatBuffer;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
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
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

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

    
    
}
