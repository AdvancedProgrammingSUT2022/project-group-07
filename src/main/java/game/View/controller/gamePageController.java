package game.View.controller;

import game.Controller.game.GameController;
import game.Model.Terrain;
import game.View.components.Tile;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.awt.event.KeyListener;

import static java.lang.Math.abs;
import static java.lang.Math.random;

public class gamePageController {

    public AnchorPane game;
    public double firstX;
    public double firstY;

    public void initialize() {
        firstX = game.getTranslateX();
        firstY = game.getTranslateY();
        System.out.println("x : " + firstX + "     y : " + firstY);

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
        switch (keyEvent.getCode()) {
            case LEFT -> moveLeft(game);
            case RIGHT -> moveRight(game);
            case UP -> moveUp(game);
            case DOWN -> moveDown(game);
        }
    }

    private void moveLeft(AnchorPane game) {
        double n = game.getTranslateX() + 10;
        if (canMoveLeft(n))
            game.setTranslateX(game.getTranslateX() + 10);
    }

    private void moveRight(AnchorPane game) {
        double n = game.getTranslateX() - 10;
        if (canMoveRight(n))
            game.setTranslateX(game.getTranslateX() - 10);
    }

    private void moveUp(AnchorPane game) {
        double n = game.getTranslateY() + 10;
        if (canMoveUp(n))
            game.setTranslateY(game.getTranslateY() + 10);
    }

    private void moveDown(AnchorPane game) {
        double n = game.getTranslateY() - 10;
        if (canMoveDown(n))
            game.setTranslateY(game.getTranslateY() - 10);
    }

    private boolean canMoveLeft(double n) {
        return !(n >= firstX);
    }

    private boolean canMoveRight(double n) {
        int width = GameController.getInstance().getMapHeight();
        double number = 1.5 * width * 75 - 1045;
        return !(-n >= firstX + number);
    }

    private boolean canMoveUp(double n) {
        return !(n >= firstY + 60);
    }

    private boolean canMoveDown(double n) {
        int height = GameController.getInstance().getMapWidth();
        double number = height * Tile.getTileWidth() - 690;
        return !(-n >= firstY + number);
    }
}
