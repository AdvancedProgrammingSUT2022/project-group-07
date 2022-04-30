package Controller.game;

import Model.*;
import Enum.TerrainFeatures;
import Enum.TypeOfTechnology;

public class NonCombatUnitController {

    // TODO type of improvement?
    // TODO handle statics

//    private boolean isSettlerUnit(Unit unit) {
//        return true;
//    }
//
//    private String foundCity(Location location) {
//        return "";
//    }
//
//    private boolean isWorker(Unit unit) {
//        return true;
//    }

    private String hasImprovement(Improvement improvement, Location location) {
        return "";
    }

    private boolean canBuildImprovement(Improvement improvement) {
        return true;
    }

    private String buildImprovement(String improvement, Terrain terrain) {
        return "";
    }

    public static Terrain getTerrainByLocation(Location location, GameController gameController) {
        return null;
    }

    private boolean needsRepairing(Location location) {
        return true;
    }

    public static Terrain isJungleOrForestHere(Location location) {
        Terrain[][] terrain = GameController.map;

        for (int i = 0; i < GameController.getMapHeight(); i++) {
            for (int j = 0; j < GameController.getMapWidth(); j++) {
                if (terrain[i][j].getLocation().getX() == location.getX()
                        && terrain[i][j].getLocation().getY() == location.getY()
                        && (terrain[i][j].getTerrainFeatures() == TerrainFeatures.FOREST
                        || terrain[i][j].getTerrainFeatures() == TerrainFeatures.JUNGLE))
                    return terrain[i][j];
            }
        }
        return null;
    }

    public static boolean isRouteHere(Location location, GameController gameController) {
        Terrain[][] terrain = GameController.map;
        boolean routeIsForCurrentCivilization = false;

        for (int i = 0; i < GameController.getMapHeight(); i++) {
            for (int j = 0; j < GameController.getMapWidth(); j++) {
                if (terrain[i][j].getLocation().getX() == location.getX()
                        && terrain[i][j].getLocation().getY() == location.getY()
                        && terrain[i][j].getTechnology().getTypeOfTechnology() == TypeOfTechnology.RAILROAD) {

                }
            }
        }

        for (Technology technology : gameController.getCurrentCivilization().getGainedTechnologies()) {
            if (technology.getTypeOfTechnology() == TypeOfTechnology.RAILROAD) {
                gameController.getCurrentCivilization().getGainedTechnologies().remove(technology);
                break;
            }
        }
        return false;
    }

    public static Terrain isMarshHere(Location location) {
        Terrain[][] terrain = GameController.map;

        for (int i = 0; i < GameController.getMapHeight(); i++) {
            for (int j = 0; j < GameController.getMapWidth(); j++) {
                if (terrain[i][j].getLocation().getX() == location.getX()
                        && terrain[i][j].getLocation().getY() == location.getY()
                        && terrain[i][j].getTerrainFeatures() == TerrainFeatures.MARSH)
                    return terrain[i][j];
            }
        }
        return null;
    }
}
