package game.View.components;


import game.Enum.Resources;
import game.Enum.TerrainFeatures;
import game.Enum.TypeOfTerrain;
import game.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Window;


import java.net.URL;
import java.util.Locale;

public class Tile extends Polygon {

    private static final double r = 75;
    private static final double n = Math.sqrt(r * r * 0.75);
    private final static double TILE_HEIGHT = 2 * r;
    private final static double TILE_WIDTH = 2 * n;
    private double x;
    private double y;
    private ImageView feature;
    private Popup popup;

    public Popup getPopup() {
        return popup;
    }
    public ImageView getFeature() {
        return feature;
    }
    public static double getTileHeight() {return TILE_HEIGHT;}
    public static double getTileWidth() {
            return TILE_WIDTH;
        }
    public static double getR(){
            return r;
        }
    public static double getN(){
            return n;
        }

    public Tile(double x, double y) {
        this.x = x + 18;
        this.y = y - 50;
        getPoints().addAll(
                x,y,
                x + 0.5*r , y + n,
                x + 1.5*r , y + n,
                x + TILE_HEIGHT , y ,
                x + 1.5 * r , y - n ,
                x + 0.5 * r , y - n
        );
        setFill(Color.WHITE);
        setStrokeWidth(1);
        setStroke(Color.TRANSPARENT);
    }

    public void setBackground(String addressType, String addressTypeFeature, Resources resources,
                              TypeOfTerrain typeOfTerrain, TerrainFeatures tFeature) {
        URL address = getClass().getResource("/game/assets/civAsset/map/Tiles/terrainsAndFeatures/" + addressTypeFeature);
        setFill(new ImagePattern((new Image(getClass().getResource("/game/assets/civAsset/map/Tiles/terrainsAndFeatures/"
                + addressType).toExternalForm()))));

        if (address != null) {
            ImagePattern imagePattern = new ImagePattern(new Image(address.toExternalForm()));
            this.feature = new ImageView();
            this.feature.setFitHeight(110);
            this.feature.setFitWidth(110);
            this.feature.setLayoutX(this.x);
            this.feature.setLayoutY(this.y);
            this.feature.setImage(imagePattern.getImage());
        }
        if (resources != null) {
            checkMouseAction(typeOfTerrain, resources, tFeature);
        }
        else {
            checkMouseAction(typeOfTerrain, tFeature);
        }
    }

    private void checkMouseAction(TypeOfTerrain typeOfTerrain, TerrainFeatures feature) {
        setOnMouseClicked(mouseEvent -> {
            Window window = Main.scene.getWindow();
            this.popup = new Popup();

            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setStyle("-fx-background-color: rgba(255,255,255,0.49)");
            anchorPane.setMinSize(100, 100);

            Text main = new Text("\"Tile Info\"");
            Text terrainType = new Text("● Terrain Type: " + typeOfTerrain.getName());
            Text combat, mpNumber;

            if (feature != null) {
                mpNumber = new Text("● MP: " + findMp(typeOfTerrain, feature));
                combat = new Text("● Change of Combat: % " + findCombatChange(typeOfTerrain, feature));
                setStyles(anchorPane, feature, 120.0, 60.0, 205.0);
            } else {
                combat = new Text("● Change of Combat: % " + typeOfTerrain.getChangeOfCombat() * 100);
                mpNumber = new Text("● MP: " + typeOfTerrain.getMpNeeded());
            }

            main.setLayoutY(20);
            terrainType.setLayoutY(60);
            mpNumber.setLayoutY(95);
            combat.setLayoutY(130);

            main.setStyle("-fx-font-weight: bold;" +
                    "-fx-font-size: 15;" +
                    "-fx-fill: #651426");
            terrainType.setStyle("-fx-font-weight: bold;" +
                    "-fx-fill: #87009a");

            AnchorPane.setLeftAnchor(main, 75.0);
            AnchorPane.setLeftAnchor(terrainType, 60.0);
            AnchorPane.setLeftAnchor(mpNumber, 60.0);
            AnchorPane.setLeftAnchor(combat, 60.0);

            anchorPane.getChildren().add(main);
            openPopup(window, anchorPane, terrainType, mpNumber, combat);
        });
    }

