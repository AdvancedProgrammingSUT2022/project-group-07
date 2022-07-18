package game.View.controller;

import game.Controller.game.GameController;
import game.Controller.game.MapMovement;
import game.Controller.game.SelectController;
import game.Main;
import game.Model.Terrain;
import game.Model.Unit;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;

import java.io.IOException;


public class gamePageController {

    public AnchorPane game;
    public double firstX;
    public double firstY;

    // icon panel and stuff
    public ToolBar iconPanel = new ToolBar();
    public ImageView goldIcon = new ImageView() ;
    public ImageView scienceIcon = new ImageView() ;
    public ImageView happinessIcon = new ImageView() ;
    public Label goldLabel = new Label() ;
    public Label scienceLabel = new Label() ;
    public Label happinessLabel = new Label() ;

    // research panel and stuff
    public ImageView currentResearchImageView = new ImageView() ;
    ProgressBar progressBar = new ProgressBar() ;
    public ToolBar researchPanel = new ToolBar() ;

    // unit panel and stuff
    public Button prevUnitButton = new Button("⬅") ;
    public Button nextUnitButton = new Button("➡") ;
    public ImageView selectedUnitImageView = new ImageView() ;
    public Label selectedUnitDataLabel = new Label() ;
    public ToolBar selectedUnitPanel = new ToolBar() ;

    // next turn button
    public ImageView nextTurnImageView = new ImageView() ;

