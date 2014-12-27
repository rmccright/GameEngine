/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameEngine.Core;


import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Randolph
 */


public class Window {
    
    private static boolean fullscreen = false, vsync = false, resizable = true;
   
    public static void update(){
        setViewPort();
        Display.update();
    }
    
    public static boolean isCloseRequested(){
        return Display.isCloseRequested();
    }
    
    public static void destroy(){
        Display.destroy();
    }
    
    public static void createWindow(){
        try {
            if (fullscreen) {
                Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
            } else {
                Display.setResizable(isResizable());
                Display.setDisplayMode(new DisplayMode(800, 450));
            }
            Display.setTitle("GameEngine");
            Display.setVSyncEnabled(isVsync());
            Display.create();
        } catch (LWJGLException e) {
            Display.destroy();
            System.exit(1);
        }
        
    }
    
    public static void setViewPort(){
        glViewport(0, 0, getWidth(), getHeight());
    }
    
    public static boolean toggleFullscreen() {
        fullscreen = !fullscreen;
        try {
            if (fullscreen) {
                Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
            } else {
                Display.setResizable(isResizable());
                Display.setDisplayMode(new DisplayMode(800, 450));
            }
          
        } catch (LWJGLException e) {
        }
        return fullscreen;
    }
    
    public static int getWidth(){
        return Display.getWidth();
    }
    
    public static int getHeight(){
        return Display.getHeight();
    }
    
    public static void setTitle(String title){
        Display.setTitle(title);
    }
    
    public static boolean wasResized(){
        return Display.wasResized();
    }
    
   

    public static boolean isVsync() {
        return vsync;
    }

    public static void setVsync(boolean aVsync) {
        vsync = aVsync;
    }

    public static boolean isResizable() {
        return resizable;
    }

    public static void setResizable(boolean aResizable) {
        resizable = aResizable;
    }
    
}
