package Controller.game;

import Model.*;
import Enum.Resources ;
import Enum.TypeOfTerrain ;
import Enum.TerrainFeatures ;
import Enum.MapDimension;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;

public class MapController {
    private static Terrain[][] map ;
    private static final TypeOfTerrain[] typeOfTerrains = TypeOfTerrain.values() ;
    private static int mapHeight ;
    private static int mapWidth ;
    private static MapFrame frame = null ;
    private static Location mapCenter = null ;

    private static ArrayList<TypeOfTerrain> getAreaTypeOfTerrains (Location location){
        int x = location.getX();
        int y = location.getY();
        ArrayList<TypeOfTerrain> out = new ArrayList<>() ;
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

    private static boolean areAllNeighboursOcean(ArrayList<TypeOfTerrain> areaTypeOfTerrains){
        for (TypeOfTerrain areaTypeOfTerrain : areaTypeOfTerrains) {
            if (areaTypeOfTerrain!=TypeOfTerrain.OCEAN)
                return false;
        }
        return true;
    }
    /**
     * a function to generate type of terrain foreach cell of map based on it's area
     * @param location location of terrain
     * @return type of terrain that is valid for this location
     */
    private static TypeOfTerrain generateTypeOfTerrain (final Location location){
        int x = location.getX();
        int y = location.getY();
        Random rand = new Random();
        int randNum = rand.nextInt(typeOfTerrains.length);
        TypeOfTerrain out = typeOfTerrains[randNum];

        ArrayList<TypeOfTerrain> areaTypeOfTerrains = getAreaTypeOfTerrains(location);

        if (areAllNeighboursOcean(areaTypeOfTerrains) && (x<3 || x>mapWidth-3) && (y<3 || y>mapHeight-3))
            return TypeOfTerrain.OCEAN ;
        if (areaTypeOfTerrains.isEmpty() || areaTypeOfTerrains.contains(out))
            return out;

        boolean shouldChangeOut = true;
        while (shouldChangeOut){
            out = typeOfTerrains[rand.nextInt(typeOfTerrains.length)];
            if (out== TypeOfTerrain.OCEAN && (x>3 && x<mapWidth-3) && (y>3 && y<mapHeight-3))
                shouldChangeOut = true;
            else if (out== TypeOfTerrain.OCEAN && areaTypeOfTerrains.contains(TypeOfTerrain.DESERT))
                shouldChangeOut = true;

            else if (out== TypeOfTerrain.DESERT && areaTypeOfTerrains.contains(TypeOfTerrain.OCEAN) )
                shouldChangeOut = true;
            else if (out== TypeOfTerrain.DESERT && areaTypeOfTerrains.contains(TypeOfTerrain.SNOW))
                shouldChangeOut = true;
            else if (out== TypeOfTerrain.DESERT && areaTypeOfTerrains.contains(TypeOfTerrain.TUNDRA))
                shouldChangeOut = true;
            else if (out== TypeOfTerrain.DESERT && areaTypeOfTerrains.contains(TypeOfTerrain.GRASSLAND))
                shouldChangeOut = true;

            else if (out== TypeOfTerrain.SNOW && areaTypeOfTerrains.contains(TypeOfTerrain.DESERT))
                shouldChangeOut = true;
            else if (out== TypeOfTerrain.SNOW && (x>5 && x<mapWidth-5) && (y>5 && y<mapHeight-5))
                shouldChangeOut = true;
            else if (out== TypeOfTerrain.TUNDRA && areaTypeOfTerrains.contains(TypeOfTerrain.DESERT))
                shouldChangeOut = true;

            else shouldChangeOut = out == TypeOfTerrain.SNOW && areaTypeOfTerrains.contains(TypeOfTerrain.DESERT);
        }
        return out;
    }

    /**
     * a function to generate terrain features based on it's type of terrain
     * @param typeOfTerrain Type of terrain
     * @return type of feature
     */
    private static TerrainFeatures generateTypeOfTerrainFeature (final TypeOfTerrain typeOfTerrain){
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
    private static ArrayList<Resources> generateResources(final TypeOfTerrain typeOfTerrain , final TerrainFeatures terrainFeatures){
        Random rand = new Random();
        ArrayList<Resources> out = new ArrayList<>();
        Resources[] possibleResources = typeOfTerrain.getPossibleResources();
        if (possibleResources!=null) {
            for (Resources possibleResource : possibleResources) {
                if (rand.nextInt()%4==0)
                    out.add(possibleResource) ;
            }
        }
        if (terrainFeatures!=null && terrainFeatures.getPossibleResources()!=null){
            Resources[] possibleTerrainFeatureResources = terrainFeatures.getPossibleResources();
            for (Resources possibleTerrainFeatureResource : possibleTerrainFeatureResources) {
                if (rand.nextInt() % 4 == 0)
                    out.add(possibleTerrainFeatureResource);
            }
        }
        return out;
    }

    /**
     * a function to initialize starting map
     * @return map initialized map
     */
    public static Terrain[][] createMap (int mapWidth , int mapHeight){
        map = new Terrain[mapHeight][mapWidth] ;
        MapController.mapHeight = mapHeight ;
        MapController.mapWidth = mapWidth ;
        for (int y=0 ; y<mapHeight ; y++){
            for (int x=0 ; x<mapWidth ; x++){
                TypeOfTerrain typeOfTerrainUsed = generateTypeOfTerrain(new Location(x,y)) ;
                TerrainFeatures typeOfTerrainFeatureUsed = generateTypeOfTerrainFeature(typeOfTerrainUsed);
                ArrayList<Resources> resources = generateResources(typeOfTerrainUsed , typeOfTerrainFeatureUsed);
                map[y][x] = new Terrain(typeOfTerrainUsed , typeOfTerrainFeatureUsed, true , resources , new Location(x,y) , null) ;
            }
        }
        return map ;
    }

    public static void setMapCenter(Location location){
        if (mapCenter==null)
            mapCenter = new Location(location.getX() , location.getY()) ;
        else {
            mapCenter.setX(location.getX());
            mapCenter.setY(location.getY());
        }
    }

    public static String moveMap (Matcher matcher){
        String direction = matcher.group("direction") ;
        int upperRow = Math.max(mapCenter.getY()-1 , 0) ;
        int lowerRow = Math.min(mapCenter.getY()+1 , mapHeight-1) ;
        int leftCol = Math.max(mapCenter.getX()-1 , 0) ;
        int rightCol = Math.min(mapCenter.getX()+1 , mapWidth-1) ;
        switch (direction) {
            case "R" -> mapCenter.setX(rightCol);
            case "L" -> mapCenter.setX(leftCol);
            case "U" -> mapCenter.setY(upperRow);
            case "D" -> mapCenter.setY(lowerRow);
        }
        return "moved map to direction " + direction ;
    }

    public static void printMap (Terrain[][] map , Civilization currentCivilization , ArrayList<Civilization> civilizations){
        CivilizationController.updateFogOfWar(currentCivilization , map , mapWidth , mapHeight);
        if (frame!=null)
            frame.dispose();
        frame = new MapFrame(MapDimension.STANDARD , map , mapCenter , civilizations ,currentCivilization);
    }


}

