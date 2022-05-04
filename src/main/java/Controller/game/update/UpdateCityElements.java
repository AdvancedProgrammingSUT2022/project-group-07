package Controller.game.update;

import Controller.game.CityController;
import Controller.game.TerrainController;
import Controller.game.units.Worker;
import Model.*;
import Enum.TypeOfUnit;
import Enum.TypeOfImprovement;
import Enum.TerrainFeatures;

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

    public static void updateRoutsAboutToBeCreated(Civilization currentCivilization) {
//        Location location;
        for (Unit unit : currentCivilization.getUnits()) {
            if (unit.getTypeOfUnit() == TypeOfUnit.WORKER) {
                for (Route road : unit.getRoadsAboutToBeBuilt()) {
                    road.setTurnsNeeded(road.getTurnsNeeded() - 1);
                    if (road.getTurnsNeeded() == 0)
                        Worker.buildRoute(road, currentCivilization);
                }
            }
        }
    }

    // just for selected civilization!
    public static void maintenance(Civilization civilization) {
        int number = civilization.getNumberOfRailroadsAndRoads() / civilization.getCities().size();
        for (City city : civilization.getCities()) {
            city.setGold(civilization.getGold() - number);
        }
    }

    public static void updateImprovementsAboutToBeCreated(Civilization currentCivilization) {
        for (Unit unit : currentCivilization.getUnits()) {
            if (unit.getTypeOfUnit() == TypeOfUnit.WORKER) {
                for (Improvement improvement : unit.getImprovementsAboutToBeCreated()) {
                    if (improvement.getTypeOfImprovement() == TypeOfImprovement.FARM)
                        updateFarm(improvement, unit);
                    if (improvement.getTypeOfImprovement() == TypeOfImprovement.MINE)
                        updateMine(improvement, unit);
                    else
                        updateOtherImprovements(improvement, unit);
                }
            }
        }
    }

    private static void updateOtherImprovements(Improvement improvement, Unit unit) {
        improvement.setTurn(improvement.getTurn() - 1);
        if (improvement.getTurn() == 0)
            Worker.buildImprovement(improvement, unit);
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

    public static void foodConsumption(Civilization civilization) {
        for (City city : civilization.getCities()) {
            city.setFood(city.getFood() - city.getCitizens().size() * 2);
            if (city.getFood() < 0) city.setFood(0);
        }
    }

    public static void citizensIncome(Civilization civilization) {
        for (City city : civilization.getCities()) {
            int gold = 0;
            int food = 0;
            int production = 0;
            for (Citizen citizen : city.getCitizens()) {
                if (citizen.getTerrain() == null) continue;
                TerrainOutput terrainOutput = TerrainController.getTerrainsOutput(civilization , citizen.getTerrain());
                gold += terrainOutput.getGold();
                food += terrainOutput.getFood();
                production += terrainOutput.getProduction();
            }
            city.setFood(city.getFood() + food);
            city.setGold(city.getGold() + gold);
            city.setProduction(city.getProduction() + production);
        }
    }
}
