package main;

import audio.OpenALManager;
import scene.Camera;
import scene.Scene;
import listeners.InputListenerManager;

public class Application {
    public static void start() {
        Window.start();
        Scene.getInstance();            //Initialize Scene
        UserInterface.getInstance();    //Initialize UI
        ApplicationStatus.setStatus(ApplicationStatus.Status.RUNNING);
    }

    public static void update(long timeElapsed) {
        InputListenerManager.updateMouseWorldCoordinates();
        Camera.getInstance().update(timeElapsed);
        Scene.getInstance().update(timeElapsed);
    }

    public static void render(long timeElapsed) {
        OpenGLManager.prepareFrame();
        Scene.getInstance().render();
        UserInterface.getInstance().render(timeElapsed);
    }

    public static void stop() {
        OpenALManager.destroy();
        InputListenerManager.release();
    }
}

