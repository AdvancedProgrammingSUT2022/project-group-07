package game.Controller.game;

import game.Enum.TerrainFeatures;
import game.Enum.TypeOfTerrain;
import game.Model.Terrain;
import game.View.components.Tile;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class TileController {
    public static Tile createTile(Terrain terrain , int x, int y) {

        double yCoord = x * Tile.getTileWidth() + (y % 2) * Tile.getN() + 10;
        double xCoord = y * Tile.getTileHeight() * 0.75 + 10;

        Tile tile = new Tile(xCoord, yCoord , terrain);

        return tile;
    }

    public static void findBackGround(Tile tile) {
        if (!GameController.getInstance().getCurrentCivilization().getVisibleTerrains().contains(tile.getTerrain())) {
            tile.setFill(Color.DARKGRAY);
            return;
        }
        String address1, address2 = null;
        address1 = tile.getTerrain().getTypeOfTerrain().getName() + ".png";

        if (tile.getTerrain().getTerrainFeatures() != null) {
//            System.out.println("Type of feature = " + typeOfTerrainFeatureUsed.getName() + "    Terrain = " + typeOfTerrainUsed.getName());
            address2 = tile.getTerrain().getTypeOfTerrain().getName() + "+"
                    + tile.getTerrain().getTerrainFeatures().getName() + ".png";
        }
        tile.setBackground(address1, address2);
    }
}
