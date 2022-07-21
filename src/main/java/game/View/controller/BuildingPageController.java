package game.View.controller;

import game.Controller.game.SelectController;
import game.Enum.Building;
import game.Enum.TypeOfTechnology;
import game.Model.City;
import game.Model.Civilization;
import game.Model.UnderConstructionBuilding;
import game.Model.User;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class BuildingPageController implements Initializable {

    public AnchorPane anchorPane;
    public GridPane ownedBuildingsGridPane;
    public Label ownedLabel;
    public GridPane availableBuildingsGridPane;
    public Label availableLabel;

    int rowCount ;
    int colCount ;

    City city ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        city = SelectController.selectedCity ;
        city = new City("sample city" , new Civilization("sample civilization" , new User()));

        anchorPane.getStyleClass().add("anchorPane") ;

        ownedLabel.setText("Owned buildings of city " + city.getName());
        availableLabel.setText("Available buildings for this city");
        ownedLabel.getStyleClass().add("label");
        availableLabel.getStyleClass().add("label");

        rowCount = ownedBuildingsGridPane.getRowCount() ;
        colCount = ownedBuildingsGridPane.getColumnCount() ;

        ownedBuildingsGridPane.setAlignment(Pos. CENTER);
        availableBuildingsGridPane.setAlignment(Pos.CENTER);

        Thread updater = new Thread(() -> {
            Runnable runnable = () -> {
                initializeOwnedBuildings();
                initializeAvailableBuildings();
            };
            while (true) {
                Platform.runLater(runnable);
                try {Thread.sleep(5000);}
                catch (InterruptedException ignored){}
            }
        }) ;
        updater.setDaemon(true);
        updater.start();
    }

    private void initializeOwnedBuildings() {
        ArrayList<Building> buildings = city.getBuildings() ;
        int col = 0 ;
        int row = 0 ;
        for (Building building : buildings) {
            anchorPane.getChildren().remove(getNodeFromGridPane(ownedBuildingsGridPane , col , row)) ;
            ImageView imageView = getImageView(building);
            ownedBuildingsGridPane.add(imageView , col , row);
            GridPane.setHalignment(imageView, HPos.CENTER);
            GridPane.setValignment(imageView, VPos.CENTER);

            col += 1 ;
            col %= colCount ;
            row = col==0 ? row + 1 : row ;
            row %= rowCount ;
        }

    }

    private void initializeAvailableBuildings (){
        ArrayList<Building> buildings = city.getBuildings() ;
        int col = 0 ;
        int row = 0 ;
        for (Building building : Building.values()) {
            // check if we already have this building !
            if (buildings.contains(building))
                continue;

            // check if we have tech needed for this building
            TypeOfTechnology techNeeded = building.getTechnologyRequired() ;
            if (techNeeded != null && !city.getOwnership().getGainedTypeOfTechnologies().contains(techNeeded))
                continue;

            ImageView imageView = getImageView(building) ;
            addActionToAvailableBuildings(imageView , building);

            availableBuildingsGridPane.getChildren().remove(getNodeFromGridPane(availableBuildingsGridPane , col , row)) ;

            availableBuildingsGridPane.add(imageView , col , row);
            GridPane.setHalignment(imageView, HPos.CENTER);
            GridPane.setValignment(imageView, VPos.CENTER);

            col += 1 ;
            col %= colCount ;
            row = col==0 ? row + 1 : row ;
            row %= rowCount ;
        }
    }

    public ImageView getImageView (Building building){
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(getClass().getResource("/game/images/buildings/" + building + ".png").toExternalForm()));
        imageView.setFitHeight(45);
        imageView.setFitWidth(45);

        String techNeeded = building.getTechnologyRequired()==null ? "Nothing " : building.getTechnologyRequired().getName().replace("_" , " ") ;
        String tip = String.format("%s\nTechnology required : %s\ncost to build : %d\nmaintenance : %d", building , techNeeded , building.getCost() , building.getMaintenance()) ;
        Tooltip tooltip = new Tooltip(tip);
        tooltip.setStyle("-fx-font-size: 14; -fx-text-fill: white; -fx-text-alignment: left;");
        Tooltip.install(imageView , tooltip);

        imageView.getStyleClass().add("imageView") ;
        return imageView ;
    }

    public void addActionToAvailableBuildings (ImageView imageView , Building building){
        imageView.setOnMouseClicked(mouseEvent -> {
            switch (mouseEvent.getButton()){
                case PRIMARY -> handleCreatingBuilding(building);
                case SECONDARY -> handleBuyingBuilding(building);
            }
        });
    }

    private ImageView getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return (ImageView) node;
            }
        }
        return null;
    }

    public void exitPage(MouseEvent mouseEvent) {
        Node  source = (Node)  mouseEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private void handleBuyingBuilding (Building building){
        if (city.getOwnership().getGold() < building.getCost()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You don't have enough gold to purchase this building !");
            alert.show();
        }
        else {
            city.addBuilding(building);
            city.getOwnership().setGold(city.getOwnership().getGold()-building.getCost());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Building " + building.toString().replace("_" , " ")+ " added to city " + city.getName());
            alert.show();
        }
    }

    private void handleCreatingBuilding (Building building){
        UnderConstructionBuilding underConstructionBuilding = city.getUnderConstructionBuilding() ;

        if (underConstructionBuilding != null){
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Changing construction");
            confirmation.setHeaderText(underConstructionBuilding.getBuilding().toString().replace("_" , " ") + " is already under construction ! Are you sure you want to change construction ?");
            confirmation.setContentText("Please choose :");

            Optional<ButtonType> option = confirmation.showAndWait();
            if (option.get() == null) {
                return;
            }
            else if (option.get() == ButtonType.OK) {
                int turns = Math.min(building.getCost() / city.getCitizens().size()+city.getTerrains().size() , 15) ;
                city.setUnderConstructionBuilding(new UnderConstructionBuilding(building , turns));
                Alert done = new Alert(Alert.AlertType.INFORMATION);
                done.setContentText("Building replaced !");
                done.show();
            }
            else if (option.get() == ButtonType.CANCEL) {
                Alert done = new Alert(Alert.AlertType.INFORMATION);
                done.setContentText("Cancelled");
                done.show();
            }

        }

        else {
            int turns = Math.min(building.getCost() / city.getCitizens().size()+city.getTerrains().size() , 15) ;
            city.setUnderConstructionBuilding(new UnderConstructionBuilding(building , turns));
            Alert done = new Alert(Alert.AlertType.INFORMATION);
            done.setContentText("Building added !");
            done.show();
        }
    }


}