    public void initialize() {
        Main.scene.setFill(new ImagePattern(new Image(getClass().getResource("/game/assets/Backgrounds/blue.jpg").toExternalForm())));
        firstX = game.getTranslateX();
        firstY = game.getTranslateY();
        Terrain[][] terrains = GameController.getInstance().getMap();
        for (Terrain[] terrain : terrains) {
            for (Terrain terrain1 : terrain) {
                game.getChildren().add(terrain1.getTile());
                if (terrain1.getTile().getFeature() != null)
                    game.getChildren().add(terrain1.getTile().getFeature());
            }
        }
        // initializing panels
        initializeIconPanel();
        initializeResearchPanel();
        initializeSelectedUnitPanel();
        initializeNextTurnButton();
        game.getChildren().add(iconPanel);
        game.getChildren().add(researchPanel);
        game.getChildren().add(selectedUnitPanel);
        game.getChildren().add(nextTurnImageView);
        // updating info panel thread
        Thread infoPanelThread = new Thread(() -> {
            Runnable runnable = () -> {
                updateLabels();
                updateResearchPanel();
                updateSelectedUnitPanel();
            } ;
            while (true) {
                Platform.runLater(runnable);
                try {Thread.sleep(10);}
                catch (InterruptedException ignored) {}
            }
        });
        infoPanelThread.start();
        // updating info panel pos to escape lag !!!
        Thread posThread = new Thread(() -> {
            Runnable runnable = this::updateInfoPanelPosition;
            while (true) {
                Platform.runLater(runnable);
                try {Thread.sleep(3);}
                catch (InterruptedException ignored){}
            }
        }) ;
        posThread.start();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                game.requestFocus();
            }
        });

    }

    public void move(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case LEFT -> MapMovement.moveLeft(game, firstX);
            case RIGHT -> MapMovement.moveRight(game, firstX);
            case UP -> MapMovement.moveUp(game, firstY);
            case DOWN -> MapMovement.moveDown(game, firstY);
        }
    }

    public void initializeIconPanel (){
        initializeIcons();
        initializeTooltips();
        iconPanel.getItems().clear();
        iconPanel.getItems().addAll(goldIcon , goldLabel , scienceIcon , scienceLabel , happinessIcon , happinessLabel) ;
        iconPanel.getStyleClass().add("iconPanel") ;
    }

    public void initializeTooltips (){
        goldLabel.setTooltip(new Tooltip("Civilization total gold (starting value : 0)"));
        scienceLabel.setTooltip(new Tooltip("Civilization total science (starting value : 0)"));
        happinessLabel.setTooltip(new Tooltip("Civilization total happiness (starting value : 10)"));
    }

    public void initializeIcons (){
        goldIcon.setImage(new Image(Main.class.getResource("/game/images/icons/GOLD_ICON.png").toExternalForm()));
        scienceIcon.setImage(new Image(Main.class.getResource("/game/images/icons/SCIENCE_ICON.png").toExternalForm()));
        happinessIcon.setImage(new Image(Main.class.getResource("/game/images/icons/HAPPINESS_ICON.png").toExternalForm()));

        goldLabel.getStyleClass().add("iconLabel") ;
        scienceLabel.getStyleClass().add("iconLabel") ;
        happinessLabel.getStyleClass().add("iconLabel") ;
        goldIcon.setFitWidth(30);
        goldIcon.setFitHeight(30);
        goldIcon.setPreserveRatio(true);
        scienceIcon.setFitWidth(30);
        scienceIcon.setFitHeight(30);
        scienceIcon.setPreserveRatio(true);
        happinessIcon.setFitWidth(30);
        happinessIcon.setFitHeight(30);
        happinessIcon.setPreserveRatio(true);
    }

    public void updateLabels (){
        goldLabel.setText(GameController.getInstance().getCurrentCivilization().getGold()+"");
        scienceLabel.setText(GameController.getInstance().getCurrentCivilization().getScience()+"");
        happinessLabel.setText(GameController.getInstance().getCurrentCivilization().getHappiness()+"");
    }

    public void updateInfoPanelPosition(){
        double x = game.getTranslateX() * (-1) ;
        double y = game.getTranslateY() * (-1) ;
        iconPanel.setLayoutX(x);
        iconPanel.setLayoutY(y);
        researchPanel.setLayoutX(x);
        researchPanel.setLayoutY(40+y);
        selectedUnitPanel.setLayoutX(x);
        selectedUnitPanel.setLayoutY(520+y);
        nextTurnImageView.setLayoutX(1000+x);
        nextTurnImageView.setLayoutY(600+y);
    }

    private void initializeResearchPanel() {
        researchPanel.setOrientation(Orientation.VERTICAL);
        researchPanel.getStyleClass().add("researchPanel") ;
        researchPanel.getItems().clear();
        researchPanel.getItems().addAll(currentResearchImageView, progressBar);
        progressBar.getStyleClass().add("technologyProgressBar") ;
        currentResearchImageView.setOnMouseClicked(mouseEvent -> {
            Main.loadNewStage("Technology tree" , "technologyTreePage");
        });
    }

    public void updateResearchPanel (){
        currentResearchImageView.setFitWidth(100);
        currentResearchImageView.setPreserveRatio(true);
        currentResearchImageView.setSmooth(true);

        try {
            currentResearchImageView.setImage(new Image(
                    Main.class.getResource("/game/images/technologies/" + GameController.getInstance().getCurrentCivilization().getCurrentResearch().getTypeOfTechnology().getName() + ".png").toExternalForm())) ;
            int predictedTurns = GameController.getInstance().getCurrentCivilization().getCurrentResearch().getTypeOfTechnology().getScienceNeeded() / GameController.getInstance().getCurrentCivilization().getScience() ;
            double progress = ((double) GameController.getInstance().getCurrentCivilization().getCurrentResearch().getRemainingTurns()) / ((double) predictedTurns );
            progressBar.setProgress(progress);
            if (!researchPanel.getItems().contains(progressBar))
                researchPanel.getItems().add(progressBar) ;
            progressBar.setTooltip(new Tooltip(GameController.getInstance().getCurrentCivilization().getCurrentResearch().getTypeOfTechnology().getName()));
        } catch (NullPointerException e){
            currentResearchImageView.setImage(new Image(
                    Main.class.getResource("/game/images/icons/Research.png").toExternalForm()));
            researchPanel.getItems().remove(progressBar);
        }
    }

    public void initializeSelectedUnitPanel (){
        selectedUnitPanel.getItems().clear();
        selectedUnitPanel.getStyleClass().add("selectedUnitPanel");
        selectedUnitPanel.getItems().addAll(selectedUnitImageView , prevUnitButton , selectedUnitDataLabel , nextUnitButton);
        handleButtonActions();
    }

    public void updateSelectedUnitPanel (){
        selectedUnitImageView.setFitWidth(100);
        selectedUnitImageView.setPreserveRatio(true);
        selectedUnitImageView.setSmooth(true);

        try {
            Unit selectedUnit = SelectController.selectedUnit ;
            selectedUnitImageView.setImage(new Image(
                    Main.class.getResource("/game/assets/civAsset/units/Units/" + selectedUnit.getTypeOfUnit().getName() + ".png").toExternalForm()));
            String unitData = String.format("HP : %d\nMP : %d\nStatus : %s\nLocation : (%d,%d)"
                    , selectedUnit.getHp() , selectedUnit.getMp() , selectedUnit.getUnitStatus()
                    , selectedUnit.getLocation().getX() , selectedUnit.getLocation().getY());
            selectedUnitDataLabel.setText(unitData);
            if (!game.getChildren().contains(selectedUnitPanel))
                game.getChildren().add(selectedUnitPanel);
        } catch (NullPointerException e){
            game.getChildren().remove(selectedUnitPanel);
        }
    }

    public void handleButtonActions(){
        prevUnitButton.setFocusTraversable(false);
        prevUnitButton.setOnMouseClicked(mouseEvent -> {
            SelectController.selectPrevUnit();
            updateSelectedUnitPanel();
            game.requestFocus();
        });
        nextUnitButton.setFocusTraversable(false);
        nextUnitButton.setOnMouseClicked(mouseEvent -> {
            SelectController.selectNextUnit();
            updateSelectedUnitPanel();
            game.requestFocus();
        });
        prevUnitButton.getStyleClass().add("arrowButton") ;
        nextUnitButton.getStyleClass().add("arrowButton") ;
    }

    public void initializeNextTurnButton (){
        nextTurnImageView.setFitHeight(100);
        nextTurnImageView.setFitWidth(100);
        nextTurnImageView.setPreserveRatio(true);
        nextTurnImageView.getStyleClass().add("nextTurn") ;
        nextTurnImageView.setOnMouseClicked(mouseEvent -> GameController.getInstance().nextTurn(GameController.getInstance()));
        nextTurnImageView.setImage(new Image(getClass().getResource("/game/images/icons/NEXT_TURN_ICON.png").toExternalForm()));
    }

}
