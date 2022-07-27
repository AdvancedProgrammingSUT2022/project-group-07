package game.View.controller;

import game.Controller.game.GameController;
import game.Controller.game.LogAndNotification.NotificationController;
import game.Controller.game.MapController;
import game.Controller.game.MapMovement;
import game.Controller.game.SelectController;
import game.Enum.Building;
import game.Enum.TypeOfUnit;
import game.Main;
import game.Model.*;
import game.View.components.Tile;
import javafx.application.Platform;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class GamePageController {

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
    public ImageView setting;
    ProgressBar progressBar = new ProgressBar() ;
    public ToolBar researchPanel = new ToolBar() ;

    // unit panel and stuff
    public Button prevUnitButton = new Button("⬅") ;
    public Button nextUnitButton = new Button("➡") ;
    public ImageView selectedUnitImageView = new ImageView() ;
    public Label selectedUnitDataLabel = new Label() ;
    public ToolBar selectedUnitPanel = new ToolBar() ;

    //unit actions :
    public ToolBar unitActions = new ToolBar();
    // next turn button
    public ImageView nextTurnImageView = new ImageView() ;

    // other mini panels
    public ToolBar othersPanel = new ToolBar() ;
    public ImageView cityImageView = new ImageView() ;
    public Label cityLabel = new Label("City panel") ;
    public ImageView demographicImageView = new ImageView() ;
    public Label demographicLabel = new Label("Demographic panel") ;
    public ImageView notificationImageView = new ImageView() ;
    public Label notificationLabel = new Label("Notification Panel");
    public ImageView militaryImageView = new ImageView() ;
    public Label militaryLabel = new Label("Military Panel") ;
    public ImageView economicImageView = new ImageView() ;
    public Label economicLabel = new Label("Economic Panel") ;

    // diplomacy panel stuff
    public ImageView diplomacyPanelImageView = new ImageView(new Image(getClass().getResource("/game/images/icons/DIPLOMACY_ICON.png").toExternalForm())) ;

    // save button
    //public Button saveButton = new Button("Save") ;

    // current civilization Label
    //public Label currentCivilizationLabel = new Label() ;

    public ImageView cityPanelImageView = new ImageView(new Image(getClass().getResource("/game/assets/Civ_LEADER_CATHERINE_DE_MEDICI.png").toExternalForm())) ;

    // cheat stage :
    final BooleanProperty controlPressed = new SimpleBooleanProperty(false);
    final BooleanProperty leftShiftPressed = new SimpleBooleanProperty(false);
    final BooleanProperty cPressed = new SimpleBooleanProperty(false);
    final BooleanBinding ctrlAndShiftAndCPressed = controlPressed.and(leftShiftPressed.and(cPressed));



    ///////////////////////////////////////////////////////////////
    // setting panel ::
    private AnchorPane settingPane;
    private VBox settingBox;
    private Popup popup = new Popup();

    public void initialize() {
        Main.scene.setFill(new ImagePattern(new Image(getClass().getResource("/game/assets/Backgrounds/blue.jpg").toExternalForm())));
        firstX = game.getTranslateX();
        firstY = game.getTranslateY();
        Tile[][] map = GameController.getInstance().getMap();
        for (Tile[] tiles : map) {
            for (Tile tile  : tiles) {
                game.getChildren().add(tile);
                if (tile.getFeature() != null)
                    game.getChildren().add(tile.getFeature());
                tile.updateUnitBackground();
                game.getChildren().add(tile.getCivilUnit());

                if (tile.getTerrain().hasRuin()
                        && GameController.getInstance().getCurrentCivilization().getVisibleTerrains().contains(tile.getTerrain()))
                {
                    ImageView imageView = new ImageView(new Image(getClass().getResource("/game/images/Tiles/Ruin.png").toExternalForm()));
                    imageView.setFitWidth(60);
                    imageView.setFitHeight(60);
                    imageView.setLayoutX(tile.getX());
                    imageView.setLayoutY(tile.getY());
                    Tooltip.install(imageView , new Tooltip("Gives you " + tile.getTerrain().getTypeOfRuin().toString().replace("FREE_" , "")+" !"));
                    game.getChildren().add(imageView);
                }
            }
        }

        MapController.setMapCenter(GameController.getInstance().getCurrentCivilization().getUnits().get(0).getLocation() ,
                game);
        // initializing panels
        initializeIconPanel();
        initializeResearchPanel();
        initializeSelectedUnitPanel();
        initializeNextTurnButton();
        initializeOthersPanel() ;
        initializeDiplomacyPanel() ;

        CityPanelController.initializeCityPanel(cityPanelImageView);


        game.getChildren().add(iconPanel);
        game.getChildren().add(researchPanel);
        game.getChildren().add(selectedUnitPanel);
        game.getChildren().add(nextTurnImageView);
        game.getChildren().add(othersPanel) ;
        game.getChildren().add(diplomacyPanelImageView) ;
        game.getChildren().add(cityPanelImageView);
        game.getChildren().add(unitActions);

        /////////////////////////////////////////////////////////////////

        setting = new ImageView(new Image(getClass().getResource("/game/assets/MainWindow/settings_gear.png").toExternalForm()));
        setting.setOnMouseClicked(this::settingHandle);
        setting.setPreserveRatio(true);
        setting.setFitHeight(50);
        setting.setFitWidth(50);
        game.getChildren().add(setting);
//        game.getChildren().add(currentCivilizationLabel);
//        game.getChildren().add(saveButton);
//        saveButton.setOnMouseClicked(mouseEvent -> {
//            GameController.getInstance().saveData(GameController.getInstance() , "save1");
//        });

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
        infoPanelThread.setDaemon(true);
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
        posThread.setDaemon(true);
        posThread.start();
        // updating mini panels
        Thread miniPanelsThread = new Thread(() -> {
            Runnable runnable = this::updateOthersPanel;
            while (true) {
                Platform.runLater(runnable);
                try {Thread.sleep(15000);}
                catch (InterruptedException ignored){}
            }
        }) ;
        miniPanelsThread.setDaemon(true);
        miniPanelsThread.start();

        // hell of a test bro
//        Thread test = new Thread(() -> {
//            Runnable runnable = () -> {
//                currentCivilizationLabel.setText(GameController.getInstance().getCurrentCivilization().getName());
//                currentCivilizationLabel.setStyle("-fx-font-size: 20");
//            } ;
//            while (true) {
//                Platform.runLater(runnable);
//                try {Thread.sleep(400);}
//                catch (InterruptedException ignored) {}
//            }
//        });
//        test.setDaemon(true);
//        test.start();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                game.requestFocus();
            }
        });
        initializeGameKeyboardButtons();
        Main.playMenuMusic();
        //Movement.initializeMovements();
    }

    private void initializeSetting() {
        Button resume = new Button("resume");
        resume.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                popup.hide();
            }
        });
        Button autoSave = new Button("auto save");
        autoSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GameController.getInstance().saveData(GameController.getInstance() , "save1");
            }
        });
        Button manualSave = new Button("save");
        autoSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GameController.getInstance().saveData(GameController.getInstance() , "save2");
            }
        });
        Button muteSound = new Button("mute");
        muteSound.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MediaPlayer mediaPlayer = Main.getMediaPlayer();
                if (mediaPlayer.isMute()) {
                    muteSound.setText("mute");
                }
                else {
                    muteSound.setText("unmute");
                }
                mediaPlayer.setMute(!mediaPlayer.isMute());
            }
        });
        Button exitGame = new Button("exit");
        exitGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Main.changeScene("mainMenu");
                    popup.hide();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        
        String style = "   -fx-background-image: url(../resources/game/assets/Button_Normal.png);" +
                "    -fx-background-size:  100% 100%;" +
                "    -fx-background-repeat: no-repeat;" +
                "-fx-background-color: darkblue;" +
                "    -fx-pref-height: 22;" +
                "-fx-pref-width: 500;"+
                "    -fx-font-size: 25;" +
                "    -fx-font-family: \"B Nazanin\";" +
                "    -fx-text-fill: white;" +
                "    -fx-opacity: 80%;";
        exitGame.setStyle(style);
        muteSound.setStyle(style);
        resume.setStyle(style);
        autoSave.setStyle(style);
        manualSave.setStyle(style);
        settingBox = new VBox(resume , autoSave , manualSave ,muteSound , exitGame);
        settingBox.setPadding(new Insets(10 , 10 , 10 , 10));
        settingBox.setSpacing(10);
        settingPane.getChildren().add(settingBox);
    }


    private void initializeUnitActions() {

        unitActions.getItems().clear();
        Button foundCity = new Button("found city");
        Button move = new Button("move");
        Button deleteUnit = new Button("delete unit");
        Button sleep = new Button("sleep");
        unitActions.getItems().addAll(foundCity , move , deleteUnit , sleep);
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(foundCity); buttons.add(move); buttons.add(deleteUnit); buttons.add(sleep);
        Movement.initializeActionButtons(buttons , game);
    }


    private void initializeGameKeyboardButtons() {
        ctrlAndShiftAndCPressed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (ctrlAndShiftAndCPressed.get()) {
                    try {
                        CheatController.startUp();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        game.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.SHIFT) leftShiftPressed.set(true);
                else if (keyEvent.getCode() == KeyCode.CONTROL) controlPressed.set(true);
                else if (keyEvent.getCode() == KeyCode.C) cPressed.set(true);

                else if (keyEvent.getCode() == KeyCode.LEFT)  MapMovement.moveLeft(game, firstX);
                else if (keyEvent.getCode() == KeyCode.RIGHT)  MapMovement.moveRight(game, firstX);
                else if (keyEvent.getCode() == KeyCode.UP)  MapMovement.moveUp(game, firstY);
                else if (keyEvent.getCode() == KeyCode.DOWN)  MapMovement.moveDown(game, firstY);
            }
        });
        game.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.SHIFT) leftShiftPressed.set(false);
                else if (keyEvent.getCode() == KeyCode.CONTROL) controlPressed.set(false);
                else if (keyEvent.getCode() == KeyCode.C) cPressed.set(false);
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

    private void initializeDiplomacyPanel() {
        diplomacyPanelImageView.setFitWidth(80);
        diplomacyPanelImageView.setFitHeight(80);
        Tooltip.install(diplomacyPanelImageView , new Tooltip("Diplomacy Panel"));
        diplomacyPanelImageView.getStyleClass().add("diplomacyPanelImageView") ;
        diplomacyPanelImageView.setOnMouseClicked(mouseEvent -> Main.loadNewStage("Diplomacy panel" , "diplomacyMenu"));
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
        setting.setLayoutX(x + 1020);
        setting.setLayoutY(300 + y);
        othersPanel.setLayoutX(300+x);
        othersPanel.setLayoutY(0+y);
        diplomacyPanelImageView.setLayoutX(x+1000);
        diplomacyPanelImageView.setLayoutY(y+80);
        unitActions.setLayoutX(x + 500);
        unitActions.setLayoutY(660 + y);
        settingPane = new AnchorPane();
        settingPane.setLayoutX(100);
        settingPane.setLayoutY(100);
        initializeSetting();
//        saveButton.setLayoutX(x+400);
//        saveButton.setLayoutY(y+300);
//        currentCivilizationLabel.setLayoutX(x+300);
//        currentCivilizationLabel.setLayoutY(y+200);
        cityPanelImageView.setLayoutX(x+1000);
        cityPanelImageView.setLayoutY(y+180);
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
            if (!game.getChildren().contains(unitActions)) {
                game.getChildren().add(unitActions);
                initializeUnitActions();
            }
        } catch (NullPointerException e){
            game.getChildren().remove(selectedUnitPanel);
            game.getChildren().remove(unitActions);
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
        nextTurnImageView.setOnMouseClicked(mouseEvent -> GameController.getInstance().nextTurn(GameController.getInstance(), game));
        nextTurnImageView.setImage(new Image(getClass().getResource("/game/images/icons/NEXT_TURN_ICON.png").toExternalForm()));
    }

    public void initializeOthersPanel (){
        cityImageView = new ImageView(new Image(Main.class.getResource("/game/images/icons/SCIENCE_ICON.png").toExternalForm())) ;
        demographicImageView = new ImageView(new Image(Main.class.getResource("/game/images/icons/SCIENCE_ICON.png").toExternalForm())) ;
        notificationImageView = new ImageView(new Image(Main.class.getResource("/game/images/icons/SCIENCE_ICON.png").toExternalForm())) ;
        militaryImageView = new ImageView(new Image(Main.class.getResource("/game/images/icons/SCIENCE_ICON.png").toExternalForm())) ;
        economicImageView = new ImageView(new Image(Main.class.getResource("/game/images/icons/SCIENCE_ICON.png").toExternalForm())) ;
        ImageView[] all = {cityImageView , demographicImageView , notificationImageView , militaryImageView , economicImageView} ;
        for (ImageView imageView : all) {
            imageView.setFitWidth(30);
            imageView.setFitHeight(30);
            imageView.setPreserveRatio(true);
        }
        othersPanel.getItems().addAll(cityImageView , cityLabel
                , demographicImageView , demographicLabel
                , notificationImageView , notificationLabel
                , militaryImageView , militaryLabel
                , economicImageView ,economicLabel);
        othersPanel.setLayoutX(300);
        othersPanel.setLayoutY(0);
        othersPanel.getStyleClass().add("othersPanel") ;
    }

    public void updateOthersPanel (){
        updateCityStatus();
        updateDemographicStatus();
        updateNotificationsStatus() ;
        updateMilitaryStatus() ;
        updateEconomyStatus();
    }

    public void updateCityStatus (){
        if (SelectController.selectedCity != null ){
            City city = SelectController.selectedCity ;
            String cityInfo = String.format("City %s\nFood : %d\nGold : %d\nProduction : %d\nPopulation : %d\nTurns till city growth : %d\nScience achievement : +%d"
                    , city.getName() , city.getFood() , city.getOwnership().getGold() , city.getProduction()
                    , city.getCitizens().size() , city.getTurnsTillGrowth() , city.getCitizens().size()+5);
            cityLabel.setTooltip(new Tooltip(cityInfo));
        }
    }

    public void updateDemographicStatus (){
        Civilization civilization = GameController.getInstance().getCurrentCivilization();
        ArrayList<City> cities = civilization.getCities();
        int numberOfTilesOwned = 0 ;
        for (City city : cities)
            numberOfTilesOwned += city.getTerrains().size();
        float progress = ((float) numberOfTilesOwned*100) / (float)(GameController.getInstance().getMapHeight()*GameController.getInstance().getMapWidth()) ;
        String demographicInfo = String.format("Total gold : %d\nTotal food : %d\nCities : %d\nTiles owned : %d\nRoad and Railroads : %d\nTotal progress : %f percent"
                , civilization.getGold() , civilization.getFood() , cities.size() , numberOfTilesOwned
                , civilization.getNumberOfRailroadsAndRoads() , progress);
        demographicLabel.setTooltip(new Tooltip(demographicInfo));
    }

    public void updateNotificationsStatus (){
        StringBuilder notificationInfo = new StringBuilder();
        ArrayList<Notification> currentCivilizationNotifications = NotificationController.getNotifications().get(GameController.getInstance().getCurrentCivilization()) ;

        if (currentCivilizationNotifications==null){
            if (notificationInfo.toString().length()==0)
                notificationLabel.setTooltip(new Tooltip("No notifications !"));
            return;
        }

        for (int i=currentCivilizationNotifications.size()-1 ; i>=0 ; i--) {
            Notification currentCivilizationNotification = currentCivilizationNotifications.get(i);
            notificationInfo.append(String.format("%s\n\t%3d turns ago at %s\n"
                    , currentCivilizationNotification.getMessage()
                    , GameController.getInstance().getTurn() - currentCivilizationNotification.getTurnOfCreation()
                    , currentCivilizationNotification.getRealTimeCreated()));
        }
        notificationLabel.setTooltip(new Tooltip(notificationInfo.toString()));
    }

    public void updateMilitaryStatus (){
        StringBuilder militaryInfo = new StringBuilder();
        Civilization civilization = GameController.getInstance().getCurrentCivilization();
        for (Unit unit : civilization.getUnits()) {
            if (unit.getTypeOfUnit()== TypeOfUnit.WORKER || unit.getTypeOfUnit()==TypeOfUnit.SETTLER)
                continue;
            Location location = unit.getLocation();
            militaryInfo.append(String.format("military unit %s : \n\tLocation : (%d,%d)\n\tStatus : %s\n\tHp : %d\ntMp : %d\n",
                    unit.getTypeOfUnit().getName(), location.getX(), location.getY(), unit.getUnitStatus(), unit.getHp(), unit.getMp()));
        }
        militaryLabel.setTooltip(new Tooltip(militaryInfo.toString()));
        if (militaryInfo.length() == 0)
            militaryLabel.setTooltip(new Tooltip("No military unit !"));
    }

    public void updateEconomyStatus (){
        Civilization civilization = GameController.getInstance().getCurrentCivilization();
        ArrayList<City> cities = civilization.getCities();
        int totalDefencePower = 0 ;
        int totalProduction = 0;
        int totalPopulation = 0 ;
        HashSet<Building> buildings = new HashSet<>();
        for (City city : cities) {
            totalDefencePower += city.getDefencePower();
            totalProduction += city.getProduction() ;
            totalPopulation += city.getCitizens().size() ;
            buildings.addAll(new HashSet<>(city.getBuildings()));
        }
        StringBuilder economyInfo = new StringBuilder(String.format("Total defence power : %d\nTotal production : %d\nTotal population : %d\nBuildings : \n"
                , totalDefencePower, totalProduction, totalPopulation));
        for (Building building : buildings)
            economyInfo.append(building).append("\t");

        economicLabel.setTooltip(new Tooltip(economyInfo.toString()));
    }

    public void settingHandle(MouseEvent mouseEvent) {
        Window window = Main.scene.getWindow();
        popup.getContent().add(settingPane);
        popup.show(window);
    }
}
