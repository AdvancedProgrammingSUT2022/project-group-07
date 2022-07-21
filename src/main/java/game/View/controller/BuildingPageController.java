package game.View.controller;

import game.Controller.game.SelectController;
import game.Enum.Building;
import game.Model.City;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
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
        city = SelectController.selectedCity ;

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
            if (buildings.contains(building))
                continue;

            ImageView imageView = getImageView(building) ;
            addActionToAvailableBuildings(imageView , building);

            anchorPane.getChildren().remove(getNodeFromGridPane(availableBuildingsGridPane , col , row)) ;

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

}
