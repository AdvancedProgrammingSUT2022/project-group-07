package Controller.game.movement;

import Controller.game.GameController;
import Model.Location;
import Model.Terrain;
import Enum.TerrainFeatures;
import Model.Unit;

import java.util.ArrayList;

public class TheShortestPath {
    // TODO check in removing later
    private static int height = GameController.getMapHeight();
    private static int width = GameController.getMapWidth();
    private static int[][] mpMap = new int[height * width][height * width];
    private static int[][] distance = new int[height * width][height * width];
    private static int[][] nextTerrain = new int[height * width][height * width];

    public static void run() {
        initializeMpMap();
        initializeDistances();
        findTheShortestPath();
    }

    private static void initializeMpMap() {
        for (int i = 1; i <= height * width; i++) {
            for (int j = 0; j < height * width; j++) {
                mpMap[i - 1][j] = Integer.MAX_VALUE;
            }
            initializeNeighbors(i);
        }
    }

    private static void initializeNeighbors(int i) {
        Terrain[][] terrain = GameController.map;
        Terrain left, upLeft, upRight, right, downRight, downLeft;
        int k, s;
        if (i % width == 0) {
            s = width - 1;
            k = i / width - 1;
        }
        else {
            s = i % width - 1;
            k = i / width;
        }
        mpMap[i - 1][i - 1] = 0;
        if (s > 0) {
            left = terrain[k][s - 1];
            mpMap[i - 1][i - 2] = left.getMp();
        }
        if (k > 0) {
            if (k % 2  == 0 && s > 0) upLeft = terrain[k - 1][s - 1];
            else upLeft = terrain[k - 1][s];
            mpMap[i - 1][i - width - 1] = upLeft.getMp();
        }
        if (k > 0) {
            if (k % 2 == 0) upRight = terrain[k - 1][s];
            else {
                if (s != width - 1) upRight = terrain[k - 1][s + 1];
                else upRight = null;
            }
            if (upRight != null) mpMap[i - 1][i - width] = upRight.getMp();
        }
        if (s < width - 1) {
            right = terrain[k][s + 1];
            mpMap[i - 1][i] = right.getMp();
        }
        if (k < height - 1) {
            if (k % 2 == 0 && s != 0) downLeft = terrain[k + 1][s - 1];
            else downLeft = terrain[k + 1][s];
            mpMap[i - 1][i + width - 1] = downLeft.getMp();
        }
        if (k < height - 1) {
            if (k % 2 == 0 ) {
                downRight = terrain[k + 1][s];
                mpMap[i - 1][i + width - 1] = downRight.getMp();
            }
            else {
                if (s != width - 1) {
                    downRight = terrain[k + 1][s + 1];
                    mpMap[i - 1][i + width] = downRight.getMp();
                }
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

    // method for FOG OF WAR
    public static ArrayList<Terrain> showPath(Location origin, Location destination) {
        Terrain[][] terrain = GameController.map;
        int start = findStartOrEnd(origin);
        int end = findStartOrEnd(destination);

        ArrayList<Terrain> knownTerrains = new ArrayList<>();
        if (start != -1 && end != -1) {
            if (nextTerrain[start][end] == -1)
                return null;
            if (start % width == 0) knownTerrains.add(terrain[start / width - 1][width - 1]);
            else knownTerrains.add(terrain[start / width][start % width - 1]);
            while (start != end) {
                start = nextTerrain[start][end];
                if (start % width == 0) knownTerrains.add(terrain[start / width - 1][width - 1]);
                else knownTerrains.add(terrain[start / width][start % width - 1]);
            }
        }
        return knownTerrains;
    }

    private static int findStartOrEnd(Location location) {
        Terrain[][] terrain = GameController.map;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (terrain[i][j].getLocation().getX() == location.getX()
                        && terrain[i][j].getLocation().getY() == location.getY())
                    return width * i + j + 1;
            }
        }
        return -1;
    }

    public static String checkNeededMpForMove(Location origin, Location destination) {
        // TODO check if it needs to continue in other turns

        Unit selectedUnit = SelectController.selectedUnit;
        ArrayList<Terrain> path = TheShortestPath.showPath(origin, destination);
        ArrayList<Terrain> goThrough = new ArrayList<>();
        int mp = selectedUnit.getMp();

        if (path == null) return "";
        // TODO river to pass
        ArrayList<Terrain> path2 = new ArrayList<>(path);
        path2.remove(0);
        for (Terrain terrain : path2) {
            mp -= terrain.getMp();
            goThrough.add(terrain);
            if (mp <= 0) {
                selectedUnit.setLocation(terrain.getLocation());
                SelectController.currentLocation = terrain.getLocation();
                // updating fog of war using static method of CivilizationController
                CivilizationController.updateFogOfWar(selectedUnit.getCivilization(), GameController.getMap(), GameController.getMapWidth(), GameController.getMapHeight());
                return "Selected unit moved to ( " + terrain.getLocation().getX() + " , " + terrain.getLocation().getY() + " )!";
            }
        }

        // updating fog of war using static method of CivilizationController
        CivilizationController.updateFogOfWar(selectedUnit.getCivilization(), GameController.getMap(), GameController.getMapWidth(), GameController.getMapHeight());

        SelectController.selectedUnit.setLocation(destination);
        SelectController.currentLocation = destination;
        return "Selected unit moved to position ( " + destination.getX() + " , " + destination.getY() + " ) successfully!";
    }

    // check if destination is mountain, ocean or river which are impassable
    // TODO river for destination ?!
    public static String destinationIsValid(Location destination) {
        Terrain[][] terrain = GameController.map;
        String typeOfTerrain;
        Location terrainLocation;

        for (int i = 0; i < GameController.getMapHeight(); i++) {
            for (int j = 0; j < GameController.getMapWidth(); j++) {
                terrainLocation = terrain[i][j].getLocation();
                typeOfTerrain = terrain[i][j].getTypeOfTerrain().getName();

                if ((typeOfTerrain.equals("mountain")
                        || typeOfTerrain.equals("ocean"))
                        && terrainLocation.getX() == destination.getX()
                        && terrainLocation.getY() == destination.getY())
                    return typeOfTerrain;

                if (terrain[i][j].getTerrainFeatures() != null
                        && terrain[i][j].getTerrainFeatures() == TerrainFeatures.ICE
                        && terrainLocation.getX() == destination.getX()
                        && terrainLocation.getY() == destination.getY())
                    return "ice";
            }
        }
        return null;
    }
}
