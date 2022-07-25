package game.Server.Controller.game;

import game.Common.Enum.TypeOfTerrain;
import game.Common.Model.*;
import game.Server.Controller.game.update.UpdateCityElements;
import game.Server.Controller.game.update.UpdateCivilizationElements;
import game.Server.Controller.game.update.UpdateHappiness;
import game.Client.View.components.Tile;

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
            (Location location , Tile[][]map , int mapWidth , int mapHeight){
        ArrayList<Tile> out = new ArrayList<Tile>();
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
        ArrayList<Terrain> outt = new ArrayList<>();
        for (Tile tile : out) {
            outt.add(tile.getTerrain());
        }
        return outt ;
    }

    public static void updateFogOfWar(final Civilization civilization , final Tile[][] map , int mapWidth , int mapHeight){
        // TODO: update civilization fog of war using it's owned units across the whole map
        // to do this , we first iterate on this civilization units and add first layer neighbours
        // every unit can see all 6 neighbours of itself , we add these 6 neighbours to an array
        // now we iterate on each of these 6 neighbours and find their neighbours again
        // if terrain is not mountain we add all of it's neighbours to an arrayList
        // at the end , we add contents of array list to knownTerrains of our civilization
        // also after buying a tile , radius 2 of it should become known
        ArrayList<Unit> units = civilization.getUnits();
        ArrayList<City> cities = civilization.getCities();
        ArrayList<Terrain> shouldBeAdd = new ArrayList<>();
        ArrayList<Terrain> visibleTerrains = new ArrayList<>();
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
        UpdateCivilizationElements.updateRuinsEffects(civilization);
        UpdateCivilizationElements.updateDiplomacyRequests(civilization);
        UpdateCityElements.citizensIncome(civilization);
        UpdateCityElements.maintenance(civilization);
        UpdateCivilizationElements.update(civilization , gameController);
        UpdateCityElements.cityGrowth(civilization);
        UpdateCityElements.citizenGrowth(civilization);
        UpdateCityElements.update(civilization, gameController);
        UpdateCityElements.foodConsumption(civilization);
        UpdateCityElements.updateUnderConstructionBuildings(civilization);
        UpdateHappiness.update(civilization, gameController);
        //TODO harchidige ke moond!
    }

}
