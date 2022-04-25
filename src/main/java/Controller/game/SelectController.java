package Controller.game;

import Model.City;
import Model.Civilization;
import Model.Location;
import Model.Unit;

import java.util.regex.Matcher;

public class SelectController {
    public static Unit selectedUnit;
    public static City selectedCity;
    public static Location currentLocation;

    public static String selectNonCombatUnit(Matcher matcher, GameController gameController) {
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        currentLocation = new Location(x, y);

        if (!positionIsValid(currentLocation))
            return "Position ( " + x + " , " + y + " ) is not valid!";

        if (!hasUnit(currentLocation , gameController))
            return "There isn't any nonCombatUnit in position ( " + x + " , " + y + " )!";

        return "NonCombatUnit selected successfully! \n Info : \n"
                + "-type of unit : " + selectedUnit.getTypeOfUnit()
                + "\n-unit status : " + selectedUnit.getUnitStatus()
                + "\n-location : ( " + selectedUnit.getLocation().getX()
                + " , " + selectedUnit.getLocation().getY() + " )"
                + "\n-hp : " + selectedUnit.getHp()
                + "\n-civilization name : " + selectedUnit.getCivilization().getName();
    }

    private String selectCombatUnit(Location location) {
        return "";
    }

    public static boolean positionIsValid(Location location) {
        return (location.getY() <= GameController.getMapHeight()
                && location.getY() >= 0
                && location.getX() <= GameController.getMapWidth()
                && location.getX() >= 0);
    }

    private static boolean hasUnit(Location location , GameController gameController) {
        for (Civilization civilization: gameController.getCivilizations()) {
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
