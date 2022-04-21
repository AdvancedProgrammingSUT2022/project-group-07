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

    public static String selectNonCombatUnit(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        Location currentLocation = new Location(x, y);
        if (!positionIsValid(currentLocation))
            return "NonCombatUnit's position is not valid!";
        // TODO is this Unit for this player? --> UnitController.hasOwnerShip + mp
        for (Civilization civilization: GameController.getCivilizations()) {
            for (Unit unit : civilization.getUnits()) {
                if (unit.getLocation().equals(currentLocation)) {
                    selectedUnit = unit;
                }
            }
        }
        return "NonCombatUnit selected successfully!";
    }

    private String selectCombatUnit(Location location) {
        return "";
    }

    private static boolean positionIsValid(Location location) {
        // TODO inja unit darim?
        return true;
    }

    private String selectCity(String name) {
        return "";
    }

    private String selectCity(Location location) {
        return "";
    }
}
