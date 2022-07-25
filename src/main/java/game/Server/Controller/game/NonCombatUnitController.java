package game.Server.Controller.game;

import game.Client.View.components.Tile;
import game.Common.Enum.TerrainFeatures;
import game.Common.Enum.TypeOfUnit;
import game.Common.Model.Location;
import game.Common.Model.Terrain;
import game.Common.Model.Unit;

public class NonCombatUnitController {

    // TODO type of improvement?
    // TODO handle statics
    // TODO want a method to just check the location of the terrain --> get terrain by location in TerrainController

    public static boolean isSettler(Unit unit) {
        return unit.getTypeOfUnit() == TypeOfUnit.SETTLER;
    }

    public static boolean isWorker(Unit unit) {
        return unit.getTypeOfUnit() == TypeOfUnit.WORKER;
    }


    public static Terrain isJungleHere(Location location) {
        Tile[][] tiles = GameController.getInstance().getMap();

        for (int i = 0; i < GameController.getInstance().getMapHeight(); i++) {
            for (int j = 0; j < GameController.getInstance().getMapWidth(); j++) {
                if (tiles[i][j].getTerrain().getLocation().getX() == location.getX()
                        && tiles[i][j].getTerrain().getLocation().getY() == location.getY()
                        && tiles[i][j].getTerrain().getTerrainFeatures() == TerrainFeatures.JUNGLE)
                    return tiles[i][j].getTerrain();
            }
        }
        return null;
    }

    public static Terrain isForestHere(Location location) {
        Tile[][] tiles = GameController.getInstance().getMap();

        for (int i = 0; i < GameController.getInstance().getMapHeight(); i++) {
            for (int j = 0; j < GameController.getInstance().getMapWidth(); j++) {
                if (tiles[i][j].getTerrain().getLocation().getX() == location.getX()
                        && tiles[i][j].getTerrain().getLocation().getY() == location.getY()
                        && tiles[i][j].getTerrain().getTerrainFeatures() == TerrainFeatures.FOREST)
                    return tiles[i][j].getTerrain();
            }
        }
        return null;
    }

    public static Terrain isRouteHere(Location location) {
        Tile[][] tiles = GameController.getInstance().getMap();

        for (int i = 0; i < GameController.getInstance().getMapHeight(); i++) {
            for (int j = 0; j < GameController.getInstance().getMapWidth(); j++) {
                if (tiles[i][j].getTerrain().getLocation().getX() == location.getX()
                        && tiles[i][j].getTerrain().getLocation().getY() == location.getY()
                        && (tiles[i][j].getTerrain().hasRailRoad()
                        || tiles[i][j].getTerrain().hasRoad()))
                    return tiles[i][j].getTerrain();
            }
        }
        return null;
    }

    public static Terrain isMarshHere(Location location) {
        Tile[][] tiles = GameController.getInstance().getMap();

        for (int i = 0; i < GameController.getInstance().getMapHeight(); i++) {
            for (int j = 0; j < GameController.getInstance().getMapWidth(); j++) {
                if (tiles[i][j].getTerrain().getLocation().getX() == location.getX()
                        && tiles[i][j].getTerrain().getLocation().getY() == location.getY()
                        && tiles[i][j].getTerrain().getTerrainFeatures() == TerrainFeatures.MARSH)
                    return tiles[i][j].getTerrain();
            }
        }
        return null;
    }
}
