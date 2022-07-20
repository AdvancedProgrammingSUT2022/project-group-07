package game.Controller.game;

import game.Model.*;
import game.Enum.Resources ;
import game.Enum.TypeOfTerrain ;
import game.Enum.TerrainFeatures ;
import game.Enum.MapDimension;
import game.Enum.RiverSide;
import game.View.components.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;

public class MapController {
    private static Terrain[][] map ;
    private static final TypeOfTerrain[] typeOfTerrains = TypeOfTerrain.values() ;
    private static int mapHeight ;
    private static int mapWidth ;
    private static MapFrame frame = null ;
    private static Location mapCenter = null ;
    private static final HashMap<RiverSide , RiverSide> oppositeRiverSides = new HashMap<RiverSide, RiverSide>(){
        {
            put(RiverSide.LEFT , RiverSide.RIGHT);
            put(RiverSide.UPPER_LEFT , RiverSide.LOWER_RIGHT);
            put(RiverSide.LOWER_LEFT , RiverSide.UPPER_RIGHT);
            put(RiverSide.RIGHT , RiverSide.LEFT);
            put(RiverSide.UPPER_RIGHT , RiverSide.LOWER_LEFT);
            put(RiverSide.LOWER_RIGHT , RiverSide.UPPER_LEFT);
        }
    };

    private static boolean isPostionValid(int x , int y){
        return (x<mapWidth && x>=0 && y<mapHeight && y>=0) ;
    }

