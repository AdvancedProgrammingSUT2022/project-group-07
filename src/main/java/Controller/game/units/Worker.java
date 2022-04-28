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

    // TODO handle same errors
    // TODO handle statics!
    public static String removeJungle(Matcher matcher, GameController gameController) {
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        Unit selectedUnit = SelectController.selectedUnit;
        Terrain currentTerrain;
        TerrainFeatures feature;

        Location currentLocation = new Location(x, y);

        if (selectedUnit == null)
            return "There isn't any selected unit!";

        if (!UnitController.hasOwnerShip(selectedUnit, gameController))
            return "This unit does not belong to you!";

        if (selectedUnit.getTypeOfUnit() != TypeOfUnit.WORKER)
            return "The selected unit is " + selectedUnit.getTypeOfUnit().getName()
                    + ". It should be Worker for this action!";

        if ((currentTerrain = NonCombatUnitController.isJungleOrForestHere(currentLocation)) == null)
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

        if (selectedUnit == null)
            return "There isn't any selected unit!";

        if (!UnitController.hasOwnerShip(selectedUnit, gameController))
            return "This unit does not belong to you!";

        if (selectedUnit.getTypeOfUnit() != TypeOfUnit.WORKER)
            return "The selected unit is " + selectedUnit.getTypeOfUnit().getName()
                    + ". It should be Worker for this action!";

        // TODO remove route
        return "";
    }

    private String repair(Location location) {
        return "";
    }
}
