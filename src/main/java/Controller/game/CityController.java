package Controller.game;

import Model.City;
import Model.Civilization;
import Model.Terrain;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;

public class CityController {
    private static City selectedCity = null ;

    public static String selectCityByLocation(Matcher matcher , final ArrayList<Civilization> civilizations) {
        int x = Integer.parseInt(matcher.group("X")) ;
        int y = Integer.parseInt(matcher.group("Y")) ;
        int width = GameController.getMapWidth() ;
        int height = GameController.getMapHeight() ;
        if (!(x>=0 && x<width && y>=0 && y<height))
            return "invalid location" ;
        for (Civilization civilization : civilizations) {
            for (City city : civilization.getCities()) {
                if (city.getTerrains().get(0).getLocation().getX()==x &&
                        city.getTerrains().get(0).getLocation().getY()==y){
                    selectedCity = city ;
                    return "city selected successfully" ;
                }
            }
        }
        return "no city in this location" ;
    }

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
                selectedCity.addTerrain(availableTerrain);
                return "terrain added to city : although we haven't checked your gold yet ayoub" ;
            }
        }
        return "you can't buy this tile" ;
    }

}
