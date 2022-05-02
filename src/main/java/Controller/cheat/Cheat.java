package Controller.cheat;

import Controller.game.GameController;
import Model.City;
import Model.Civilization;

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
        return "cheat! : " + amount + " added to science!";
        //TODO update civilization elements!
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
        return "cheat : " + amount + " added to each city's food!";
        //TODO update civilization elements!
    }

    public static String productionCheat(Matcher matcher, GameController gameController) {
        int amount = Integer.parseInt(matcher.group("amount"));
        Civilization civilization = gameController.getCurrentCivilization();
        for (City city : civilization.getCities()) {
            city.setProduction(city.getProduction() + amount);
        }
        return "cheat : " + amount + " added to each city's production!";
    }
}
