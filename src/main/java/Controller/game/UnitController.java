package Controller.game;

import Controller.game.movement.TheShortestPath;
import Model.*;
import Enum.UnitStatus;
import Enum.TypeOfUnit;
import Model.Location;
import Model.Terrain;
import Model.Unit;

import java.util.ArrayList;
import java.util.regex.Matcher;

import static Controller.game.movement.Move.*;

public class UnitController {
    private static City selectedCity = SelectController.selectedCity;

    public static String moveUnit(Matcher matcher, GameController gameController , Unit unit) {
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        if (unit == null)
            return "There isn't any selected unit!";
        Location origin = unit.getLocation();
        Location destination = new Location(x, y);
        if (!hasOwnerShip(unit, gameController))
            return "This unit does not belong to you!";
        if (!SelectController.positionIsValid(destination))
            return "Destination ( " + destination.getX() + " , " + destination.getY() + " ) is not valid!";
        ArrayList<Terrain> path = TheShortestPath.showPath(origin, destination);
        unit.setPathToGo(path);
        return moveUnit(path ,  gameController , unit , destination);
    }

    public static String moveUnit(ArrayList<Terrain> path , GameController gameController , Unit unit , Location destination) {
        String placeName;
        if ((placeName = destinationIsValid(destination)) != null)
            return "Your destination is a " + placeName + " so you can not go to it!";
        if (isEnemyUnitInDestination(destination, gameController))
            return "an enemy unit is in your destination at the moment!";
        if (SameHomeUnitInDestination(destination, gameController))
            return "another unit with same military status is in the destination selected!";
        if (unit.getTimesMovedThisTurn() >= 2)
            return "unit is out of move!";
        return checkNeededMpForMove(path , unit , gameController);
    }

    public static int isCombatUnit(Unit unit) {
        if (unit.getTypeOfUnit() == TypeOfUnit.SETTLER
        || unit.getTypeOfUnit() == TypeOfUnit.WORKER)
            return 0;
        return 1;
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
        Unit selectedUnit = SelectController.selectedUnit;
        String error = checkUnit(selectedUnit, gameController);

        if (error != null)
            return error;
        SelectController.selectedUnit.setUnitStatus(UnitStatus.SLEEP);
        return "Selected unit has slept successfully!";
    }


    public void healUnit(Unit unit) {
        // TODO heal!
    }

    public static String cancelMission(GameController gameController) {
        Unit selectedUnit = SelectController.selectedUnit;
        String error = checkUnit(selectedUnit, gameController);

        if (error != null)
            return error;
        selectedUnit.setImprovementAboutToBeCreated(null);
        selectedUnit.setRouteAboutToBeBuilt(null);
        return "All of the missions of the selected unit have been canceled!";
    }

    public static String wake(GameController gameController) {
        Unit selectedUnit = SelectController.selectedUnit;
        String error = checkUnit(selectedUnit, gameController);

        if (error != null)
            return error;
        SelectController.selectedUnit.setUnitStatus(UnitStatus.ACTIVE);
        return "Selected unit is awake!";
    }

    public static String deleteUnit(GameController gameController) {
        Unit selectedUnit = SelectController.selectedUnit;
        String error = checkUnit(selectedUnit, gameController);
        City city = SelectController.selectedCity;

        if (error != null)
            return error;
        // find the selected unit in current civilization and remove it.
        city.setGold((int) (city.getGold() + 0.1 * selectedUnit.getTypeOfUnit().getCost()));
        gameController.getCurrentCivilization().getUnits().remove(selectedUnit);
        return "Unit deleted successfully!";
    }

    private static String checkUnit(Unit selectedUnit, GameController gameController) {
        if (selectedUnit == null)
            return "There isn't any selected unit!";

        if (!hasOwnerShip(selectedUnit, gameController))
            return "This unit does not belong to you!";
        return null;
    }

    public String upgrade(Unit unit) {
        return "";
    }
}
