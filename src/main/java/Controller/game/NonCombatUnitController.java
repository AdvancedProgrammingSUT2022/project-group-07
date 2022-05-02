package Controller.game;

import Controller.game.units.Worker;
import Model.*;
import Enum.TerrainFeatures;
import Enum.TypeOfTechnology;
import Enum.TypeOfUnit;

public class NonCombatUnitController {

    // TODO type of improvement?
    // TODO handle statics
    // TODO want a method to just check the location of the terrain --> get terrain by location in TerrainController

//    private boolean isSettlerUnit(Unit unit) {
//        return true;
//    }
//
//    private String foundCity(Location location) {
//        return "";
//    }
//
//    public static Unit isWorker(Civilization currentCivilization) {
//        for (Unit unit : currentCivilization.getUnits()) {
//            if (unit.getTypeOfUnit() == TypeOfUnit.WORKER) {
//                return unit;
//            }
//        }
//        return null;
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
