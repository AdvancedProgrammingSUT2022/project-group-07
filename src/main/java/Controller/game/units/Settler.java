package Controller.game.units;

import Controller.game.GameController;
import Controller.game.LogAndNotification.NotificationController;
import Controller.game.SelectController;
import Controller.game.TerrainController;
import Controller.game.UnitController;
import Model.*;
import Enum.TypeOfUnit;
import Enum.TypeOfTerrain;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class Settler {

    public static String foundCity(Matcher matcher, GameController gameController) {
        Unit settler = SelectController.selectedUnit;
        if (settler == null) return "There isn't any selected unit!";
        if (!UnitController.hasOwnerShip(settler, gameController)) return "This unit does not belong to you!";
        if (settler.getTypeOfUnit() != TypeOfUnit.SETTLER) return "This unit is not a settler!";
        String foundCityResult = CanFoundCityHere(settler, gameController);
        if (foundCityResult.startsWith("can't found city here because")) return foundCityResult;
        return foundA_NewCity(settler, gameController);
    }

    private static String CanFoundCityHere(Unit settler, GameController gameController) {
        Location cityLocation = settler.getLocation();
        Terrain currentTerrain = TerrainController.getTerrainByLocation(cityLocation);
        ArrayList<Civilization> civilizations = gameController.getCivilizations();
        for (Civilization civilization : civilizations) {
            for (City city : civilization.getCities()) {
                for (Terrain enemyTerrain : city.getTerrains()) {
                    if (currentTerrain.getLocation().getX() == enemyTerrain.getLocation().getX()
                            && currentTerrain.getLocation().getY() == enemyTerrain.getLocation().getY())
                        return "can't found city here because this tile belongs to " + civilization.getName();
                }
            }
        }
        return "founding city here is possible!";
    }

    private static String foundA_NewCity(Unit settler, GameController gameController) {
        Civilization civilization = gameController.getCurrentCivilization();
        int numberOfCities = civilization.getCities().size();
        City city = new City("city" + (numberOfCities + 1) + " Of" + civilization.getName(), civilization);
        if (TerrainController.getTerrainByLocation(settler.getLocation()).getTypeOfTerrain() == TypeOfTerrain.HILL) {
            city.setHp(city.getHp() + 5);
        }
        city.getTerrains().add(TerrainController.getTerrainByLocation(settler.getLocation()));
        city.addCitizen(new Citizen(1));
        civilization.addCity(city);
        NotificationController.logCityFounded(city , civilization);
        if (numberOfCities == 0) civilization.setCapital(city);
        return "found new city!";
    }
}
