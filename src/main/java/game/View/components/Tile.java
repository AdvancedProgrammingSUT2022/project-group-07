package game.View.components;


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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.net.URL;
import java.util.ArrayList;

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

    public void setBackground(String addressType, String addressTypeFeature) {
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
