package game.Controller.game.movement;

import game.Controller.game.CivilizationController;
import game.Controller.game.GameController;
import game.Controller.game.TerrainController;
import game.Model.Civilization;
import game.Model.Location;
import game.Model.Terrain;
import game.Enum.TerrainFeatures;
import game.Model.Unit;
import game.Enum.TypeOfUnit;
import java.util.ArrayList;


import static game.Controller.game.UnitController.isCombatUnit;

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

    public static String checkNeededMpForMove(ArrayList<Terrain> path, Unit unit , GameController gameController) {
        if (path == null) return "";
        Location destination = path.get(path.size() - 1).getLocation();
        ArrayList<Terrain> goThrough = new ArrayList<>();
        int mp = unit.getMp();
        path.remove(0);
        for (Terrain terrain : path) {
            mp -= terrain.getMp();
            if (isInZoneOfControl(terrain , unit , gameController)) {mp = -1;}
            goThrough.add(terrain);
            if (mp <= 0) {
                unit.setLocation(terrain.getLocation());
                CivilizationController.updateFogOfWar(unit.getCivilization(), GameController.getMap(), GameController.getMapWidth(), GameController.getMapHeight());
                unit.setTimesMovedThisTurn(10);
                updateGonePath(unit, goThrough);
                return "Selected unit moved to ( " + terrain.getLocation().getX() + " , " + terrain.getLocation().getY() + " )!";
            }
        }

        // updating fog of war using static method of CivilizationController
        CivilizationController.updateFogOfWar(unit.getCivilization(), GameController.getMap(), GameController.getMapWidth(), GameController.getMapHeight());

        unit.setLocation(destination);
        unit.setTimesMovedThisTurn(unit.getTimesMovedThisTurn() + 1);
        updateGonePath(unit, goThrough);
        return "Selected unit moved to position ( " + destination.getX() + " , " + destination.getY() + " ) successfully!";
    }

    private static boolean isInZoneOfControl(Terrain terrain, Unit unit, GameController gameController) {
        ArrayList<Terrain> neighbours = CivilizationController.getNeighbourTerrainsByRadius1(
                terrain.getLocation() , GameController.getMap() , GameController.getMapWidth() , GameController.getMapHeight()
        );
        for (Civilization civilization : gameController.getCivilizations()) {
            if (civilization.equals(unit.getCivilization())) continue;
            for (Unit enemyUnit : civilization.getUnits()) {
                if (enemyUnit.getTypeOfUnit() == TypeOfUnit.WORKER || enemyUnit.getTypeOfUnit() == TypeOfUnit.SETTLER)
                    continue;
                if (neighbours.contains(TerrainController.getTerrainByLocation(enemyUnit.getLocation())))
                    return true;
            }
        }
        return false;
    }

    private static void updateGonePath(Unit unit, ArrayList<Terrain> goThrough) {
        for (Terrain terrain : goThrough) {
            unit.getPathToGo().remove(terrain);
        }
    }

    public static boolean SameHomeUnitInDestination(Location destination, GameController gameController , Unit unit) {
        Civilization civilization = gameController.getCurrentCivilization();
        for (Unit unit1 : civilization.getUnits()) {
            if (destination.getX() == unit1.getLocation().getX()
                    && destination.getY() == unit1.getLocation().getY()
                    && isCombatUnit(unit1) == isCombatUnit(unit))
                return true;
        }
        return false;
    }

}
