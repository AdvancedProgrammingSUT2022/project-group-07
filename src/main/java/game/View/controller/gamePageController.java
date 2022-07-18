package game.View.controller;

import game.Controller.game.GameController;
import game.Controller.game.SelectController;
import game.Enum.TypeOfTechnology;
import game.Main;
import game.Model.Technology;
import game.Model.Terrain;
import game.Model.Unit;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;


public class gamePageController implements Initializable {

    public AnchorPane game;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Terrain[][] terrains = GameController.getInstance().getMap();
        for (Terrain[] terrain : terrains) {
            for (Terrain terrain1 : terrain) {
                game.getChildren().add(terrain1.getTile());
            }
        }

        initializeIconPanel();
        initializeResearchPanel();
        initializeSelectedUnitPanel();

        game.getChildren().add(iconPanel);
        game.getChildren().add(researchPanel);
        game.getChildren().add(selectedUnitPanel);
    }

    public void initializeIconPanel (){
        initializeIcons();
        updateLabels();
        initializeTooltips();
        iconPanel.setPrefWidth(400);
        iconPanel.setPrefHeight(40);
        iconPanel.getItems().addAll(goldIcon , goldLabel , scienceIcon , scienceLabel , happinessIcon , happinessLabel) ;
        iconPanel.setStyle("-fx-opacity: 0.70 ; -fx-background-color: black");
        iconPanel.setLayoutX(0);
        iconPanel.setLayoutY(0);
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

    private void initializeResearchPanel() {
        researchPanel.setOrientation(Orientation.VERTICAL);
        researchPanel.setLayoutX(0);
        researchPanel.setLayoutY(40);
        researchPanel.setPrefHeight(150);
        researchPanel.setPrefWidth(150);
        researchPanel.setStyle("-fx-background-color: rgba(79,79,79,0.30) ; -fx-alignment: center");
        researchPanel.getItems().addAll(currentResearchImageView, progressBar);
        updateResearchPanel();
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
        selectedUnitPanel.setLayoutX(0);
        selectedUnitPanel.setLayoutY(600);
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

}
