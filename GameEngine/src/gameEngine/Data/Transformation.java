package gameEngine.Data;

import gameEngine.Utilities.Quaternion;
import gameEngine.Utilities.Vector3f;

/**
 *
 * @author Randoph
 */
public class Transformation {
                public Vector3f translation  = new Vector3f(0, 0, 0);
		public Quaternion rotation  = new Quaternion(0, 0, 0, 1);
		public Vector3f scale = new Vector3f(1f, 1f, 1f);
                public Vector3f eulerRotation = new Vector3f(0,0,0);
                
}
