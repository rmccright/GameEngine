import gameEngine.Core.Graphics;
import gameEngine.Core.Input;
import gameEngine.Core.Models;
import gameEngine.Core.Transforms;
import gameEngine.Core.Window;
import gameEngine.Data.ShaderProgram;
import gameEngine.Data.Transformation;
import gameEngine.Utilities.Matrix4f;
import gameEngine.Utilities.Quaternion;
import gameEngine.Utilities.Time;
import gameEngine.Utilities.Vector2f;
import gameEngine.Utilities.Vector3f;
import gameEngine.Core.Shaders;
import gameEngine.Core.VOs;
import gameEngine.Core.VectorFields;
import gameEngine.Data.SceneObject;
import java.util.ArrayList;
import java.util.List;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFinish;
import static org.lwjgl.opengl.GL11.glGetError;
import org.lwjgl.util.glu.GLU;

/**
 *
 * @author Randoph
 */
public class MainThread implements Runnable {

    public static void main(String[] args) {
      
      
        MainThread m = new MainThread();
        Thread t = new Thread(m);
        t.start();
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    public MainThread() {

    }

   

    @Override
    public void run() {
        engineLoopCycle();

    }

    boolean EngineRunning = false;

    public void engineLoopCycle() {
        EngineRunning = true;

        setup();
        Time.setLastFPS();
        while (EngineRunning) {
            if (Window.isCloseRequested()) {
                EngineRunning = false;
            }
            
            if (Window.wasResized()) {
                projectionMatrix.InitPerspective(-75f, (float) Window.getWidth() / Window.getHeight(), .1f, 5000f);
                Window.setViewPort();
            }


            Input.update();
            update();
            Window.update();

            glFinish();
            checkError();
            Time.updateFPS();
            System.out.println(Time.getFPS());
        }

    }

    public void checkError() {
        int error = glGetError();
        if (error != GL_NO_ERROR) {
            System.err.println("An error has occured: " + GLU.gluErrorString(error));
        }
    }

    ShaderProgram testShader;
    Matrix4f projTrans;
    Matrix4f projectionMatrix;
    int worldView ;
    int modelView ;
    public void setup() {
        Window.createWindow();

        
        
        SceneObject test = new SceneObject();
        SceneObject test2 = new SceneObject();
        test.vao = VOs.createVAOFromModel(VectorFields.vectorFieldToModel("Print Bed",VectorFields.createVectorField(2,2),381,330));
        test2.vao = VOs.createVAOFromModel(Models.loadModel("C:\\Users\\Randoph\\Dropbox\\FeetzShare\\Deb's first shoe\\Recreus_sandals\\recreus_left_sandal.stl"));
        test.transformation = Transforms.createTransform();
        test2.transformation =  Transforms.createTransform();
        Graphics.placeVAOOnGraphicsCard(VOs.getVAO(test.vao));
        Graphics.placeVAOOnGraphicsCard(VOs.getVAO(test2.vao));
        sceneObjects.add(test);
        sceneObjects.add(test2);
        
        
        
        
        worldView = Transforms.createTransform();
        projectionMatrix = new Matrix4f();
        projectionMatrix.InitPerspective(-75f, (float) Window.getWidth() / Window.getHeight(), .1f, 5000f);

        Window.setViewPort();

        testShader = new ShaderProgram();
        Shaders.createShaderProgram(testShader);
        Shaders.addFragmentShaderToProgram(testShader, Shaders.loadShader("shaders/basicFragment.fs.txt"));
        Shaders.addVertexShaderToProgram(testShader, Shaders.loadShader("shaders/basicVertex.vs.txt"));
        Shaders.compileShaderProgram(testShader);

        Shaders.addUniform(testShader, "PVTransform");
        Shaders.addUniform(testShader, "WVTransform");
        Shaders.addUniform(testShader, "shininess");
        Shaders.addUniform(testShader, "strength");
        Shaders.addUniform(testShader, "cameraPosition");

        for (int a = 0; a < 10; a++) {
            Shaders.addUniform(testShader, "Lights[" + a + "].isEnabled");
            Shaders.addUniform(testShader, "Lights[" + a + "].type");
            Shaders.addUniform(testShader, "Lights[" + a + "].ambient");
            Shaders.addUniform(testShader, "Lights[" + a + "].color");
            Shaders.addUniform(testShader, "Lights[" + a + "].position");
            Shaders.addUniform(testShader, "Lights[" + a + "].halfVector");
            Shaders.addUniform(testShader, "Lights[" + a + "].coneDirection");
            Shaders.addUniform(testShader, "Lights[" + a + "].spotCosCutoff");
            Shaders.addUniform(testShader, "Lights[" + a + "].spotExponent");
            Shaders.addUniform(testShader, "Lights[" + a + "].constantAttenuation");
            Shaders.addUniform(testShader, "Lights[" + a + "].linearAttenuation");
            Shaders.addUniform(testShader, "Lights[" + a + "].quadraticAttenuation");
        }

        Shaders.bindShaderProgram(testShader);

        Shaders.setShaderUniformi(testShader, "Lights[" + 1 + "].isEnabled", 1);
        Shaders.setShaderUniformi(testShader, "Lights[" + 1 + "].type", 1);
        Shaders.setShaderUniform(testShader, "Lights[" + 1 + "].ambient", new Vector3f(0.1f, 0.1f, 0.1f));
        Shaders.setShaderUniform(testShader, "Lights[" + 1 + "].color", new Vector3f(0.3f, 0.3f, 0.3f));
        Shaders.setShaderUniform(testShader, "Lights[" + 1 + "].position", new Vector3f(0.0f, 100.0f, 0.0f));
        Shaders.setShaderUniform(testShader, "Lights[" + 1 + "].halfVector", new Vector3f(50.0f, 15.0f, -10.0f));
        Shaders.setShaderUniform(testShader, "Lights[" + 1 + "].coneDirection", new Vector3f(0.0f, -1.0f, 0.0f).Normalized());
        Shaders.setShaderUniformf(testShader, "Lights[" + 1 + "].spotCosCutoff", .999f);
        Shaders.setShaderUniformf(testShader, "Lights[" + 1 + "].spotExponent", 20f);
        Shaders.setShaderUniformf(testShader, "Lights[" + 1 + "].constantAttenuation", .002f);
        Shaders.setShaderUniformf(testShader, "Lights[" + 1 + "].linearAttenuation", .002f);
        Shaders.setShaderUniformf(testShader, "Lights[" + 1 + "].quadraticAttenuation", .002f);

        Shaders.setShaderUniformi(testShader, "Lights[" + 2 + "].isEnabled", 1);
        Shaders.setShaderUniformi(testShader, "Lights[" + 2 + "].type", 2);
        Shaders.setShaderUniform(testShader, "Lights[" + 2 + "].ambient", new Vector3f(0.10f, 0.10f, 0.10f));
        Shaders.setShaderUniform(testShader, "Lights[" + 2 + "].color", new Vector3f(0.3f, 0.3f, 0.6f));
        Shaders.setShaderUniform(testShader, "Lights[" + 2 + "].position", new Vector3f(-200.0f, 0.0f, -20.0f));
        Shaders.setShaderUniform(testShader, "Lights[" + 2 + "].halfVector", new Vector3f(0.0f, 1.0f, 0.0f));
        Shaders.setShaderUniform(testShader, "Lights[" + 2 + "].coneDirection", new Vector3f(0.0f, -1.0f, 0.0f).Normalized());
        Shaders.setShaderUniformf(testShader, "Lights[" + 2 + "].spotCosCutoff", .999f);
        Shaders.setShaderUniformf(testShader, "Lights[" + 2 + "].spotExponent", 20f);
        Shaders.setShaderUniformf(testShader, "Lights[" + 2 + "].constantAttenuation", .2f);
        Shaders.setShaderUniformf(testShader, "Lights[" + 2 + "].linearAttenuation", .001f);
        Shaders.setShaderUniformf(testShader, "Lights[" + 2 + "].quadraticAttenuation", .0001f);

        Shaders.setShaderUniformi(testShader, "Lights[" + 3 + "].isEnabled", 1);
        Shaders.setShaderUniformi(testShader, "Lights[" + 3 + "].type", 3);
        Shaders.setShaderUniform(testShader, "Lights[" + 3 + "].ambient", new Vector3f(0.10f, 0.10f, 0.10f));
        Shaders.setShaderUniform(testShader, "Lights[" + 3 + "].color", new Vector3f(0.9f, 0.3f, 0.3f));
        Shaders.setShaderUniform(testShader, "Lights[" + 3 + "].position", new Vector3f(0.0f, 120.0f, 20.0f));
        Shaders.setShaderUniform(testShader, "Lights[" + 3 + "].halfVector", new Vector3f(0.0f, 1.0f, 0.0f));
        Shaders.setShaderUniform(testShader, "Lights[" + 3 + "].coneDirection", new Vector3f(0.0f, -1.0f, 0.0f).Normalized());
        Shaders.setShaderUniformf(testShader, "Lights[" + 3 + "].spotCosCutoff", .9999f);
        Shaders.setShaderUniformf(testShader, "Lights[" + 3 + "].spotExponent", 20f);
        Shaders.setShaderUniformf(testShader, "Lights[" + 3 + "].constantAttenuation", .1f);
        Shaders.setShaderUniformf(testShader, "Lights[" + 3 + "].linearAttenuation", .001f);
        Shaders.setShaderUniformf(testShader, "Lights[" + 3 + "].quadraticAttenuation", .001f);

        Shaders.setShaderUniformf(testShader, "shininess", 20f);
        Shaders.setShaderUniformf(testShader, "strength", 10f);
        Shaders.setShaderUniform(testShader, "cameraPosition", Transforms.getTransform(worldView).translation);

        glEnable(GL_DEPTH_TEST);
    }

   public static List<SceneObject> sceneObjects = new ArrayList();  
    public void update() {

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
         Shaders.setShaderUniform(testShader, "cameraPosition", Transforms.getTransform(worldView).translation);
         
         
        for (int a = 0; a < sceneObjects.size(); a++) {
            Shaders.setShaderUniform(testShader, "PVTransform", Transforms.getProjectedTransformation(sceneObjects.get(a).transformation, worldView, projectionMatrix));
            Shaders.setShaderUniform(testShader, "WVTransform", Transforms.GetTransformation(sceneObjects.get(a).transformation));
            Graphics.renderVAO(sceneObjects.get(a).vao);
        }

        input();

    }
        

    
    
    //camera stuff move soon
    boolean mouseLocked = false;
    Vector2f centerPosition = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);

