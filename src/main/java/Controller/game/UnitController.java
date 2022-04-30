package Controller.game;

import Controller.game.movement.TheShortestPath;
import Model.Civilization;
import Model.Location;
import Model.Terrain;
import Model.Unit;
import Enum.TypeOfUnit;
import java.util.ArrayList;
import java.util.regex.Matcher;

import Enum.TerrainFeatures;

import static Controller.game.movement.Move.*;

public class UnitController {

    public static String moveUnit(Matcher matcher, GameController gameController) {
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        Unit selectedUnit = SelectController.selectedUnit;


        if (SelectController.selectedUnit == null)
            return "There isn't any selected unit!";

        Location origin = selectedUnit.getLocation();
        Location destination = new Location(x, y);
        String placeName;

        if (!hasOwnerShip(selectedUnit, gameController))
            return "This unit does not belong to you!";

        if (!SelectController.positionIsValid(destination))
            return "Destination ( " + x + " , " + y + " ) is not valid!";

        if ((placeName = destinationIsValid(destination)) != null)
            return "Your destination is a " + placeName + " so you can not go to it!";

        if (isEnemyUnitInDestination(destination , gameController))
            return "an enemy unit is in your destination at the moment!";
        if (SameHomeUnitInDestination(destination , gameController))
            return "another unit with same military status is in the destination selected!";

        return checkNeededMpForMove(origin, destination);
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
