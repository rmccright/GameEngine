/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameEngine.Data;

/**
 *
 * @author Randoph
 */
    public class vertexAtribute{
        public int index, size, stride, pointer;
        public boolean normalized;
        
        public void setAtributes(int index, int size, int stride, int pointer, boolean normalized){
            this.index = index;
            this.size = size;
            this.stride = stride; 
            this.pointer = pointer;
            this.normalized = normalized;
        
        }
    }