package Controller.game;

import Controller.game.movement.Move;
import Controller.game.update.UpdateCityElements;
import Controller.game.update.UpdateCivilizationElements;
import Model.*;
import Enum.* ;

import java.util.ArrayList;
import java.util.Arrays;

import static Controller.game.update.UpdateCivilizationElements.updateResearch;

import static Controller.game.update.UpdateCivilizationElements.updateResearch;

public class CivilizationController {
    private static Civilization civilization;

    public static void setCivilization(Civilization currentCivilization){
        civilization=currentCivilization;
    }

    public Civilization getCivilization() {
        return civilization;
    }

    /**
     * a function to update whole happiness of a civilization
     */
    public static void updateHappiness(){

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
    public static void updateResearch(Civilization civilization){
        Technology currentResearch = civilization.getCurrentResearch();
        if (currentResearch==null)
            return;
        if (currentResearch.getRemainingTurns()==0) {
            civilization.addTechnology(currentResearch);
            civilization.setCurrentResearch(null);
        }
        else
            currentResearch.setRemainingTurns(currentResearch.getRemainingTurns() - 1);
    }

    public static void updateScience(Civilization civilization){
        int totalCitizens = 0 ;
        for (City city : civilization.getCities())
            totalCitizens += city.getCitizens().size();
        int scienceToBeAdded = civilization.getCities().size()*5 + totalCitizens ;
        civilization.setScience(civilization.getScience()+scienceToBeAdded);
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
        // also after buying a tile , radius 2 of it should become known
        ArrayList<Unit> units = civilization.getUnits();
        ArrayList<City> cities = civilization.getCities();
        ArrayList<Terrain> shouldBeAdd = new ArrayList<Terrain>();
        ArrayList<Terrain> visibleTerrains = new ArrayList<Terrain>();
        for (Unit unit : units) {
            ArrayList<Terrain> firstLayerNeighbours = getNeighbourTerrainsByRadius1(unit.getLocation() , map , mapWidth , mapHeight) ;
            shouldBeAdd.addAll(firstLayerNeighbours);
            for (Terrain firstLayerNeighbour : firstLayerNeighbours) {
                ArrayList<Terrain> secondLayerNeighbours = getNeighbourTerrainsByRadius1(firstLayerNeighbour.getLocation() , map , mapWidth , mapHeight) ;
                if (firstLayerNeighbour.getTypeOfTerrain()!= TypeOfTerrain.MOUNTAIN)
                    shouldBeAdd.addAll(secondLayerNeighbours);
            }
        }
        for (City city : cities) {
            for (Terrain terrain : city.getTerrains()) {
                ArrayList<Terrain> firstLayerNeighbours = getNeighbourTerrainsByRadius1(terrain.getLocation() , map , mapWidth , mapHeight) ;
                shouldBeAdd.addAll(firstLayerNeighbours);
                for (Terrain firstLayerNeighbour : firstLayerNeighbours) {
                    ArrayList<Terrain> secondLayerNeighbours = getNeighbourTerrainsByRadius1(firstLayerNeighbour.getLocation() , map , mapWidth , mapHeight) ;
                    shouldBeAdd.addAll(secondLayerNeighbours);
                }
            }
        }
        for (Terrain terrain : shouldBeAdd) {
            civilization.addKnownTerrain(terrain);
            if (!visibleTerrains.contains(terrain))
                visibleTerrains.add(terrain);
        }
        civilization.setVisibleTerrains(visibleTerrains);
    }

    public static void updateCivilizationElements(GameController gameController) {
        Civilization civilization = gameController.getCurrentCivilization();
        Move.UnitMovementsUpdate(civilization , gameController); //TODO update multi turn moves
        //TODO update creating units
        CivilizationController.updateResearch(civilization);
        CivilizationController.updateScience(civilization);
        //TODO update food, gold and production
        //TODO update citizens food consumption
        Move.UnitMovementsUpdate(civilization , gameController);
        UpdateCityElements.citizensIncome(civilization);
        UpdateCityElements.maintenance(civilization);
        UpdateCivilizationElements.update(civilization);
        // maintenance
        UpdateCityElements.cityGrowth(civilization);
        UpdateCityElements.citizenGrowth(civilization);
        UpdateCivilizationElements.update(civilization);
        UpdateCityElements.updateUnitsAboutToBeCreate(civilization);
        UpdateCityElements.updateRoutsAboutToBeCreated(civilization);
        Move.UnitMovementsUpdate(civilization , gameController);
        updateResearch(civilization);
        UpdateCityElements.foodConsumption(civilization);
        Move.UnitMovementsUpdate(civilization , gameController);
        //TODO harchidige ke moond!
    }

}
