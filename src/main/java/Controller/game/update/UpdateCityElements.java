package Controller.game.update;

import Controller.game.CityController;
import Controller.game.units.Worker;
import Model.*;
import Enum.TypeOfUnit;

public class UpdateCityElements {
    public static void updateUnitsAboutToBeCreate(Civilization currentCivilization) {
        for (City city : currentCivilization.getCities()) {
            for (TypeOfUnit unit : city.getWantedUnits()) {
                unit.setTurnsNeededToCreate(unit.getCost() / city.getProduction());
                if (city.getProduction() >= unit.getCost()) {
                    Terrain cityCenter = city.getTerrains().get(0);
                    city.setProduction(city.getProduction() - unit.getCost());
                    CityController.createUnit(currentCivilization, unit, cityCenter.getLocation(), city);
                }
            }
        }
    }

    public static void updateRoadsAboutToBeCreated(Civilization currentCivilization) {
        Location location;
        for (Unit unit : currentCivilization.getUnits()) {
            if (unit.getTypeOfUnit() == TypeOfUnit.WORKER) {
                for (Road road : unit.getRoadsAboutToBeBuilt()) {
                    road.setTurnsNeeded(-1);
                    if (road.getTurnsNeeded() == 0) {
                        location = road.getLocation();
                        Worker.buildRoad(location, road, currentCivilization);
                    }
                }
            }
        }
    }

    public static void updateRailRoadsAboutToBeCreated(Civilization currentCivilization) {
        Location location;
        for (Unit unit : currentCivilization.getUnits()) {
            if (unit.getTypeOfUnit() == TypeOfUnit.WORKER) {
                for (Technology railroad : unit.getRailroadsAboutToBeBuilt()){
                    railroad.setRemainingTurns(-1);
                    if (railroad.getRemainingTurns() == 0) {
                        location = railroad.getLocation();
                        Worker.buildRailRoad(location, railroad, currentCivilization);
                    }
                }
            }
        }
    }

    // just for selected civilization!
    public static void maintenance(Civilization civilization) {
        civilization.setGold(civilization.getGold() - civilization.getNumberOfRailroadsAndRoads());
    }
}
