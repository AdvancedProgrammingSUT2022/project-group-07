package Controller.game;

import Model.*;

import Enum.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.random.*;

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
        map = new Terrain[mapHeight][mapWidth] ;
        TypeOfTerrain[] typeOfTerrains = TypeOfTerrain.values() ;
        int typeOfTerrainRandNumber = new Random().nextInt(typeOfTerrains.length);
        int hasRivetRandNumber = new Random().nextInt(2) ;
        for (int y=0 ; y<mapHeight ; y++){
            for (int x=0 ; x<mapWidth ; x++){
                map[y][x] = new Terrain(typeOfTerrains[typeOfTerrainRandNumber] , null , true ,
                        null , new Location(x,y) , null) ;
                typeOfTerrainRandNumber = new Random().nextInt(typeOfTerrains.length) ;
//                TypeOfTerrain typeOfTerrain, TerrainFeatures terrainFeatures, boolean hasRiver,
//                ArrayList<Resources> resource, Location location, Improvement improvement
            }
        }
    }

    private void initializeCivilizations (ArrayList<User> users){
        // TODO: create list of civilizations
        this.civilizations = new ArrayList<Civilization>();
        for (int i = 0; i < users.size(); i++)
            civilizations.add(new Civilization("c"+Integer.toString(i+1) , users.get(i))) ;
    }

    public GameController(ArrayList<User> users) {
        initializeCivilizations(users);
        initializeMap();
    }

    public void run(){

    }

    public void printMap(){
        for (int y=0 ; y<mapHeight ; y++){
            for (int x=0 ; x<mapWidth ; x++)
                System.out.printf("%15s" , map[y][x].getTypeOfTerrain());
            System.out.println();
        }
    }

    public void move (String direction){

    }

    public void move (Location location){

    }

    public void showCity (String cityName){

    }



}
