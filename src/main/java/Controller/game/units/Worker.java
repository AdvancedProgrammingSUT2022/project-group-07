package Controller.game.units;

import Controller.game.*;
import Model.*;
import Enum.TypeOfUnit;
import Enum.TerrainFeatures;
import Enum.TypeOfTechnology;
import Enum.TypeOfTerrain;
import Enum.Resources;
import Enum.TypeOfImprovement;

import java.util.regex.Matcher;

public class Worker {
    // TODO handle statics!
    // TODO x, y
    // TODO the way to use needed technologies
    public static String buildImprovement(Matcher matcher, GameController gameController) {
        String improvement = matcher.group("improvement");
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        Location location = new Location(x, y);

        if (improvement.equals("farm"))
            return checkToBuildFarm(gameController, location);
        if (improvement.equals("mine"))
            return checkToBuildMine(gameController, location);

        return "";
    }

    private static String checkToBuildMine(GameController gameController, Location location) {
        Civilization civilization = gameController.getCurrentCivilization();
        String error = findError(gameController);
        Terrain terrain;
        int turn;

        if (error != null)
            return error;
        if ((terrain = TerrainController.getTerrainByLocation(location)) == null)
            return "Position ( " + location.getX() + " , " + location.getY() + " ) is not valid!";
        if (!hasRequiredTech(civilization, TypeOfImprovement.MINE.getTypeOfTechnology()))
            return "Your civilization doesn't have " + TypeOfImprovement.MINE.getTypeOfTechnology().getName() + " tech to build farm!";
        if (hasAnyResource(terrain) == null
                && terrain.getTypeOfTerrain() != TypeOfTerrain.HILL)
            return "This terrain, isn't hill or it doesn't have any resources, so you can not create mine here!";

        turn = Integer.parseInt(setTurn(terrain, civilization));
        SelectController.selectedUnit.addImprovementsAboutToBeCreated(new Improvement(TypeOfImprovement.MINE, turn, terrain));
        return "Mine will be created in next " + turn + " turns!";
    }

    public static String buildMine(Improvement mine, Unit unit, TerrainFeatures featureToBeRemoved) {
        removeFeature(mine, featureToBeRemoved);
        mine.getTerrain().setImprovement(mine);
        unit.getImprovementsAboutToBeCreated().remove(mine);
        return "Mine created successfully in location ( "
                + mine.getTerrain().getLocation().getX() + " , "
                + mine.getTerrain().getLocation().getY() + " ) !";
    }

    private static String checkToBuildFarm(GameController gameController, Location location) {
        Civilization civilization = gameController.getCurrentCivilization();
        String error = findError(gameController);
        Terrain terrain;
        String forbidden;
        int turn;

        if (error != null)
            return error;
        if ((terrain = TerrainController.getTerrainByLocation(location)) == null)
            return "Position ( " + location.getX() + " , " + location.getY() + " ) is not valid!";
        if (!hasRequiredTech(civilization, TypeOfTechnology.AGRICULTURE))
            return "Your civilization doesn't have agriculture tech to build farm!";
        if (hasAnyResource(terrain) != null)
            return "This terrain has a resource, so you can't build farm there!";
        if ((forbidden = hasForbiddenFeatures(terrain)) != null)
            return "You can't build farm in this location because there is " + forbidden + " here!";

        turn = Integer.parseInt(setTurn(terrain, civilization));
        SelectController.selectedUnit.addImprovementsAboutToBeCreated(new Improvement(TypeOfImprovement.FARM, turn, terrain));
        return "Farm will be created in next " + turn + " turns!";
    }

    public static String buildFarm(Improvement farm, Unit unit, TerrainFeatures featureToBeRemoved) {
        removeFeature(farm, featureToBeRemoved);
        farm.getTerrain().setImprovement(farm);
        unit.getImprovementsAboutToBeCreated().remove(farm);
        return "Farm created successfully in location ( "
                + farm.getTerrain().getLocation().getX() + " , "
                + farm.getTerrain().getLocation().getY() + " ) !";
    }

    private static void removeFeature(Improvement improvement, TerrainFeatures featureToBeRemoved) {
        if (featureToBeRemoved == TerrainFeatures.FOREST
                || featureToBeRemoved == TerrainFeatures.JUNGLE
                || featureToBeRemoved == TerrainFeatures.MARSH)
            improvement.getTerrain().setTerrainFeatures(null);
    }

    private static String setTurn(Terrain terrain, Civilization civilization) {
        if (terrain.getTerrainFeatures() == TerrainFeatures.FOREST) {
            if (hasRequiredTech(civilization, TypeOfTechnology.MINING))
                return "10";
            else
                return "You can't build farm here because you don't have mining tech!";
        } else if (terrain.getTerrainFeatures() == TerrainFeatures.JUNGLE) {
            if (hasRequiredTech(civilization, TypeOfTechnology.BRONZE_WORKING))
                return "13";
            else
                return "You can't build farm here because you don't have bronze-working tech!";
        }
        else if (terrain.getTerrainFeatures() == TerrainFeatures.MARSH) {
            if (hasRequiredTech(civilization, TypeOfTechnology.MASONRY))
                return "12";
            else
                return "You can't build farm here because you don't have masonry tech!";
        }
        else
            return "6";
    }

    private static Resources hasAnyResource(Terrain destination) {
        return destination.getResources();
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
            return "Your civilization doesn't have wheel tech to build road!";
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
            return "Your civilization doesn't have steam-power tech to build railroad!";
        // TODO turns needed to build railroad !!
        Technology railRoad = new Technology(TypeOfTechnology.RAILROAD);
        railRoad.setRemainingTurns(TypeOfTechnology.RAILROAD.getTurnsNeeded());
        railRoad.setLocation(selectedLocation);
        SelectController.selectedUnit.addRailroadsAboutToBeBuilt(railRoad);
        return "Road will be created in next " + TypeOfTechnology.RAILROAD.getTurnsNeeded() + " turns!";
    }

    private static boolean hasEnoughGoldToBuy(GameController gameController, TypeOfTechnology technology) {
        return (gameController.getCurrentCivilization().getGold() >= technology.getCost());
    }

    public static String buildRoad(Road road, Civilization civilization) {
        // TODO any money?!
        Location location = road.getLocation();
        SelectController.selectedUnit.getRoadsAboutToBeBuilt().remove(road);
        Terrain terrain = TerrainController.getTerrainByLocation(location);
        assert terrain != null;
        terrain.setHasRoad(true);
        civilization.setNumberOfRailroadsAndRoads(civilization.getNumberOfRailroadsAndRoads() - 1);
        return "Road created successfully in location ( "
                + location.getX() + " , " + location.getY() + " ) !";
    }

    public static String buildRailRoad(Technology railRoad, Civilization civilization) {
        Location location = railRoad.getLocation();
        SelectController.selectedUnit.getRailroadsAboutToBeBuilt().remove(railRoad);
        Terrain terrain = TerrainController.getTerrainByLocation(location);
        assert terrain != null;
        civilization.addTechonolgy(railRoad);
        terrain.setHasRailRoad(true);
        civilization.setNumberOfRailroadsAndRoads(civilization.getNumberOfRailroadsAndRoads() - 1);
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

    public static boolean hasRequiredTech(Civilization civilization, TypeOfTechnology typeOfTech) {
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
