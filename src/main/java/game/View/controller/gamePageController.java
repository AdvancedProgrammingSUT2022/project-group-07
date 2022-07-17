package game.View.controller;

import game.Controller.game.GameController;
import game.Model.Terrain;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.awt.event.KeyListener;

public class gamePageController {

    public AnchorPane game;
    public static double firstX;
    public static double firstY;
    public static int counter = 0;

    public void initialize() {
        counter++;
        Terrain[][] terrains = GameController.getInstance().getMap();
        for (Terrain[] terrain : terrains) {
            for (Terrain terrain1 : terrain) {
                game.getChildren().add(terrain1.getTile());
                if (terrain1.getTile().getFeature() != null)
                    game.getChildren().add(terrain1.getTile().getFeature());
            }
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                game.requestFocus();
            }
        });
    }

    public void move(KeyEvent keyEvent) {
        if (counter == 1) {
            firstX = game.getLayoutX();
            firstY = game.getLayoutY();
        }
        switch (keyEvent.getCode()) {
            case LEFT -> moveLeft(game);
            case RIGHT -> moveRight(game);
            case UP -> moveUp(game);
            case DOWN -> moveDown(game);
        }
    }

    private static void moveLeft(AnchorPane game) {
        double n = game.getTranslateX() + 10;
//        if (canMoveLeft(n))
            game.setTranslateX(game.getTranslateX() + 10);
    }

    private static void moveRight(AnchorPane game) {
        double n = game.getTranslateX() - 10;
//        if (canMoveRight(n))
            game.setTranslateX(game.getTranslateX() - 10);
    }

    private static void moveUp(AnchorPane game) {
        double n = game.getTranslateY() + 10;
//        if (canMoveUp(n))
            game.setTranslateY(game.getTranslateY() + 10);
    }

    private static void moveDown(AnchorPane game) {
        double n = game.getTranslateY() - 10;
//        if (canMoveDown(n))
            game.setTranslateY(game.getTranslateY() - 10);
    }

    private static boolean canMoveRight(double n) {
        GameController gameController = GameController.getInstance();
        return !(n <= firstX);
    }

    private static boolean canMoveLeft(double n) {
        GameController gameController = GameController.getInstance();
        return !(n >= firstX + gameController.getMapWidth());
    }

    private static boolean canMoveUp(double n) {
        GameController gameController = GameController.getInstance();
        return !(n <= firstY);
    }

    private static boolean canMoveDown(double n) {
        GameController gameController = GameController.getInstance();
        return !(n >= firstY + gameController.getMapHeight());
    }
}
