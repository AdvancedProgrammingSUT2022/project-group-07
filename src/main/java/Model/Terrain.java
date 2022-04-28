package Model;

import Enum.TypeOfTerrain;
import Enum.TerrainFeatures;
import Enum.Resources;

import java.util.ArrayList;

public class Terrain {
    // TODO static stuff !
    // TODO setter and also this. for terrainFeature !
    private TypeOfTerrain typeOfTerrain;
    private TerrainFeatures terrainFeatures;
    private boolean hasRiver;
    private ArrayList<Resources> resources = new ArrayList<>();
    private Location location;
    private Improvement improvement;
    private int mp;
    private Technology technology;

    public Terrain(TypeOfTerrain typeOfTerrain, TerrainFeatures terrainFeatures, boolean hasRiver,
                   ArrayList<Resources> resource, Location location, Improvement improvement, Technology technology) {
        this.typeOfTerrain = typeOfTerrain;
        this.terrainFeatures = terrainFeatures;
        this.hasRiver = hasRiver;
        this.resources = new ArrayList<Resources>();
        this.resources = resource;
        this.location = location;
        this.improvement = improvement;
        this.mp = typeOfTerrain.getMpNeeded();
        if (terrainFeatures != null)
            this.mp += terrainFeatures.getMp();
        this.technology = technology;
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

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public void setTerrainFeatures(TerrainFeatures terrainFeatures) {
        this.terrainFeatures = terrainFeatures;
    }

    public Technology getTechnology() {
        return technology;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }
}
