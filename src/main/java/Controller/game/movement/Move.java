package Controller.game.movement;

import Controller.game.CivilizationController;
import Controller.game.GameController;
import Controller.game.SelectController;
import Controller.game.TerrainController;
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

    public static String checkNeededMpForMove(ArrayList<Terrain> path , Unit unit) {
        // TODO check if it needs to continue in other turns
        if (path == null) return "";
        Location destination = path.get(path.size() - 1).getLocation();
        ArrayList<Terrain> goThrough = new ArrayList<>();
        int mp = unit.getMp();
        // TODO river to pass
        path.remove(0);
        for (Terrain terrain : path) {
            mp -= terrain.getMp();
            goThrough.add(terrain);
            if (mp <= 0) {
                unit.setLocation(terrain.getLocation());
                CivilizationController.updateFogOfWar(unit.getCivilization(), GameController.getMap(), GameController.getMapWidth(), GameController.getMapHeight());
                unit.setTimesMovedThisTurn(10);
                updateGonePath(unit , goThrough);
                return "Selected unit moved to ( " + terrain.getLocation().getX() + " , " + terrain.getLocation().getY() + " )!";
            }
        }

        // updating fog of war using static method of CivilizationController
        CivilizationController.updateFogOfWar(unit.getCivilization(), GameController.getMap(), GameController.getMapWidth(), GameController.getMapHeight());

        unit.setLocation(destination);
        unit.setTimesMovedThisTurn(unit.getTimesMovedThisTurn() + 1);
        updateGonePath(unit , goThrough);
        return "Selected unit moved to position ( " + destination.getX() + " , " + destination.getY() + " ) successfully!";
    }

    private static void updateGonePath(Unit unit, ArrayList<Terrain> goThrough) {
        for (Terrain terrain : goThrough) {
            unit.getPathToGo().remove(terrain);
        }
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

    public static void UnitMovementsUpdate(Civilization civilization, GameController gameController) {
        for (Unit unit : civilization.getUnits()) {unit.setTimesMovedThisTurn(0);}
        for (Unit unit : civilization.getUnits()) {
            if (!unit.getPathToGo().isEmpty()) {
                moveUnit(unit.getPathToGo() , gameController , unit
                , unit.getPathToGo().get(unit.getPathToGo().size() - 1).getLocation());
            }
        }
    }
}
