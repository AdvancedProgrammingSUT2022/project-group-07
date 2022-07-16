package game.View.controller;

import game.Main;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

import java.net.URL;
import java.util.ResourceBundle;

public class MainGameController implements Initializable {

    private final static double r = 50; // the inner radius from hexagon center to outer corner
    private final static double n = Math.sqrt(r * r * 0.75); // the inner radius from hexagon center to middle of the axis
    private final static double TILE_HEIGHT = 2 * r;
    private final static double TILE_WIDTH = 2 * n;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AnchorPane tileMap = new AnchorPane();
        Scene content = new Scene(tileMap, 1100, 700);
        Main.mainStage.setScene(content);

        int rowCount = 6; // row
        int tilesPerRow = 10; // column
        int xStartOffset = 100; // to right
        int yStartOffset = 50; // to down

        for (int x = 0; x < tilesPerRow; x++) {
            for (int y = 0; y < rowCount; y++) {
                double xCoord = x * TILE_WIDTH + (y % 2) * n + xStartOffset;
                double yCoord = y * TILE_HEIGHT * 0.75 + yStartOffset;

                Polygon tile = new Tile(xCoord, yCoord);
                tileMap.getChildren().add(tile);
            }
        }
        Main.mainStage.show();
    }

    private static class Tile extends Polygon {
        Tile(double x, double y) {
            // creates the polygon using the corner coordinates
            getPoints().addAll(
                    x, y,
                    x, y + r,
                    x + n, y + r * 1.5,
                    x + TILE_WIDTH, y + r,
                    x + TILE_WIDTH, y,
                    x + n, y - r * 0.5
            );

            // set up the visuals and a click listener for the tile
            setFill(new ImagePattern(new Image(getClass().getResource("/game/images/Tiles/Tundra+Forest+Camp.png").toExternalForm())));
//            setFill(Color.ANTIQUEWHITE);
            setStrokeWidth(1);
            setStroke(Color.BLACK);
            setOnMouseClicked(e -> System.out.println("Clicked: " + this));
        }
    }
}
