package Controller.cheat;

import Controller.game.GameController;
import Controller.game.SelectController;
import Controller.game.UnitController;
import Controller.game.update.UpdateCivilizationElements;
import Model.City;
import Model.Civilization;
import Model.Unit;

import java.util.regex.Matcher;

public class Cheat {

    public static String turnCheat(Matcher matcher , GameController gameController) {
        int amount = Integer.parseInt(matcher.group("amount"));
        gameController.setTurn(gameController.getTurn() + amount);
        return "cheat! : " + amount + " turns passed!";
    }

    public static String goldCheat(Matcher matcher , GameController gameController) {
        int amount = Integer.parseInt(matcher.group("amount"));
        Civilization civilization = gameController.getCurrentCivilization();
        for (City city : civilization.getCities()) {
            city.setGold(city.getGold() + amount);
        }
        return "cheat! : " + amount + " added to each city's gold!";
    }

    public static String scienceCheat(Matcher matcher, GameController gameController) {
        int amount = Integer.parseInt(matcher.group("amount"));
        Civilization civilization = gameController.getCurrentCivilization();
        civilization.setScience(civilization.getScience() + amount);
        UpdateCivilizationElements.updateScience(civilization);
        return "cheat! : " + amount + " added to science!";
    }

    public static String happinessCheat(Matcher matcher, GameController gameController) {
        int amount = Integer.parseInt(matcher.group("amount"));
        Civilization civilization = gameController.getCurrentCivilization();
        civilization.setHappiness(civilization.getHappiness() + amount);
        return "cheat : " + amount + " added to happiness!";
    }

    public static String foodCheat(Matcher matcher, GameController gameController) {
        int amount = Integer.parseInt(matcher.group("amount"));
        Civilization civilization = gameController.getCurrentCivilization();
        for (City city : civilization.getCities()) {
            city.setGold(city.getGold() + amount);
        }
        UpdateCivilizationElements.updateFood(civilization);
        return "cheat : " + amount + " added to each city's food!";
    }

    public static String productionCheat(Matcher matcher, GameController gameController) {
        int amount = Integer.parseInt(matcher.group("amount"));
        Civilization civilization = gameController.getCurrentCivilization();
        for (City city : civilization.getCities()) {
            city.setProduction(city.getProduction() + amount);
        }
        return "cheat : " + amount + " added to each city's production!";
    }

    public static String healUnit() {
        Unit unit = SelectController.selectedUnit;
        if (unit == null) return "no unit was selected!";
        else {
            unit.setHp(10);
            return "unit was healed successfully!";
        }
    }

    public static String healCity() {
        City city = SelectController.selectedCity;
        if (city == null) return "no city was selected!";
        else city.setHp(50);
        return "city was healed successfully!";
    }

    public static String addEnemyCity(GameController gameController) {
        City city = SelectController.selectedCity;
        Civilization civilization = gameController.getCurrentCivilization();
        if (city == null) return "no city was selected!";
        if (civilization.getCities().contains(city)) return "you cannot cheat on your own city!";
        for (Civilization gameControllerCivilization : gameController.getCivilizations()) {
            if (gameControllerCivilization.getCities().contains(city))
                gameControllerCivilization.getCities().remove(city);
        }
        civilization.addCity(city);
        return "enemy city added to yours successfully!";
    }

    public static String disableEnemyUnit(GameController gameController) {
        Unit unit = SelectController.selectedUnit;
        if (unit == null) return "no unit was selected!";
        if (gameController.getCurrentCivilization().getUnits().contains(unit))
            return "selected unit is your own!";
        unit.setTimesMovedThisTurn(10);
        return "enemy unit is out of move!";
    }

    public static String plantingBombsOnEnemyCity(GameController gameController) {
        City city = SelectController.selectedCity;
        Civilization civilization = gameController.getCurrentCivilization();
        if (city == null) return "no city was selected!";
        if (civilization.getCities().contains(city)) return "this is your own city!";
        city.setHp(1);
        return "selected city was bombarded!";
    }
}
