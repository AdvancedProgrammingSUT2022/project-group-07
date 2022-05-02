package Controller.game;

import Model.*;
import Enum.TypeOfUnit;
import Enum.Resources;
import Enum.TypeOfTechnology;
import Enum.UnitStatus;

import java.util.ArrayList;
import java.util.regex.Matcher;


import static Controller.game.SelectController.selectedCity;

public class CityController {

    public static boolean isCitySelected(){
        return selectedCity != null;
    }

    public static String showTilesOwned(){
        StringBuilder out = new StringBuilder();
        if (!isCitySelected())
            return "select a city first" ;
        for (Terrain terrain : selectedCity.getTerrains()) {
            String add = terrain.getTypeOfTerrain() + " : " + terrain.getLocation().getX() + " , " + terrain.getLocation().getY() ;
            out.append(add).append("\n");
        }
        return out.toString();
    }

    private static ArrayList<Terrain> getAvailableTilesToBuy(final Terrain[][] map , int mapWidth , int mapHeight){
        ArrayList<Terrain> tileAvailable = new ArrayList<>();
        ArrayList<Terrain> allCivilizationOwnedTiles = new ArrayList<>();
        for (City city : selectedCity.getOwnership().getCities())
            allCivilizationOwnedTiles.addAll(city.getTerrains());

        for (Terrain terrain : selectedCity.getTerrains()) {
            ArrayList<Terrain> neighbours = CivilizationController.getNeighbourTerrainsByRadius1(terrain.getLocation() , map , mapWidth , mapHeight) ;
            for (Terrain neighbour : neighbours) {
                if (!tileAvailable.contains(neighbour)
                    && !allCivilizationOwnedTiles.contains(neighbour))
                    tileAvailable.add(neighbour);
            }
        }
        return tileAvailable ;
    }

    public static String showTilesAvailable(final Terrain[][] map , int mapWidth , int mapHeight){
        StringBuilder out = new StringBuilder("available tiles : \n");
        if (!isCitySelected())
            return "select a city first" ;
        ArrayList<Terrain> tileAvailable = getAvailableTilesToBuy(map , mapWidth , mapHeight) ;
        for (Terrain terrain : tileAvailable) {
            String add = terrain.getTypeOfTerrain() + " : "
                    + terrain.getLocation().getX() + " , "
                    + terrain.getLocation().getY() + " --> "
                    + terrain.getPrice();
            out.append(add).append("\n") ;
        }
        return out.toString();
    }

    public static String buyTile (Matcher matcher , final Terrain[][] map , int mapWidth , int mapHeight){
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        if (!isCitySelected())
            return "select a city first" ;
        if (!(x>=0 && x<mapWidth && y>=0 && y<mapHeight))
            return "invalid tile location" ;

        ArrayList<Terrain> availableTerrains = getAvailableTilesToBuy(map , mapWidth , mapHeight);
        for (Terrain availableTerrain : availableTerrains) {
            if (availableTerrain.getLocation().getY()==y
             && availableTerrain.getLocation().getX()==x){
                if (selectedCity.getOwnership().getGold()<availableTerrain.getPrice())
                    return "you don't have enough gold to buy this tile" ;
                else {
                    selectedCity.getOwnership().setGold(
                            selectedCity.getOwnership().getGold()-availableTerrain.getPrice()
                    );
                    selectedCity.addTerrain(availableTerrain);
                    return "terrain added to city";
                }
            }
        }
        return "you can't buy this tile" ;
    }

    public static boolean isTileInCity(City city, Terrain terrain) {
        for (Terrain cityTerrain : city.getTerrains()) {
            if (cityTerrain.equals(terrain))
                return true;
        }
        return false;
    }

    public boolean isTileOwned(City city , Terrain terrain) {
        for (Terrain cityTerrain : city.getTerrains()) {
            if (cityTerrain.equals(terrain))
                return true;
        }
        return false;
    }

    public static String createUnit(Civilization currentCivilization, TypeOfUnit typeOfUnit, Location location, City city) {
        int turn = typeOfUnit.getCost() / city.getProduction();
        Unit newUnit = new Unit(typeOfUnit, UnitStatus.ACTIVE, location, typeOfUnit.getHp(), currentCivilization, turn);
        currentCivilization.addUnit(newUnit);
        city.getWantedUnits().remove(typeOfUnit);
        city.setProduction(-1 * typeOfUnit.getCost());
        return typeOfUnit + " has been added successfully in location ( "
                + location.getX() + " , " + location.getY() + " ) !";
    }
}
