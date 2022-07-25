package game.Server.Controller.game;

import game.Client.View.components.Tile;
import javafx.scene.layout.AnchorPane;

public class MapMovement {

    public static void moveLeft(AnchorPane game, double firstX) {
        double n = game.getTranslateX() + 10;
        if (canMoveLeft(n, firstX))
            game.setTranslateX(game.getTranslateX() + 10);
    }

    public static void moveRight(AnchorPane game, double firstX) {
        double n = game.getTranslateX() - 10;
        if (canMoveRight(n, firstX))
            game.setTranslateX(game.getTranslateX() - 10);
    }

    public static void moveUp(AnchorPane game, double firstY) {
        double n = game.getTranslateY() + 10;
        if (canMoveUp(n, firstY))
            game.setTranslateY(game.getTranslateY() + 10);
    }

    public static void moveDown(AnchorPane game, double firstY) {
        double n = game.getTranslateY() - 10;
        if (canMoveDown(n, firstY))
            game.setTranslateY(game.getTranslateY() - 10);
    }

    private static boolean canMoveLeft(double n, double firstX) {
        return !(n >= firstX + 50);
    }

    private static boolean canMoveRight(double n, double firstX) {
        int width = GameController.getInstance().getMapHeight();
        double number = 1.5 * width * 75 - 950;
        return !(-n >= firstX + number);
    }

    private static boolean canMoveUp(double n, double firstY) {
        return !(n >= firstY + 100);
    }

    private static boolean canMoveDown(double n, double firstY) {
        int height = GameController.getInstance().getMapWidth();
        double number = height * Tile.getTileWidth() - 600;
        return !(-n >= firstY + number);
    }
}
