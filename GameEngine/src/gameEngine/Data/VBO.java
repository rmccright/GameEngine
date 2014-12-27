/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameEngine.Data;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Randoph
 */
public class VBO {

    public int ID, target, usage, type, offset;
    public List<vertexAtribute> vertexAtributes = new ArrayList();
    public FloatBuffer buffer;

    public void setAttributes(int ID, int target, int usage, int type, int offset, FloatBuffer buffer, List<vertexAtribute> vertexAtributes) {
        this.ID = ID;
        this.buffer = buffer;
        this.offset = offset;
        this.target = target;
        this.type = type;
        this.usage = usage;
        this.vertexAtributes = vertexAtributes;
    }
}
