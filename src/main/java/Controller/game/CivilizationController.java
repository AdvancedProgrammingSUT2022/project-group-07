package Controller.game;

import Model.*;
import Enum.* ;

import java.util.ArrayList;

public class CivilizationController {
    private Civilization civilization;

    public void setCivilization(Civilization civilization){
        this.civilization=civilization;
    }

    public Civilization getCivilization() {
        return civilization;
    }

    /**
     * a function to add or replace current research with a new technology
     * @param research the new research
     * @return result of the command (set successfully , replaced successfully , already being researched)
     */
    public String research(Technology research){
        Technology currentResearch = civilization.getCurrentResearch();
        civilization.setCurrentResearch(research);

        if (currentResearch==null)
            return "research set successfully";

        else if (currentResearch.getTypeOfTechnology() == research.getTypeOfTechnology())
            return "you are already researching for this technology";
        else
            return "research "+currentResearch+" replaced with " + research;
    }

    public void nextTurn(){
        // TODO: update all information and go to the next civilization
    }

    /**
     * a function to update whole happiness of a civilization
     */
    public void updateHappiness(){
        ArrayList<City> cities = civilization.getCities();
        int sum = 0 ;
        for (City city : cities)
            sum += city.getHappiness();
        civilization.setHappiness(civilization.getHappiness()+sum);
    }

    /**
     * a function to update whole food of a civilization
     */
    public void updateFood(){
        ArrayList<City> cities = civilization.getCities();
        int sum = 0 ;
        for (City city : cities)
            sum += city.getFood();
        civilization.setFood(civilization.getFood()+sum);
    }

    /**
     *  a function to update whole gold of a civilization
     */
    public void updateGold(){
        ArrayList<City> cities = civilization.getCities();
        int sum = 0 ;
        for (City city : cities)
            sum += city.getGold();
        civilization.setGold(civilization.getGold()+sum);
    }

    /**
     * a function to update status of a current research
     */
    public void updateResearch(){
        Technology currentResearch = civilization.getCurrentResearch();
        currentResearch.setRemainingTurns(currentResearch.getRemainingTurns()-1);
    }

    public void updateScience(){
        // TODO: 4/21/2022 update civilization total science
    }

    private static ArrayList<Terrain> getNeighbourTerrainsByRadius1
            (Location location , Terrain[][]map , int mapWidth , int mapHeight){
        ArrayList<Terrain> out = new ArrayList<Terrain>();
        int x = location.getX();
        int y = location.getY();
        int upperRow = Math.max(0,y-1);
        int lowerRow = Math.min(mapHeight ,y+1) ;
        int leftCol = Math.max(0,x-1);
        int rightCol = Math.max(mapWidth ,x+1) ;
        for (int row=upperRow ; row<=lowerRow ; row++){
            for (int col=leftCol ; col<=rightCol ; col++){
                if (row!=y || col!=x)
                    out.add(map[y][x]);
            }
        }
        return out ;
    }

    public static void updateFogOfWar(Civilization civilization , Terrain[][] map , int mapWidth , int mapHeight){
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
            for (Terrain firstLayerNeighbour : firstLayerNeighbours) {
                ArrayList<Terrain> secondLayerNeighbours = getNeighbourTerrainsByRadius1(firstLayerNeighbour.getLocation() , map , mapWidth , mapHeight) ;
                if (firstLayerNeighbour.getTypeOfTerrain()!= TypeOfTerrain.MOUNTAIN)
                    shouldBeAdd.addAll(secondLayerNeighbours);
            }
        }
        for (Terrain terrain : shouldBeAdd)
            civilization.addKnownTerrain(terrain);
    }

    public static void updateMovingUnitFogOfWar (Civilization civilization , Terrain[][] map , int mapWidth , int mapHeight){

    }

}
