package Controller.game.units;

import Controller.game.*;
import Model.*;
import Enum.TypeOfUnit;
import Enum.TerrainFeatures;
import Enum.TypeOfTechnology;
import Enum.TypeOfTerrain;
import Enum.Resources;
import Enum.TypeOfImprovement;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.regex.Matcher;

public class Worker {
    // TODO x, y
    // TODO the way to use needed technologies
    // TODO a method for the second if in check-methods
    public static String buildImprovement(Matcher matcher, GameController gameController) {
        String improvement = matcher.group("improvement");
        Unit worker = SelectController.selectedUnit;
        int x = worker.getLocation().getX();
        int y = worker.getLocation().getY();
        Location location = new Location(x, y);

        if (improvement.equals("farm"))
            return checkToBuildFarm(gameController, location);
        if (improvement.equals("mine"))
            return checkToBuildMine(gameController, location);
        else
            return checkOtherImprovementsToBuild(gameController, location, improvement);
    }

    private static String checkOtherImprovementsToBuild(GameController gameController, Location location, String improvement) {
        Civilization civilization = gameController.getCurrentCivilization();
        String error = findError(gameController);
        Terrain terrain;

        if (error != null)
            return error;
        if ((terrain = TerrainController.getTerrainByLocation(location)) == null)
            return "Position ( " + location.getX() + " , " + location.getY() + " ) is not valid!";
        for (TypeOfImprovement typeOfImprovement : TypeOfImprovement.values()) {
            if (typeOfImprovement.getName().equals(improvement)) {
                ArrayList<String> canBeFoundOn = new ArrayList<>(Arrays.asList(typeOfImprovement.getCanBeFoundOn()));
                if ((error = destinationHasValidTypeOfTerrainOrFeature(canBeFoundOn, terrain, improvement)) != null)
                    return error;
                else {
                    if (!ResearchController.isTechnologyAlreadyAchieved(typeOfImprovement.getTypeOfTechnology(), civilization))
                        return "Your civilization doesn't have " + typeOfImprovement.getTypeOfTechnology().getName()
                                + " tech to build " + improvement + " !";
                    SelectController.selectedUnit.addImprovementsAboutToBeCreated(new Improvement(typeOfImprovement, 1111, terrain));
                    civilization.addImprovementsAboutToBeCreated(new Improvement(typeOfImprovement, 1111, terrain));
                    SelectController.selectedUnit.setTimesMovedThisTurn(SelectController.selectedUnit.getTimesMovedThisTurn() + 1);
                    return improvement + " will be created in next " + typeOfImprovement.getTurnsNeeded() + " turns!";
                }
            }
        }
        return "Improvement name is not valid!";
    }

    private static String destinationHasValidTypeOfTerrainOrFeature(ArrayList<String> canBeFoundOn, Terrain terrain, String improvement) {
        String typeOfTerrain = terrain.getTypeOfTerrain().getName();
        String featureOfTerrain = terrain.getTerrainFeatures().getName();
        for (String place : canBeFoundOn) {
            if (place.equals(typeOfTerrain)
                    || place.equals(featureOfTerrain))
                return null;
        }
        return "You can't build " + improvement + " here because this terrain doesn't have the required type or feature!";
    }

    public static String buildImprovement(Improvement improvement, Unit unit) {
        improvement.getTerrain().setImprovement(improvement);
        unit.getImprovementsAboutToBeCreated().remove(improvement);
        return improvement.getTypeOfImprovement().getName() + " created successfully!";
    }

    private static String checkToBuildMine(GameController gameController, Location location) {
        Civilization civilization = gameController.getCurrentCivilization();
        ArrayList<String> canBeFoundOn = new ArrayList<>(Arrays.asList(TypeOfImprovement.MINE.getCanBeFoundOn()));
        String error = findError(gameController);
        Terrain terrain;
        int turn;

        if (error != null)
            return error;
        if ((terrain = TerrainController.getTerrainByLocation(location)) == null)
            return "Position ( " + location.getX() + " , " + location.getY() + " ) is not valid!";
        if (!ResearchController.isTechnologyAlreadyAchieved(TypeOfImprovement.MINE.getTypeOfTechnology(), civilization))
            return "Your civilization doesn't have " + TypeOfImprovement.MINE.getTypeOfTechnology().getName() + " tech to build mine!";
        if (hasAnyResource(terrain) == null
                && terrain.getTypeOfTerrain() != TypeOfTerrain.HILL)
            return "This terrain, isn't hill or it doesn't have any resources, so you can not create mine here!";
        if ((error = destinationHasValidTypeOfTerrainOrFeature(canBeFoundOn , terrain, "mine")) != null)
            return error;

        turn = Integer.parseInt(setTurn(terrain, civilization));
        SelectController.selectedUnit.addImprovementsAboutToBeCreated(new Improvement(TypeOfImprovement.MINE, turn, terrain));
        civilization.addImprovementsAboutToBeCreated(new Improvement(TypeOfImprovement.MINE, turn, terrain));
        SelectController.selectedUnit.setTimesMovedThisTurn(SelectController.selectedUnit.getTimesMovedThisTurn() + 1);
        return "Mine will be created in next " + turn + " turns!";
    }

