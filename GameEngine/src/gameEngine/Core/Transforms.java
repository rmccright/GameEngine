package gameEngine.Core;

import gameEngine.Data.Transformation;
import gameEngine.Utilities.Matrix4f;
import gameEngine.Utilities.Quaternion;
import gameEngine.Utilities.Vector3f;

/**
 *
 * @author Randoph
 */
public class Transforms {

    public Transforms() {

    }

  

    public static Matrix4f GetTransformation(Transformation t) {
        Matrix4f translationMatrix = new Matrix4f().InitTranslation(t.translation.GetX(), t.translation.GetY(), t.translation.GetZ());
        Matrix4f rotationMatrix = t.rotation.ToRotationMatrix();
        Matrix4f scaleMatrix = new Matrix4f().InitScale(t.scale.GetX(), t.scale.GetY(), t.scale.GetZ());

        return translationMatrix.Mul(rotationMatrix.Mul(scaleMatrix));
    }

    public static Matrix4f getProjectedTransformation(Transformation t, Transformation camera, Matrix4f projectionMatrix) {
        Matrix4f transformationMatrix = GetTransformation(t);
        Matrix4f cameraRotation = camera.rotation.Conjugate().ToRotationMatrix();
        Matrix4f cameraTranslation = new Matrix4f().InitTranslation(-camera.translation.GetX(), -camera.translation.GetY(), -camera.translation.GetZ());

        return projectionMatrix.Mul(cameraRotation.Mul(cameraTranslation.Mul(transformationMatrix)));
    }

}
