package Controller.game;

import Model.*;
import Enum.*;

import java.util.ArrayList;
import java.util.Random;

public class GameController {
    // TODO static
    private static int mapWidth ;
    private static int mapHeight ;
    private MapDimension mapDimension ;
    // TODO static
    public static Terrain[][] map;
    private static ArrayList<User> players = new ArrayList<>();
    private static ArrayList<Civilization> civilizations = new ArrayList<>();
    private int time;
    private int turn;
    // TODO show in UML
    private static Civilization currentCivilization ;

    // these are not really important for GameController fields
    private static TypeOfTerrain[] typeOfTerrains = TypeOfTerrain.values() ;
    private static TerrainFeatures[] terrainFeatures = TerrainFeatures.values();

    // TODO static
    public static int getMapWidth(){
        return mapWidth;
    }
    public static int getMapHeight(){
        return mapHeight;
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


    public static ArrayList<User> getPlayers() {
        return players;
    }

    public static void setPlayers(ArrayList<User> players) {
        GameController.players = players;
    }

    public GameController(ArrayList<User> users) {
        mapDimension = MapDimension.STANDARD ;
        mapWidth = mapDimension.getX() ;
        mapHeight = mapDimension.getY() ;
        initializeCivilizations(users);
        initializeMap();
        TheShortestPath.run();
    }

    public void run(){

    }

    public static ArrayList<Civilization> getCivilizations() {
        return civilizations;
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


    public static Civilization getCurrentCivilization() {
        return currentCivilization;
    }

    public void setCurrentCivilization(Civilization currentCivilization) {
        GameController.currentCivilization = currentCivilization;
    }
}
