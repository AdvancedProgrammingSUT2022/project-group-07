package Model;

import Enum.TypeOfTerrain;
import Enum.TerrainFeatures;

import java.util.ArrayList;

public class Terrain {
    private TypeOfTerrain typeOfTerrain;
    private TerrainFeatures terrainFeatures;
    private boolean hasRiver;
    private ArrayList<Object> resources = new ArrayList<>();
    private Location location;
    private Improvement improvement;

    public Terrain(TypeOfTerrain typeOfTerrain, TerrainFeatures terrainFeatures, boolean hasRiver,
                   Object resource, Location location, Improvement improvement) {
        this.typeOfTerrain = typeOfTerrain;
        this.terrainFeatures = terrainFeatures;
        this.hasRiver = hasRiver;
        this.resources.add(resource);
        this.location = location;
        this.improvement = improvement;
    }

    public TypeOfTerrain getTypeOfTerrain() {
        return typeOfTerrain;
    }

    public void setTypeOfTerrain(TypeOfTerrain typeOfTerrain) {
        this.typeOfTerrain = typeOfTerrain;
    }

    public TerrainFeatures getTerrainFeatures() {
        return terrainFeatures;
    }

    public void setTerrainFeatures(TerrainFeatures terrainFeatures) {
        this.terrainFeatures = terrainFeatures;
    }

    public boolean isHasRiver() {
        return hasRiver;
    }

    public void setHasRiver(boolean hasRiver) {
        this.hasRiver = hasRiver;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Improvement getImprovement() {
        return improvement;
    }

    public void setImprovement(Improvement improvement) {
        this.improvement = improvement;
    }
}
