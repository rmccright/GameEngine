package gameEngine.Utilities;

import org.lwjgl.Sys;

/**
 *
 * @author Randolph
 */
public class Time {
     private static int fps, fpsToPrint;
     private static long lastFPS;
     private static long lastFrame;
    
     public static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
     
     public static int getDelta() {
        long currentTime = getTime();
        int delta = (int) (currentTime - lastFrame);
        lastFrame = getTime();
        return delta;
    }

    public static void updateFPS() {
        if (getTime() - lastFPS > 1000) { 
            fpsToPrint = fps;
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }
    
    public static int getFPS(){
        return fpsToPrint;
    }
    
    public static void setLastFPS(){
        lastFPS = getTime();
    }
    
}
