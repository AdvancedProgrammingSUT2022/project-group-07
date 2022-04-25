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
    private  ArrayList<User> players = new ArrayList<>();
    private  ArrayList<Civilization> civilizations = new ArrayList<>();
    private int time;
    private int turn;
    private Civilization currentCivilization ;

    public GameController() {

    }

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
        for (int i = 0; i < users.size(); i++)
            civilizations.add(new Civilization("c"+Integer.toString(i+1) , users.get(i))) ;
    }

    public  ArrayList<User> getPlayers() {
        return this.players;
    }

    public void setPlayers(ArrayList<User> players) {
        this.players = players;
    }

    public static Terrain[][] getMap() {return map; }

    public void initialize() {
        mapDimension = MapDimension.STANDARD ;
        mapWidth = mapDimension.getX() ;
        mapHeight = mapDimension.getY() ;
        initializeCivilizations(players);
        map = new Terrain[mapHeight][mapWidth] ;
        map = MapController.createMap(mapWidth , mapHeight) ;
        //TheShortestPath.run();
    }

    public void run(){

    }

    public ArrayList<Civilization> getCivilizations() {
        return this.civilizations;
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


    public  Civilization getCurrentCivilization() {
        return this.currentCivilization;
    }

    public void setCurrentCivilization(Civilization currentCivilization) {
        this.currentCivilization = currentCivilization;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
}
