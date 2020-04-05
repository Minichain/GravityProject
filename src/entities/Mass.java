package entities;

import main.Coordinates;
import main.OpenGLManager;
import main.Parameters;
import scene.Camera;
import scene.Scene;
import text.TextRendering;
import utils.MathUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

public class Mass {
    private String name;
    private Coordinates worldCoordinates;
    private Coordinates cameraCoordinates;
    private double radius;
    private double mass;
    private double density;
    private double volume;
    private double[] velocity;
    private double[][] forces;
    private double[] totalForce;

    /** GRAVITATIONAL CONSTANT **/
    public static double G = 0.000000000667408;

    public Mass(String name, double x, double y, double mass, double density, double[] velocity) {
        worldCoordinates = new Coordinates(x, y);
        cameraCoordinates = worldCoordinates.toCameraCoordinates();
        this.name = name;
        this.density = density;
        this.mass = mass;
        this.volume = mass / density;
        this.radius = Math.pow((3.0 * volume) / (4.0 * Math.PI), 1.0/ 3.0);
        this.velocity = velocity;
        Scene.getMassEntites().add(this);
    }

    public void update(long timeElapsed) {
        if (Scene.getMassEntites().size() > 1) {
            forces = new double[Scene.getMassEntites().size() - 1][2];
            totalForce = new double[2];
            int forceIterator = 0;
            for (int i = 0; i < Scene.getMassEntites().size(); i++) {
                Mass currentMass = Scene.getMassEntites().get(i);
                if (currentMass != this) {
                    double force = G * (this.mass * currentMass.mass) / (MathUtils.module(this.worldCoordinates, currentMass.worldCoordinates));
                    double[] vector = new double[]{currentMass.worldCoordinates.x - this.worldCoordinates.x, currentMass.worldCoordinates.y - this.worldCoordinates.y};
                    double[] normalizedVector = MathUtils.normalizeVector(vector);
                    double forceXAxis = force * normalizedVector[0];
                    double forceYAxis = force * normalizedVector[1];
                    forces[forceIterator][0] = forceXAxis;
                    forces[forceIterator][1] = forceYAxis;
                    totalForce[0] += forceXAxis;
                    totalForce[1] += forceYAxis;
                    forceIterator++;
                }
            }
            double[] acceleration = new double[]{totalForce[0] / mass, totalForce[1] / mass};
            this.velocity[0] += acceleration[0];
            this.velocity[1] += acceleration[1];
            worldCoordinates.x += this.velocity[0] * timeElapsed;
            worldCoordinates.y += this.velocity[1] * timeElapsed;
        }
        cameraCoordinates = worldCoordinates.toCameraCoordinates();
    }

    public void render() {
        int numberOfVertices = 16;
        double angleStep = 2.0 * Math.PI / (double) numberOfVertices;
        double angle = 0;
        glDisable(GL_TEXTURE_2D);

        OpenGLManager.glBegin(GL_TRIANGLES);
        for (int i = 0; i < numberOfVertices; i++) {
            double radius = this.radius * Camera.getZoom();
            double x1 = (Math.cos(angle) * radius) + cameraCoordinates.x;
            double y1 = (Math.sin(angle) * radius) + cameraCoordinates.y;
            double x2 = (Math.cos(angle + angleStep) * radius) + cameraCoordinates.x;
            double y2 = (Math.sin(angle + angleStep) * radius) + cameraCoordinates.y;
            glVertex2d(x1, y1);
            glVertex2d(x2, y2);
            glVertex2d(cameraCoordinates.x, cameraCoordinates.y);
            angle += angleStep;
        }
        glEnd();


        if (Parameters.isDebugMode()) {
            OpenGLManager.glBegin(GL_LINES);
//        /** FORCES **/
//        for (int i = 0; i < forces.length; i++) {
//            glVertex2d(cameraCoordinates.x, cameraCoordinates.y);
//            glVertex2d(cameraCoordinates.x + this.forces[i][0] * 2500 * Camera.getZoom(), cameraCoordinates.y + this.forces[i][1] * 2500 * Camera.getZoom());
//        }
//        glVertex2d(cameraCoordinates.x, cameraCoordinates.y);
//        glVertex2d(cameraCoordinates.x + this.totalForce[0] * 2500 * Camera.getZoom(), cameraCoordinates.y + this.totalForce[1] * 2500 * Camera.getZoom());

            /** VELOCITY **/

            double factor = 500;

            glColor4f(1f, 0f, 0f, 1f);
            glVertex2d(cameraCoordinates.x, cameraCoordinates.y);
            glVertex2d(cameraCoordinates.x, cameraCoordinates.y + this.velocity[1] * factor * Camera.getZoom());
            glColor4f(0f, 1f, 0f, 1f);
            glVertex2d(cameraCoordinates.x, cameraCoordinates.y);
            glVertex2d(cameraCoordinates.x + this.velocity[0] * factor * Camera.getZoom(), cameraCoordinates.y);
            glColor4f(1f, 1f, 1f, 1f);
            glVertex2d(cameraCoordinates.x, cameraCoordinates.y);
            glVertex2d(cameraCoordinates.x + this.velocity[0] * factor * Camera.getZoom(), cameraCoordinates.y + this.velocity[1] * factor * Camera.getZoom());

            glEnd();
        }

        TextRendering.renderText((int) cameraCoordinates.x, (int) (cameraCoordinates.y + radius * Camera.getZoom()), name, 1);
    }

    public double getMass() {
        return mass;
    }

    public Coordinates getWorldCoordinates() {
        return worldCoordinates;
    }

    public Coordinates getCameraCoordinates() {
        return cameraCoordinates;
    }
}
