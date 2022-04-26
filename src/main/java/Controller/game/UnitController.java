package Controller.game;

import Model.Location;
import Model.Terrain;
import Model.Unit;

import java.util.ArrayList;
import java.util.regex.Matcher;
import Enum.TerrainFeatures;

public class UnitController {

    public static String moveUnit(Matcher matcher, GameController gameController) {
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        Unit selectedUnit = SelectController.selectedUnit;
        Location origin = selectedUnit.getLocation();
        Location destination = new Location(x, y);
        String placeName;

        if (SelectController.selectedUnit == null)
            return "There isn't any selected unit!";

        if (!hasOwnerShip(selectedUnit , gameController))
            return "This unit does not belong to you!";

        if (!SelectController.positionIsValid(destination))
            return "Destination ( " + x + " , " + y + " ) is not valid!";

        if ((placeName = destinationIsValid(destination)) != null)
            return "Your destination is a " + placeName + " so you can not go to it!";

        return checkNeededMpForMove(origin, destination);
    }

    private static String checkNeededMpForMove(Location origin, Location destination) {
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
                CivilizationController.updateFogOfWar(selectedUnit.getCivilization() , GameController.getMap() , GameController.getMapWidth() , GameController.getMapHeight());
                return "Selected unit moved to ( " + terrain.getLocation().getX() + " , " + terrain.getLocation().getY() + " )!";
            }
        }

        // updating fog of war using static method of CivilizationController
        CivilizationController.updateFogOfWar(selectedUnit.getCivilization() , GameController.getMap() , GameController.getMapWidth() , GameController.getMapHeight());
      
        SelectController.selectedUnit.setLocation(destination);
        SelectController.currentLocation = destination;
        return "Selected unit moved to position ( " + destination.getX() + " , " + destination.getY() + " ) successfully!";
    }

    public static boolean hasOwnerShip(Unit currentUnit, GameController gameController) {
        for (Unit unit : gameController.getCurrentCivilization().getUnits()) {
            if (currentUnit.getLocation().getX() == unit.getLocation().getX()
                    && currentUnit.getLocation().getY() == unit.getLocation().getY())
                return true;
        }
        return false;
    }

    // check if destination is mountain, ocean or river which are impassable
    // TODO river for destination ?!
    private static String destinationIsValid(Location destination) {
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

    public String sleep(Unit unit) {
        return "";
    }

    public void healUnit(Unit unit) {

    }

    public String cancelMission(Unit unit) {
        return "";
    }

    public String wake(Unit unit) {
        return "";
    }

    public String deleteUnit(Unit unit) {
        return "";
    }

    public String upgrade(Unit unit) {
        return "";
    }
}
