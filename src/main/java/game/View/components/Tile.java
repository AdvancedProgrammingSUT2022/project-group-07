package game.View.components;


import game.Controller.game.*;
import game.Controller.game.*;
import game.Controller.game.SelectController;
import game.Controller.game.SelectController;
import game.Enum.Resources;
import game.Enum.TerrainFeatures;
import game.Enum.TypeOfTerrain;
import game.Main;
import game.Model.Civilization;
import game.Model.Terrain;
import game.Model.Unit;
import game.View.controller.Movement;
import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
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

    // left click ---> tile info
    // right click ---> select city center

    private static ArrayList<Tile> tiles = new ArrayList<>();
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
    private Unit attack;
    private Unit civil;
    private Circle cityCenter;
    public Unit getAttack() {
        return attack;
    }

    public void setAttack(Unit attack) {
        this.attack = attack;
    }

    public Unit getCivil() {
        return civil;
    }

    public void setCivil(Unit civil) {
        this.civil = civil;
    }

    private Popup popup;
    private Unit unit;

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

    public Circle getCityCenter() {
        return cityCenter;
    }

    public void setCityCenter(Circle cityCenter) {
        this.cityCenter = cityCenter;
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
        tiles.add(this);
        this.civilUnit = new Circle();
        this.civilUnit.setRadius(0);
        this.attackUnit = new Circle();
        this.attackUnit.setRadius(0);
    }


    public static ArrayList<Tile> getTiles() {
        return tiles;
    }



    public void setBackground(String addressType, String addressTypeFeature, Resources resources,
                              TypeOfTerrain typeOfTerrain)
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
            checkMouseAction(resources);
        }
        else {
            checkMouseAction();
        }
    }

    private Text setMainTypeOfTerrain(AnchorPane anchorPane, double mainPos,
                                      double typePos, double effectPos) {
        Text main = new Text("\"Tile Info\"");
        Text terrainType = new Text("● Terrain Type: " + this.terrain.getTypeOfTerrain().getName() + "➙ ");
        Text terrainTypeEffect = terrainTypeEffect();

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

    private void checkMouseAction() {
        setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                CityController.checkCityCenter(this);
            }
            else {
                Window window = Main.scene.getWindow();
                this.popup = new Popup();
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                if (Movement.isMoving) {
                    Movement.move(this);
                    return;
                }
                CityController.checkCityCenter(this);
            }
            else {
                AnchorPane anchorPane = new AnchorPane();
                anchorPane.setStyle("-fx-background-color: rgba(255,255,255,0.49)");
                anchorPane.setMinSize(100, 100);

                Text terrainType = setMainTypeOfTerrain(anchorPane, 75.0, 60.0, 220.0);
                Text combat, mpNumber;

                if (feature != null) {
                    mpNumber = new Text("● MP: " + findMp());
                    combat = new Text("● Change of Combat: % " + findCombatChange());
                    setStyles(anchorPane, 120.0, 60.0, 220.0);
                } else {
                    combat = new Text("● Change of Combat: % " + this.terrain.getTypeOfTerrain().getChangeOfCombat() * 100);
                    mpNumber = new Text("● MP: " + this.terrain.getTypeOfTerrain().getMpNeeded());
                }

                mpNumber.setLayoutY(95);
                combat.setLayoutY(130);

                mpNumber.setStyle("-fx-font-weight: bold");
                combat.setStyle("-fx-font-weight: bold");

                AnchorPane.setLeftAnchor(mpNumber, 60.0);
                AnchorPane.setLeftAnchor(combat, 60.0);

                openPopup(window, anchorPane, terrainType, mpNumber, combat);
//                CityController.checkCityCenter(this);
                }
            }
        });
    }

    private void checkMouseAction(Resources resources) {
        setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                CityController.checkCityCenter(this);
            }else {
                Window window = Main.scene.getWindow();
                this.popup = new Popup();
                if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    if (Movement.isMoving) {
                        Movement.move(this);
                        return;
                    }
                    CityController.checkCityCenter(this);
                } else {
                    AnchorPane anchorPane = new AnchorPane();
                    anchorPane.setStyle("-fx-background-color: rgba(255,255,255,0.49)");
                    Image image = (new Image(getClass().getResource("/game/assets/civAsset/resources/"
                            + resources.getName() + ".png").toExternalForm()));
                    anchorPane.getChildren().add(new ImageView(image));

                    Text terrainType = setMainTypeOfTerrain(anchorPane, 150.0, 150.0, 305.0);
                    Text resource = new Text("● Resources:");
                    Text type = new Text(resources.getName() + " --> " +
                            resources.getTypeOfResource().toString().toLowerCase(Locale.ROOT) + "➙ ");
                    Text resEffect = resourceEffect(resources);
                    Text mpNumber, combat;

                    if (feature != null) {
                        mpNumber = new Text("● MP: " + findMp());
                        combat = new Text("● Change of Combat: % " + findCombatChange());
                        setStyles(anchorPane, 210.0, 150.0, 305.0);
                    } else {
                        combat = new Text("● Change of Combat: % " + this.terrain.getTypeOfTerrain().getChangeOfCombat() * 100);
                        mpNumber = new Text("● MP: " + this.terrain.getTypeOfTerrain().getMpNeeded());
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
                }
            }
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


    private int findMp() {
        return this.terrain.getTypeOfTerrain().getMpNeeded() + this.terrain.getTerrainFeatures().getMp();
    }

    private int findCombatChange() {
        return (int) ((this.terrain.getTerrainFeatures().getChangeOfCombat() +
                this.terrain.getTypeOfTerrain().getChangeOfCombat()) * 100);
    }

    private void setStyles(AnchorPane anchorPane, double featureName,
                           double title, double featureEffect) {
        Text tFeature = new Text("● Feature:");
        Text f = new Text(this.terrain.getTerrainFeatures().getName() + "➙ ");
        Text effect = featureEffect();

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

    private Text featureEffect() {
        int food = this.terrain.getTypeOfTerrain().getFood();
        int production = this.terrain.getTypeOfTerrain().getProduction();
        int gold = this.terrain.getTypeOfTerrain().getGold();
        Text text = new Text("‣ food: " + food + "\n‣ production: "
                + production + "\n‣ gold: " + gold);
        text.setLayoutY(180);
        return text;
    }

    private Text terrainTypeEffect() {
        int food = this.terrain.getTypeOfTerrain().getFood();
        int production = this.terrain.getTypeOfTerrain().getProduction();
        int gold = this.terrain.getTypeOfTerrain().getGold();
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

    public Unit getUnit() {
        return unit;
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

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void updateUnitBackground() {
        GameController gameController = GameController.getInstance();
        for (Civilization civilization : gameController.getCivilizations()) {
            for (Unit unit : civilization.getUnits()) {
                if (TerrainController.getTerrainByLocation(unit.getLocation()).equals(this.getTerrain())) {
                    this.unit = unit;
                    if (UnitController.isCivilUnit(unit)) this.civil = unit;
                    else this.attack = unit;
                    setUnitBackground(unit);
                }
            }

        }
    }

    private void setUnitBackground(Unit unit) {
        if (UnitController.isCivilUnit(unit)) {
            if (this.civilUnit.getRadius() == 0) {
                this.civilUnit.setCenterX(this.x - 10);
                this.civilUnit.setCenterY(this.y);
                this.civilUnit.setRadius(35);
            }
            this.civilUnit.setFill(new ImagePattern(new Image(Main.class.getResource(
                    "/game/assets/civAsset/units/Units/" + unit.getTypeOfUnit().getName() + ".png"
            ).toExternalForm())));
            this.civilUnit.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    SelectController.selectedUnit = civil;
                }
            });
        } else {
            if (this.attackUnit.getRadius() == 0) {
                this.attackUnit = new Circle(this.x - 20, this.y, 35);
            }
            this.attackUnit.setFill(new ImagePattern(new Image(Main.class.getResource(
                    "/game/assets/civAsset/units/Units/" + unit.getTypeOfUnit().getName() + ".png"
            ).toExternalForm())));
            this.attackUnit.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    SelectController.selectedUnit = attack;
                }
            });
        }
    }
    public void setCityCenter() {
        cityCenter = new Circle(x - 75, y - 30 , 35);
        cityCenter.setFill(new ImagePattern(new Image(Main.class.getResource(
                "/game/assets/cityInfo/city.png"
        ).toExternalForm())));
        cityCenter.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                SelectController.selectedCity = GameController.getInstance().getCurrentCivilization().getCities().get(
                        GameController.getInstance().getCurrentCivilization().getCities().size() - 1
                );
            }
        });
    }
}
