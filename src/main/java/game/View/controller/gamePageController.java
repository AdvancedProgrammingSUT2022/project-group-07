package game.View.controller;

import game.Controller.Chat.MessageController;
import game.Controller.UserController;
import game.Controller.game.GameController;
import game.Controller.game.SelectController;
import game.Enum.TypeOfTechnology;
import game.Main;
import game.Model.Technology;
import game.Model.Terrain;
import game.Model.Unit;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class gamePageController {

    public AnchorPane game;
    public static double firstX;
    public static double firstY;
    public static int counter = 0;

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
    public Button prevUnitButton = new Button("prev") ;
    public Button nextUnitButton = new Button("next") ;
    public ImageView selectedUnitImageView = new ImageView() ;
    public Label selectedUnitDataLabel = new Label() ;
    public ToolBar selectedUnitPanel = new ToolBar() ;

    // next turn button
    public ImageView nextTurnImageView = new ImageView() ;


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
                updateInfoPanelPosition();
            } ;
            while (true) {
                Platform.runLater(runnable);
                try {Thread.sleep(10);}
                catch (InterruptedException ignored) {}
            }
        });
        infoPanelThread.start();

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

    public void initializeIconPanel (){
        initializeIcons();
        initializeTooltips();
        iconPanel.setPrefWidth(400);
        iconPanel.setPrefHeight(40);
        iconPanel.getItems().clear();
        iconPanel.getItems().addAll(goldIcon , goldLabel , scienceIcon , scienceLabel , happinessIcon , happinessLabel) ;
        iconPanel.setStyle("-fx-opacity: 0.70 ; -fx-background-color: black");
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
        iconPanel.setLayoutX(game.getTranslateX()*(-1));
        iconPanel.setLayoutY(game.getTranslateY()*(-1));
        researchPanel.setLayoutX(game.getTranslateX()*(-1));
        researchPanel.setLayoutY(40+game.getTranslateY()*(-1));
        selectedUnitPanel.setLayoutX(game.getTranslateX()*(-1));
        selectedUnitPanel.setLayoutY(600+game.getTranslateY()*(-1));
        nextTurnImageView.setLayoutX(game.getTranslateX()*(-1)+1000);
        nextTurnImageView.setLayoutY(game.getTranslateY()*(-1)+600);
    }

    private void initializeResearchPanel() {
        researchPanel.setOrientation(Orientation.VERTICAL);
        researchPanel.setPrefHeight(150);
        researchPanel.setPrefWidth(150);
        researchPanel.setStyle("-fx-background-color: rgba(79,79,79,0.30) ; -fx-alignment: center");
        researchPanel.getItems().clear();
        researchPanel.getItems().addAll(currentResearchImageView, progressBar);
    }

    public void updateResearchPanel (){
        currentResearchImageView.setFitWidth(100);
        currentResearchImageView.setPreserveRatio(true);
        currentResearchImageView.setSmooth(true);

        // TODO : next line should be deleted in real game !
        GameController.getInstance().getCurrentCivilization().setCurrentResearch(new Technology(TypeOfTechnology.AGRICULTURE));

        try {
            currentResearchImageView.setImage(new Image(
                    Main.class.getResource("/game/images/technologies/" + GameController.getInstance().getCurrentCivilization().getCurrentResearch().getTypeOfTechnology().getName() + ".png").toExternalForm())) ;
            int predictedTurns = GameController.getInstance().getCurrentCivilization().getCurrentResearch().getTypeOfTechnology().getScienceNeeded() / GameController.getInstance().getCurrentCivilization().getScience() ;
            double progress = ((double) GameController.getInstance().getCurrentCivilization().getCurrentResearch().getRemainingTurns()) / ((double) predictedTurns );
            progressBar.setProgress(progress);
            progressBar.setDisable(false);
        } catch (NullPointerException e){
            currentResearchImageView.setImage(new Image(
                    Main.class.getResource("/game/images/icons/Research.png").toExternalForm()));
            progressBar.setDisable(true);
        }
        progressBar.setStyle("-fx-background-color: Red ; -fx-background-radius: 20 ;");
        progressBar.setTooltip(new Tooltip(GameController.getInstance().getCurrentCivilization().getCurrentResearch().getTypeOfTechnology().getName()));
    }

    public void initializeSelectedUnitPanel (){
        selectedUnitPanel.getItems().clear();
        selectedUnitPanel.getItems().addAll(selectedUnitImageView , prevUnitButton , selectedUnitDataLabel , nextUnitButton);
        updateSelectedUnitPanel();
    }

    public void updateSelectedUnitPanel (){
        selectedUnitImageView.setFitWidth(100);
        selectedUnitImageView.setPreserveRatio(true);
        selectedUnitImageView.setSmooth(true);

        try {
            selectedUnitImageView.setImage(new Image(
                    Main.class.getResource("/game/assets/civAsset/units/Units/" + GameController.getInstance().getCurrentCivilization().getUnits().get(0) + ".png").toExternalForm()));
            Unit selectedUnit = SelectController.selectedUnit ;
            String unitData = String.format("HP : %d\nMP : %d\nStatus : %s" , selectedUnit.getHp() , selectedUnit.getMp() , selectedUnit.getUnitStatus());
            selectedUnitDataLabel.setText(unitData);
            selectedUnitPanel.setDisable(false);
        } catch (NullPointerException e){
            selectedUnitImageView.setImage(new Image(
                    Main.class.getResource("/game/images/icons/Unit.png").toExternalForm()));
            selectedUnitPanel.setDisable(true);
        }
    }

    public void handleButtonActions(){
        prevUnitButton.setOnMouseClicked(mouseEvent -> {
            SelectController.selectPrevUnit();
            updateSelectedUnitPanel();
        });

        nextUnitButton.setOnMouseClicked(mouseEvent -> {
            SelectController.selectNextUnit();
            updateSelectedUnitPanel();
        });
    }

    public void initializeNextTurnButton (){
        nextTurnImageView.setFitHeight(100);
        nextTurnImageView.setFitWidth(100);
        nextTurnImageView.setPreserveRatio(true);
        nextTurnImageView.setOnMouseClicked(mouseEvent -> GameController.getInstance().setTurn(GameController.getInstance().getTurn()+1));
        nextTurnImageView.setImage(new Image(getClass().getResource("/game/images/icons/NEXT_TURN_ICON.png").toExternalForm()));
    }

}
