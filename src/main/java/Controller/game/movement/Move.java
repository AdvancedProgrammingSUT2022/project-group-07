package Controller.game.movement;

import Controller.game.CivilizationController;
import Controller.game.GameController;
import Controller.game.SelectController;
import Model.Civilization;
import Model.Location;
import Model.Terrain;
import Enum.TerrainFeatures;
import Model.Unit;

import java.util.ArrayList;

import static Controller.game.UnitController.isCombatUnit;
import static Controller.game.UnitController.moveUnit;

public class Move {

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

    public static boolean isEnemyUnitInDestination(Location destination, GameController gameController) {
        ArrayList<Civilization> civilizations = gameController.getCivilizations();
        for (Civilization civilization : civilizations) {
            if (civilization.equals(gameController.getCurrentCivilization())) continue;
            for (Unit unit : civilization.getUnits()) {
                if (unit.getLocation().getX() == destination.getX() && unit.getLocation().getY() == destination.getY())
                    return true;
            }
        }
        return false;
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

    public static boolean SameHomeUnitInDestination(Location destination, GameController gameController) {
        Civilization civilization = gameController.getCurrentCivilization();
        for (Unit unit : civilization.getUnits()) {
            if (destination.getX() == unit.getLocation().getX()
                    && destination.getY() == unit.getLocation().getY()
                    && isCombatUnit(SelectController.selectedUnit) == isCombatUnit(unit))
                return true;
        }
        return false;
    }

    public static void multiTurnMovesUpdate(Civilization civilization) {
        for (Unit unit : civilization.getUnits()) {
            //if (!unit.getPathToGo().isEmpty())

        }
    }
}
