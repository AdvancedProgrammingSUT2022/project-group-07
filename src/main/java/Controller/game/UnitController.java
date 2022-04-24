package Controller.game;

import Model.Location;
import Model.Terrain;
import Model.Unit;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class UnitController {

    public static String moveUnit(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        Location origin = SelectController.currentLocation;
        Location destination = new Location(x, y);
        if (!hasOwnerShip(SelectController.selectedUnit))
            return "This unit does not belong to you!";
        if (SelectController.selectedUnit == null)
            return "There isn't any nonCombatUnit in position ( " + x + " , " + y + " )!";
        if (!SelectController.positionIsValid(destination))
            return "Destination ( " + x + " , " + y + " ) is not valid!";
        //////////////////////////////////////////////////////

        Unit selectedUnit = SelectController.selectedUnit;
        int mp = selectedUnit.getMp();
        ArrayList<Terrain> path = TheShortestPath.showPath(origin, destination);
        ArrayList<Terrain> goThrough = new ArrayList<>();
        assert path != null;
        for (Terrain terrain : path) {
            mp -= terrain.getMp();
            goThrough.add(terrain);
            if (mp < 0) {
                selectedUnit.setLocation(terrain.getLocation());
                // TODO update fog of war
                return "Unit moved to ( " + terrain.getLocation().getX() + " , " + terrain.getLocation().getY() + " )!";
            }
        }
        selectedUnit.setLocation(destination);
        selectedUnit = null;
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
