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

    /**
     * a function to generate type of terrain foreach cell of map based on it's area
     * @param location location of terrain
     * @return type of terrain thath is valid for this location
     */
    private TypeOfTerrain generateTypeOfTerrain (final Location location){
        int x = location.getX();
        int y = location.getY();

        Random rand = new Random();
        int randNum = rand.nextInt(typeOfTerrains.length);
        TypeOfTerrain out = typeOfTerrains[randNum];

        ArrayList<TypeOfTerrain> areaTypeOfTerrains = getAreaTypeOfTerrains(location);
        if (areaTypeOfTerrains.isEmpty() || areaTypeOfTerrains.contains(out))
            return out;

        boolean shouldChangeOut = true;
        while (shouldChangeOut){
            out = typeOfTerrains[rand.nextInt(typeOfTerrains.length)];

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

    /**
     * a function to generate terrain features based on it's type of terrain
     * @param typeOfTerrain Type of terrain
     * @return type of feature
     */
    private TerrainFeatures generateTypeOfTerrainFeature (final TypeOfTerrain typeOfTerrain){
        Random rand = new Random();
        TerrainFeatures[] possibleTerrainFeatures = typeOfTerrain.getPossibleFeatures();
        if (possibleTerrainFeatures==null)
            return null;
        int randNum = rand.nextInt((possibleTerrainFeatures.length)+2);
        if (randNum<possibleTerrainFeatures.length)
            return possibleTerrainFeatures[randNum];
        else
            return null;
    }

    /**
     * a function to generate random resources based on it's type of terrain and also terrain feature (if valid)
     * @param typeOfTerrain type of the terrain
     * @return an array list of resources int this terrain
     */
    private ArrayList<Resources> generateResources(final TypeOfTerrain typeOfTerrain , final TerrainFeatures terrainFeatures){
        Random rand = new Random();
        ArrayList<Resources> out = new ArrayList<Resources>();
        Resources[] possibleResources = typeOfTerrain.getPossibleResources();
        if (possibleResources!=null) {
            for (int i = 0; i < possibleResources.length; i++) {
                if (rand.nextInt()%4==0)
                    out.add(possibleResources[i]);
            }
        }
        if (terrainFeatures!=null && terrainFeatures.getPossibleResources()!=null){
            Resources[] possibleTerrainFeatrueResources = terrainFeatures.getPossibleResources();
            for (int i = 0 ; i < possibleTerrainFeatrueResources.length ; i++){
                if (rand.nextInt()%4==0)
                    out.add(possibleTerrainFeatrueResources[i]);
            }
        }
        return out;
    }

    /**
     * a function to initialize starting map
     */
    private void initializeMap (){
        // TODO: iterate on array and create our map
        map = new Terrain[mapHeight][mapWidth] ;
        Random rand = new Random();
        TypeOfTerrain typeOfTerrainUsed ;
        TerrainFeatures typeOfTerrainFeatureUsed ;
        ArrayList<Resources> resources;

        for (int y=0 ; y<mapHeight ; y++){
            for (int x=0 ; x<mapWidth ; x++){
                typeOfTerrainUsed = generateTypeOfTerrain(new Location(x,y)) ;
                typeOfTerrainFeatureUsed = generateTypeOfTerrainFeature(typeOfTerrainUsed);
                resources = generateResources(typeOfTerrainUsed , typeOfTerrainFeatureUsed);
                map[y][x] = new Terrain(typeOfTerrainUsed , typeOfTerrainFeatureUsed, true ,
                                        resources , new Location(x,y) , null) ;
            }
        }
    }

    /**
     * a function to initialize civilizations at the very beginning of the game
     * @param users list of logged in users who wants to play
     */
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
        String out ;
        for (int y=0 ; y<mapHeight ; y++){
            if (y%2==1)
                System.out.print("\t");
            for (int x=0 ; x<mapWidth ; x++){
                out = map[y][x].getTypeOfTerrain().toString().substring(0,3) + "+" + map[y][x].getTerrainFeatures() + map[y][x].getResources() ;
                System.out.print(out + "\t");
            }
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
