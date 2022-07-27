package game.View.controller;

import game.Controller.UserController;
import game.Controller.game.City.CreateUnit;
import game.Controller.game.CityController;
import game.Controller.game.GameController;
import game.Controller.game.TileController;
import game.Controller.game.citizen.CitizenController;
import game.Main;
import game.Model.Civilization;
import game.View.components.Tile;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import game.Model.Citizen;
import game.Model.Civilization;
import game.Model.User;
import game.View.components.Tile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

import static game.Controller.game.SelectController.selectedCity;

public class CityPanelController {

    // TODO initialize city info + city selection
    // lock citizen ---> [citizen number] [tileNumber]
    // unlock citizen ---> [citizen number]
    // change unit construction ---> [first unit] [second unit]

    public Button buyTileButton;
    public Text name;
    public Text food;
    public Text production;
    public Text citizens;
    public Text power;
    public Text hp;
    public Button lockButton;
    public Button removeButton;
    public TextField citizen;
    public Button changeButton;
    public TextField constructionText;
    public Button purchaseButton;
    public AnchorPane anchorPane;

    public static void openCityPanel() {
        if (selectedCity == null) CityPanelController.showError("Please select a city first!");
        else
            Main.loadNewStage("city", "cityPanel");
    }

    public void initialize() {
        name.setText(selectedCity.getName());
        food.setText(String.valueOf(selectedCity.getFood()));
        production.setText(String.valueOf(selectedCity.getProduction()));
        citizens.setText(String.valueOf(selectedCity.getCitizens().size()));
        hp.setText(String.valueOf(selectedCity.getHp()));
        power.setText(String.valueOf(selectedCity.getDefencePower()));
    }

    public void buyTile(ActionEvent actionEvent) {
        Civilization currentCiv = GameController.getInstance().getCurrentCivilization();

        if (!CityController.isCitySelected())
            showError("Please select a city!");
        else if (!currentCiv.getCities().contains(selectedCity)) {
            showError("Selected city is not yours!");
        }

        Tile[][] map = GameController.getInstance().getMap();
        int mapWidth = GameController.getInstance().getMapWidth();
        int mapHeight = GameController.getInstance().getMapHeight();

        ArrayList<Tile> availableTerrains = TileController.getAvailableTilesToBuy(selectedCity, map, mapWidth, mapHeight);
        for (Tile tile : availableTerrains) {
            TileController.changeStroke(tile, Color.RED, 2);
        }
        // TODO buy tile
    }

    public void close(MouseEvent mouseEvent) {
        Main.otherStage.close();
    }

    public void lockCitizen(ActionEvent actionEvent) {
        String[] string = citizen.getText().split(" ");
        CitizenController.lock(selectedCity, string);
    }

    public void removeCitizenWork(ActionEvent actionEvent) {
        CitizenController.unlock(selectedCity, citizen.getText());
    }

    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

    public static void showConfirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.show();
    }

    public void changeConstruction(ActionEvent actionEvent) {
        CreateUnit.changeUnitConstruction(constructionText.getText());
    }

    public void purchase(ActionEvent actionEvent) {
        Main.otherStage.close();
        Main.loadNewStage("purchase", "purchase");
    }

    public static void initializeCityPanel(ImageView cityPanelImageView) {
        cityPanelImageView.setFitWidth(80);
        cityPanelImageView.setFitHeight(80);
        Tooltip.install(cityPanelImageView , new Tooltip("City Panel"));
        cityPanelImageView.getStyleClass().add("cityPanelImageView") ;
        cityPanelImageView.setOnMouseClicked(mouseEvent -> CityPanelController.openCityPanel());
    }

    public void showUnemployedCitizens(ActionEvent actionEvent) {
        if (selectedCity.getCitizens().size() != 0
                && hasUnemployed()) {
            TableView<Citizen> citizenTableView = new TableView<>();
            TableColumn column = new TableColumn<>("number");

            citizenTableView.setEditable(true);
            citizenTableView.getColumns().add(column);

            column.setCellValueFactory(new PropertyValueFactory<>("number"));
            column.prefWidthProperty().bind(citizenTableView.widthProperty().multiply(0.5));
            column.setResizable(false);
            citizenTableView.setItems(getUnemployedCitizens());

            anchorPane.getChildren().add(citizenTableView);
            AnchorPane.setLeftAnchor(citizenTableView, 350.0);
            AnchorPane.setTopAnchor(citizenTableView, 100.0);
            anchorPane.setOnMouseClicked(mouseEvent -> citizenTableView.setVisible(false));
        }
        else if (selectedCity.getCitizens().size() == 0) {
            showError("This city doesn't have any citizen!");
        } else if (!hasUnemployed()) {
            showError("This city doesn't have any unemployed citizen!");
        }
    }

    private ObservableList<Citizen> getUnemployedCitizens() {
        ObservableList<Citizen> citizens = FXCollections.observableArrayList();
        for (Citizen citizen : selectedCity.getCitizens()) {
            if (citizen.getTerrain() != null)
                citizens.add(citizen);
        }
        return citizens;
    }

    private boolean hasUnemployed() {
        for (Citizen citizen : selectedCity.getCitizens()) {
            if (citizen.getTerrain() == null) return true;
        }
        return false;
    }
}
