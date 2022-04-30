package Controller.game;

import Model.City;
import Model.Civilization;
import Model.Location;
import Model.Unit;
import Enum.UnitStatus;
import Enum.TypeOfUnit;

import java.util.regex.Matcher;

public class UnitController {

    // TODO the first two errors are the same in all methods --> handle
    // TODO handle this --> age unit khab bashe, nemitoone darkhaste jadid dashte bashe ta vaghti behesh dastoor jadid bedan
    public static String moveUnit(Matcher matcher, GameController gameController) {
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        Unit selectedUnit = SelectController.selectedUnit;
        Location origin = selectedUnit.getLocation();
        Location destination = new Location(x, y);
        String placeName;

        if (SelectController.selectedUnit == null)
            return "There isn't any selected unit!";

        if (!hasOwnerShip(selectedUnit, gameController))
            return "This unit does not belong to you!";

        if (!SelectController.positionIsValid(destination))
            return "Destination ( " + x + " , " + y + " ) is not valid!";

        if ((placeName = TheShortestPath.destinationIsValid(destination)) != null)
            return "Your destination is " + placeName + " so you can not go to it!";

        return TheShortestPath.checkNeededMpForMove(origin, destination);
    }

    public static boolean hasOwnerShip(Unit currentUnit, GameController gameController) {
        for (Unit unit : gameController.getCurrentCivilization().getUnits()) {
            if (currentUnit.getLocation().getX() == unit.getLocation().getX()
                    && currentUnit.getLocation().getY() == unit.getLocation().getY())
                return true;
        }
        return false;
    }

    public static String sleep(GameController gameController) {
        // show errors just when you select the unit
        if (SelectController.selectedUnit == null)
            return "There isn't any selected unit!";

        if (!hasOwnerShip(SelectController.selectedUnit, gameController))
            return "This unit does not belong to you!";

        SelectController.selectedUnit.setUnitStatus(UnitStatus.SLEEP);
        return "Selected unit has slept successfully!";
    }

    public static String fortifyUnit(GameController gameController) {
        Unit selectedUnit = SelectController.selectedUnit;

        if (selectedUnit == null)
            return "There isn't any selected unit!";

        if (!hasOwnerShip(selectedUnit, gameController))
            return "This unit does not belong to you!";

        if (selectedUnit.getTypeOfUnit() == TypeOfUnit.WORKER
                || selectedUnit.getTypeOfUnit() == TypeOfUnit.SETTLER)
            return "The selected unit is " + selectedUnit.getTypeOfUnit().getName()
                    + ". It should be a combatUnit for this action!";

        // TODO HEAL and main things to do!
        return "";
    }

    public void healUnit(Unit unit) {
    }

    public static String cancelMission(GameController gameController) {
        if (SelectController.selectedUnit == null)
            return "There isn't any selected unit!";

        if (!hasOwnerShip(SelectController.selectedUnit, gameController))
            return "This unit does not belong to you!";
        // TODO handle unit's movements first!
        // TODO remove all movements!
        return "All of the missions of the selected unit have been canceled!";
    }

    public static String wake(GameController gameController) {
        if (SelectController.selectedUnit == null)
            return "There isn't any selected unit!";

        if (!hasOwnerShip(SelectController.selectedUnit, gameController))
            return "This unit does not belong to you!";

        SelectController.selectedUnit.setUnitStatus(UnitStatus.ACTIVE);
        return "Selected unit is awake!";
    }

    public static String deleteUnit(GameController gameController) {
        Unit selectedUnit = SelectController.selectedUnit;

        if (selectedUnit == null)
            return "There isn't any selected unit!";

        if (!hasOwnerShip(selectedUnit, gameController))
            return "This unit does not belong to you!";

        // find the selected unit in current civilization and remove it.
        for (Unit unit : gameController.getCurrentCivilization().getUnits()) {
            if (unit.getLocation().getX() == selectedUnit.getLocation().getX()
                    && unit.getLocation().getY() == selectedUnit.getLocation().getY()) {
                gameController.getCurrentCivilization().getUnits().remove(unit);
                break;
            }
        }
        return "Unit deleted successfully!";
    }

    // TODO what is this ?
    public String upgrade(Unit unit) {
        return "";
    }

    public static String createUnit(Matcher matcher, GameController gameController) {
        String unitName = matcher.group("unit");
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        Civilization currentCivilization = gameController.getCurrentCivilization();
        City selectedCity = SelectController.selectedCity;

        if (selectedCity == null)
            return "Select a city first!";

        // TODO what was turn in Unit?
        for (TypeOfUnit typeOfUnit : TypeOfUnit.values()) {
            if (typeOfUnit.getName().equals(unitName)) {
                if (selectedCity.getGold() >= typeOfUnit.getCost()) {
                    Unit newUnit = new Unit(typeOfUnit, UnitStatus.ACTIVE, new Location(x, y),
                            typeOfUnit.getHp(), currentCivilization, 0);

                    selectedCity.setGold(-1 * typeOfUnit.getCost());
                    currentCivilization.setGold(-1 * typeOfUnit.getCost());
                    currentCivilization.addUnit(newUnit);
                    return "Unit has been added successfully!";
                }
                else
                    return "You don't have enough gold to create this unit!";
            }
        }

        return "";
    }
}