    public static String buildMine(Improvement mine, Unit unit, TerrainFeatures featureToBeRemoved) {
        removeFeature(mine, featureToBeRemoved);
        mine.getTerrain().setImprovement(mine);
        unit.getImprovementsAboutToBeCreated().remove(mine);
        unit.getCivilization().getImprovementsAboutToBeCreated().remove(mine);
        return "Mine created successfully in location ( "
                + mine.getTerrain().getLocation().getX() + " , "
                + mine.getTerrain().getLocation().getY() + " ) !";
    }

    private static String checkToBuildFarm(GameController gameController, Location location) {
        Civilization civilization = gameController.getCurrentCivilization();
        ArrayList<String> canBeFoundOn = new ArrayList<>(Arrays.asList(TypeOfImprovement.FARM.getCanBeFoundOn()));
        String error = findError(gameController);
        Terrain terrain;
        String forbidden;
        int turn;

        if (error != null)
            return error;
        if ((terrain = TerrainController.getTerrainByLocation(location)) == null)
            return "Position ( " + location.getX() + " , " + location.getY() + " ) is not valid!";
        if (!ResearchController.isTechnologyAlreadyAchieved(TypeOfTechnology.AGRICULTURE, civilization))
            return "Your civilization doesn't have agriculture tech to build farm!";
        if (hasAnyResource(terrain) != null)
            return "This terrain has a resource, so you can't build farm there!";
        if ((forbidden = hasForbiddenFeatures(terrain)) != null)
            return "You can't build farm in this location because there is " + forbidden + " here!";
        if ((error = destinationHasValidTypeOfTerrainOrFeature(canBeFoundOn, terrain, "farm")) != null)
            return error;

        String checkTurns = setTurn(terrain, civilization);
        if (checkTurns.equals("6")
                || checkTurns.equals("10")
                || checkTurns.equals("12")
                || checkTurns.equals("13")) {
            turn = Integer.parseInt(checkTurns);
            SelectController.selectedUnit.addImprovementsAboutToBeCreated(new Improvement(TypeOfImprovement.FARM, turn, terrain));
            civilization.addImprovementsAboutToBeCreated(new Improvement(TypeOfImprovement.FARM, turn, terrain));
            SelectController.selectedUnit.setTimesMovedThisTurn(SelectController.selectedUnit.getTimesMovedThisTurn() + 1);
            return "Farm will be created in next " + turn + " turns!";
        }
        return checkTurns;
    }

