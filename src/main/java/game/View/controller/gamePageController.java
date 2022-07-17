package game.View.controller;

import game.Controller.game.GameController;
import game.Model.Terrain;
import javafx.scene.layout.AnchorPane;

public class gamePageController {
    public AnchorPane game;

    public void initialize() {
        Terrain[][] terrains = GameController.getInstance().getMap();
        for (Terrain[] terrain : terrains) {
            for (Terrain terrain1 : terrain) {
                game.getChildren().add(terrain1.getTile());
            }
        }
    }
}