    private void checkMouseAction(TypeOfTerrain typeOfTerrain, Resources resources, TerrainFeatures feature) {
        setOnMouseClicked(mouseEvent -> {

            Window window = Main.scene.getWindow();
            this.popup = new Popup();

            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setStyle("-fx-background-color: rgba(255,255,255,0.49)");
            Image image = (new Image(getClass().getResource("/game/assets/civAsset/resources/"
                    + resources.getName() + ".png").toExternalForm()));
            anchorPane.getChildren().add(new ImageView(image));

            Text main = new Text("\"Tile Info\"");
            Text resource = new Text("● Resources:");
            Text type = new Text(resources.getName() + " --> " +
                    resources.getTypeOfResource().toString().toLowerCase(Locale.ROOT) + " ➙ ");
            Text resEffect = resourceEffect(resources);
            Text terrainType = new Text("● Terrain Type: " + typeOfTerrain.getName());
            Text mpNumber, combat;

            if (feature != null) {
                mpNumber = new Text("● MP: " + findMp(typeOfTerrain, feature));
                combat = new Text("● Change of Combat: % " + findCombatChange(typeOfTerrain, feature));
                setStyles(anchorPane, feature, 210.0, 150.0, 295.0);
            }
            else {
                combat = new Text("● Change of Combat: % " + typeOfTerrain.getChangeOfCombat()*100);
                mpNumber = new Text("● MP: " + typeOfTerrain.getMpNeeded());
            }

            resource.setLayoutY(65);
            type.setLayoutY(80);
            main.setLayoutY(20);
            mpNumber.setLayoutY(110);
            combat.setLayoutY(135);
            terrainType.setLayoutY(45);

            mpNumber.setStyle("-fx-font-weight: bold");
            main.setStyle("-fx-font-weight: bold;" +
                    "-fx-font-size: 15;" +
                    "-fx-fill: #651426");
            resource.setStyle("-fx-font-weight: bold");
            combat.setStyle("-fx-font-weight: bold");
            terrainType.setStyle("-fx-font-weight: bold;" +
                    "-fx-fill: #87009a");

            AnchorPane.setLeftAnchor(type, 170.0);
            AnchorPane.setLeftAnchor(resource, 150.0);
            AnchorPane.setLeftAnchor(mpNumber, 150.0);
            AnchorPane.setLeftAnchor(main, 150.0);
            AnchorPane.setLeftAnchor(resEffect, 295.0);
            AnchorPane.setLeftAnchor(combat, 150.0);
            AnchorPane.setLeftAnchor(terrainType, 150.0);

            anchorPane.getChildren().add(type);
            anchorPane.getChildren().add(resource);
            anchorPane.getChildren().add(mpNumber);
            anchorPane.getChildren().add(main);
            openPopup(window, anchorPane, resEffect, terrainType, combat);
        });
    }

    private void openPopup(Window window, AnchorPane anchorPane,
                           Text resEffect, Text terrainType, Text combat) {
        anchorPane.getChildren().add(resEffect);
        anchorPane.getChildren().add(combat);
        anchorPane.getChildren().add(terrainType);
        popup.getContent().add(anchorPane);

        setOnMouseMoved(e -> popup.hide());

        popup.setAutoHide(true);
        popup.show(window);
    }


    private int findMp(TypeOfTerrain typeOfTerrain, TerrainFeatures feature) {
        return typeOfTerrain.getMpNeeded() + feature.getMp();
    }

    private int findCombatChange(TypeOfTerrain typeOfTerrain, TerrainFeatures feature) {
        return (int) ((feature.getChangeOfCombat() +
                typeOfTerrain.getChangeOfCombat()) * 100);
    }

    private void setStyles(AnchorPane anchorPane, TerrainFeatures feature,
                           double featureName, double title, double featureEffect) {
        Text tFeature = new Text("● Feature:");
        Text f = new Text(feature.getName() + " ➙ ");
        Text effect = featureEffect(feature);

        tFeature.setLayoutY(170);
        f.setLayoutY(170);
        tFeature.setStyle("-fx-font-weight: bold");

        AnchorPane.setLeftAnchor(f, featureName);
        AnchorPane.setLeftAnchor(tFeature, title);
        AnchorPane.setLeftAnchor(effect, featureEffect);

        anchorPane.getChildren().add(f);
        anchorPane.getChildren().add(tFeature);
        anchorPane.getChildren().add(effect);

    }

    private Text resourceEffect(Resources resource) {
        int food = resource.getFood();
        int production = resource.getProduction();
        int gold = resource.getGold();
        Text text = new Text("‣ food: " + food + "\n‣ production: "
                + production + "\n‣ gold: " + gold);
        text.setLayoutY(65);
        return text;
    }

    private Text featureEffect(TerrainFeatures terrainFeature) {
        int food = terrainFeature.getFood();
        int production = terrainFeature.getProduction();
        int gold = terrainFeature.getGold();
        Text text = new Text("‣ food: " + food + "\n‣ production: "
                + production + "\n‣ gold: " + gold);
        text.setLayoutY(160);
        return text;
    }
}