    public void input() {
        float x = 0, z = 0;

        float sensitivity = 0.3f;
        float movAmt = (float) (0.018f * Time.getDelta());
        centerPosition = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);
        if (Input.getMousePressed(1)) {
            Input.setCursor(true);
            mouseLocked = false;
           
        }
        if (Input.getMousePressed(0)) {
            Input.setMousePosition(centerPosition);
            Input.setCursor(false);
            mouseLocked = true;
        }

        if (Input.getKeyHeld(Input.KEY_W)) {
            z += movAmt;
        }
        if (Input.getKeyHeld(Input.KEY_S)) {
            z -= movAmt;
        }
        if (Input.getKeyHeld(Input.KEY_A)) {
            x -= movAmt;
        }
        if (Input.getKeyHeld(Input.KEY_D)) {
            x += movAmt;
        }
        
        Transforms.move(worldView, new Vector3f(10 * x, 0, 10 * z).Rotate(Transforms.getTransform(worldView).rotation));  

        if (mouseLocked) {
            Vector2f deltaPos = Input.getMousePosition().Sub(centerPosition);

            boolean rotY = deltaPos.GetX() != 0;
            boolean rotX = deltaPos.GetY() != 0;

            if (rotY) {
                Transforms.rotate(worldView, new Quaternion(new Vector3f(0, (Transforms.getTransform(worldView).rotation.GetUp().GetY() / Math.abs(Transforms.getTransform(worldView).rotation.GetUp().GetY())), 0), (float) Math.toRadians(deltaPos.GetX() * sensitivity)));
            }

            if (rotX) {
                Transforms.rotate(worldView, new Quaternion(Transforms.getTransform(worldView).rotation.GetRight(), (float) Math.toRadians(-deltaPos.GetY() * sensitivity)));
            }

            if (rotY || rotX) {
                Input.setMousePosition(new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2));
            }
        }
    }

}
