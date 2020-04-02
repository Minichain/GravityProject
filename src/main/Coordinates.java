package main;

import scene.Camera;

public class Coordinates {
    public double x;
    public double y;

    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates toCameraCoordinates() {
        return new Coordinates((x - Camera.getInstance().getCoordinates().x + (Camera.getWidth() / 2)) * Camera.getZoom(),
                (y - Camera.getInstance().getCoordinates().y + (Camera.getHeight() / 2)) * Camera.getZoom());
    }

    public Coordinates toWorldCoordinates() {
        return new Coordinates(x / Camera.getZoom() + Camera.getInstance().getCoordinates().x - (Camera.getWidth() / 2),
                y / Camera.getZoom() + Camera.getInstance().getCoordinates().y - (Camera.getHeight() / 2));
    }

    public static Coordinates cameraToWindowCoordinates(double x, double y) {
        return new Coordinates(x * Window.getCameraWindowScaleFactor()[0], y * Window.getCameraWindowScaleFactor()[1]);
    }

    public static Coordinates windowToCameraCoordinates(double x, double y) {
        return new Coordinates(x / Window.getCameraWindowScaleFactor()[0], y / Window.getCameraWindowScaleFactor()[1]);
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
