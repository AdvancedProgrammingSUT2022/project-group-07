package game.Client.View.controller;

import game.Client.ClientDataController;
import game.Client.Main;
import game.Client.View.components.Tile;
import game.Common.Enum.Building;
import game.Common.Enum.Network.TypeOfRequest;
import game.Common.Enum.TypeOfResponse;
import game.Common.Enum.TypeOfUnit;
import game.Common.Model.*;
import game.Common.Model.Network.ClientRequest;
import game.Server.Controller.game.GameControllerDecoy;
import game.Server.Controller.game.LogAndNotification.NotificationController;
import game.Server.Controller.game.MapController;
import game.Server.Controller.game.MapMovement;
import game.Server.Controller.game.SelectController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

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

    public ImageView notYourTurnImageView = new ImageView(new Image(Main.class.getResource("/game/images/icons/NOT_YOUR_TURN.gif").toExternalForm())) ;

    public void initialize() {

        ClientDataController.updateThisCivilization();
        Main.scene.setFill(new ImagePattern(new Image(getClass().getResource("/game/assets/Backgrounds/blue.jpg").toExternalForm())));

        for (Unit unit : ClientDataController.getThisCivilization().getUnits()) {
            System.out.println(unit + " type of " + unit.getTypeOfUnit() + " at " + unit.getLocation().getX() + " and " + unit.getLocation().getY());
        }

        drawTiles();
        // initializing panels
        initializeIconPanel();
        initializeResearchPanel();
        initializeSelectedUnitPanel();
        initializeNextTurnButton();
        initializeOthersPanel() ;
        initializeDiplomacyPanel() ;


        for (Civilization civilization : ClientDataController.gameController.getCivilizations()) {
            System.out.println(civilization.getUnits());
            System.out.println(civilization.getUnits().size());
        }

        // receiving data from server thread
        Thread receivingThread = new Thread(() -> {
            ServerResponse serverResponse;
            while (true){
                try {
                    serverResponse = Main.getClientHandler().getResponse();
                    System.out.println("server responses !");
                    System.out.println("it is " + serverResponse.getTypeOfResponse());
                    if (serverResponse.getTypeOfResponse().equals(TypeOfResponse.GAME_UNDER_ACTION)) {
                        ClientDataController.setGameController(serverResponse.getGameControllerDecoy());
                        ClientDataController.updateThisCivilization();
                        Platform.runLater(this::drawTiles);
                    } else if (serverResponse.getTypeOfResponse().equals(TypeOfResponse.GAME_ENDED)) {
                        // close this page
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        receivingThread.setDaemon(true);
        receivingThread.start();

        // updating info panel thread
        Thread infoPanelThread = new Thread(() -> {
            Runnable runnable = () -> {
                updateLabels();
                updateResearchPanel();
                updateSelectedUnitPanel();
                ClientDataController.updateThisCivilization();
            } ;
            while (true) {
                Platform.runLater(runnable);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                }
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

    }

    public void drawTiles (){
        firstX = game.getTranslateX();
        firstY = game.getTranslateY();
        Tile[][] map = ClientDataController.getGameController().getMap();
        for (Tile[] tiles : map) {
            for (Tile tile  : tiles) {
                game.getChildren().remove(tile);
                game.getChildren().add(tile);
                if (tile.getFeature() != null) {
                    game.getChildren().remove(tile.getFeature());
                    game.getChildren().add(tile.getFeature());
                }
                tile.updateUnitBackground(ClientDataController.getGameController());
                if (tile.getCivilUnit() != null) {
                    game.getChildren().remove(tile.getCivilUnit());
                    game.getChildren().add(tile.getCivilUnit());
                }

                if (tile.getTerrain().hasRuin()
                        && ClientDataController.getThisCivilization().getVisibleTerrains().contains(tile.getTerrain()))
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
        MapController.setMapCenter(ClientDataController.getThisCivilization().getUnits().get(0).getLocation() , game);

        game.getChildren().remove(iconPanel);
        game.getChildren().remove(researchPanel);
        game.getChildren().remove(selectedUnitPanel);
        game.getChildren().remove(nextTurnImageView);
        game.getChildren().remove(othersPanel);
        game.getChildren().remove(diplomacyPanelImageView);

        game.getChildren().add(iconPanel);
        game.getChildren().add(researchPanel);
        game.getChildren().add(selectedUnitPanel);
        game.getChildren().add(nextTurnImageView);
        game.getChildren().add(othersPanel) ;
        game.getChildren().add(diplomacyPanelImageView) ;

        if (ClientDataController.getThisCivilization().equals(ClientDataController.getGameController().getCurrentCivilization())) {
            if (!game.getChildren().contains(notYourTurnImageView))
                game.getChildren().add(notYourTurnImageView);
        } else game.getChildren().remove(notYourTurnImageView);
    }

    public void move(KeyEvent keyEvent) {
        System.out.println(keyEvent.getCode() + " pressed!");
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
        goldLabel.setText(ClientDataController.getThisCivilization().getGold()+"");
        scienceLabel.setText(ClientDataController.getThisCivilization().getScience()+"");
        happinessLabel.setText(ClientDataController.getThisCivilization().getHappiness()+"");
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
        othersPanel.setLayoutX(300+x);
        othersPanel.setLayoutY(0+y);
        diplomacyPanelImageView.setLayoutX(x+1000);
        diplomacyPanelImageView.setLayoutY(y+80);

            notYourTurnImageView.setFitWidth(200);
            notYourTurnImageView.setFitHeight(200);
            notYourTurnImageView.setLayoutX(x+400);
            notYourTurnImageView.setLayoutY(y+250);
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
                    Main.class.getResource("/game/images/technologies/" + ClientDataController.getThisCivilization().getCurrentResearch().getTypeOfTechnology().getName() + ".png").toExternalForm())) ;
            int predictedTurns = ClientDataController.getThisCivilization().getCurrentResearch().getTypeOfTechnology().getScienceNeeded() / ClientDataController.getGameController().getCurrentCivilization().getScience() ;
            double progress = ((double) ClientDataController.getThisCivilization().getCurrentResearch().getRemainingTurns()) / ((double) predictedTurns );
            progressBar.setProgress(progress);
            if (!researchPanel.getItems().contains(progressBar))
                researchPanel.getItems().add(progressBar) ;
            progressBar.setTooltip(new Tooltip(ClientDataController.getThisCivilization().getCurrentResearch().getTypeOfTechnology().getName()));
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
        // TODO : send a request to server to say that this game needs to go to next turn
        nextTurnImageView.setOnMouseClicked(mouseEvent -> {
            try {
                Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.NEXT_TURN , new GameControllerDecoy(ClientDataController.getGameController()) , ClientDataController.uuid));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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
        Civilization civilization = ClientDataController.getThisCivilization();
        ArrayList<City> cities = civilization.getCities();
        int numberOfTilesOwned = 0 ;
        for (City city : cities)
            numberOfTilesOwned += city.getTerrains().size();
        float progress = ((float) numberOfTilesOwned*100) / (float)(ClientDataController.getGameController().getMapHeight()*ClientDataController.getGameController().getMapWidth()) ;
        String demographicInfo = String.format("Total gold : %d\nTotal food : %d\nCities : %d\nTiles owned : %d\nRoad and Railroads : %d\nTotal progress : %f percent"
                , civilization.getGold() , civilization.getFood() , cities.size() , numberOfTilesOwned
                , civilization.getNumberOfRailroadsAndRoads() , progress);
        demographicLabel.setTooltip(new Tooltip(demographicInfo));
    }

    public void updateNotificationsStatus (){
        StringBuilder notificationInfo = new StringBuilder();
        ArrayList<Notification> currentCivilizationNotifications = NotificationController.getNotifications().get(ClientDataController.getThisCivilization()) ;

        if (currentCivilizationNotifications==null){
            if (notificationInfo.toString().length()==0)
                notificationLabel.setTooltip(new Tooltip("No notifications !"));
            return;
        }

        for (int i=currentCivilizationNotifications.size()-1 ; i>=0 ; i--) {
            Notification currentCivilizationNotification = currentCivilizationNotifications.get(i);
            notificationInfo.append(String.format("%s\n\t%3d turns ago at %s\n"
                    , currentCivilizationNotification.getMessage()
                    , ClientDataController.getGameController().getTurn() - currentCivilizationNotification.getTurnOfCreation()
                    , currentCivilizationNotification.getRealTimeCreated()));
        }
        notificationLabel.setTooltip(new Tooltip(notificationInfo.toString()));
    }

    public void updateMilitaryStatus (){
        StringBuilder militaryInfo = new StringBuilder();
        Civilization civilization = ClientDataController.getThisCivilization();
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
        Civilization civilization = ClientDataController.getThisCivilization();
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

}