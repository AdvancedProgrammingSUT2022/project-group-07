package Controller.game;

import Controller.game.movement.Move;
import Model.City;
import Model.Location;
import Model.Terrain;
import Model.Unit;
import Enum.CombatType;
import Enum.UnitStatus;

import java.util.regex.Matcher;

public class CombatUnitController {
    // TODO ranged units!

    public static String siegeUnits() {
        Unit selectedUnit = SelectController.selectedUnit;
        String placeName;

        if (selectedUnit.getUnitStatus() != UnitStatus.RANGED_UNIT_IS_READY)
            return "Unit is not set-up!";

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

    public static String pillage(GameController gameController) {
        Unit selectedUnit = SelectController.selectedUnit;
        int x = selectedUnit.getLocation().getX();
        int y = selectedUnit.getLocation().getY();
        Location destination = new Location(x, y);
        Terrain terrain;

        if (!isMilitary(selectedUnit))
            return "Selected unit is not military!";
        if ((terrain = TerrainController.getTerrainByLocation(destination)) == null)
            return "Position ( " + destination.getX() + " , " + destination.getY() + " ) is not valid!";
        if (!TerrainController.hasImprovement(terrain)
                && !TerrainController.hasRoad(terrain))
            return "This terrain doesn't have any road or improvement, so you can't pillage!";
        if (selectedUnit.getTimesMovedThisTurn() >= 2)
            return "Unit is out of move!";
        if (terrain.isPillaged())
            return "This terrain is already pillaged!";
        // TODO consider in movements, map, ...
        terrain.setPillaged(true);
        // some gold for unit's civilization
        selectedUnit.getCivilization().setGold(selectedUnit.getCivilization().getGold() + 10);
        return "Pillaged successfully!";
    }

    public static String setUpUnit(Matcher matcher) {
        // TODO if unit is asleep or ...
        Unit selectedUnit = SelectController.selectedUnit;
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        Location destination = new Location(x, y);
        String placeName;

        if (!isRangedUnit(selectedUnit))
            return "Selected unit is not ranged!";
        if (!SelectController.positionIsValid(destination))
            return "Position ( " + destination.getX() + " , " + destination.getY() + " ) is not valid!";
        if ((placeName = Move.destinationIsValid(destination)) != null)
            return "Your destination is " + placeName + ". You can't setUp!";

        selectedUnit.setUnitStatus(UnitStatus.RANGED_UNIT_IS_READY);
        return "";
    }

    private static boolean isRangedUnit(Unit unit) {
        return (unit.getTypeOfUnit().getCombatType() == CombatType.SIEGE
                || unit.getTypeOfUnit().getCombatType() == CombatType.ARCHERY
                || unit.getTypeOfUnit().getCombatType() == CombatType.GUNPOWDER);
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
