package Controller.game;

import Model.*;

import Enum.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.random.*;

public class GameController {
    private static final int mapWidth = 10 ;
    private static final int mapHeight = 6 ;
    private Terrain[][] map;
    private ArrayList<Civilization> civilizations;
    private int time;
    private int turn;
    private Civilization currentCivilization ;

    // these are not really important for GameController fields
    private static TypeOfTerrain[] typeOfTerrains = TypeOfTerrain.values() ;
    private static TerrainFeatures[] terrainFeatures = TerrainFeatures.values();


    public int getMapWidth(){
        return mapWidth;
    }
    public int getMapHeight(){
        return mapHeight;
    }


    private Terrain getPreviousTerrain (Location location){
        int x = location.getX();
        int y = location.getY();
        if (x-1<0)
            return null;
        else
            return map[y][x-1];
    }

    private TypeOfTerrain generateTypeOfTerrain (Location location){
        Random rand = new Random();
        int randNum = rand.nextInt(typeOfTerrains.length);

        TypeOfTerrain out = typeOfTerrains[randNum];
        Terrain previousTerrain = getPreviousTerrain(location);

        if (previousTerrain==null || previousTerrain.getTypeOfTerrain()==out)
            return out;

        while (true){
            TypeOfTerrain prev = previousTerrain.getTypeOfTerrain();
            if ((prev==TypeOfTerrain.DESERT && out==TypeOfTerrain.OCEAN) ||
                (prev==TypeOfTerrain.OCEAN && out==TypeOfTerrain.DESERT)  )
                out = typeOfTerrains[rand.nextInt(typeOfTerrains.length)];
            else if ((prev==TypeOfTerrain.DESERT && (out==TypeOfTerrain.SNOW||out==TypeOfTerrain.TUNDRA)) ||
                     (out==TypeOfTerrain.DESERT && (prev==TypeOfTerrain.SNOW||prev==TypeOfTerrain.TUNDRA)) )
                out = typeOfTerrains[rand.nextInt(typeOfTerrains.length)];

            else
                return out;
        }

    }

    private void initializeMap (){
        // TODO: iterate on array and create our map
        map = new Terrain[mapHeight][mapWidth] ;

        Random rand = new Random();

        TypeOfTerrain typeOfTerrainUsed ;

        for (int y=0 ; y<mapHeight ; y++){
            for (int x=0 ; x<mapWidth ; x++){
                typeOfTerrainUsed = generateTypeOfTerrain(new Location(x,y)) ;

                map[y][x] = new Terrain(typeOfTerrainUsed , null
                        , true ,
                        null , new Location(x,y) , null) ;
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
                System.out.printf("|(%d,%d) : %10s+%12s|\t" ,map[y][x].getLocation().getX() , map[y][x].getLocation().getY(), map[y][x].getTypeOfTerrain() , map[y][x].getTerrainFeatures());
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
