package Controller.game;

import Model.Civilization;
import Model.Location;
import Model.Terrain;
import Model.Unit;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class UnitController {

    public static String moveUnit(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        Unit selectedUnit = SelectController.selectedUnit;
        Location origin = SelectController.currentLocation;
        Location destination = new Location(x, y);
        String placeName;

        if (SelectController.selectedUnit == null)
            return "There isn't any selected unit!";

        if (!hasOwnerShip(selectedUnit))
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

        assert path != null;
        // TODO river to pass
        for (Terrain terrain : path) {
            mp -= terrain.getMp();
            goThrough.add(terrain);
            if (mp <= 0
                    || terrain.getTerrainFeatures().getName().equals("river")) {
                selectedUnit.setLocation(terrain.getLocation());
                // updating fog of war using static method of CivilizationController
                CivilizationController.updateFogOfWar(selectedUnit.getCivilization() , GameController.getMap() , GameController.getMapWidth() , GameController.getMapHeight());
                return "Unit moved to ( " + terrain.getLocation().getX() + " , " + terrain.getLocation().getY() + " )!";
            }
        }

        // updating fog of war using static method of CivilizationController
        CivilizationController.updateFogOfWar(selectedUnit.getCivilization() , GameController.getMap() , GameController.getMapWidth() , GameController.getMapHeight());
      
        SelectController.selectedUnit.setLocation(destination);
        SelectController.currentLocation = destination;
        SelectController.selectedUnit = null;
        return "Selected unit moved to position ( " + destination.getX() + " , " + destination.getY() + " ) successfully!";
    }

    public static boolean hasOwnerShip(Unit currentUnit) {
        for (Unit unit : GameController.getCurrentCivilization().getUnits()) {
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

                if (terrain[i][j].getTerrainFeatures().getName().equals("ice")
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
