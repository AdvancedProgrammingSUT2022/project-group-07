package Controller.game;

import Model.Location;
import Model.Terrain;

public class TerrainController {
    public static Terrain getTerrainByLocation(Location location) {
        Terrain[][] terrains = GameController.getMap();
        for (int i = 0; i < GameController.getMapHeight(); i++) {
            for (int j = 0; j < GameController.getMapWidth(); j++) {
                if (location.getX() == terrains[i][j].getLocation().getX()
                && location.getY() == terrains[i][j].getLocation().getY())
                    return terrains[i][j];
            }
        }
        return null;
    }
}
