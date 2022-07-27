package game.View.controller;

import game.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class PurchasePageController {

    public Button buildingButton;
    public Button unitButton;
    public Button cityButton;

    public void buyBuilding(ActionEvent actionEvent) {
    }

    public void buyUnit(ActionEvent actionEvent) {
        Main.otherStage.close();
        Main.loadNewStage("unitMenu", "unitMenu");
    }

    public void goToCity(ActionEvent actionEvent) {
        Main.otherStage.close();
        Main.loadNewStage("cityPanel", "cityPanel");
    }
}
