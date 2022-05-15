package Controller.game.City;

import Controller.game.CityController;
import Controller.game.GameController;
import Controller.game.UnitController;
import Model.*;
import Enum.TypeOfUnit;
import Enum.TypeOfTechnology;
import Enum.Resources;
import Enum.UnitStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;

import static Controller.game.SelectController.selectedCity;

public class CreateUnit {

    public static String checkRequiredTechsAndResourcesToCreateUnit(Matcher matcher, GameController gameController) {
        if (selectedCity == null)
            return "Select a city first!";

        String unitName = matcher.group("unit");
        Civilization currentCivilization = gameController.getCurrentCivilization();
        Terrain cityCenter = selectedCity.getTerrains().get(0);

        for (TypeOfUnit typeOfUnit : TypeOfUnit.values()) {
            if (typeOfUnit.getName().equals(unitName)) {
                if (selectedCity.getProduction() >= typeOfUnit.getCost()) {
                    TypeOfTechnology requiredTech = typeOfUnit.getTechnologyRequired();

                    if (requiredTech == null)
                        return checkRequiredResourceWhenTechIsNull(currentCivilization, typeOfUnit, cityCenter, gameController);

                    return checkTechAndResource(currentCivilization, typeOfUnit, cityCenter, gameController);
                }
                else {
                    selectedCity.addWantedUnit(typeOfUnit);
                    return "Unit will be created in next turns!";
                }
            }
        }
        return "Unit name is not valid!";
    }

    private static boolean cityHasRequiredResource(Resources requiredResource) {
        ArrayList<Resources> ownedResources = new ArrayList<>();

        for (Terrain terrain : selectedCity.getTerrains()) {
            ownedResources.add(terrain.getResources());
        }
        return ownedResources.contains(requiredResource);
    }

    private static String checkTechAndResource(Civilization currentCivilization, TypeOfUnit typeOfUnit, Terrain cityCenter,
                                               GameController gameController) {
        ArrayList<Technology> ownedTechs = currentCivilization.getGainedTechnologies();
        TypeOfTechnology requiredTech = typeOfUnit.getTechnologyRequired();
        String check;

        for (Technology ownedTech : ownedTechs) {
            if (ownedTech.getTypeOfTechnology() == requiredTech) {
                Resources requiredResource = typeOfUnit.getResources();
                if ((check = hasRequiredResource(requiredResource, typeOfUnit)) != null)
                    return check;
                return "Your city doesn't have the required resource to create this unit!";
            }
        }
        return "Your civilization doesn't have the required tech to create this unit!";
    }

    private static String checkRequiredResourceWhenTechIsNull(Civilization currentCivilization, TypeOfUnit typeOfUnit, Terrain cityCenter,
                                                              GameController gameController) {
        Resources requiredResource = typeOfUnit.getResources();
        String check = hasRequiredResource(requiredResource, typeOfUnit);
        if (check != null)
            return check;

        return "Your city doesn't have the required resource to create this unit!";
    }

    //
    private static String hasRequiredResource(Resources requiredResource, TypeOfUnit typeOfUnit) {
        if (requiredResource == null) {
            selectedCity.addWantedUnit(typeOfUnit);
            return "Unit will be created in next turns!";
        }

        if (cityHasRequiredResource(requiredResource)) {
            selectedCity.addWantedUnit(typeOfUnit);
            return "Unit will be created in next turns!";
        }
        return null;
    }

    public static String buyUnitWithGold(GameController gameController) {
        Civilization currentCivilization = gameController.getCurrentCivilization();
        TypeOfUnit unit = selectedCity.getWantedUnits().get(0);
        Location location = selectedCity.getTerrains().get(0).getLocation();
        if (currentCivilization.getGold() >= unit.getCost()) {
            if (UnitController.anotherUnitIsInCenter(gameController, selectedCity))
                return unit + " wants to be created. Please move the unit which is in city center first!";
            return createUnitWithGold(unit, location, currentCivilization);
        }
        return "Your city doesn't have enough gold to buy this unit!";
    }

    private static String createUnitWithGold(TypeOfUnit unit, Location location, Civilization currentCivilization) {
        Unit newUnit = new Unit(unit, UnitStatus.ACTIVE, location, unit.getHp(), currentCivilization, 0);
        currentCivilization.addUnit(newUnit);
        currentCivilization.setGold(currentCivilization.getGold() - unit.getCost());
        selectedCity.getWantedUnits().remove(unit);
        return unit + " has been created successfully in location ( "
                + location.getX() + " , " + location.getY() + " ) !";
    }

    public static String changeUnitConstruction(Matcher matcher) {
        int first = Integer.parseInt(matcher.group("first")) - 1;
        int second = Integer.parseInt(matcher.group("second")) - 1;
        ArrayList<TypeOfUnit> wantedUnits = selectedCity.getWantedUnits();

        if (numberIsNotValid(first))
            return "First number is not valid!";
        if (numberIsNotValid(second))
            return "Second number is not valid!";

        Collections.swap(wantedUnits, first, second);
        return "Unit construction changed successfully!";
    }

    public static String removeUnitConstruction(Matcher matcher) {
        int numberOfUnit = Integer.parseInt(matcher.group("number")) - 1;
        ArrayList<TypeOfUnit> wantedUnits = selectedCity.getWantedUnits();

        if (numberIsNotValid(numberOfUnit))
            return "Number is not valid!";

        wantedUnits.remove(numberOfUnit);
        return "Unit construction removed successfully!";
    }

    private static boolean numberIsNotValid(int number) {
        int sizeOfWantedUnits = selectedCity.getWantedUnits().size();
        return (number >= sizeOfWantedUnits
                || number < 0);
    }
}
