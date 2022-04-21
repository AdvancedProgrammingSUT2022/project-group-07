package Controller.game;

import Model.City;
import Model.Civilization;
import Model.Location;
import Model.Unit;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class SelectController {
    public static Unit selectedUnit;
    public static City selectedCity;
    // TODO : add to UML
    public static Location currentLocation;

    public static String selectNonCombatUnit(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        currentLocation = new Location(x, y);
        if (!positionIsValid(currentLocation))
            return "Position ( " + x + " , " + y + " ) is not valid!";
        return "NonCombatUnit selected successfully!";
    }

    private String selectCombatUnit(Location location) {
        return "";
    }

    private static boolean positionIsValid(Location location) {
        for (Civilization civilization: GameController.getCivilizations()) {
            for (Unit unit : civilization.getUnits()) {
                if (unit.getLocation().equals(location)) {
                    selectedUnit = unit;
                    return true;
                }
            }
        }
        return false;
    }

    private String selectCity(String name) {
        return "";
    }

    private String selectCity(Location location) {
        return "";
    }
}
