package game.Controller.game.City;

import game.Controller.game.GameController;
import game.Controller.game.TileController;
import game.Controller.game.UnitController;
import game.Model.*;
import game.Enum.TypeOfUnit;
import game.Enum.TypeOfTechnology;
import game.Enum.Resources;
import game.Enum.UnitStatus;
import game.View.components.Tile;
import game.View.controller.CityPanelController;
import game.View.controller.UnitMenuController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;

import static game.Controller.game.SelectController.selectedCity;

public class CreateUnit {

    public static boolean createUnit(String name) {
        GameController gameController = GameController.getInstance();
        if (selectedCity == null) {
            UnitMenuController.showError("Select a city first!");
            return false;
        }

        Civilization currentCivilization = gameController.getCurrentCivilization();
        Terrain cityCenter = selectedCity.getTerrains().get(0);

        for (TypeOfUnit typeOfUnit : TypeOfUnit.values()) {
            if (typeOfUnit.getName().equals(name)) {
                if (selectedCity.getProduction() >= typeOfUnit.getCost()) {
                    TypeOfTechnology requiredTech = typeOfUnit.getTechnologyRequired();

                    if (requiredTech == null) {
                        checkRequiredResourceWhenTechIsNull(currentCivilization, typeOfUnit, cityCenter, gameController);
                        return false;
                    }
                    checkTechAndResource(currentCivilization, typeOfUnit, cityCenter, gameController);
                }
                else {
                    selectedCity.addWantedUnit(typeOfUnit);
                    UnitMenuController.showConfirm("Unit will be created in next turns!");
                }
                return true;
            }
        }
        return true;
    }

    private static boolean cityHasRequiredResource(Resources requiredResource) {
        ArrayList<Resources> ownedResources = new ArrayList<>();

        for (Terrain terrain : selectedCity.getTerrains()) {
            ownedResources.add(terrain.getResources());
        }
        return ownedResources.contains(requiredResource);
    }

    private static boolean checkTechAndResource(Civilization currentCivilization, TypeOfUnit typeOfUnit, Terrain cityCenter,
                                               GameController gameController) {
        ArrayList<Technology> ownedTechs = currentCivilization.getGainedTechnologies();
        TypeOfTechnology requiredTech = typeOfUnit.getTechnologyRequired();
        String check;

        for (Technology ownedTech : ownedTechs) {
            if (ownedTech.getTypeOfTechnology() == requiredTech) {
                Resources requiredResource = typeOfUnit.getResources();
                if ((check = hasRequiredResource(requiredResource, typeOfUnit)) != null) {
                    UnitMenuController.showConfirm(check);
                    return true;
                }
                else {
                    UnitMenuController.showError("Your city doesn't have the required resource to create this unit!");
                    return false;
                }
            }
        }
        UnitMenuController.showError("Your civilization doesn't have the required tech to create this unit!");
        return false;
    }

    private static void checkRequiredResourceWhenTechIsNull(Civilization currentCivilization, TypeOfUnit typeOfUnit, Terrain cityCenter,
                                                              GameController gameController) {
        Resources requiredResource = typeOfUnit.getResources();
        String check = hasRequiredResource(requiredResource, typeOfUnit);
        if (check != null)
            UnitMenuController.showConfirm(check);
        else
            UnitMenuController.showError("Your city doesn't have the required resource to create this unit!");
    }

    private static String hasRequiredResource(Resources requiredResource, TypeOfUnit typeOfUnit) {
        if (requiredResource == null
                || cityHasRequiredResource(requiredResource)) {
            selectedCity.addWantedUnit(typeOfUnit);
            return "Unit will be created in next turns!";
        }
        return null;
    }

    public static String buyUnitWithGold(GameController gameController) {
        Civilization currentCivilization = gameController.getCurrentCivilization();
//        TypeOfUnit unit = selectedCity.getWantedUnits().get(0);
        TypeOfUnit unit = TypeOfUnit.WORKER;
        Location location = selectedCity.getTerrains().get(0).getLocation();
        if (currentCivilization.getGold() >= unit.getCost()) {
            if (UnitController.anotherUnitIsInCenter(gameController, selectedCity)) {
                CityPanelController.showError(unit + " wants to be created. Please move the unit which is in city center first!");
                return unit + " wants to be created. Please move the unit which is in city center first!";
            }
            return createUnitWithGold(unit, location, currentCivilization);
        }
        return "Your city doesn't have enough gold to buy this unit!";
    }

    private static String createUnitWithGold(TypeOfUnit unit, Location location, Civilization currentCivilization) {
        Unit newUnit = new Unit(unit, UnitStatus.ACTIVE, location, unit.getHp(), currentCivilization, 0);
        currentCivilization.addUnit(newUnit);
        currentCivilization.setGold(currentCivilization.getGold() - unit.getCost());
        selectedCity.getWantedUnits().remove(unit);

        Tile tile = TileController.getTileByTerrain(selectedCity.getTerrains().get(0));
        tile.updateUnitBackground();

        return unit + " has been created successfully in location ( "
                + location.getX() + " , " + location.getY() + " ) !";
    }

    public static boolean changeUnitConstruction(String input) {
        String[] string = input.split(" ");
        int first = Integer.parseInt(string[0]) - 1;
        int second = Integer.parseInt(string[1]) - 1;
        if (selectedCity == null) {
            CityPanelController.showError("select a city first!");
            return false;
        }

        ArrayList<TypeOfUnit> wantedUnits = selectedCity.getWantedUnits();

        if (numberIsNotValid(first)) {
            CityPanelController.showError("First number is not valid!");
            return false;
        }
        if (numberIsNotValid(second)) {
            CityPanelController.showError("Second number is not valid!");
            return false;
        }

        Collections.swap(wantedUnits, first, second);
        CityPanelController.showConfirm("Unit construction changed successfully!");
        return true;
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
