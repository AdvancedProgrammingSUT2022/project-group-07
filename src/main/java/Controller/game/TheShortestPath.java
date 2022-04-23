package Controller.game;

import Model.Terrain;

public class TheShortestPath {
    // TODO type fields
    static int height = GameController.getMapHeight();
    static int width = GameController.getMapWidth();
    private static int[][] mpMap = new int[height * width][height * width];
    private static int[][] distance = new int[height * width][height * width];
    private static int[][] nextTerrain = new int[height * width][height * width];

    public static void run() {
        initialiseMpMap();
        initialiseDistances();
        findTheShortestPath();
    }

    private static void initialiseMpMap() {

    }


    private static void initialiseDistances() {

    }

    private static void findTheShortestPath() {

    }
}
