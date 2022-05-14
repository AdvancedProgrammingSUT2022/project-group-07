package Controller.game.update;

import Controller.game.CityController;
import Controller.game.GameController;
import Controller.game.LogAndNotification.NotificationController;
import Controller.game.MapController;
import Controller.game.TerrainController;
import Controller.game.TerrainController;
import Controller.game.units.Worker;
import Model.*;
import Enum.TypeOfUnit;
import Enum.TypeOfImprovement;
import Enum.TerrainFeatures;
import Enum.TypeOfImprovement;
import Enum.TerrainFeatures;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

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
        for (Unit unit : currentCivilization.getUnits()) {
            if (unit.getTypeOfUnit() == TypeOfUnit.WORKER) {
                Route route = unit.getRouteAboutToBeBuilt();
                if (route != null) {
                    route.setTurnsNeeded(route.getTurnsNeeded() - 1);
                    if (route.getTurnsNeeded() == 0)
                        Worker.buildRoute(route, currentCivilization);
                }
            }
        }
    }

    // just for selected civilization!
    public static void maintenance(Civilization civilization) {
        // TODO + 1?
        if (civilization.getCities().size()==0)
            return;
        int number = civilization.getUnits().size() + civilization.getNumberOfRailroadsAndRoads() / civilization.getCities().size();
        for (City city : civilization.getCities()) {
            city.setGold(civilization.getGold() - number - city.getBuildings().size());
        }
    }

    public static void updateImprovementsAboutToBeCreated(Civilization currentCivilization) {
        for (Unit unit : currentCivilization.getUnits()) {
            if (unit.getTypeOfUnit() == TypeOfUnit.WORKER) {
                Improvement improvement = unit.getImprovementAboutToBeCreated();
                if (improvement != null) {
                    if (improvement.getTypeOfImprovement() == TypeOfImprovement.FARM)
                        updateFarm(improvement, unit);
                    else if (improvement.getTypeOfImprovement() == TypeOfImprovement.MINE)
                        updateMine(improvement, unit);
                    else
                        updateOtherImprovements(improvement, unit);
                }
                if (unit.getRepairTurns() != 0)
                    updateRepairment(unit, currentCivilization);
            }
        }
    }

    private static void updateRepairment(Unit unit, Civilization civilization) {
        unit.setRepairTurns(unit.getRepairTurns() - 1);
        if (unit.getRepairTurns() == 0)
            Worker.repair(unit, civilization);
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
                if (citizen.getTerrain() == null) food += 1;
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

    public static void cityGrowth (Civilization civilization){
        ArrayList<City> cities = civilization.getCities();
        for (City city : cities) {
            if (city.getTurnsTillGrowth()==0){
                ArrayList<Terrain> availableTerrains = CityController.getAvailableTilesToBuy(
                        city , GameController.getMap() , GameController.getMapWidth() , GameController.getMapHeight()
                );
                Terrain terrainToBuy = availableTerrains.get((new Random()).nextInt(availableTerrains.size()));
                CityController.addTileToCity(city , terrainToBuy) ;
                NotificationController.logNewTileAddedToCity(terrainToBuy , city);
                int turnsTillGrowth = (int) (Math.log(128*city.getTerrains().size()-city.getCitizens().size()) / Math.log(2)) ;
                city.setTurnsTillGrowth(turnsTillGrowth+1) ;
            }
            else
                city.setTurnsTillGrowth(city.getTurnsTillGrowth()-1);
        }
    }

    public static void citizenGrowth (Civilization civilization){
        ArrayList<City> cities = civilization.getCities();
        for (City city : cities) {
            if (city.getCitizens().size()==0) continue;
            if (city.getFood()>=Math.pow(2,city.getCitizens().size())) {
                city.addCitizen(new Citizen(city.getCitizens().size()));
                System.out.println("new citizen added to city " + city.getName());
            }
        }
    }

}
