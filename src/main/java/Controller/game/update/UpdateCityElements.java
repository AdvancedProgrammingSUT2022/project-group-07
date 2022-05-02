package Controller.game.update;

import Controller.game.CityController;
import Controller.game.units.Worker;
import Model.*;
import Enum.TypeOfUnit;
import Enum.TypeOfImprovement;
import Enum.TerrainFeatures;
import Enum.TypeOfTechnology;

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
                    road.setTurnsNeeded(road.getTurnsNeeded() - 1);
                    if (road.getTurnsNeeded() == 0)
                        Worker.buildRoad(road, currentCivilization);
                }
            }
        }
    }

    public static void updateRailRoadsAboutToBeCreated(Civilization currentCivilization) {
        Location location;
        for (Unit unit : currentCivilization.getUnits()) {
            if (unit.getTypeOfUnit() == TypeOfUnit.WORKER) {
                for (Technology railroad : unit.getRailroadsAboutToBeBuilt()){
                    railroad.setRemainingTurns(railroad.getRemainingTurns() - 1);
                    if (railroad.getRemainingTurns() == 0)
                        Worker.buildRailRoad(railroad, currentCivilization);
                }
            }
        }
    }

    // just for selected civilization!
    public static void maintenance(Civilization civilization) {
        civilization.setGold(civilization.getGold() - civilization.getNumberOfRailroadsAndRoads());
    }

    public static void updateImprovementsAboutToBeCreated(Civilization currentCivilization) {
        for (Unit unit : currentCivilization.getUnits()) {
            if (unit.getTypeOfUnit() == TypeOfUnit.WORKER) {
                for (Improvement improvement : unit.getImprovementsAboutToBeCreated()) {
                    if (improvement.getTypeOfImprovement() == TypeOfImprovement.FARM)
                        updateFarm(improvement, unit);
                    if (improvement.getTypeOfImprovement() == TypeOfImprovement.MINE)
                        updateMine(improvement, unit);
                }
            }
        }
    }

    private static void updateMine(Improvement mine, Unit unit) {
        TerrainFeatures feature = mine.getTerrain().getTerrainFeatures();
        mine.setTurn(mine.getTurn() - 1);
        if (mine.getTurn() == 0)
            Worker.buildMine(mine, unit, feature);
    }

    private static void updateFarm(Improvement farm, Unit unit) {
        TerrainFeatures feature = farm.getTerrain().getTerrainFeatures();
        farm.setTurn(farm.getTurn() - 1);
        if (farm.getTurn() == 0)
            Worker.buildFarm(farm, unit, feature);
    }
    // TODO maybe somewhere else!
}
