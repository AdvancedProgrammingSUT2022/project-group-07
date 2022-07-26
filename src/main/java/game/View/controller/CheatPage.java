package game.View.controller;

import game.Controller.cheat.Cheat;
import game.Controller.game.GameController;
import game.Enum.regexes.GameMenuCommands;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;

public class CheatPage {
    public Button cheatButton;
    public TextField cheatText;

    public void cheatCode(ActionEvent actionEvent) {
        String input = cheatText.getText();
        Matcher matcher;
        GameController gameController = GameController.getInstance();
         if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INCREASE_TURN)) != null) {
            String result = Cheat.turnCheat(matcher , gameController);
            showSuccess(result);
        }
        else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INCREASE_GOLD)) != null) {
            String result = Cheat.goldCheat(matcher , gameController);
            showSuccess(result);
        }
        else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INCREASE_FOOD)) != null) {
            String result = Cheat.foodCheat(matcher , gameController);
            showSuccess(result);
        }
        else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INCREASE_SCIENCE)) != null) {
            String result = Cheat.scienceCheat(matcher , gameController);
            showSuccess(result);
        }
        else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INCREASE_PRODUCTION)) != null) {
            String result = Cheat.productionCheat(matcher , gameController);
            showSuccess(result);
        }
        else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INCREASE_HAPPINESS)) != null) {
            String result = Cheat.happinessCheat(matcher , gameController);
            showSuccess(result);
        }
        else if ((GameMenuCommands.getMatcher(input , GameMenuCommands.HEAL_UNIT)) != null) {
            String result = Cheat.healUnit();
            showSuccess(result);
        }
        else if (GameMenuCommands.getMatcher(input , GameMenuCommands.HEAL_CITY) != null) {
            String result = Cheat.healCity();
            showSuccess(result);
        }
        else if (GameMenuCommands.getMatcher(input , GameMenuCommands.ADD_ENEMY_CITY_TO_YOURS) != null) {
            String result = Cheat.addEnemyCity(gameController);
            showSuccess(result);
        }
        else if (GameMenuCommands.getMatcher(input , GameMenuCommands.DISABLE_ENEMY_UNIT) != null) {
            String result = Cheat.disableEnemyUnit(gameController);
            showSuccess(result);
        }
        else if (GameMenuCommands.getMatcher(input , GameMenuCommands.BOMBING_ENEMY_CITY) != null){
            String result = Cheat.plantingBombsOnEnemyCity(gameController);
            showSuccess(result);
        }
        else if (GameMenuCommands.getMatcher(input , GameMenuCommands.SET_TIMES_MOVED_TO_ZERO) != null) {
            String result = Cheat.setTimesMovedZero(gameController);
            showSuccess(result);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("no valid cheat entered!!");
            alert.show();
         }
    }
    public void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.show();
    }
}
