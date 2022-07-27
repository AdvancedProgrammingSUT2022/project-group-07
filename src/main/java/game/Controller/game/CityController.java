package game.Controller.game;

import game.Controller.game.LogAndNotification.NotificationController;
import game.Model.*;
import game.Enum.TypeOfUnit;
import game.Enum.UnitStatus;
import game.View.components.Tile;
import game.View.controller.CityPanelController;
import javafx.scene.control.Alert;

public class CityController {

    public static boolean isCitySelected(){
        return SelectController.selectedCity != null;
    }

    public static String showTilesOwned(){
        StringBuilder out = new StringBuilder();
        if (!isCitySelected())
            return "select a city first" ;
        for (Terrain terrain : SelectController.selectedCity.getTerrains()) {
            String add = terrain.getTypeOfTerrain() + " : " + terrain.getLocation().getX() + " , " + terrain.getLocation().getY() ;
            out.append(add).append("\n");
        }
        return out.toString();
    }

    public static String addTileToCity (City city , Terrain terrain){
        city.addTerrain(terrain);
        NotificationController.logNewTileAddedToCity(terrain , city);
        return "tile " + terrain.getLocation().getX() + "," + terrain.getLocation().getY() + " added to city" ;
    }

    public static boolean isTileInCity(City city, Terrain terrain) {
        for (Terrain cityTerrain : city.getTerrains()) {
            if (cityTerrain.equals(terrain))
                return true;
        }
        return false;
    }

    public static String createUnit(Civilization currentCivilization, TypeOfUnit typeOfUnit, Location location,
                                    City city, GameController gameController) {
        int turn;
        if (city.getProduction() == 0)
            turn = 0;
        else
            turn = typeOfUnit.getCost() / city.getProduction();

        if (UnitController.anotherUnitIsInCenter(gameController, city)) {
//            return typeOfUnit + " wants to be created. Please move the unit which is in city center first!";
            CityPanelController.showError(typeOfUnit + " wants to be created. Please move the unit which is in city center first!");
            return null;
        }
        Unit newUnit = new Unit(typeOfUnit, UnitStatus.ACTIVE, location, typeOfUnit.getHp(), currentCivilization, turn);
        currentCivilization.addUnit(newUnit);
        city.getWantedUnits().remove(typeOfUnit);
        city.setProduction(city.getProduction() - typeOfUnit.getCost());
        NotificationController.logUnitCreated(typeOfUnit , currentCivilization);
        setUnitPic();

        Tile tile = TileController.getTileByTerrain(SelectController.selectedCity.getTerrains().get(0));
        tile.updateUnitBackground();
        return typeOfUnit + " has been created successfully in location ( "
                + location.getX() + " , " + location.getY() + " ) !";
    }

    private static void setUnitPic() {

    }

    public static String showUnemployedCitizens(City city) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Citizen citizen : city.getCitizens()) {
            if (citizen.getTerrain() == null) stringBuilder.append(citizen.getNumber() + "\n");
        }
        return stringBuilder.toString();
    }

    public static boolean isInCenterOfOwnCity(Terrain currentTerrain, Civilization currentCivilization, GameController gameController) {
        for (Civilization civilization : gameController.getCivilizations()) {
            for (City city : civilization.getCities()) {
                if (currentCivilization.equals(civilization)
                        && currentTerrain.equals(city.getTerrains().get(0)))
                    return true;
            }
        }
        return false;
    }

    public static City getCityByTerrain(Civilization civilization, Terrain currentTerrain) {
        for (City city : civilization.getCities()) {
            if (city.getTerrains().contains(currentTerrain))
                return city;
        }
        return null;
    }

    //////////////////////////////////////

    public static void checkCityCenter(Tile tile) {
        Terrain terrain = tile.getTerrain();
        City city = getCityByTerrain(GameController.getInstance().getCurrentCivilization(), terrain);
        assert city != null;
        if (terrain.equals(city.getTerrains().get(0))) {
            SelectController.selectedCity = city;
            showConfirm("City selected successfully!");
        }
    }

    public static void showConfirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.show();
    }
}
