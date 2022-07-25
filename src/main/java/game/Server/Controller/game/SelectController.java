package game.Server.Controller.game;

import game.Common.Model.*;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class SelectController {
    public static Unit selectedUnit;
    public static City selectedCity;
    public static Location currentLocation;

    public static String selectNonCombatUnit(Matcher matcher, GameController gameController) {
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        Location location = new Location(x, y);

        if (!positionIsValid(location))
            return "Position ( " + x + " , " + y + " ) is not valid!";

        if (!hasNonCombatUnit(location , gameController))
            return "There isn't any nonCombatUnit in position ( " + x + " , " + y + " )!";

        return "CombatUnit selected successfully! \n Info : \n"
                + "-type of unit : " + selectedUnit.getTypeOfUnit()
                + "\n-unit status : " + selectedUnit.getUnitStatus()
                + "\n-location : ( " + selectedUnit.getLocation().getX()
                + " , " + selectedUnit.getLocation().getY() + " )"
                + "\n-hp : " + selectedUnit.getHp()
                + "\n-civilization name : " + selectedUnit.getCivilization().getName();
    }

    public static String selectCombatUnit(Matcher matcher, GameController gameController) {
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        Location location = new Location(x, y);

        if (!positionIsValid(location))
            return "Position ( " + x + " , " + y + " ) is not valid!";

        if (!hasCombatUnit(location , gameController))
            return "There isn't any combatUnit in position ( " + x + " , " + y + " )!";

        return "CombatUnit selected successfully! \n Info : \n"
                + "-type of unit : " + selectedUnit.getTypeOfUnit()
                + "\n-unit status : " + selectedUnit.getUnitStatus()
                + "\n-location : ( " + selectedUnit.getLocation().getX()
                + " , " + selectedUnit.getLocation().getY() + " )"
                + "\n-hp : " + selectedUnit.getHp()
                + "\n-civilization name : " + selectedUnit.getCivilization().getName();
    }

    public static boolean positionIsValid(Location location) {
        return (location.getY() <= GameController.getInstance().getMapHeight()
                && location.getY() >= 0
                && location.getX() <= GameController.getInstance().getMapWidth()
                && location.getX() >= 0);
    }

    private static boolean hasNonCombatUnit(Location location , GameController gameController) {
        for (Civilization civilization: gameController.getCivilizations()) {
            for (Unit unit : civilization.getUnits()) {
                if (CombatUnitController.isMilitary(unit)) continue;
                if (unit.getLocation().getX() == location.getX()
                        && unit.getLocation().getY() == location.getY()) {
                    selectedUnit = unit;
                    return true;
                }
            }
        }
        return false;
    }
    private static boolean hasCombatUnit(Location location , GameController gameController) {
        for (Civilization civilization: gameController.getCivilizations()) {
            for (Unit unit : civilization.getUnits()) {
                if (!CombatUnitController.isMilitary(unit)) continue;
                if (unit.getLocation().getX() == location.getX()
                        && unit.getLocation().getY() == location.getY()) {
                    selectedUnit = unit;
                    return true;
                }
            }
        }
        return false;
    }

    public static String selectCityByLocation(Matcher matcher , final ArrayList<Civilization> civilizations){
        int x = Integer.parseInt(matcher.group("X")) ;
        int y = Integer.parseInt(matcher.group("Y")) ;
        int width = GameController.getInstance().getMapWidth() ;
        int height = GameController.getInstance().getMapHeight() ;
        if (!positionIsValid(new Location(x,y)))
            return "invalid location" ;
        for (Civilization civilization : civilizations) {
            for (City city : civilization.getCities()) {
                for (Terrain terrain : city.getTerrains()) {
                    if (terrain.getLocation().getX()==x
                        && terrain.getLocation().getY()==y) {
                        selectedCity = city;
                        return "city selected successfully";
                    }
                }
            }
        }
        return "no city in this location" ;
    }

    public static String selectCityByName(Matcher matcher , final  ArrayList<Civilization> civilizations) {
        String cityName = matcher.group("cityName") ;
        for (Civilization civilization : civilizations) {
            for (City city : civilization.getCities()) {
                if (city.getName().equalsIgnoreCase(cityName)){
                    selectedCity = city ;
                    return "city selected successfully" ;
                }
            }
        }
        return "no city with this name";
    }

    public static void selectNextUnit (){
        ArrayList<Unit> units = GameController.getInstance().getCurrentCivilization().getUnits();
        int index = units.indexOf(selectedUnit);
        if (index+1<units.size())
            selectedUnit = units.get(index+1);
        else
            selectedUnit = units.get(0);
    }

    public static void selectPrevUnit (){
        ArrayList<Unit> units = GameController.getInstance().getCurrentCivilization().getUnits();
        int index = units.indexOf(selectedUnit);
        if (index-1>=0)
            selectedUnit = units.get(index-1);
        else
            selectedUnit = units.get(units.size()-1);
    }
}
