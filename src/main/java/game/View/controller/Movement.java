package game.View.controller;

import game.Controller.game.*;
import game.Controller.game.units.Settler;
import game.Enum.UnitStatus;
import game.Model.City;
import game.Model.Civilization;
import game.Model.Terrain;
import game.Model.Unit;
import game.View.components.Tile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class Movement {
    public static boolean isMoving = false;

    public static void initializeMovements() {

    }

    public static void initializeActionButtons(ArrayList<Button> buttons, AnchorPane game) {
        Button foundCity = buttons.get(0);
        foundCity.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String result = Settler.foundCity(null, GameController.getInstance());
                if (result.equals("found new city!")) {

                    Civilization civilization = GameController.getInstance().currentCivilization;
                    City city = civilization.getCities().get(civilization.getCities().size() - 1);
                    Unit unit = SelectController.selectedUnit;
                    Tile tile = TileController.getTileByTerrain(TerrainController.getTerrainByLocation(unit.getLocation()));
                    tile.setCityCenter();
                    game.getChildren().add(tile.getCityCenter());
                    //TileController.getTileByTerrain(city.getTerrains().get(0)).
                }
            }
        });

        /////////////////////////////////////////////////

        Button move = buttons.get(1);
        move.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                isMoving = true;
            }
        });
        Button delete = buttons.get(2);
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                UnitController.deleteUnit(GameController.getInstance());
                Terrain terrain = TerrainController.getTerrainByLocation(SelectController.selectedUnit.getLocation());
                Tile tile = TileController.getTileByTerrain(terrain);
                tile.setUnit(null);
                tile.getCivilUnit().setRadius(0);
                SelectController.selectedUnit = null;
            }
        });
        Button sleep = buttons.get(3);
        sleep.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SelectController.selectedUnit.setUnitStatus(UnitStatus.SLEEP);
            }
        });
    }

    public static void move(Tile tile) {
        Unit unit = SelectController.selectedUnit;
        Terrain terrain = TerrainController.getTerrainByLocation(unit.getLocation());
        Tile tileFirst = TileController.getTileByTerrain(terrain);
        String result = UnitController.moveUnit(tile, GameController.getInstance(), unit);
        if (result.startsWith("Selected unit moved to")) {
            tileFirst.getCivilUnit().setRadius(0);
            tileFirst.getAttackUnit().setRadius(0);
            tileFirst.setCivil(null);
            tileFirst.setAttack(null);
            tileFirst.setUnit(null);
            for (Tile[] tiles : GameController.getInstance().getMap()) {
                for (Tile tile1 : tiles) {
                    tile1.updateUnitBackground();
                    TileController.findBackGround(tile1);
                }
            }
        }
        isMoving = false;
    }
}
