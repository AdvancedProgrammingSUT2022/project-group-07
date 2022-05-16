package Controller.game;

import Controller.game.LogAndNotification.NotificationController;
import Model.*;
import Enum.TypeOfUnit;
import Enum.UnitStatus;
import Enum.TypeOfTerrain;

import java.util.ArrayList;
import java.util.regex.Matcher;


import static Controller.game.SelectController.selectedCity;

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

    public static ArrayList<Terrain> getAvailableTilesToBuy(City selectedCity , final Terrain[][] map , int mapWidth , int mapHeight){
        ArrayList<Terrain> tileAvailable = new ArrayList<>();
        ArrayList<Terrain> allCivilizationOwnedTiles = new ArrayList<>();
        for (City city : selectedCity.getOwnership().getCities())
            allCivilizationOwnedTiles.addAll(city.getTerrains());

        for (Terrain terrain : selectedCity.getTerrains()) {
            ArrayList<Terrain> neighbours = CivilizationController.getNeighbourTerrainsByRadius1(terrain.getLocation() , map , mapWidth , mapHeight) ;
            for (Terrain neighbour : neighbours) {
                if (!tileAvailable.contains(neighbour)
                    && !allCivilizationOwnedTiles.contains(neighbour)
                    && neighbour.getTypeOfTerrain()!= TypeOfTerrain.OCEAN)
                    tileAvailable.add(neighbour);
            }
        }
        return tileAvailable ;
    }

    public static String showTilesAvailable(final Terrain[][] map , int mapWidth , int mapHeight){
        StringBuilder out = new StringBuilder("available tiles : \n");
        if (!isCitySelected())
            return "select a city first" ;
        ArrayList<Terrain> tileAvailable = getAvailableTilesToBuy(selectedCity , map , mapWidth , mapHeight) ;
        for (Terrain terrain : tileAvailable) {
            String add = terrain.getTypeOfTerrain() + " : "
                    + terrain.getLocation().getX() + " , "
                    + terrain.getLocation().getY() + " --> "
                    + terrain.getPrice();
            out.append(add).append("\n") ;
        }
        return out.toString();
    }

    public static String buyTile (Matcher matcher , final Terrain[][] map , int mapWidth , int mapHeight, Civilization civilization){
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        if (!isCitySelected())
            return "select a city first" ;
        if (!(x>=0 && x<mapWidth && y>=0 && y<mapHeight))
            return "invalid tile location" ;

        ArrayList<Terrain> availableTerrains = getAvailableTilesToBuy(selectedCity , map , mapWidth , mapHeight);
        for (Terrain availableTerrain : availableTerrains) {
            if (availableTerrain.getLocation().getY()==y
             && availableTerrain.getLocation().getX()==x){
                if (civilization.getGold() < availableTerrain.getPrice())
                    return "you don't have enough gold to buy this tile" ;
                else {
                    civilization.setGold(civilization.getGold() - availableTerrain.getPrice());
                    return addTileToCity(selectedCity , availableTerrain);
                }
            }
        }
        return "you can't buy this tile" ;
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

        if (UnitController.anotherUnitIsInCenter(gameController, city))
            return typeOfUnit + " wants to be created. Please move the unit which is in city center first!";

        Unit newUnit = new Unit(typeOfUnit, UnitStatus.ACTIVE, location, typeOfUnit.getHp(), currentCivilization, turn);
        currentCivilization.addUnit(newUnit);
        city.getWantedUnits().remove(typeOfUnit);
        city.setProduction(city.getProduction() - typeOfUnit.getCost());
        NotificationController.logUnitCreated(typeOfUnit , currentCivilization);
        return typeOfUnit + " has been created successfully in location ( "
                + location.getX() + " , " + location.getY() + " ) !";
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
}
