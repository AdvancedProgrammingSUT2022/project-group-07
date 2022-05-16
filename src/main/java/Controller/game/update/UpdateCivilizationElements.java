package Controller.game.update;

import Controller.game.CivilizationController;
import Controller.game.GameController;

import Controller.game.TerrainController;
import Model.*;
import Enum.UnitStatus;

import Controller.game.LogAndNotification.NotificationController;
import Controller.game.combat.CityDefending;


import java.util.ArrayList;

import static Controller.game.UnitController.moveUnit;

public class UpdateCivilizationElements {

    /**
     * a function to update whole food of a civilization
     */
    public static void updateFood(Civilization civilization){
        ArrayList<City> cities = civilization.getCities();
        int sum = 0 ;
        for (City city : cities)
            sum += city.getFood();
        civilization.setFood(civilization.getFood()+sum);
    }

    /**
     * a function to update status of a current research
     */
    public static void updateResearch(Civilization civilization){
        Technology currentResearch = civilization.getCurrentResearch();
        if (currentResearch==null)
            return;
        if (currentResearch.getRemainingTurns()==0) {
            civilization.addTechnology(currentResearch);
            NotificationController.logResearchCompleted(currentResearch.getTypeOfTechnology() , civilization);
            civilization.setCurrentResearch(null);
        }
        else
            currentResearch.setRemainingTurns(currentResearch.getRemainingTurns()-1);
    }

    public static void updateScience(Civilization civilization){
        int numberOfCitizens = 0;
        for (City city : civilization.getCities())
            numberOfCitizens += city.getCitizens().size();
        civilization.setScience(civilization.getScience() + numberOfCitizens + civilization.getCities().size()*5);
        if (civilization.getGold() < 0 ) {
            int goldDebt = Math.abs(civilization.getGold()) ;
            int amountOfLoss = (int)(Math.log(goldDebt)/Math.log(2)) ;
            int scienceWithLossOfDebt = Math.max(civilization.getScience()-amountOfLoss , 0);
            civilization.setScience(scienceWithLossOfDebt);
            NotificationController.logScienceLossBecauseOfGoldDebt(amountOfLoss , civilization);
        }
    }

    public static void update(Civilization civilization, GameController gameController) {
        updateResearch(civilization);
        updateScience(civilization);
        updateFood(civilization);
        checkAlertUnits(civilization, gameController);
        updateFortifyHp(civilization);
        updateCityHp(civilization);
        updateCityDefencing(civilization , gameController);
        }
    private static void updateCityDefencing(Civilization civilization , GameController gameController) {
        for (City city : civilization.getCities()) {
            CityDefending.DefendFromPossibleTrespassers(city , gameController);
        }
    }

    private static void updateCityHp(Civilization civilization) {
        for (City city : civilization.getCities())
            if (city.getHp() < 30) city.setHp(city.getHp() + 1);
    }

    public static void UnitMovementsUpdate(Civilization civilization, GameController gameController) {
        for (Unit unit : civilization.getUnits()) {unit.setTimesMovedThisTurn(0);}
        for (Unit unit : civilization.getUnits()) {
            if (!unit.getPathToGo().isEmpty()) {
                moveUnit(unit.getPathToGo() , gameController , unit
                        , unit.getPathToGo().get(unit.getPathToGo().size() - 1).getLocation());
            }
        }
    }

    private static void checkAlertUnits(Civilization currentCivilization, GameController gameController) {
        for (Unit currentUnit : currentCivilization.getUnits()) {
            if (currentUnit.getUnitStatus() == UnitStatus.ALERT) {
                ArrayList<Terrain> neighborTerrains = CivilizationController.getNeighbourTerrainsByRadius1
                        (currentUnit.getLocation(), GameController.getMap(), GameController.getMapWidth()
                                , GameController.getMapHeight());
                for (Civilization civilization : gameController.getCivilizations()) {
                    if (!civilization.equals(currentCivilization)) {
                        for (Unit enemy : civilization.getUnits()) {
                            Terrain enemyTerrain = TerrainController.getTerrainByLocation(enemy.getLocation());
                            if (neighborTerrains.contains(enemyTerrain))
                                currentUnit.setUnitStatus(UnitStatus.ACTIVE);
                        }
                    }
                }
            }
        }
    }

    private static void updateFortifyHp(Civilization civilization) {
        for (Unit unit : civilization.getUnits()) {
            if (unit.getUnitStatus() == UnitStatus.FORTIFY) {
              unit.setHp(unit.getHp() + 1);
              if (unit.getHp() == unit.getTypeOfUnit().getHp())
                  unit.setUnitStatus(UnitStatus.ACTIVE);
            }
        }
    }
}
