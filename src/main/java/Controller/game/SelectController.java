package Controller.game;

import Model.City;
import Model.Civilization;
import Model.Location;
import Model.Unit;
import Enum.MapDimension;

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
        if (!hasNonCombatUnit(currentLocation))
            return "There isn't any nonCombatUnit in position ( " + x + " , " + y + " )!";
        return "NonCombatUnit selected successfully! \n Info : \n"
                + "-type of unit : " + selectedUnit.getTypeOfUnit()
                + "-unit status : " + selectedUnit.getUnitStatus()
                + "-location : ( " + selectedUnit.getLocation().getX()
                + " , " + selectedUnit.getLocation().getY() + " )"
                + "-hp : " + selectedUnit.getHp()
                + "-civilization name : " + selectedUnit.getCivilization().getName();
    }

    private String selectCombatUnit(Location location) {
        return "";
    }

    // TODO Map size !!
    private static boolean positionIsValid(Location location) {
        return (location.getY() <= GameController.getMapHeight()
                && location.getY() >= 0
                && location.getX() <= GameController.getMapWidth()
                && location.getX() >= 0);
    }
     // TODO UML
    private static boolean hasNonCombatUnit(Location location) {
        for (Civilization civilization: GameController.getCivilizations()) {
            for (Unit unit : civilization.getUnits()) {
                if (unit.getLocation().getX() == location.getX()
                        && unit.getLocation().getY() == location.getY()) {
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
