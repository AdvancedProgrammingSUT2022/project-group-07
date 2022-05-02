package Controller.game;

import Model.*;
import Enum.* ;

import java.util.ArrayList;
import java.util.Arrays;

public class CivilizationController {
    private static Civilization civilization;

    public static void setCivilization(Civilization currentCivilization){
        civilization=currentCivilization;
    }

    public Civilization getCivilization() {
        return civilization;
    }

    /**
     * a function to update all resources of a civilization
     * @param civilization current civilization to update
     */
    public static void updateAll(Civilization civilization){
        // TODO: update all information and go to the next civilization
        setCivilization(civilization);
        updateScience();
        updateGold();
        updateResearch();
        updateFood();
        updateHappiness();
    }

    /**
     * a function to update whole happiness of a civilization
     */
    public static void updateHappiness(){
        ArrayList<City> cities = civilization.getCities();
        int sum = 0 ;
        for (City city : cities)
            sum += city.getHappiness();
        civilization.setHappiness(civilization.getHappiness()+sum);
    }

    /**
     * a function to update whole food of a civilization
     */
    public static void updateFood(){
        ArrayList<City> cities = civilization.getCities();
        int sum = 0 ;
        for (City city : cities)
            sum += city.getFood();
        civilization.setFood(civilization.getFood()+sum);
    }

    /**
     *  a function to update whole gold of a civilization
     */
    public static void updateGold(){
        ArrayList<City> cities = civilization.getCities();
        int sum = 0 ;
        for (City city : cities)
            sum += city.getGold();
        civilization.setGold(civilization.getGold()+sum);
    }

    /**
     * a function to update status of a current research
     */
    public static void updateResearch(){
        Technology currentResearch = civilization.getCurrentResearch();
        if (currentResearch==null)
            return;
        if (currentResearch.getRemainingTurns()==0) {
            civilization.addTechonolgy(currentResearch);
            civilization.setCurrentResearch(null);
        }
        else
            currentResearch.setRemainingTurns(currentResearch.getRemainingTurns()-1);
    }

    public static void updateScience(){
        // TODO: 4/21/2022 update civilization total science
    }

    public static ArrayList<Terrain> getNeighbourTerrainsByRadius1
            (Location location , Terrain[][]map , int mapWidth , int mapHeight){
        ArrayList<Terrain> out = new ArrayList<Terrain>();
        int x = location.getX();
        int y = location.getY();
        int upperRow = Math.max(0,y-1);
        int lowerRow = Math.min(mapHeight-1 ,y+1) ;
        int leftCol = Math.max(0,x-1);
        int rightCol = Math.min(mapWidth-1 ,x+1) ;

        out.add(map[upperRow][x]);
        if (y%2==1)
            out.add(map[upperRow][rightCol]);
        else
            out.add(map[upperRow][leftCol]);
        out.addAll(Arrays.asList(map[y]).subList(leftCol, rightCol + 1));
        out.add(map[lowerRow][x]);
        if (y%2==1)
            out.add(map[lowerRow][rightCol]);
        else
            out.add(map[lowerRow][leftCol]);
        return out ;
    }

    public static void updateFogOfWar(final Civilization civilization , final Terrain[][] map , int mapWidth , int mapHeight){
        // TODO: update civilization fog of war using it's owned units across the whole map
        // to do this , we first iterate on this civilization units and add first layer neighbours
        // every unit can see all 6 neighbours of itself , we add these 6 neighbours to an array
        // now we iterate on each of these 6 neighbours and find their neighbours again
        // if terrain is not mountain we add all of it's neighbours to an arrayList
        // at the end , we add contents of array list to knownTerrains of our civilization
        ArrayList<Unit> units = civilization.getUnits();
        ArrayList<Terrain> shouldBeAdd = new ArrayList<Terrain>();

        for (Unit unit : units) {
            ArrayList<Terrain> firstLayerNeighbours = getNeighbourTerrainsByRadius1(unit.getLocation() , map , mapWidth , mapHeight) ;
            shouldBeAdd.addAll(firstLayerNeighbours);
            for (Terrain firstLayerNeighbour : firstLayerNeighbours) {
                ArrayList<Terrain> secondLayerNeighbours = getNeighbourTerrainsByRadius1(firstLayerNeighbour.getLocation() , map , mapWidth , mapHeight) ;
                if (firstLayerNeighbour.getTypeOfTerrain()!= TypeOfTerrain.MOUNTAIN)
                    shouldBeAdd.addAll(secondLayerNeighbours);
            }
        }
        for (Terrain terrain : shouldBeAdd)
            civilization.addKnownTerrain(terrain);
    }


}
