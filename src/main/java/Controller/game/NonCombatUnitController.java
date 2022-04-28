package Controller.game;

import Model.Improvement;
import Model.Location;
import Model.Terrain;
import Model.Unit;

public class NonCombatUnitController {

    // TODO type of improvement?

    private boolean isSettlerUnit(Unit unit) {
        return true;
    }

    private String foundCity(Location location) {
        return "";
    }

    private boolean isWorker(Unit unit) {
        return true;
    }

    private String hasImprovement(Improvement improvement, Location location) {
        return "";
    }

    private boolean canBuildImprovement(Improvement improvement) {
        return true;
    }

    private String buildImprovement(String improvement, Terrain terrain) {
        return "";
    }

//    private Terrain getTerrainByLocation(Location location) {
//
//    }

    private boolean needsRepairing(Location location) {
        return true;
    }

    private boolean isJungleHere(Location location) {
        return true;
    }

    private boolean isRouteHere(Location location) {
        return true;
    }
}