    public static String buildFarm(Improvement farm, Unit unit, TerrainFeatures featureToBeRemoved) {
        removeFeature(farm, featureToBeRemoved);
        farm.getTerrain().setImprovement(farm);
        unit.getImprovementsAboutToBeCreated().remove(farm);
        unit.getCivilization().getImprovementsAboutToBeCreated().remove(farm);
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
            if (!ResearchController.isTechnologyAlreadyAchieved(TypeOfTechnology.MINING, civilization))
                return "10";
            else
                return "You can't build farm here because you don't have mining tech!";
        } else if (terrain.getTerrainFeatures() == TerrainFeatures.JUNGLE) {
            if (!ResearchController.isTechnologyAlreadyAchieved(TypeOfTechnology.BRONZE_WORKING, civilization))
                return "13";
            else
                return "You can't build farm here because you don't have bronze-working tech!";
        }
        else if (terrain.getTerrainFeatures() == TerrainFeatures.MARSH) {
            if (!ResearchController.isTechnologyAlreadyAchieved(TypeOfTechnology.MASONRY, civilization))
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

    public static String checkToBuildRoute(Matcher matcher, GameController gameController) {
        Civilization civilization = gameController.getCurrentCivilization();
        String name = matcher.group("name");
        Unit worker = SelectController.selectedUnit;
        int x = worker.getLocation().getX();
        int y = worker.getLocation().getY();
        Location currentLocation = new Location(x, y);
        String error = findError(gameController);
        Terrain terrain;
        String forbidden;

        if (error != null)
            return error;
        if ((terrain = TerrainController.getTerrainByLocation(currentLocation)) == null)
            return "Position ( " + x + " , " + y + " ) is not valid!";
        if (!ResearchController.isTechnologyAlreadyAchieved(TypeOfTechnology.THE_WHEEL, gameController.getCurrentCivilization()))
            return "Your civilization doesn't have wheel tech to build road!";
        if ((forbidden = hasForbiddenFeatures(terrain)) != null)
            return "You can't build road in this location because there is " + forbidden + " here!";

        if (name.equals("road")) {
            SelectController.selectedUnit.addRoadsAboutToBeBuilt(new Route("road",3, currentLocation));
            civilization.addRoutsAboutToBeBuilt(new Route("road",3, currentLocation));
            SelectController.selectedUnit.setTimesMovedThisTurn(SelectController.selectedUnit.getTimesMovedThisTurn() + 1);
            return "Road will be created in next 3 turns!";
        }
        SelectController.selectedUnit.addRoadsAboutToBeBuilt(new Route("railroad",3, currentLocation));
        civilization.addRoutsAboutToBeBuilt(new Route("road",3, currentLocation));
        SelectController.selectedUnit.setTimesMovedThisTurn(SelectController.selectedUnit.getTimesMovedThisTurn() + 1);
        return "Railroad will be created in next 3 turns!";
    }

    public static String buildRoute(Route rout, Civilization civilization) {
        String typeOfRoad = rout.getName();
        Location location = rout.getLocation();
        SelectController.selectedUnit.getRoadsAboutToBeBuilt().remove(rout);
        Terrain terrain = TerrainController.getTerrainByLocation(location);
        assert terrain != null;
<<<<<<< HEAD
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
=======
        if (typeOfRoad.equals("road")) {
            terrain.setHasRoad(true);
            civilization.setNumberOfRailroadsAndRoads(civilization.getNumberOfRailroadsAndRoads() - 1);
            return "Road created successfully in location ( "
                    + location.getX() + " , " + location.getY() + " ) !";
        }
>>>>>>> main
        terrain.setHasRailRoad(true);
        civilization.setNumberOfRailroadsAndRoads(civilization.getNumberOfRailroadsAndRoads() - 1);
        return "Road created successfully in location ( "
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

    public static String removeJungleOrForestOrMarsh(Matcher matcher, GameController gameController) {
        String featureName = matcher.group("feature");
        Unit worker = SelectController.selectedUnit;
        int x = worker.getLocation().getX();
        int y = worker.getLocation().getY();
        String error = findError(gameController);
        Terrain currentTerrain;

        if (error != null)
            return error;

        Location selectedLocation = new Location(x, y);

        if (!SelectController.positionIsValid(selectedLocation))
            return "Position ( " + x + " , " + y + " ) is not valid!";
        switch (featureName) {
            case "forest":
                if ((currentTerrain = NonCombatUnitController.isForestHere(selectedLocation)) == null)
                    return "There isn't any forest in this location!";
                break;
            case "jungle":
                if ((currentTerrain = NonCombatUnitController.isJungleHere(selectedLocation)) == null)
                    return "There isn't any jungle in this location!";
                break;
            case "marsh":
                if ((currentTerrain = NonCombatUnitController.isMarshHere(selectedLocation)) == null)
                    return "There isn't any marsh in this location!";
                break;
            default:
                return "Name of feature is not valid!";
        }

        TerrainFeatures feature = currentTerrain.getTerrainFeatures();
        currentTerrain.setTerrainFeatures(null);
        return feature + " removed successfully!";
    }

    public static String removeRoute(GameController gameController) {
        Unit worker = SelectController.selectedUnit;
        int x = worker.getLocation().getX();
        int y = worker.getLocation().getY();
        Location selectedLocation = new Location(x, y);
        Civilization civilization = gameController.getCurrentCivilization();
        String error = findError(gameController);
        Terrain terrain;

        if (error != null)
            return error;
        if (!SelectController.positionIsValid(selectedLocation))
            return "Position ( " + x + " , " + y + " ) is not valid!";
        if ((terrain = NonCombatUnitController.isRouteHere(selectedLocation)) == null)
            return "There isn't any rout here!";

        if (terrain.hasRoad()) {
            return removeRout(terrain, civilization);
        }
        return removeRailRoad(terrain, civilization);
    }

    private static String removeRout(Terrain terrain, Civilization civilization) {
        terrain.setHasRoad(false);
        civilization.setNumberOfRailroadsAndRoads(civilization.getNumberOfRailroadsAndRoads() - 1);
        return "Road removed successfully!";
    }

    private static String removeRailRoad(Terrain terrain, Civilization civilization) {
        terrain.setHasRailRoad(false);
        civilization.setNumberOfRailroadsAndRoads(civilization.getNumberOfRailroadsAndRoads() - 1);
        return "Railroad removed successfully!";
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
        if (selectedUnit.getTimesMovedThisTurn() >= 2)
            return "Unit is out of move!";
        return null;
    }

    public static String repair(Matcher matcher, GameController gameController) {
        // TODO repair!
        return "";
    }

}
