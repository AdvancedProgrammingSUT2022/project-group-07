package game.Controller.game;

import game.Enum.TerrainFeatures;
import game.Enum.TypeOfTerrain;
import game.View.components.Tile;
import javafx.scene.shape.Polygon;

public class TileController {
    public static Tile createTile(TypeOfTerrain typeOfTerrainUsed, TerrainFeatures typeOfTerrainFeatureUsed, int x, int y) {

        double yCoord = x * Tile.getTileWidth() + (y % 2) * Tile.getN() + 10;
        double xCoord = y * Tile.getTileHeight() * 0.75 + 10;

        Tile tile = new Tile(xCoord, yCoord);
        findBackGround(tile , typeOfTerrainUsed , typeOfTerrainFeatureUsed);
        return tile;
    }

    private static void findBackGround(Tile tile, TypeOfTerrain typeOfTerrainUsed , TerrainFeatures typeOfTerrainFeatureUsed) {
        String address;
        address = typeOfTerrainUsed.getName() + ".png";
        tile.setBackground(address);
    }
}
