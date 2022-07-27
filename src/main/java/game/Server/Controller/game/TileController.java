package game.Server.Controller.game;

import game.Client.ClientDataController;
import game.Client.Main;
import game.Common.Model.Terrain;
import game.Client.View.components.Tile;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class TileController {
    private static Image image = new Image(Main.class.getResource("/game/assets/civAsset/map/Tiles/fogOfWar.jpg").toExternalForm());
    public static Tile createTile(Terrain terrain , int x, int y) {

        double yCoord = x * Tile.getTileWidth() + (y % 2) * Tile.getN() + 10;
        double xCoord = y * Tile.getTileHeight() * 0.75 + 10;

        Tile tile = new Tile(xCoord, yCoord , terrain);

        return tile;
    }

    public static void findBackGround(GameController gameController , Tile tile) {
        if (gameController.getCurrentCivilization().getVisibleTerrains().contains(tile.getTerrain())) {
            tile.setFill(new ImagePattern(image));
//            tile.setFill(Color.DARKGRAY);
            return;
        }
        String address1, address2 = null;
        address1 = tile.getTerrain().getTypeOfTerrain().getName() + ".png";

        if (tile.getTerrain().getTerrainFeatures() != null) {
//            System.out.println("Type of feature = " + typeOfTerrainFeatureUsed.getName() + "    Terrain = " + typeOfTerrainUsed.getName());
            address2 = tile.getTerrain().getTypeOfTerrain().getName() + "+"
                    + tile.getTerrain().getTerrainFeatures().getName() + ".png";
        }
        tile.setBackground(address1, address2, tile.getTerrain().getResources()
                , tile.getTerrain().getTypeOfTerrain() ,
                tile.getTerrain().getTerrainFeatures());
    }

    public static void findBackGroundInClientSide(GameController gameController , Tile tile) {
        if (!ClientDataController.getThisCivilization().getVisibleTerrains().contains(tile.getTerrain())) {
            tile.setFill(new ImagePattern(image));
            return;
        }
        String address1, address2 = null;
        address1 = tile.getTerrain().getTypeOfTerrain().getName() + ".png";

        if (tile.getTerrain().getTerrainFeatures() != null) {
//            System.out.println("Type of feature = " + typeOfTerrainFeatureUsed.getName() + "    Terrain = " + typeOfTerrainUsed.getName());
            address2 = tile.getTerrain().getTypeOfTerrain().getName() + "+"
                    + tile.getTerrain().getTerrainFeatures().getName() + ".png";
        }
        tile.setBackground(address1, address2, tile.getTerrain().getResources()
                , tile.getTerrain().getTypeOfTerrain() ,
                tile.getTerrain().getTerrainFeatures());
    }
}
