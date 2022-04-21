package Controller.game;

import Model.Location;
import Model.Unit;

import java.util.regex.Matcher;

public class UnitController {

    // TODO handle mp !

    public static String moveUnit(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        Location origin = SelectController.currentLocation;
        Location destination = new Location(x, y);
        if (!hasOwnerShip(SelectController.selectedUnit))
            return "This unit does not belong to you!";

        return "Selected unit moved to position + ( " + destination.getX() + " , " + destination.getY() + " ) successfully!";
    }

    public static int minimumMp(Location origin, Location destination, int mp) {
//        int mp1, mp2;
//        if (origin.getX() <= destination.getX()) {
//            Location newLocation = new Location(origin.getX()+1, origin.getY());
//            mp2 = mp + getTerrainByLocation(newLocation).getMp + ;
//            mp(newLocation, destination, mp2);
//        }
        // y

        return 0;
    }

    // TODO add currentCivilization to GameController
    public static boolean hasOwnerShip(Unit currentUnit) {
        for (Unit Unit : GameController.currentCivilization.getUnits) {
            if (currentUnit.getLocation().equals(unit.getLocation))
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
