package game.View.controller;

import game.Controller.game.City.CreateUnit;
import game.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class UnitMenuController {
    // TODO worker and settler?
    public Button back;

    public void backToCity(ActionEvent actionEvent) throws IOException {
        Main.otherStage.close();
        Main.loadNewStage("purchase", "purchase");
    }

    public void chariotArcher(MouseEvent mouseEvent) {
        CreateUnit.createUnit("chariotArcher");
    }

    public void canon(MouseEvent mouseEvent) {
        CreateUnit.createUnit("canon");
    }

    public void artillery(MouseEvent mouseEvent) {
        CreateUnit.createUnit("artillery");
    }

    public void antiTankGun(MouseEvent mouseEvent) {
        CreateUnit.createUnit("antiTankGun");
    }

    public void catapult(MouseEvent mouseEvent) {
        CreateUnit.createUnit("catapult");
    }

    public void archer(MouseEvent mouseEvent) {
        CreateUnit.createUnit("archer");
    }

    public void horseman(MouseEvent mouseEvent) {
        CreateUnit.createUnit("horseman");
    }

    public void longSwordsman(MouseEvent mouseEvent) {
        CreateUnit.createUnit("longSwordsman");
    }

    public void lancer(MouseEvent mouseEvent) {
        CreateUnit.createUnit("lancer");
    }

    public void infantry(MouseEvent mouseEvent) {
        CreateUnit.createUnit("infantry");
    }

    public void knight(MouseEvent mouseEvent) {
        CreateUnit.createUnit("knight");
    }

    public void musketman(MouseEvent mouseEvent) {
        CreateUnit.createUnit("musketman");
    }

    public void cavalry(MouseEvent mouseEvent) {
        CreateUnit.createUnit("cavalry");
    }

    public void crossbowman(MouseEvent mouseEvent) {
        CreateUnit.createUnit("crossbowman");
    }

    public void warrior(MouseEvent mouseEvent) {
        CreateUnit.createUnit("warrior");
    }

    public void trebuchet(MouseEvent mouseEvent) {
        CreateUnit.createUnit("trebuchet");
    }

    public void tank(MouseEvent mouseEvent) {
        CreateUnit.createUnit("tank");
    }

    public void swordsman(MouseEvent mouseEvent) {
        CreateUnit.createUnit("swordsman");
    }

    public void spearman(MouseEvent mouseEvent) {
        CreateUnit.createUnit("spearman");
    }

    public void scout(MouseEvent mouseEvent) {
        CreateUnit.createUnit("scout");
    }

    public void rifleman(MouseEvent mouseEvent) {
        CreateUnit.createUnit("rifleman");
    }

    public void pikeman(MouseEvent mouseEvent) {
        CreateUnit.createUnit("pikeman");
    }

    public void panzer(MouseEvent mouseEvent) {
        CreateUnit.createUnit("panzer");
    }

    public static void showConfirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.show();
    }

    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}
