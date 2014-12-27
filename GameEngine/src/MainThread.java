import gameEngine.Core.Graphics;
import gameEngine.Core.Input;
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
import gameEngine.Core.VectorFields;
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

    Transformation worldView = new Transformation();
    Transformation modelView = new Transformation();

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

    public void setup() {
        Window.createWindow();

        Graphics.placeVAOOnGraphicsCard(Graphics.createVAOFromModel(VectorFields.vectorFieldToModel("Terrain Test", VectorFields.loadVectorField("C:\\Users\\Randoph\\Desktop\\test2.vf"), 5, 5)));

        modelView.rotation = new Quaternion(new Vector3f(1.0f, 0f, 0f), (float) Math.toRadians(0));
        modelView.scale = new Vector3f(1f, 1f, 1f);
        modelView.translation = new Vector3f(0, 0, 30);

        projectionMatrix = new Matrix4f();
        projectionMatrix.InitPerspective(-75f, (float) Window.getWidth() / Window.getHeight(), .1f, 5000f);

        projTrans = Transforms.getProjectedTransformation(modelView, worldView, projectionMatrix);

        Window.setViewPort();

        testShader = new ShaderProgram();
        Graphics.createShaderProgram(testShader);
        Graphics.addFragmentShaderToProgram(testShader, Shaders.loadShader("shaders\\basicFragment.fs.txt"));
        Graphics.addVertexShaderToProgram(testShader, Shaders.loadShader("shaders\\basicVertex.vs.txt"));
        Graphics.compileShaderProgram(testShader);

        Graphics.addUniform(testShader, "PVTransform");
        Graphics.addUniform(testShader, "WVTransform");
        Graphics.addUniform(testShader, "shininess");
        Graphics.addUniform(testShader, "strength");
        Graphics.addUniform(testShader, "cameraPosition");

        for (int a = 0; a < 10; a++) {
            Graphics.addUniform(testShader, "Lights[" + a + "].isEnabled");
            Graphics.addUniform(testShader, "Lights[" + a + "].type");
            Graphics.addUniform(testShader, "Lights[" + a + "].ambient");
            Graphics.addUniform(testShader, "Lights[" + a + "].color");
            Graphics.addUniform(testShader, "Lights[" + a + "].position");
            Graphics.addUniform(testShader, "Lights[" + a + "].halfVector");
            Graphics.addUniform(testShader, "Lights[" + a + "].coneDirection");
            Graphics.addUniform(testShader, "Lights[" + a + "].spotCosCutoff");
            Graphics.addUniform(testShader, "Lights[" + a + "].spotExponent");
            Graphics.addUniform(testShader, "Lights[" + a + "].constantAttenuation");
            Graphics.addUniform(testShader, "Lights[" + a + "].linearAttenuation");
            Graphics.addUniform(testShader, "Lights[" + a + "].quadraticAttenuation");
        }

        Graphics.bindShaderProgram(testShader);

        Graphics.setShaderUniformi(testShader, "Lights[" + 1 + "].isEnabled", 1);
        Graphics.setShaderUniformi(testShader, "Lights[" + 1 + "].type", 1);
        Graphics.setShaderUniform(testShader, "Lights[" + 1 + "].ambient", new Vector3f(0.1f, 0.1f, 0.1f));
        Graphics.setShaderUniform(testShader, "Lights[" + 1 + "].color", new Vector3f(0.3f, 0.3f, 0.3f));
        Graphics.setShaderUniform(testShader, "Lights[" + 1 + "].position", new Vector3f(0.0f, 100.0f, 0.0f));
        Graphics.setShaderUniform(testShader, "Lights[" + 1 + "].halfVector", new Vector3f(50.0f, 15.0f, -10.0f));
        Graphics.setShaderUniform(testShader, "Lights[" + 1 + "].coneDirection", new Vector3f(0.0f, -1.0f, 0.0f).Normalized());
        Graphics.setShaderUniformf(testShader, "Lights[" + 1 + "].spotCosCutoff", .999f);
        Graphics.setShaderUniformf(testShader, "Lights[" + 1 + "].spotExponent", 20f);
        Graphics.setShaderUniformf(testShader, "Lights[" + 1 + "].constantAttenuation", .002f);
        Graphics.setShaderUniformf(testShader, "Lights[" + 1 + "].linearAttenuation", .002f);
        Graphics.setShaderUniformf(testShader, "Lights[" + 1 + "].quadraticAttenuation", .002f);

        Graphics.setShaderUniformi(testShader, "Lights[" + 2 + "].isEnabled", 1);
        Graphics.setShaderUniformi(testShader, "Lights[" + 2 + "].type", 2);
        Graphics.setShaderUniform(testShader, "Lights[" + 2 + "].ambient", new Vector3f(0.10f, 0.10f, 0.10f));
        Graphics.setShaderUniform(testShader, "Lights[" + 2 + "].color", new Vector3f(0.3f, 0.3f, 0.6f));
        Graphics.setShaderUniform(testShader, "Lights[" + 2 + "].position", new Vector3f(-200.0f, 0.0f, -20.0f));
        Graphics.setShaderUniform(testShader, "Lights[" + 2 + "].halfVector", new Vector3f(0.0f, 1.0f, 0.0f));
        Graphics.setShaderUniform(testShader, "Lights[" + 2 + "].coneDirection", new Vector3f(0.0f, -1.0f, 0.0f).Normalized());
        Graphics.setShaderUniformf(testShader, "Lights[" + 2 + "].spotCosCutoff", .999f);
        Graphics.setShaderUniformf(testShader, "Lights[" + 2 + "].spotExponent", 20f);
        Graphics.setShaderUniformf(testShader, "Lights[" + 2 + "].constantAttenuation", .2f);
        Graphics.setShaderUniformf(testShader, "Lights[" + 2 + "].linearAttenuation", .001f);
        Graphics.setShaderUniformf(testShader, "Lights[" + 2 + "].quadraticAttenuation", .0001f);

        Graphics.setShaderUniformi(testShader, "Lights[" + 3 + "].isEnabled", 1);
        Graphics.setShaderUniformi(testShader, "Lights[" + 3 + "].type", 3);
        Graphics.setShaderUniform(testShader, "Lights[" + 3 + "].ambient", new Vector3f(0.10f, 0.10f, 0.10f));
        Graphics.setShaderUniform(testShader, "Lights[" + 3 + "].color", new Vector3f(0.9f, 0.3f, 0.3f));
        Graphics.setShaderUniform(testShader, "Lights[" + 3 + "].position", new Vector3f(0.0f, 120.0f, 20.0f));
        Graphics.setShaderUniform(testShader, "Lights[" + 3 + "].halfVector", new Vector3f(0.0f, 1.0f, 0.0f));
        Graphics.setShaderUniform(testShader, "Lights[" + 3 + "].coneDirection", new Vector3f(0.0f, -1.0f, 0.0f).Normalized());
        Graphics.setShaderUniformf(testShader, "Lights[" + 3 + "].spotCosCutoff", .9999f);
        Graphics.setShaderUniformf(testShader, "Lights[" + 3 + "].spotExponent", 20f);
        Graphics.setShaderUniformf(testShader, "Lights[" + 3 + "].constantAttenuation", .1f);
        Graphics.setShaderUniformf(testShader, "Lights[" + 3 + "].linearAttenuation", .001f);
        Graphics.setShaderUniformf(testShader, "Lights[" + 3 + "].quadraticAttenuation", .001f);

        Graphics.setShaderUniformf(testShader, "shininess", 20f);
        Graphics.setShaderUniformf(testShader, "strength", 10f);
        Graphics.setShaderUniform(testShader, "cameraPosition", worldView.translation);

        glEnable(GL_DEPTH_TEST);
    }

    public void update() {

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        for (int a = 0; a < Graphics.vaos.size(); a++) {
            Graphics.renderVAO(Graphics.vaos.get(a));
        }

        input();

        Graphics.setShaderUniform(testShader, "cameraPosition", worldView.translation);

        projTrans = Transforms.getProjectedTransformation(modelView, worldView, projectionMatrix);
        Graphics.setShaderUniform(testShader, "PVTransform", projTrans);
        Graphics.setShaderUniform(testShader, "WVTransform", Transforms.GetTransformation(modelView));

        if (Window.wasResized()) {
            projectionMatrix.InitPerspective(-75f, (float) Window.getWidth() / Window.getHeight(), .1f, 5000f);

            projTrans = Transforms.getProjectedTransformation(modelView, worldView, projectionMatrix);

            Window.setViewPort();
            Graphics.setShaderUniform(testShader, "PVTransform", projTrans);
        }

    }

    boolean mouseLocked = false;
    Vector2f centerPosition = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);

    public void input() {
        float x = 0, z = 0;

        float sensitivity = 0.3f;
        float movAmt = (float) (0.018f * Time.getDelta());
//                float rotAmt = (float)(100 * Time.getDelta());
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

        worldView.translation = worldView.translation.Add(new Vector3f(10 * x, 0, 10 * z).Rotate(worldView.rotation));

        if (mouseLocked) {
            Vector2f deltaPos = Input.getMousePosition().Sub(centerPosition);

            boolean rotY = deltaPos.GetX() != 0;
            boolean rotX = deltaPos.GetY() != 0;

            if (rotY) {
                worldView.rotation = (new Quaternion(new Vector3f(0, 1 * (worldView.rotation.GetUp().GetY() / Math.abs(worldView.rotation.GetUp().GetY())), 0), (float) Math.toRadians(deltaPos.GetX() * sensitivity))).Mul(worldView.rotation);
            }

            if (rotX) {
                worldView.rotation = (new Quaternion(worldView.rotation.GetRight(), (float) Math.toRadians(-deltaPos.GetY() * sensitivity))).Mul(worldView.rotation);
            }

            if (rotY || rotX) {
                Input.setMousePosition(new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2));
            }
        }
    }

}
