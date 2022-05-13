package Controller.game;

import Controller.game.movement.Move;
import Model.City;
import Model.Location;
import Model.Terrain;
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
        if (!SelectController.positionIsValid(destination))
            return "Position ( " + destination.getX() + " , " + destination.getY() + " ) is not valid!";
        if (!isSiegeUnit(selectedUnit))
            return "Combat type of selected unit should be siege!";
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

    private static boolean isMeleeUnit(Unit unit) {
        return unit.getTypeOfUnit().getCombatType() == CombatType.MELEE;
    }

    public static String pillage(Matcher matcher, GameController gameController) {
        // TODO which units can pillage?
        Unit selectedUnit = SelectController.selectedUnit;
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        Location destination = new Location(x, y);
        Terrain terrain;

        if (!isMilitary(selectedUnit))
            return "Selected unit is not military!";
        if ((terrain = TerrainController.getTerrainByLocation(destination)) == null)
            return "Position ( " + destination.getX() + " , " + destination.getY() + " ) is not valid!";
        if (!TerrainController.hasImprovement(terrain)
                && !TerrainController.hasRoad(terrain))
            return "This terrain doesn't have any road or improvement, so you can't pillage!";
        if (terrain.isPillaged())
            return "This terrain is already pillaged!";
        // TODO consider in movements, map, ...
        terrain.setPillaged(true);
        return "Pillaged successfully!";
    }

    private String alert(Unit unit) {
        return "";
    }

    private String fortify(Unit unit) {
        return "";
    }

    private String garrison(Unit Unit, City City) {
        return "";
    }

    private String setUpUnit(Unit unit) {
        return "";
    }

    private boolean canAttack(Unit unit, Location location) {
        return true;
    }

    private String attack(Unit unit, City city) {
        return "";
    }

    private String attack(Unit unit, Unit enemyUnit) {
        return "";
    }

//    private City getCurrentCity(Unit unit, ArrayList<City> cities) {
//
//    }

//    private City getCityByLocation(Location location) {
//
//    }
//
//    private Unit getUnitByLocation(Location location) {
//
//    }

}
