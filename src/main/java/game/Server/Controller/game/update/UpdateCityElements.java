package game.Server.Controller.game.update;

import game.Common.Enum.TerrainFeatures;
import game.Common.Enum.TypeOfImprovement;
import game.Common.Enum.TypeOfUnit;
import game.Common.Model.*;
import game.Server.Controller.game.CityController;
import game.Server.Controller.game.GameController;
import game.Server.Controller.game.LogAndNotification.NotificationController;
import game.Server.Controller.game.TerrainController;
import game.Server.Controller.game.units.Worker;

import java.util.ArrayList;
import java.util.Random;

public class UpdateCityElements {

    public static void update(Civilization civilization, GameController gameController) {
        updateImprovementsAboutToBeCreated(civilization);
        updateUnitsAboutToBeCreate(civilization, gameController);
        updateRoutsAboutToBeCreated(civilization);
    }

    public static void updateUnitsAboutToBeCreate(Civilization currentCivilization, GameController gameController) {
        for (City city : currentCivilization.getCities()) {
            for (TypeOfUnit unit : city.getWantedUnits()) {
                if (city.getProduction() != 0)
                    unit.setTurnsNeededToCreate(unit.getCost() / city.getProduction());
                if (city.getProduction() >= unit.getCost()) {
                    Terrain cityCenter = city.getTerrains().get(0);
                    city.setProduction(city.getProduction() - unit.getCost());
                    CityController.createUnit(currentCivilization, unit, cityCenter.getLocation(), city, gameController);
                    break;
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
        if (civilization.getCities().size() == 0)
            return;
        int routesAndUnits = civilization.getNumberOfRailroadsAndRoads() + civilization.getUnits().size();
        for (City city : civilization.getCities()) {
            civilization.setGold(civilization.getGold() - routesAndUnits - city.getBuildings().size());
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
                if (citizen.getTerrain() == null) {
                    production += 1;
                    continue;
                }
                TerrainOutput terrainOutput = TerrainController.getTerrainsOutput(civilization, citizen.getTerrain());
                gold += terrainOutput.getGold();
                food += terrainOutput.getFood();
                production += terrainOutput.getProduction();
            }
            city.setFood(city.getFood() + food);
            civilization.setGold(civilization.getGold() + gold);
            city.setProduction(city.getProduction() + production);
        }
    }

    public static void cityGrowth(Civilization civilization) {
        ArrayList<City> cities = civilization.getCities();
        for (City city : cities) {
            if (city.getTurnsTillGrowth() == 0) {
                ArrayList<Terrain> availableTerrains = CityController.getAvailableTilesToBuy(
                        city, GameController.getInstance().getMap(),
                        GameController.getInstance().getMapWidth(), GameController.getInstance().getMapHeight()
                );
                Terrain terrainToBuy = availableTerrains.get((new Random()).nextInt(availableTerrains.size()));
                CityController.addTileToCity(city, terrainToBuy);
                NotificationController.logNewTileAddedToCity(terrainToBuy, city);
                int turnsTillGrowth = (int) (Math.log(128 * city.getTerrains().size() - city.getCitizens().size()) / Math.log(2));
                city.setTurnsTillGrowth(turnsTillGrowth + 1);
            } else
                city.setTurnsTillGrowth(city.getTurnsTillGrowth() - 1);
        }
    }

    public static void citizenGrowth(Civilization civilization) {
        ArrayList<City> cities = civilization.getCities();
        for (City city : cities) {
            if (city.getCitizens().size() == 0) continue;
            if (city.getFood() >= Math.pow(2, city.getCitizens().size())) {
                city.addCitizen(new Citizen(city.getCitizens().size()));
                NotificationController.logNewCitizenAddedToCity(city);
                System.out.println("new citizen added to city " + city.getName());
            }
        }
    }

    public static void updateUnderConstructionBuildings(Civilization civilization) {
        for (City city : civilization.getCities()) {
            UnderConstructionBuilding underConstructionBuilding = city.getUnderConstructionBuilding() ;
            if (underConstructionBuilding==null) continue;

            if (underConstructionBuilding.getRemainingTurns()==0) {
                city.addBuilding(underConstructionBuilding.getBuilding());
                NotificationController.logBuildingAddedToCity(underConstructionBuilding.getBuilding() , city);
                city.setUnderConstructionBuilding(null);
            }
            else
                underConstructionBuilding.setRemainingTurns(underConstructionBuilding.getRemainingTurns()-1);
        }
    }
}
