package Controller.game;

import Controller.game.combat.ErrorHandling;
import Controller.game.combat.UnitVsCity;
import Controller.game.movement.Move;
import Model.*;
import Enum.CombatType;
import Enum.UnitStatus;

import java.util.regex.Matcher;

public class CombatUnitController {


    private static boolean isSiegeUnit(Unit unit) {
        return unit.getTypeOfUnit().getCombatType() == CombatType.SIEGE;
    }

    public static boolean isMilitary(Unit unit) {
        return !(NonCombatUnitController.isWorker(unit)
                || NonCombatUnitController.isSettler(unit));
    }

    private static boolean isMeleeUnit(Unit unit) {
        return unit.getTypeOfUnit().getCombatType() == CombatType.MELEE;
    }

    public static String pillage() {
        Unit selectedUnit = SelectController.selectedUnit;
        int x = selectedUnit.getLocation().getX();
        int y = selectedUnit.getLocation().getY();
        Location position = new Location(x, y);
        Terrain terrain = TerrainController.getTerrainByLocation(position);

        if (!isMilitary(selectedUnit))
            return "Selected unit is not military!";
        assert terrain != null;
        if (!TerrainController.hasImprovement(terrain)
                && !TerrainController.hasRoad(terrain))
            return "This terrain doesn't have any road or improvement, so you can't pillage!";
        if (selectedUnit.getTimesMovedThisTurn() >= 2)
            return "Unit is out of move!";
        if (terrain.isPillaged())
            return "This terrain is already pillaged!";
        // TODO consider in movements, map, ...
        terrain.setPillaged(true);
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

    public static boolean isRangedUnit(Unit unit) {
        return (unit.getTypeOfUnit().getCombatType() == CombatType.SIEGE
                || unit.getTypeOfUnit().getCombatType() == CombatType.ARCHERY
                || unit.getTypeOfUnit().getCombatType() == CombatType.GUNPOWDER);
    }

    public static String alert(GameController gameController) {
        Unit selectedUnit = SelectController.selectedUnit;
        String error = checkUnit(selectedUnit, gameController);
        if (error != null)
            return error;
        selectedUnit.setUnitStatus(UnitStatus.ALERT);
        return "Alerted successfully!";
    }

    public static String checkUnit(Unit selectedUnit, GameController gameController) {
        if (selectedUnit == null)
            return "There isn't any selected unit!";
        if (!UnitController.hasOwnerShip(selectedUnit, gameController))
            return "This unit does not belong to you!";
        if (!isMilitary(selectedUnit))
            return "Selected unit is not military!";
        return null;
    }

    public static String fortify(GameController gameController) {
        Unit selectedUnit = SelectController.selectedUnit;
        String error = checkUnit(selectedUnit, gameController);

        if (error != null)
            return error;

        selectedUnit.setUnitStatus(UnitStatus.FORTIFY);
        // hp will increase in next turn
        return "";
    }

    public static String garrison(GameController gameController) {
        Unit selectedUnit = SelectController.selectedUnit;
        Civilization civilization = gameController.getCurrentCivilization();
        Terrain currentTerrain = TerrainController.getTerrainByLocation(selectedUnit.getLocation());
        City currentCity = CityController.getCityByTerrain(civilization, currentTerrain);
        String error = checkUnit(selectedUnit, gameController);
        if (error != null)
            return error;
        assert currentTerrain != null;
        if (!CityController.isInCenterOfOwnCity(currentTerrain, civilization, gameController))
            return "Unit should be in the center of city in his civilization!";

        if (currentCity != null)
            currentCity.setDefencePower(currentCity.getDefencePower() + 5);
        return "Settled successfully!";
    }

    public static String attackCity(Matcher matcher, GameController gameController) {
        SelectController.selectCityByLocation(matcher , gameController.getCivilizations());
        City city = SelectController.selectedCity;
        String error;
        if ((error = ErrorHandling.findAttackCityError(SelectController.selectedUnit , city , gameController)) != null)
            return error;
        UnitVsCity.attack(SelectController.selectedUnit , city , gameController);
        SelectController.selectedUnit.setTimesMovedThisTurn(3);
        return "attacked selected city!";
    }

    public static String attackUnit(Matcher matcher) {
        return "";
    }



}
