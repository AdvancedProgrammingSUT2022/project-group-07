package Controller.game.units;

import Controller.game.*;
import Model.*;
import Enum.TypeOfUnit;
import Enum.TerrainFeatures;
import Enum.TypeOfTechnology;
import Enum.TypeOfTerrain;

import java.util.regex.Matcher;

public class Worker {

    // TODO handle statics!
    // TODO x, y
    public static String buildImprovement(Matcher matcher, GameController gameController) {
        return "";
    }

    public static String checkToBuildRoad(Matcher matcher, GameController gameController) {
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        Location currentLocation = new Location(x, y);
        String error = findError(gameController);
        Terrain terrain;
        String forbidden;

        if (error != null)
            return error;
        if ((terrain = TerrainController.getTerrainByLocation(currentLocation)) == null)
            return "Position ( " + x + " , " + y + " ) is not valid!";
        if (!hasRequiredTech(gameController.getCurrentCivilization(), TypeOfTechnology.THE_WHEEL))
            return "Your civilization doesn't have wheel to build road!";
        if ((forbidden = hasForbiddenFeatures(terrain)) != null)
            return "You can't build road in this location because there is " + forbidden + " here!";
        SelectController.selectedUnit.addRoadsAboutToBeBuilt(new Road(3, currentLocation));
        return "Road will be created in next 3 turns!";
    }

    public static String checkToBuildRailRoad(Matcher matcher, GameController gameController) {
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        Location selectedLocation = new Location(x, y);
        String error = findError(gameController);
        String forbidden;
        Terrain terrain;

        if (error != null)
            return error;
        if ((terrain = TerrainController.getTerrainByLocation(selectedLocation)) == null)
            return "Position ( " + x + " , " + y + " ) is not valid!";
        if (!hasEnoughGoldToBuy(gameController, TypeOfTechnology.RAILROAD))
            return "You don't have enough gold to build railroad!";
        if ((forbidden = hasForbiddenFeatures(terrain)) != null)
            return "You can't build railroad in this location because there is " + forbidden + " here!";
        if (!hasRequiredTech(gameController.getCurrentCivilization(), TypeOfTechnology.STEAM_POWER))
            return "Your civilization doesn't have steam-power to build railroad!";
        // TODO turns needed to build railroad!!
        Technology railRoad = new Technology(TypeOfTechnology.RAILROAD);
        railRoad.setLocation(selectedLocation);
        SelectController.selectedUnit.addRailroadsAboutToBeBuilt(railRoad);
        return "Road will be created in next " + TypeOfTechnology.RAILROAD.getTurnsNeeded() + " turns!";
    }

    private static boolean hasEnoughGoldToBuy(GameController gameController, TypeOfTechnology technology) {
        return (gameController.getCurrentCivilization().getGold() >= technology.getCost());
    }

    public static String buildRoad(Location location, Road road, Civilization civilization) {
        // TODO any money?!
        SelectController.selectedUnit.getRoadsAboutToBeBuilt().remove(road);
        Terrain terrain = TerrainController.getTerrainByLocation(location);
        assert terrain != null;
        terrain.setHasRoad(true);
        civilization.setNumberOfRailroadsAndRoads(1);
        return "Road created successfully in location ( "
                + location.getX() + " , " + location.getY() + " ) !";
    }

    public static String buildRailRoad(Location location, Technology railRoad, Civilization civilization) {
        SelectController.selectedUnit.getRailroadsAboutToBeBuilt().remove(railRoad);
        Terrain terrain = TerrainController.getTerrainByLocation(location);
        assert terrain != null;
        civilization.addTechnology(railRoad);
        terrain.setHasRailRoad(true);
        civilization.setNumberOfRailroadsAndRoads(1);
        // TODO money
        return "Railroad created successfully in location ( "
                + location.getX() + " , " + location.getY() + " ) !";
    }

    private static String hasForbiddenFeatures(Terrain currentTerrain) {
        if (currentTerrain.getTypeOfTerrain() == TypeOfTerrain.MOUNTAIN
                || currentTerrain.getTypeOfTerrain() == TypeOfTerrain.OCEAN)
            return currentTerrain.getTypeOfTerrain().getName();
        if (currentTerrain.getTerrainFeatures() == TerrainFeatures.ICE)
            return "ice";
        return null;
    }

    private static boolean hasRequiredTech(Civilization civilization, TypeOfTechnology typeOfTech) {
        for (Technology technology : civilization.getGainedTechnologies()) {
            if (technology.getTypeOfTechnology() == typeOfTech) {
                return true;
            }
        }
        return false;
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
