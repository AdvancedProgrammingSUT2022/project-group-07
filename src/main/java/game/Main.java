package game;

import game.Controller.UserController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    private static Stage mainStage;
    private static Scene scene;



    private final static double r = 50; // the inner radius from hexagon center to outer corner
    private final static double n = Math.sqrt(r * r * 0.75); // the inner radius from hexagon center to middle of the axis
    private final static double TILE_HEIGHT = 2 * r;
    private final static double TILE_WIDTH = 2 * n;



    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        //
//        AnchorPane tileMap = new AnchorPane();
//        Scene content = new Scene(tileMap, 1100, 700);
        //
        mainStage = stage;
        mainStage.setResizable(false);
        Parent root = loadFXML("loginMenu");
        stage.setTitle("game");
        scene = new Scene(root);
        UserController.loadUsers();
        stage.setScene(scene);
//        stage.setScene(content);
        //
//        int rowCount = 5; // row
//        int tilesPerRow = 5; // column
//        int xStartOffset = 400; // to right
//        int yStartOffset = 200; // to down
//
//        for (int x = 0; x < tilesPerRow; x++) {
//            for (int y = 0; y < rowCount; y++) {
//                double xCoord = x * TILE_WIDTH + (y % 2) * n + xStartOffset;
//                double yCoord = y * TILE_HEIGHT * 0.75 + yStartOffset;
//
//                Polygon tile = new Tile(xCoord, yCoord);
//                if (xCoord % 2 == 0)
//                    tile.setFill(new ImagePattern(new Image(getClass().getResource("/game/images/avatars/2.png").toExternalForm())));
//                tileMap.getChildren().add(tile);
//            }
//        }
        //


        stage.show();
    }

    private static Parent loadFXML(String name){
        try {
            URL address = new URL(Main.class.getResource("/game/fxml/" + name + ".fxml").toExternalForm());
            return FXMLLoader.load(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeWindow() {
        mainStage.close();
    }

    public static void changeScene(String fxmlName) throws IOException {
        Parent root = loadFXML(fxmlName);
        scene.setRoot(root);
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
            setFill(Color.ANTIQUEWHITE);
            setStrokeWidth(1);
            setStroke(Color.WHITE);
            setOnMouseClicked(e -> System.out.println("Clicked: " + this));
        }
    }
}
