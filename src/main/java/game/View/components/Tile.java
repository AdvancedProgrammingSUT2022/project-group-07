package game.View.components;


import game.Enum.Resources;
import game.Enum.TerrainFeatures;
import game.Enum.TypeOfTerrain;
import game.Main;
import game.Controller.game.GameController;
import game.Controller.game.TerrainController;
import game.Controller.game.UnitController;
import game.Main;
import game.Model.Civilization;
import game.Model.Terrain;
import game.Model.Unit;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Window;


import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class Tile extends Polygon {

    private static final double r = 75;
    private static final double n = Math.sqrt(r * r * 0.75);
    private final static double TILE_HEIGHT = 2 * r;
    private final static double TILE_WIDTH = 2 * n;
    private double x;
    private double y;
    private ImageView feature;
    private Terrain terrain;
    private Circle attackUnit;
    private Circle civilUnit;

    private Popup popup;

    public Popup getPopup() {
        return popup;
    }
    public ImageView getFeature() {
        return feature;
    }

    public static double getTileHeight() {
        return TILE_HEIGHT;
    }

    public static double getTileWidth() {
        return TILE_WIDTH;
    }

    public static double getR() {
        return r;
    }

    public static double getN() {
        return n;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public Tile(double x, double y, Terrain terrain) {
        this.x = x + 18;
        this.y = y - 50;
        // creates the polygon using the corner coordinates
        getPoints().addAll(
                x, y,
                x + 0.5 * r, y + n,
                x + 1.5 * r, y + n,
                x + TILE_HEIGHT, y,
                x + 1.5 * r, y - n,
                x + 0.5 * r, y - n
        );
        setFill(Color.WHITE);
        setStrokeWidth(1);
        setStroke(Color.WHITE);
        this.terrain = terrain;
    }

    public void setBackground(String addressType, String addressTypeFeature, Resources resources,
                              TypeOfTerrain typeOfTerrain, TerrainFeatures tFeature)
    {
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

    private Text setMainTypeOfTerrain(AnchorPane anchorPane, TypeOfTerrain typeOfTerrain,
                                      double mainPos, double typePos, double effectPos) {
        Text main = new Text("\"Tile Info\"");
        Text terrainType = new Text("● Terrain Type: " + typeOfTerrain.getName() + "➙ ");
        Text terrainTypeEffect = terrainTypeEffect(typeOfTerrain);

        main.setLayoutY(20);
        terrainType.setLayoutY(60);

        main.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 15;" +
                "-fx-fill: #651426");
        terrainType.setStyle("-fx-font-weight: bold;" +
                "-fx-fill: #87009a");

        AnchorPane.setLeftAnchor(main, mainPos);
        AnchorPane.setLeftAnchor(terrainType, typePos);
        AnchorPane.setLeftAnchor(terrainTypeEffect, effectPos);

        anchorPane.getChildren().add(main);
        anchorPane.getChildren().add(terrainTypeEffect);

        return terrainType;
    }

    private void checkMouseAction(TypeOfTerrain typeOfTerrain, TerrainFeatures feature) {
        setOnMouseClicked(mouseEvent -> {
            Window window = Main.scene.getWindow();
            this.popup = new Popup();

            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setStyle("-fx-background-color: rgba(255,255,255,0.49)");
            anchorPane.setMinSize(100, 100);

            Text terrainType = setMainTypeOfTerrain(anchorPane, typeOfTerrain, 75.0, 60.0, 220.0);
            Text combat, mpNumber;

            if (feature != null) {
                mpNumber = new Text("● MP: " + findMp(typeOfTerrain, feature));
                combat = new Text("● Change of Combat: % " + findCombatChange(typeOfTerrain, feature));
                setStyles(anchorPane, feature, 120.0, 60.0, 220.0);
            } else {
                combat = new Text("● Change of Combat: % " + typeOfTerrain.getChangeOfCombat() * 100);
                mpNumber = new Text("● MP: " + typeOfTerrain.getMpNeeded());
            }

            mpNumber.setLayoutY(95);
            combat.setLayoutY(130);

            mpNumber.setStyle("-fx-font-weight: bold");
            combat.setStyle("-fx-font-weight: bold");

            AnchorPane.setLeftAnchor(mpNumber, 60.0);
            AnchorPane.setLeftAnchor(combat, 60.0);

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

            Text terrainType = setMainTypeOfTerrain(anchorPane, typeOfTerrain, 150.0, 150.0, 305.0);
            Text resource = new Text("● Resources:");
            Text type = new Text(resources.getName() + " --> " +
                    resources.getTypeOfResource().toString().toLowerCase(Locale.ROOT) + "➙ ");
            Text resEffect = resourceEffect(resources);
            Text mpNumber, combat;

            if (feature != null) {
                mpNumber = new Text("● MP: " + findMp(typeOfTerrain, feature));
                combat = new Text("● Change of Combat: % " + findCombatChange(typeOfTerrain, feature));
                setStyles(anchorPane, feature, 210.0, 150.0, 305.0);
            }
            else {
                combat = new Text("● Change of Combat: % " + typeOfTerrain.getChangeOfCombat()*100);
                mpNumber = new Text("● MP: " + typeOfTerrain.getMpNeeded());
            }

            resource.setLayoutY(105);
            type.setLayoutY(120);
            mpNumber.setLayoutY(140);
            combat.setLayoutY(160);

            mpNumber.setStyle("-fx-font-weight: bold");
            resource.setStyle("-fx-font-weight: bold");
            combat.setStyle("-fx-font-weight: bold");

            AnchorPane.setLeftAnchor(type, 170.0);
            AnchorPane.setLeftAnchor(resource, 150.0);
            AnchorPane.setLeftAnchor(mpNumber, 150.0);
            AnchorPane.setLeftAnchor(resEffect, 305.0);
            AnchorPane.setLeftAnchor(combat, 150.0);

            anchorPane.getChildren().add(type);
            anchorPane.getChildren().add(resource);
            anchorPane.getChildren().add(mpNumber);
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
        Text f = new Text(feature.getName() + "➙ ");
        Text effect = featureEffect(feature);

        tFeature.setLayoutY(190);
        f.setLayoutY(190);
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
        text.setLayoutY(105);
        return text;
    }

    private Text featureEffect(TerrainFeatures terrainFeature) {
        int food = terrainFeature.getFood();
        int production = terrainFeature.getProduction();
        int gold = terrainFeature.getGold();
        Text text = new Text("‣ food: " + food + "\n‣ production: "
                + production + "\n‣ gold: " + gold);
        text.setLayoutY(180);
        return text;
    }

    private Text terrainTypeEffect(TypeOfTerrain typeOfTerrain) {
        int food = typeOfTerrain.getFood();
        int production = typeOfTerrain.getProduction();
        int gold = typeOfTerrain.getGold();
        Text text = new Text("‣ food: " + food + "\n‣ production: "
                + production + "\n‣ gold: " + gold);
        text.setLayoutY(45);
        return text;
    }


    public Circle getAttackUnit() {
        return attackUnit;
    }

    public Circle getCivilUnit() {
        return civilUnit;
    }

    public void setAttackUnit(Circle attackUnit) {
        this.attackUnit = attackUnit;
    }

    public void setCivilUnit(Circle civilUnit) {
        this.civilUnit = civilUnit;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void updateUnitBackground() {
        GameController gameController = GameController.getInstance();
        for (Civilization civilization : gameController.getCivilizations()) {
            for (Unit unit : civilization.getUnits()) {
                if (TerrainController.getTerrainByLocation(unit.getLocation()).equals(this.getTerrain())) {
                    setUnitBackground(unit);
                }
            }
        }
    }

    private void setUnitBackground(Unit unit) {
        if (UnitController.isCivilUnit(unit)) {
            if (this.civilUnit == null) this.civilUnit = new Circle(this.x - 10, this.y, 35);
            this.civilUnit.setFill(new ImagePattern(new Image(Main.class.getResource(
                    "/game/assets/civAsset/units/Units/" + unit.getTypeOfUnit().getName() + ".png"
            ).toExternalForm())));
        } else {
            if (this.attackUnit == null) {
                this.attackUnit = new Circle(this.x - 20, this.y, 35);
            }
            this.attackUnit.setFill(new ImagePattern(new Image(Main.class.getResource(
                    "/game/assets/civAsset/units/Units/" + unit.getTypeOfUnit().getName() + ".png"
            ).toExternalForm())));
        }
    }
}
