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
        initEntities();
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
        massEntites =  new ArrayList<>();
        new Mass("Sun", initialCoordinates.x, initialCoordinates.y, 1000000000, 1000, new double[]{0, 0});
        new Mass("Mercury", initialCoordinates.x - 400, initialCoordinates.y, 100000, 1000, new double[]{0, 0.16});
        new Mass("Venus", initialCoordinates.x - 800, initialCoordinates.y, 100000, 1000, new double[]{0, 0.155});
        new Mass("Earth", initialCoordinates.x - 1200, initialCoordinates.y, 500000, 1000, new double[]{0, 0.15});
//        new Mass("S1", initialCoordinates.x - 1206, initialCoordinates.y, 4, 1000, new double[]{0, 0.155});
//        new Mass("S2", initialCoordinates.x - 1206.5, initialCoordinates.y, 4, 1000, new double[]{0, 0.154});
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
