package Controller.game;

import Controller.game.movement.Move;
import Model.City;
import Model.Location;
import Model.Unit;
import Enum.CombatType;

import java.util.regex.Matcher;


public class CombatUnitController {
    // TODO ranged units!

    public static String siegeUnits(Matcher matcher, GameController gameController) {
        Unit selectedUnit = SelectController.selectedUnit;
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        Location destination = new Location(x, y);
        String placeName;

        if (!isMilitary(selectedUnit))
            return "Selected unit is not military!";
        if (!isSiegeUnit(selectedUnit))
            return "Combat type of selected unit should be siege!";
        if (!SelectController.positionIsValid(destination))
            return "Position ( " + x + " , " + y + " ) is not valid!";
        if ((placeName = Move.destinationIsValid(destination)) != null)
            return "Your destination is " + placeName + ". You cannot siege!";

        // TODO other errors and main action
        return "";
    }

    private static boolean isSiegeUnit(Unit unit) {
        return unit.getTypeOfUnit().getCombatType() == CombatType.SIEGE;
    }

    private static boolean isMilitary(Unit unit) {
        return !(NonCombatUnitController.isWorker(unit)
                || NonCombatUnitController.isSettler(unit));
    }

    private String alert(Unit unit) {
        return "";
    }

    private String fortify(Unit unit) {
        return "";
    }

//    private City getCurrentCity(Unit unit, ArrayList<City> cities) {
//
//    }

    private String garrison(Unit Unit, City City) {
        return "";
    }

    private String setUpUnit(Unit unit) {
        return "";
    }

    private boolean canAttack(Unit unit, Location location) {
        return true;
    }

//    private City getCityByLocation(Location location) {
//
//    }
//
//    private Unit getUnitByLocation(Location location) {
//
//    }

    private String attack(Unit unit, City city) {
        return "";
    }

    private String attack(Unit unit, Unit enemyUnit) {
        return "";
    }
}
