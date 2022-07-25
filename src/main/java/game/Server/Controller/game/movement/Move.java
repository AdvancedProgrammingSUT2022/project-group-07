package game.Server.Controller.game.movement;

import game.Server.Controller.game.CivilizationController;
import game.Server.Controller.game.GameController;
import game.Server.Controller.game.TerrainController;
import game.Common.Model.Civilization;
import game.Common.Model.Location;
import game.Common.Model.Terrain;
import game.Common.Enum.TerrainFeatures;
import game.Common.Model.Unit;
import game.Common.Enum.TypeOfUnit;
import game.Client.View.components.Tile;
import game.Server.Controller.game.UnitController;

import java.util.ArrayList;

public class Move {

    // check if destination is mountain, ocean or river which are impassable
    // TODO river for destination ?!
    public static String destinationIsValid(Location destination) {
        Tile[][] tiles = GameController.getInstance().getMap();
        String typeOfTerrain;
        Location terrainLocation;

        for (int i = 0; i < GameController.getInstance().getMapHeight(); i++) {
            for (int j = 0; j < GameController.getInstance().getMapWidth(); j++) {
                terrainLocation = tiles[i][j].getTerrain().getLocation();
                typeOfTerrain = tiles[i][j].getTerrain().getTypeOfTerrain().getName();

                if ((typeOfTerrain.equals("mountain")
                        || typeOfTerrain.equals("ocean"))
                        && terrainLocation.getX() == destination.getX()
                        && terrainLocation.getY() == destination.getY())
                    return typeOfTerrain;

                if (tiles[i][j].getTerrain().getTerrainFeatures() != null
                        && tiles[i][j].getTerrain().getTerrainFeatures() == TerrainFeatures.ICE
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
                CivilizationController.updateFogOfWar(unit.getCivilization(), GameController.getInstance().getMap(),
                        GameController.getInstance().getMapWidth(), GameController.getInstance().getMapHeight());
                unit.setTimesMovedThisTurn(10);
                updateGonePath(unit, goThrough);
                return "Selected unit moved to ( " + terrain.getLocation().getX() + " , " + terrain.getLocation().getY() + " )!";
            }
        }

        // updating fog of war using static method of CivilizationController
        CivilizationController.updateFogOfWar(unit.getCivilization(), GameController.getInstance().getMap(),
                GameController.getInstance().getMapWidth(), GameController.getInstance().getMapHeight());

        unit.setLocation(destination);
        unit.setTimesMovedThisTurn(unit.getTimesMovedThisTurn() + 1);
        updateGonePath(unit, goThrough);
        return "Selected unit moved to position ( " + destination.getX() + " , " + destination.getY() + " ) successfully!";
    }

    private static boolean isInZoneOfControl(Terrain terrain, Unit unit, GameController gameController) {
        ArrayList<Terrain> neighbours = CivilizationController.getNeighbourTerrainsByRadius1(
                terrain.getLocation() , GameController.getInstance().getMap() ,
                GameController.getInstance().getMapWidth() , GameController.getInstance().getMapHeight()
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
                    && UnitController.isCombatUnit(unit1) == UnitController.isCombatUnit(unit))
                return true;
        }
        return false;
    }

}
