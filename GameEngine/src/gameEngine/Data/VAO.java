/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameEngine.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Randoph
 */
    public class VAO{
        public String modelName;
        public int ID, mode, first, count;
        public List<VBO> vbos;
        
        public void setAtributes(String modelName, int mode, int first, int count, List<VBO> vbos){
            this.modelName = modelName;
            this.mode = mode;
            this.first = first;
            this.count = count;
            this.vbos = vbos;
        }
    }
    
