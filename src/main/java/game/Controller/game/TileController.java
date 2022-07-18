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
        String address1, address2 = null;
        address1 = typeOfTerrainUsed.getName() + ".png";

        if (typeOfTerrainFeatureUsed != null) {
//            System.out.println("Type of feature = " + typeOfTerrainFeatureUsed.getName() + "    Terrain = " + typeOfTerrainUsed.getName());
            address2 = typeOfTerrainUsed.getName() + "+" + typeOfTerrainFeatureUsed + ".png";
        }
        tile.setBackground(address1, address2);
    }
}
