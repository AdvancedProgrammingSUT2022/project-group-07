package Controller.game;

import Model.Location;
import Model.Terrain;

public class TheShortestPath {
    // TODO type fields
    static int height = GameController.getMapHeight();
    static int width = GameController.getMapWidth();
    private static int[][] mpMap = new int[height * width][height * width];
    private static int[][] distance = new int[height * width][height * width];
    private static int[][] nextTerrain = new int[height * width][height * width];

    public static void run() {
        initializeMpMap();
        initializeDistances();
        findTheShortestPath();
    }

    // TODO public?
    public static void initializeMpMap() {
        // getMapHeight static shod!!!!!!!!
        // TODO maMp never read ??
        Terrain[][] terrain = GameController.map;
        Terrain left, upLeft, upRight, right, downRight, downLeft;
        int k, s;

        for (int i = 1; i <= height * width; i++) {
            for (int j = 0; j < height * width; j++) {
                mpMap[i - 1][j] = Integer.MAX_VALUE;
            }
            k = i / height;
            s = i % height - 1;
            mpMap[i - 1][i - 1] = 0;
            if (k > 0) {
                left = terrain[k - 1][s];
                mpMap[i - 1][i - 2] = left.getMp();
            }
            if (s > 0 && k > 0) {
                upLeft = terrain[k - 1][s - 1];
                mpMap[i - 1][i - height - 1] = upLeft.getMp();
            }
            if (s > 0) {
                upRight = terrain[k][s - 1];
                mpMap[i - 1][i - height] = upRight.getMp();
            }
            if (k < height) {
                right = terrain[k + 1][s];
                mpMap[i - 1][i] = right.getMp();
            }
            if (k > 0 && s < width) {
                downLeft = terrain[k - 1][s + 1];
                mpMap[i - 1][i + height - 1] = downLeft.getMp();
            }
            if (s < width) {
                downRight = terrain[k][s + 1];
                mpMap[i - 1][i + height] = downRight.getMp();
            }
        }
    }


    private static void initializeDistances() {
        for (int i = 0; i < height * width; i++) {
            for (int j = 0; j < height * width; j++) {
                distance[i][j] = mpMap[i][j];
                if (mpMap[i][j] != Integer.MAX_VALUE)
                    nextTerrain[i][j] = j;
                else
                    nextTerrain[i][j] = -1;
            }
        }
    }

    private static void findTheShortestPath() {
        int infinite = Integer.MAX_VALUE;

        for (int i = 0; i < height * width; i++) {
            for (int j = 0; j < height * width; j++) {
                for (int k = 0; k < height * width; k++) {

                    if (distance[j][i] == infinite
                            || distance[i][k] == infinite)
                        continue;

                    if (distance[j][k]
                            > distance[j][i] + distance[i][k]) {
                        distance[j][k] = distance[j][i] + distance[i][k];
                        nextTerrain[j][k] = nextTerrain[j][i];
                    }
                }
            }
        }
    }

    public void handleFogOfWar(Location origin, Location destination) {

    }
}
