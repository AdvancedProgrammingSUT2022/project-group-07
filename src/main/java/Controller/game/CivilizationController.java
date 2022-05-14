package Controller.game;

import Controller.game.movement.Move;
import Controller.game.update.UpdateCityElements;
import Controller.game.update.UpdateCivilizationElements;
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
        UpdateCivilizationElements.UnitMovementsUpdate(civilization , gameController);
        UpdateCityElements.citizensIncome(civilization);
        UpdateCityElements.maintenance(civilization);
        UpdateCivilizationElements.update(civilization, gameController);
        UpdateCityElements.cityGrowth(civilization);
        UpdateCityElements.citizenGrowth(civilization);
        UpdateCityElements.update(civilization, gameController);
        UpdateCityElements.foodConsumption(civilization);
        //TODO harchidige ke moond!
    }

}
