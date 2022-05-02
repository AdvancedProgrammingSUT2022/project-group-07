package Controller.game.City;

import Controller.game.CityController;
import Controller.game.GameController;
import Model.*;
import Enum.TypeOfUnit;
import Enum.TypeOfTechnology;
import Enum.Resources;

import java.util.ArrayList;
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
                        return checkRequiredResourceWhenTechIsNull(currentCivilization, typeOfUnit, cityCenter);

                    return checkTechAndResource(currentCivilization, typeOfUnit, cityCenter);
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
            ownedResources.addAll(terrain.getResources());
        }
        return ownedResources.contains(requiredResource);
    }

    private static String checkTechAndResource(Civilization currentCivilization, TypeOfUnit typeOfUnit, Terrain cityCenter) {
        ArrayList<Technology> ownedTechs = currentCivilization.getGainedTechnologies();
        TypeOfTechnology requiredTech = typeOfUnit.getTechnologyRequired();

        for (Technology ownedTech : ownedTechs) {
            if (ownedTech.getTypeOfTechnology() == requiredTech) {
                Resources requiredResource = typeOfUnit.getResources();

                if (requiredResource == null)
                    return CityController.createUnit(currentCivilization, typeOfUnit, cityCenter.getLocation(), selectedCity);

                if (cityHasRequiredResource(requiredResource))
                    CityController.createUnit(currentCivilization, typeOfUnit, cityCenter.getLocation(), selectedCity);

                return "Your city doesn't have the required resource to create this unit!";
            }
        }
        return "Your civilization doesn't have the required tech to create this unit!";
    }

    private static String checkRequiredResourceWhenTechIsNull(Civilization currentCivilization, TypeOfUnit typeOfUnit, Terrain cityCenter) {
        Resources requiredResource = typeOfUnit.getResources();

        if (requiredResource == null)
            return CityController.createUnit(currentCivilization, typeOfUnit, cityCenter.getLocation(), selectedCity);

        if (cityHasRequiredResource(requiredResource))
            return CityController.createUnit(currentCivilization, typeOfUnit, cityCenter.getLocation(), selectedCity);

        return "Your city doesn't have the required resource to create this unit!";
    }

    public static String buyUnitWithGold(GameController gameController) {
        Civilization currentCivilization = gameController.getCurrentCivilization();
        TypeOfUnit unit = selectedCity.getWantedUnits().get(0);
        Location location = selectedCity.getTerrains().get(0).getLocation();
        if (selectedCity.getGold() >= unit.getCost()) {
            selectedCity.setGold(-1 * unit.getCost());
            // TODO do i need this?
            currentCivilization.setGold(-1 * unit.getCost());
            return CityController.createUnit(currentCivilization, unit, location, selectedCity);
        }
        return "Your city doesn't have enough gold to buy this unit!";
    }
}
