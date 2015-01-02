package gameEngine.Core;

import gameEngine.Data.Transformation;
import gameEngine.Utilities.Matrix4f;
import gameEngine.Utilities.Quaternion;
import gameEngine.Utilities.Vector3f;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Randoph
 */
public class Transforms {

    public Transforms() {

    }
     public static List<Transformation> transforms = new ArrayList();
     public static List<Integer> ID = new ArrayList();
    
     
    public static Matrix4f GetTransformation(int transformID) {
        Transformation t = getTransform(transformID);
        Matrix4f translationMatrix = new Matrix4f().InitTranslation(t.translation.GetX(), t.translation.GetY(), t.translation.GetZ());
        Matrix4f rotationMatrix = t.rotation.ToRotationMatrix();
        Matrix4f scaleMatrix = new Matrix4f().InitScale(t.scale.GetX(), t.scale.GetY(), t.scale.GetZ());

        return translationMatrix.Mul(rotationMatrix.Mul(scaleMatrix));
    }

    public static Matrix4f getProjectedTransformation(int transformID, int cameraID, Matrix4f projectionMatrix) {
        Transformation camera = getTransform(cameraID);
        Matrix4f transformationMatrix = GetTransformation(transformID);
        Matrix4f cameraRotation = camera.rotation.Conjugate().ToRotationMatrix();
        Matrix4f cameraTranslation = new Matrix4f().InitTranslation(-camera.translation.GetX(), -camera.translation.GetY(), -camera.translation.GetZ());

        return projectionMatrix.Mul(cameraRotation.Mul(cameraTranslation.Mul(transformationMatrix)));
    }
    
    private static Integer nextID = 0;
    public static int createTransform(){
        transforms.add(new Transformation());
        Integer theID = nextID;
        nextID ++;
        ID.add(theID);
        
        return theID ;
    }
    
    public static Transformation getTransform(int transformID){
        return transforms.get(ID.indexOf(transformID));
    }

}
