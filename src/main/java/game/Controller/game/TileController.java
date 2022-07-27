package game.Controller.game;

import game.Enum.TypeOfTerrain;
import game.Main;
import game.Model.City;
import game.Model.Terrain;
import game.View.components.Tile;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;

public class TileController {
    private static Image image = new Image(Main.class.getResource("/game/assets/civAsset/map/Tiles/fogOfWar.jpg").toExternalForm());
    public static Tile createTile(Terrain terrain , int x, int y) {

        double yCoord = x * Tile.getTileWidth() + (y % 2) * Tile.getN() + 10;
        double xCoord = y * Tile.getTileHeight() * 0.75 + 10;

        Tile tile = new Tile(xCoord, yCoord , terrain);

        return tile;
    }

    public static void findBackGround(Tile tile) {
        if (!GameController.getInstance().getCurrentCivilization().getVisibleTerrains().contains(tile.getTerrain())) {
            tile.setFill(new ImagePattern(image));
            return;
        }
        String address1, address2 = null;
        address1 = tile.getTerrain().getTypeOfTerrain().getName() + ".png";

        if (tile.getTerrain().getTerrainFeatures() != null) {
            address2 = tile.getTerrain().getTypeOfTerrain().getName() + "+"
                    + tile.getTerrain().getTerrainFeatures().getName() + ".png";
        }
        tile.setBackground(address1, address2, tile.getTerrain().getResources()
                , tile.getTerrain().getTypeOfTerrain());
    }

    public static ArrayList<Tile> getTiles(ArrayList<Terrain> terrains, Tile[][] map, int mapWidth, int mapHeight) {
        ArrayList<Tile> returnTiles = new ArrayList<>();

        for (Terrain terrain : terrains) {
            for (int i = 0; i < mapHeight; i++) {
                for (int j = 0; j < mapWidth; j++) {
                    if (map[i][j].getTerrain().equals(terrain)) {
                        returnTiles.add(map[i][j]);
                    }
                }
            }
        }
        return returnTiles;
    }

    public static ArrayList<Tile> getAvailableTilesToBuy(City selectedCity , final Tile[][] map , int mapWidth , int mapHeight){
        ArrayList<Terrain> tileAvailable = new ArrayList<>();
        ArrayList<Terrain> allCivilizationOwnedTiles = new ArrayList<>();
        for (City city : selectedCity.getOwnership().getCities())
            allCivilizationOwnedTiles.addAll(city.getTerrains());

        for (Terrain terrain : selectedCity.getTerrains()) {
            ArrayList<Terrain> neighbours = CivilizationController.getNeighbourTerrainsByRadius1(terrain.getLocation() , map , mapWidth , mapHeight) ;
            for (Terrain neighbour : neighbours) {
                if (!tileAvailable.contains(neighbour)
                        && !allCivilizationOwnedTiles.contains(neighbour)
                        && neighbour.getTypeOfTerrain()!= TypeOfTerrain.OCEAN)
                    tileAvailable.add(neighbour);
            }
        }
        return TileController.getTiles(tileAvailable, map, mapWidth, mapHeight);
    }

    public static void changeStroke(Tile tile, Color color, int width) {
        tile.setStrokeWidth(width);
        tile.setStroke(color);
    }

    ////////////// may be useful! ///////////////
    public static Tile getTileByTerrain(Terrain terrain) {
        for (Tile tile : Tile.getTiles()) {
            if (tile.getTerrain().equals(terrain))
                return tile;
        }
        return null;
    }
}