    private static ArrayList<TypeOfTerrain> getAreaTypeOfTerrains (Location location) {
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
            shouldChangeOut = (out == TypeOfTerrain.OCEAN && (x > 3 && x < mapWidth - 3) && (y > 3 && y < mapHeight - 3))
                    || (out == TypeOfTerrain.OCEAN && areaTypeOfTerrains.contains(TypeOfTerrain.DESERT))
                    || (out == TypeOfTerrain.DESERT && areaTypeOfTerrains.contains(TypeOfTerrain.OCEAN))
                    || (out == TypeOfTerrain.DESERT && areaTypeOfTerrains.contains(TypeOfTerrain.SNOW))
                    || (out == TypeOfTerrain.DESERT && areaTypeOfTerrains.contains(TypeOfTerrain.TUNDRA))
                    || (out == TypeOfTerrain.DESERT && areaTypeOfTerrains.contains(TypeOfTerrain.GRASSLAND))
                    || (out == TypeOfTerrain.SNOW && areaTypeOfTerrains.contains(TypeOfTerrain.DESERT))
                    || (out == TypeOfTerrain.SNOW && (x > 5 && x < mapWidth - 5) && (y > 5 && y < mapHeight - 5))
                    || (out == TypeOfTerrain.TUNDRA && areaTypeOfTerrains.contains(TypeOfTerrain.DESERT));
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
    private static Resources generateResources(final TypeOfTerrain typeOfTerrain , final TerrainFeatures terrainFeatures){
        Random rand = new Random();
        Resources[] possibleResources = typeOfTerrain.getPossibleResources();
        if (possibleResources!=null) {
            for (Resources possibleResource : possibleResources) {
                if (rand.nextInt()% 10 ==0) {
                    return possibleResource;
                }
            }
        }
        if (terrainFeatures!=null && terrainFeatures.getPossibleResources()!=null){
            Resources[] possibleTerrainFeatureResources = terrainFeatures.getPossibleResources();
            for (Resources possibleTerrainFeatureResource : possibleTerrainFeatureResources) {
                if (rand.nextInt() % 10 == 0) {
                    return possibleTerrainFeatureResource;
                }
            }
        }
        return null;
    }

    /**
     * a function to generate chance of having a river in each tile of map based on it's neighbours
     * @param terrain tile to decide
     * @return boolean chance of having river or not (false , true)
     */
    private static boolean generateRiverChance(Terrain terrain){
        ArrayList<Terrain> neighbours = CivilizationController.getNeighbourTerrainsByRadius1(terrain.getLocation() , map , mapWidth , mapHeight) ;
        for (Terrain neighbour : neighbours) {
            if (neighbour.getTypeOfTerrain()==TypeOfTerrain.DESERT)
                return false;
        }
        return new Random().nextInt(6) == 0;
    }

    /**
     * a function to create rivers on map
     */
    private static void generateRivers(){
        for (int y=0 ; y<mapHeight ; y++){
            for (int x=0 ; x<mapWidth ; x++){
                boolean hasRiver = generateRiverChance(map[y][x]);
                if (!hasRiver)
                    continue;
                map[y][x].setRiverSides(generateRiverSidesPattern());
            }
        }
    }

    /**
     * a function to generate random pattern of river for on tile
     * @return pattern of riversides
     */
    private static ArrayList<RiverSide> generateRiverSidesPattern(){
        Random rand = new Random();
        int randNumber = rand.nextInt(6);
        return switch (randNumber) {
            case 0 -> new ArrayList<RiverSide>() {
                {
                    add(RiverSide.UPPER_LEFT);
                    add(RiverSide.UPPER_RIGHT);
                }
            };
            case 1 -> new ArrayList<RiverSide>() {
                {
                    add(RiverSide.UPPER_LEFT);
                    add(RiverSide.LEFT);
                    add(RiverSide.LOWER_LEFT);
                }
            };
            case 2 -> new ArrayList<RiverSide>() {
                {
                    add(RiverSide.LOWER_LEFT);
                    add(RiverSide.LOWER_RIGHT);
                }
            };
            case 3 -> new ArrayList<RiverSide>() {
                {
                    add(RiverSide.UPPER_RIGHT);
                    add(RiverSide.RIGHT);
                    add(RiverSide.LOWER_RIGHT);
                }
            };
            case 4 -> new ArrayList<RiverSide>(){
                {
                    add(RiverSide.LEFT);
                    add(RiverSide.UPPER_LEFT);
                }
            };
            case 5 -> new ArrayList<RiverSide>(){
                {
                    add(RiverSide.RIGHT);
                    add(RiverSide.LOWER_RIGHT);
                }
            };
            default -> null;
        };
    }

    /**
     * a function to handle neighbouring river sides between two tiles
     * @param riverSide type of river side
     * @param row y of tile
     * @param col x of tile
     */
    private static void handleRiverSide(RiverSide riverSide , int row , int col){
        int xToModify = col + riverSide.getxEffect() ;
        int yToModify = row + riverSide.getyEffect() ;

        if (row%2==0){
            switch (riverSide){
                case UPPER_LEFT, LOWER_LEFT -> xToModify -= 1 ;
            }
        }
        else {
            switch (riverSide){
                case UPPER_RIGHT, LOWER_RIGHT -> xToModify += 1 ;
            }
        }

        if (!isPostionValid(xToModify , yToModify))
            return;
        map[yToModify][xToModify].addRiverSide(oppositeRiverSides.get(riverSide));
    }

    /**
     * a function to sync river sides between two neighbours
     */
    private static void syncRiverSides(){
        for (int y=0 ; y<mapHeight ; y++){
            for (int x=0 ; x<mapWidth ; x++){
                for (RiverSide riverSide : map[y][x].getRiverSides())
                    handleRiverSide(riverSide , y , x);
            }
        }
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
                Resources resources = generateResources(typeOfTerrainUsed , typeOfTerrainFeatureUsed);
                Tile tile = TileController.createTile(typeOfTerrainUsed , typeOfTerrainFeatureUsed , x , y, resources);
                map[y][x] = new Terrain(typeOfTerrainUsed , typeOfTerrainFeatureUsed , resources , new Location(x,y) , tile) ;
            }
        }
        generateRivers();
        syncRiverSides();
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
        frame = new MapFrame(MapDimension.STANDARD , map , mapCenter , civilizations ,currentCivilization , SelectController.selectedCity);
    }

    public static String  showMapOnLocation(Matcher matcher){
        String out = "" ;
        int x = Integer.parseInt(matcher.group("X")) ;
        int y = Integer.parseInt(matcher.group("Y")) ;
        if (x<mapWidth && x>=0 && y<mapHeight && y>=0) {
            setMapCenter(new Location(x, y));
            out = "map set to " + x + " , " + y ;
        }
        else
            out = "invalid location" ;
        return out ;
    }

    public static String showMapOnCity(Matcher matcher , ArrayList<Civilization> civilizations){
        String cityName = matcher.group("cityName") ;
        String out = "no city with name " + cityName ;
        ArrayList<City> cities = new ArrayList<>();
        for (Civilization civilization : civilizations)
            cities.addAll(civilization.getCities());
        for (City city : cities) {
            if (city.getName().equalsIgnoreCase(cityName)) {
                setMapCenter(new Location(city.getTerrains().get(0).getLocation().getX()
                        , city.getTerrains().get(0).getLocation().getY()));
                out = "map set to city " + cityName;
                break;
            }
        }
        return out;
    }

}