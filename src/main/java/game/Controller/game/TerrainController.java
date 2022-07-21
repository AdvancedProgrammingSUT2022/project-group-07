package game.Controller.game;

import game.Model.Civilization;
import game.Model.Location;
import game.Model.Terrain;
import game.Model.TerrainOutput;
import game.View.components.Tile;

public class TerrainController {
    public static Terrain getTerrainByLocation(Location location) {
        Tile[][] tiles = GameController.getInstance().getMap();
        for (int i = 0; i < GameController.getInstance().getMapHeight(); i++) {
            for (int j = 0; j < GameController.getInstance().getMapWidth(); j++) {
                if (location.getX() == tiles[i][j].getTerrain().getLocation().getX()
                && location.getY() == tiles[i][j].getTerrain().getLocation().getY())
                    return tiles[i][j].getTerrain();
            }
        }
        return null;
    }

    public static TerrainOutput getTerrainsOutput(Civilization civilization, Terrain terrain) {
        TerrainOutput terrainOutput = new TerrainOutput();
        terrainOutput.setFood(terrainOutput.getFood() + terrain.getTypeOfTerrain().getFood());
        terrainOutput.setGold(terrainOutput.getGold() + terrain.getTypeOfTerrain().getGold());
        terrainOutput.setProduction(terrainOutput.getProduction() + terrain.getTypeOfTerrain().getProduction());
        if (terrain.getTerrainFeatures() != null) {
            terrainOutput.setFood(terrainOutput.getFood() + terrain.getTerrainFeatures().getFood());
            terrainOutput.setGold(terrainOutput.getGold() + terrain.getTerrainFeatures().getGold());
            terrainOutput.setProduction(terrainOutput.getProduction() + terrain.getTerrainFeatures().getProduction());
        }
        if (terrain.getResources() != null && resourceIsAvailable(terrain , civilization)) {
            terrainOutput.setFood(terrainOutput.getFood() + terrain.getResources().getFood());
            terrainOutput.setGold(terrainOutput.getGold() + terrain.getResources().getGold());
            terrainOutput.setProduction(terrainOutput.getProduction() + terrain.getResources().getProduction());
        }
        return terrainOutput;
    }

    private static boolean resourceIsAvailable(Terrain terrain, Civilization civilization) {
        if (terrain.isPillaged()) return false;
        if (terrain.getResources().getImprovementNeeded() == null) return true;
        if (terrain.getImprovement() == null) return false;
        if (terrain.getResources().getImprovementNeeded() == terrain.getImprovement().getTypeOfImprovement()) {
            if (terrain.getResources().getTechnologyNeeded() == null)
                return true;
            if (ResearchController.isTechnologyAlreadyAchieved(
                    terrain.getResources().getTechnologyNeeded(), civilization))
                return true;
        }
        return false;
    }

    public static boolean hasImprovement(Terrain terrain) {
        return terrain.getImprovement() != null;
    }

    public static boolean hasRoad(Terrain terrain) {
        return terrain.hasRoad();
    }

    public static double getChangeOfCombat(Terrain terrain) {
        double changeOfCombat = terrain.getTypeOfTerrain().getChangeOfCombat();
        if (terrain.getTerrainFeatures() != null) changeOfCombat += terrain.getTerrainFeatures().getChangeOfCombat();
        return changeOfCombat;
    }
}
