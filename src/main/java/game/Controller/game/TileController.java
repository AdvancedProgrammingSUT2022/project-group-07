package game.Controller.game;

import game.Enum.Resources;
import game.Enum.TerrainFeatures;
import game.Enum.TypeOfTerrain;
import game.View.components.Tile;

public class TileController {
    public static Tile createTile(TypeOfTerrain typeOfTerrainUsed, TerrainFeatures typeOfTerrainFeatureUsed, int x, int y, Resources resources) {

        double yCoord = x * Tile.getTileWidth() + (y % 2) * Tile.getN() + 10;
        double xCoord = y * Tile.getTileHeight() * 0.75 + 10;

        Tile tile = new Tile(xCoord, yCoord);
        findBackGround(tile , typeOfTerrainUsed , typeOfTerrainFeatureUsed, resources);
        return tile;
    }

    private static void findBackGround(Tile tile, TypeOfTerrain typeOfTerrainUsed , TerrainFeatures typeOfTerrainFeatureUsed, Resources resources) {
        String address1, address2 = null;
        address1 = typeOfTerrainUsed.getName() + ".png";

        if (typeOfTerrainFeatureUsed != null) {
            address2 = typeOfTerrainUsed.getName() + "+" + typeOfTerrainFeatureUsed + ".png";
        }
        tile.setBackground(address1, address2, resources, typeOfTerrainUsed, typeOfTerrainFeatureUsed);
    }
}
