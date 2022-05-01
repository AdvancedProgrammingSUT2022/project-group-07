package Controller.cheat;

import Controller.game.GameController;
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
        civilization.setGold(civilization.getGold() + amount);
        return "cheat! : " + amount + " added to gold!";
    }
}
