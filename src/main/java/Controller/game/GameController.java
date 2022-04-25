package Controller.game;

import Model.*;
import Enum.*;

import java.util.ArrayList;
import java.util.Random;

public class GameController {
    private static int mapWidth ;
    private static int mapHeight ;
    private static MapDimension mapDimension ;
    public static Terrain[][] map;
    private static ArrayList<User> players = new ArrayList<>();
    private static ArrayList<Civilization> civilizations = new ArrayList<>();
    private int time;
    private int turn;
    private static Civilization currentCivilization ;

    public static int getMapWidth(){
        return mapWidth;
    }
    public static int getMapHeight(){
        return mapHeight;
    }


    /**
     * a function to initialize civilizations at the very beginning of the game
     * @param users list of logged-in users who wants to play
     */
    private void initializeCivilizations (ArrayList<User> users){
        // TODO: create some real civilization names
        GameController.civilizations = new ArrayList<Civilization>();
        for (int i = 0; i < users.size(); i++)
            civilizations.add(new Civilization("c"+Integer.toString(i+1) , users.get(i))) ;
    }

    public static ArrayList<User> getPlayers() {
        return players;
    }

    public static void setPlayers(ArrayList<User> players) {
        GameController.players = players;
    }

    public static Terrain[][] getMap() {return map; }

    public GameController(ArrayList<User> users) {
        mapDimension = MapDimension.STANDARD ;
        mapWidth = mapDimension.getX() ;
        mapHeight = mapDimension.getY() ;
        initializeCivilizations(users);
        map = new Terrain[mapHeight][mapWidth] ;
        map = MapController.createMap(mapWidth , mapHeight) ;
//        TheShortestPath.run();
    }

    public void run(){

    }

    public static ArrayList<Civilization> getCivilizations() {
        return civilizations;
    }

    public void printMap(){
        new MapFrame(50 , 80 , 23 , mapWidth , mapHeight , map) ;
    }

    public void move (String direction){

    }

    public void move (Location location){

    }

    public void showCity (String cityName){

    }


    public static Civilization getCurrentCivilization() {
        return currentCivilization;
    }

    public void setCurrentCivilization(Civilization currentCivilization) {
        GameController.currentCivilization = currentCivilization;
    }
}
