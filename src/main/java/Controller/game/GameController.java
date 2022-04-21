package Controller.game;

import Model.*;

import Enum.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.random.*;

public class GameController {
    private static final int mapWidth = 10 ;
    private static final int mapHeight = 12 ;
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

    private ArrayList<TypeOfTerrain> getAreaTypeOfTerrains (Location location){
        int x = location.getX();
        int y = location.getY();
        ArrayList<TypeOfTerrain> out = new ArrayList<TypeOfTerrain>() ;
        int upperRow = Math.max(0 , y-1);
        int leftCol = Math.max(0 , x-1);
        for (int row = upperRow ; row<=y ; row++){
            for (int col = leftCol ; col<=x ; col++ ){
                if (row!=y || col!=x)
                    out.add(map[row][col].getTypeOfTerrain());
            }
        }
        return out;
    }


    private TypeOfTerrain generateTypeOfTerrain (final Location location){
        int x = location.getX();
        int y = location.getY();

        Random rand = new Random();
        int randNum = rand.nextInt(typeOfTerrains.length);
        TypeOfTerrain out = typeOfTerrains[randNum];

        ArrayList<TypeOfTerrain> areaTypeOfTerrains = getAreaTypeOfTerrains(location);
        System.out.println(areaTypeOfTerrains.stream().toList());
        if (areaTypeOfTerrains.isEmpty() || areaTypeOfTerrains.contains(out))
            return out;

        boolean shouldChangeOut = true;
        while (shouldChangeOut){
            out = typeOfTerrains[rand.nextInt(typeOfTerrains.length)];
            System.out.println("has choose " + out);

            if (out==TypeOfTerrain.OCEAN && (x>3 && x<mapWidth-3) && (y>3 && y<mapHeight-3))
                shouldChangeOut = true;
            else if (out==TypeOfTerrain.OCEAN && areaTypeOfTerrains.contains(TypeOfTerrain.DESERT))
                shouldChangeOut = true;

            else if (out==TypeOfTerrain.DESERT && areaTypeOfTerrains.contains(TypeOfTerrain.OCEAN) )
                shouldChangeOut = true;
            else if (out==TypeOfTerrain.DESERT && areaTypeOfTerrains.contains(TypeOfTerrain.SNOW))
                shouldChangeOut = true;
            else if (out==TypeOfTerrain.DESERT && areaTypeOfTerrains.contains(TypeOfTerrain.TUNDRA))
                shouldChangeOut = true;
            else if (out==TypeOfTerrain.DESERT && areaTypeOfTerrains.contains(TypeOfTerrain.GRASSLAND))
                shouldChangeOut = true;

            else if (out==TypeOfTerrain.TUNDRA && areaTypeOfTerrains.contains(TypeOfTerrain.DESERT))
                shouldChangeOut = true;

            else if (out==TypeOfTerrain.SNOW && areaTypeOfTerrains.contains(TypeOfTerrain.DESERT))
                shouldChangeOut = true;

            else
                shouldChangeOut = false;
        }
        return out;
    }

    private void initializeMap (){
        // TODO: iterate on array and create our map
        map = new Terrain[mapHeight][mapWidth] ;

        Random rand = new Random();

        TypeOfTerrain typeOfTerrainUsed ;

        for (int y=0 ; y<mapHeight ; y++){
            for (int x=0 ; x<mapWidth ; x++){
                typeOfTerrainUsed = generateTypeOfTerrain(new Location(x,y)) ;
                System.out.println("added " + typeOfTerrainUsed + " to location ("+x+","+y+")");
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
            if (y%2==1)
                System.out.print("\t");
            for (int x=0 ; x<mapWidth ; x++)
                System.out.printf("%10s" , map[y][x].getTypeOfTerrain());
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
