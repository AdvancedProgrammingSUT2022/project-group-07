package Controller.game.update;

import Controller.game.GameController;
import Controller.game.LogAndNotification.NotificationController;
import Model.City;
import Model.Civilization;
import Model.Technology;
import Model.Unit;

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
     *  a function to update whole gold of a civilization
     */
    public static void updateGold(Civilization civilization){
        ArrayList<City> cities = civilization.getCities();
        int sum = 0 ;
        for (City city : cities)
            sum += city.getGold();
        civilization.setGold(civilization.getGold()+sum);
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
        for (City city : civilization.getCities()) {
            numberOfCitizens += city.getCitizens().size();
        }
        civilization.setScience(civilization.getScience() + numberOfCitizens + 3);
    }

    public static void update(Civilization civilization) {
        updateResearch(civilization);
        updateScience(civilization);
        updateFood(civilization);
        updateGold(civilization);
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
}
