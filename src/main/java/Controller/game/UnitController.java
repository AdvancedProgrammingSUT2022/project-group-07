package Controller.game;

import Model.*;
import Enum.UnitStatus;
import Enum.TypeOfUnit;
import Enum.TypeOfTechnology;
import Enum.Resources;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class UnitController {
    private static City selectedCity = SelectController.selectedCity;

    // TODO the first two errors are the same in all methods --> handle
    // TODO handle this --> age unit khab bashe, nemitoone darkhaste jadid dashte bashe ta vaghti behesh dastoor jadid bedan
    public static String moveUnit(Matcher matcher, GameController gameController) {
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        Unit selectedUnit = SelectController.selectedUnit;
        Location origin = selectedUnit.getLocation();
        Location destination = new Location(x, y);
        String placeName;

        if (SelectController.selectedUnit == null)
            return "There isn't any selected unit!";

        if (!hasOwnerShip(selectedUnit, gameController))
            return "This unit does not belong to you!";

        if (!SelectController.positionIsValid(destination))
            return "Destination ( " + x + " , " + y + " ) is not valid!";

        if ((placeName = TheShortestPath.destinationIsValid(destination)) != null)
            return "Your destination is " + placeName + " so you can not go to it!";

        return TheShortestPath.checkNeededMpForMove(origin, destination);
    }

    public static boolean hasOwnerShip(Unit currentUnit, GameController gameController) {
        for (Unit unit : gameController.getCurrentCivilization().getUnits()) {
            if (currentUnit.getLocation().getX() == unit.getLocation().getX()
                    && currentUnit.getLocation().getY() == unit.getLocation().getY())
                return true;
        }
        return false;
    }

    public static String sleep(GameController gameController) {
        // show errors just when you select the unit
        if (SelectController.selectedUnit == null)
            return "There isn't any selected unit!";

        if (!hasOwnerShip(SelectController.selectedUnit, gameController))
            return "This unit does not belong to you!";

        SelectController.selectedUnit.setUnitStatus(UnitStatus.SLEEP);
        return "Selected unit has slept successfully!";
    }

    public static String fortifyUnit(GameController gameController) {
        Unit selectedUnit = SelectController.selectedUnit;

        if (selectedUnit == null)
            return "There isn't any selected unit!";

        if (!hasOwnerShip(selectedUnit, gameController))
            return "This unit does not belong to you!";

        if (selectedUnit.getTypeOfUnit() == TypeOfUnit.WORKER
                || selectedUnit.getTypeOfUnit() == TypeOfUnit.SETTLER)
            return "The selected unit is " + selectedUnit.getTypeOfUnit().getName()
                    + ". It should be a combatUnit for this action!";

        // TODO HEAL and main things to do!
        return "";
    }

    public void healUnit(Unit unit) {
        // TODO heal!
    }

    public static String cancelMission(GameController gameController) {
        if (SelectController.selectedUnit == null)
            return "There isn't any selected unit!";

        if (!hasOwnerShip(SelectController.selectedUnit, gameController))
            return "This unit does not belong to you!";
        // TODO handle unit's movements first!
        // TODO remove all movements!
        return "All of the missions of the selected unit have been canceled!";
    }

    public static String wake(GameController gameController) {
        if (SelectController.selectedUnit == null)
            return "There isn't any selected unit!";

        if (!hasOwnerShip(SelectController.selectedUnit, gameController))
            return "This unit does not belong to you!";

        SelectController.selectedUnit.setUnitStatus(UnitStatus.ACTIVE);
        return "Selected unit is awake!";
    }

    public static String deleteUnit(GameController gameController) {
        Unit selectedUnit = SelectController.selectedUnit;

        if (selectedUnit == null)
            return "There isn't any selected unit!";

        if (!hasOwnerShip(selectedUnit, gameController))
            return "This unit does not belong to you!";

        // find the selected unit in current civilization and remove it.
        for (Unit unit : gameController.getCurrentCivilization().getUnits()) {
            if (unit.getLocation().getX() == selectedUnit.getLocation().getX()
                    && unit.getLocation().getY() == selectedUnit.getLocation().getY()) {
                gameController.getCurrentCivilization().getUnits().remove(unit);
                break;
            }
        }
        return "Unit deleted successfully!";
    }

    // TODO what is this ?
    public String upgrade(Unit unit) {
        return "";
    }

    public static String checkRequiredTechsAndResourcesToCreateUnit(Matcher matcher, GameController gameController) {
        if (selectedCity == null)
            return "Select a city first!";

        String unitName = matcher.group("unit");
        Civilization currentCivilization = gameController.getCurrentCivilization();
        Terrain cityCenter = selectedCity.getTerrains().get(0);

        // TODO saf
        for (TypeOfUnit typeOfUnit : TypeOfUnit.values()) {
            if (typeOfUnit.getName().equals(unitName)) {
                if (selectedCity.getProduction() >= typeOfUnit.getCost()) {
                    TypeOfTechnology requiredTech = typeOfUnit.getTechnologyRequired();

                    if (requiredTech == null)
                        return checkRequiredResourceWhenTechIsNull(currentCivilization, typeOfUnit, cityCenter);

                    return checkTechAndResource(currentCivilization, typeOfUnit, cityCenter);
                }
                else
                    // TODO handle turns
                    return "Unit will be created in next turns!";
            }
        }
        return "Unit name is not valid!";
    }

    private static boolean cityHasRequiredResource(Resources requiredResource) {
        ArrayList<Resources> ownedResources = new ArrayList<>();

        for (Terrain terrain : selectedCity.getTerrains()) {
            ownedResources.addAll(terrain.getResources());
        }
        return ownedResources.contains(requiredResource);
    }

    private static String createUnit(Civilization currentCivilization, TypeOfUnit typeOfUnit, Location location) {
        Unit newUnit = new Unit(typeOfUnit, UnitStatus.ACTIVE, location, typeOfUnit.getHp(), currentCivilization, 0);
        currentCivilization.addUnit(newUnit);
        return "Unit has been added successfully!";
    }

    private static String checkTechAndResource(Civilization currentCivilization, TypeOfUnit typeOfUnit, Terrain cityCenter) {
        ArrayList<Technology> ownedTechs = currentCivilization.getGainedTechnologies();
        TypeOfTechnology requiredTech = typeOfUnit.getTechnologyRequired();

        for (Technology ownedTech : ownedTechs) {
            if (ownedTech.getTypeOfTechnology() == requiredTech) {
                Resources requiredResource = typeOfUnit.getResources();

                if (requiredResource == null)
                    return createUnit(currentCivilization, typeOfUnit, cityCenter.getLocation());

                if (cityHasRequiredResource(requiredResource))
                    createUnit(currentCivilization, typeOfUnit, cityCenter.getLocation());

                return "Your city doesn't have the required resource to create this unit!";
            }
        }
        return "Your civilization doesn't have the required tech to create this unit!";
    }

    private static String checkRequiredResourceWhenTechIsNull(Civilization currentCivilization, TypeOfUnit typeOfUnit, Terrain cityCenter) {
        Resources requiredResource = typeOfUnit.getResources();

        if (requiredResource == null)
            return createUnit(currentCivilization, typeOfUnit, cityCenter.getLocation());

        if (cityHasRequiredResource(requiredResource))
            return createUnit(currentCivilization, typeOfUnit, cityCenter.getLocation());

        return "Your city doesn't have the required resource to create this unit!";
    }
}
