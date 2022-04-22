package Model;

import Enum.TypeOfTerrain;
import Enum.TerrainFeatures;
import Enum.Resources;

import java.util.ArrayList;

public class Terrain {
    private TypeOfTerrain typeOfTerrain;
    private TerrainFeatures terrainFeatures;
    private boolean hasRiver;
    private ArrayList<Resources> resources = new ArrayList<>();
    private Location location;
    private Improvement improvement;
    private int mp;

    public Terrain(TypeOfTerrain typeOfTerrain, TerrainFeatures terrainFeatures, boolean hasRiver,
                   ArrayList<Resources> resource, Location location, Improvement improvement) {
        this.typeOfTerrain = typeOfTerrain;
        this.terrainFeatures = terrainFeatures;
        this.hasRiver = hasRiver;
        this.resources = new ArrayList<Resources>();
        this.resources = resource;
        this.location = location;
        this.improvement = improvement;
    }

    public TypeOfTerrain getTypeOfTerrain() {
        return typeOfTerrain;
    }

    public TerrainFeatures getTerrainFeatures() {
        return terrainFeatures;
    }

    public boolean isHasRiver() {
        return hasRiver;
    }

    public Location getLocation() {
        return location;
    }

    public Improvement getImprovement() {
        return improvement;
    }

    public ArrayList<Resources> getResources() {
        return resources;
    }
}
