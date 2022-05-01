package Controller.game.units;

import Controller.game.GameController;
import Controller.game.NonCombatUnitController;
import Controller.game.SelectController;
import Controller.game.UnitController;
import Model.Location;
import Model.Terrain;
import Model.Unit;
import Enum.TypeOfUnit;
import Enum.TerrainFeatures;

import java.util.regex.Matcher;

public class Worker {

    // TODO handle statics!
    public static String buildImprovement(Matcher matcher, GameController gameController) {
        return "";
    }

    public static String buildRoad(Matcher matcher, GameController gameController) {
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        String error = findError(gameController);

        if (error != null)
            return error;

        Location currentLocation = new Location(x, y);
        // TODO build road --> what is road?
        return "";
    }

    public static String buildRailRoad(Matcher matcher, GameController gameController) {
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        Location selectedLocation = new Location(x, y);

        if (!SelectController.positionIsValid(selectedLocation))
            return "Position ( " + x + " , " + y + " ) is not valid!";
        // TODO build
        return "";
    }

    public static String removeJungle(Matcher matcher, GameController gameController) {
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        String error = findError(gameController);
        Terrain currentTerrain;
        TerrainFeatures feature;

        if (error != null)
            return error;

        Location selectedLocation = new Location(x, y);

        if (!SelectController.positionIsValid(selectedLocation))
            return "Position ( " + x + " , " + y + " ) is not valid!";
        if ((currentTerrain = NonCombatUnitController.isJungleOrForestHere(selectedLocation)) == null)
            return "There isn't any forest or jungle in this location!";

        feature = currentTerrain.getTerrainFeatures();
        currentTerrain.setTerrainFeatures(null);
        return feature + " removed successfully!";
    }

    public static String removeRoute(Matcher matcher, GameController gameController) {
//        int x = Integer.parseInt(matcher.group("X"));
//        int y = Integer.parseInt(matcher.group("Y"));
//        Location currentLocation = new Location(x, y);
        Unit selectedUnit = SelectController.selectedUnit;
        String error = findError(gameController);

        if (error != null)
            return error;

        // TODO remove route
        return "";
    }

    public static String removeMarsh(Matcher matcher, GameController gameController) {
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        String error = findError(gameController);
        Terrain currentTerrain;

        if (error != null)
            return error;

        Location currentLocation = new Location(x, y);

        if ((currentTerrain = NonCombatUnitController.isMarshHere(currentLocation)) == null)
            return "There isn't marsh in this location!";

        currentTerrain.setTerrainFeatures(null);
        return "Marsh removed successfully!";
    }

    public static String repair(Matcher matcher, GameController gameController) {
        // TODO repair!
        return "";
    }

    private static String findError(GameController gameController) {
        Unit selectedUnit = SelectController.selectedUnit;

        if (selectedUnit == null)
            return "There isn't any selected unit!";

        if (!UnitController.hasOwnerShip(selectedUnit, gameController))
            return "This unit does not belong to you!";

        if (selectedUnit.getTypeOfUnit() != TypeOfUnit.WORKER)
            return "The selected unit is " + selectedUnit.getTypeOfUnit().getName()
                    + ". It should be Worker for this action!";

        return null;
    }
}
