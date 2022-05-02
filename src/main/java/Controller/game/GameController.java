package Controller.game;

import Controller.game.movement.TheShortestPath;
import Model.*;
import Enum.*;

import java.util.ArrayList;
import java.util.Random;

public class GameController {
    private static int mapWidth ;
    private static int mapHeight ;
    private static MapDimension mapDimension ;
    public static Terrain[][] map;
    private ArrayList<User> players = new ArrayList<>();
    private ArrayList<Civilization> civilizations ;
    private int time;
    private int turn;
    private Civilization currentCivilization ;

    public GameController() {
        this.turn = 0;
    }

    public static int getMapWidth(){
        return mapWidth;
    }
    public static int getMapHeight(){
        return mapHeight;
    }

    /**
     * a function to generate settler units locations while initializing civilizations
     * @param locations all used locations
     * @return random available location
     */
    private Location generateSettlerUnitLocation (ArrayList<Location> locations){
        Random rand = new Random();
        int x=rand.nextInt(mapWidth) , y=rand.nextInt(mapHeight) ;
        while (locations.contains(new Location(x,y))
                || map[y][x].getTypeOfTerrain()==TypeOfTerrain.OCEAN
                || map[y][x].getTypeOfTerrain()==TypeOfTerrain.MOUNTAIN){
            x = rand.nextInt(mapWidth) ;
            y = rand.nextInt(mapHeight) ;
        }
        return new Location(x,y);
    }

    /**
     * a function to initialize civilizations at the very beginning of the game
     * @param users list of logged-in users who wants to play
     */
    private void initializeCivilizations (ArrayList<User> users){
        // TODO: create some real civilization names
        civilizations = new ArrayList<Civilization>();
        for (int i = 0; i < users.size(); i++)
            civilizations.add(new Civilization("c" + Integer.toString(i + 1), users.get(i)));
        Random rand = new Random();
        ArrayList<Location> locations = new ArrayList<Location>();
        Location newUnitLocation ;
        for (Civilization civilization : civilizations) {
            newUnitLocation = generateSettlerUnitLocation(locations);
            locations.add(newUnitLocation);
            civilization.addUnit(new Unit(TypeOfUnit.SETTLER , UnitStatus.ACTIVE , newUnitLocation , TypeOfUnit.SETTLER.getHp() , civilization , 0));
        }
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
        map = new Terrain[mapHeight][mapWidth] ;
        map = MapController.createMap(mapWidth , mapHeight) ;
        initializeCivilizations(players);
        TheShortestPath.run();
        setCurrentCivilization(civilizations.get(0));
        MapController.setMapCenter(currentCivilization.getUnits().get(0).getLocation());
    }

    public void run(){

    }

    public ArrayList<Civilization> getCivilizations() {
        return this.civilizations;
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
