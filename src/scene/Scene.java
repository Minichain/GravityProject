package scene;

import entities.Mass;
import main.*;

import java.util.ArrayList;

public class Scene {
    private static Scene instance = null;

    private static int renderDistance = 1000; //TODO This should depend on the Window and Camera parameters
    private static int updateDistance = 1250; //TODO This should depend on... what?

    private static Coordinates initialCoordinates;

    private static ArrayList<Mass> massEntites;

    private Scene() {
        initialCoordinates = new Coordinates(2500, 2500);
        massEntites =  new ArrayList<>();
        new Mass("S1", initialCoordinates.x, initialCoordinates.y, 1980000000, 10000000, new double[]{0, 0});
        new Mass("P1", initialCoordinates.x - 100, initialCoordinates.y, 10, 50, new double[]{0, -0.075});
        new Mass("P2", initialCoordinates.x - 200, initialCoordinates.y, 10, 50, new double[]{0, -0.075});
        new Mass("P3", initialCoordinates.x - 300, initialCoordinates.y, 20, 50, new double[]{0, -0.075});
        new Mass("P4", initialCoordinates.x - 400, initialCoordinates.y, 40, 50, new double[]{0, -0.075});
        new Mass("P5", initialCoordinates.x - 500, initialCoordinates.y, 30, 50, new double[]{0, -0.075});
        new Mass("P6", initialCoordinates.x - 600, initialCoordinates.y, 30, 50, new double[]{0, -0.075});
        new Mass("P7", initialCoordinates.x - 700, initialCoordinates.y, 30, 50, new double[]{0, -0.075});
        new Mass("P8", initialCoordinates.x - 800, initialCoordinates.y, 30, 50, new double[]{0, -0.075});
        new Mass("P9", initialCoordinates.x - 900, initialCoordinates.y, 100, 50, new double[]{0, -0.075});
        new Mass("P10", initialCoordinates.x - 1000, initialCoordinates.y, 100, 50, new double[]{0, -0.075});
        new Mass("P11", initialCoordinates.x - 1100, initialCoordinates.y, 100, 50, new double[]{0, -0.075});
        new Mass("P12", initialCoordinates.x - 1200, initialCoordinates.y, 100, 50, new double[]{0, -0.075});

    }

    public static Scene getInstance() {
        if (instance == null) {
            instance = new Scene();
        }
        return instance;
    }

    public void update(long timeElapsed) {
        if (ApplicationStatus.getStatus() != ApplicationStatus.Status.RUNNING) {
            return;
        }

        if (!massEntites.isEmpty()) {
            for (Mass massEntity : massEntites) {
                massEntity.update(timeElapsed);
            }
        }
    }

    public static Coordinates getInitialCoordinates() {
        return initialCoordinates;
    }

    public void initEntities() {
        Camera.getInstance().reset();
    }

    public void render() {
        int oneAxisDistance = (int) (renderDistance * Math.sin(Math.PI / 2));
        Coordinates topLeftWorldCoordinates = new Coordinates(Camera.getInstance().getCoordinates().x - oneAxisDistance, Camera.getInstance().getCoordinates().y - oneAxisDistance);
        Coordinates topRightWorldCoordinates = new Coordinates(Camera.getInstance().getCoordinates().x + oneAxisDistance, Camera.getInstance().getCoordinates().y - oneAxisDistance);
        Coordinates bottomLeftWorldCoordinates = new Coordinates(Camera.getInstance().getCoordinates().x - oneAxisDistance, Camera.getInstance().getCoordinates().y + oneAxisDistance);

        if (!massEntites.isEmpty()) {
            for (Mass massEntity : massEntites) {
                massEntity.render();
            }
        }
    }

    public static ArrayList<Mass> getMassEntites() {
        return massEntites;
    }
}
