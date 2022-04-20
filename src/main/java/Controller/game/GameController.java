package Controller.game;

import Model.Civilization;
import Model.Location;
import Model.Terrain;
import Model.User;

import java.util.ArrayList;

public class GameController {
    private static final int mapWidth = 20 ;
    private static final int mapHeight = 18 ;
    private Terrain[][] map;
    private ArrayList<Civilization> civilizations;
    private int time;
    private int turn;
    private Civilization currentCivilization ;

    public int getMapWidth(){
        return mapWidth;
    }
    public int getMapHeight(){
        return mapHeight;
    }

    private void initializeMap (){
        // TODO: iterate on array and create our map
    }

    private void initializeCivilizations (ArrayList<User> users){
        // TODO: create list of civilizations
        this.civilizations = new ArrayList<Civilization>();
        for (int i = 0; i < users.size(); i++)
            civilizations.add(new Civilization("c"+Integer.toString(i+1) , users.get(i))) ;
    }

    public GameController(ArrayList<User> users) {
        initializeCivilizations(users);
        //initializeMap();
    }

    public void run(){

    }

    public String printMap(){

    }

    public void move (String direction){

    }

    public void move (Location location){

    }

    public void showCity (String cityName){

    }



}
