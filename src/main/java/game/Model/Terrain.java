package game.Model;

import game.Enum.*;
import game.View.components.Tile;

import java.awt.*;
import java.util.ArrayList;

public class Terrain {
    private TypeOfTerrain typeOfTerrain;
    private TerrainFeatures terrainFeatures;
    private Resources resources;
    private Location location;
    private Improvement improvement;
    private int mp;
    private Technology technology;
    private boolean hasRailRoad;
    private boolean hasRoad;
    private boolean pillaged;
    private boolean hasRuin ;
    private TypeOfRuin typeOfRuin ;


    private ArrayList<RiverSide> riverSides;

    public Terrain(TypeOfTerrain typeOfTerrain, TerrainFeatures terrainFeatures,
                   Resources resource, Location location) {
        this.typeOfTerrain = typeOfTerrain;
        this.terrainFeatures = terrainFeatures;
        this.resources = resource;
        this.location = location;
        this.riverSides = new ArrayList<RiverSide>();
        this.mp = typeOfTerrain.getMpNeeded();
        if (terrainFeatures != null)
            this.mp += terrainFeatures.getMp();
        hasRuin = false ;
    }

    public TypeOfTerrain getTypeOfTerrain() {
        return typeOfTerrain;
    }

    public TerrainFeatures getTerrainFeatures() {
        return terrainFeatures;
    }

    public Location getLocation() {
        return location;
    }

    public Improvement getImprovement() {
        return improvement;
    }

    public Resources getResources() {
        return resources;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getPrice() {
        int price = terrainFeatures != null ? 5 : 0;
        if (resources != null) price += 5;
        price += 5;
        return price;
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

    public boolean hasRailRoad() {
        return hasRailRoad;
    }

    public void setHasRailRoad(boolean hasRailRoad) {
        this.hasRailRoad = hasRailRoad;
    }

    public boolean hasRoad() {
        return hasRoad;
    }

    public void setHasRoad(boolean hasRoad) {
        this.hasRoad = hasRoad;
    }

    public void setRiverSides(ArrayList<RiverSide> terrainRiverSides) {
        for (RiverSide terrainRiverSide : terrainRiverSides) {
            if (!this.riverSides.contains(terrainRiverSide))
                this.riverSides.add(terrainRiverSide);
        }
    }

    public void addRiverSide(RiverSide riverSide) {
        if (this.riverSides.contains(riverSide))
            this.riverSides.add(riverSide);
    }

    public ArrayList<RiverSide> getRiverSides() {
        return riverSides;
    }

    public void setImprovement(Improvement improvement) {
        this.improvement = improvement;
    }

    public boolean isPillaged() {
        return pillaged;
    }

    public void setPillaged(boolean pillaged) {
        this.pillaged = pillaged;
    }


    public void setHasRuin(boolean hasRuin) {
        this.hasRuin = hasRuin;
    }

    public boolean hasRuin (){
        return hasRuin ;
    }

    public void setTypeOfRuin(TypeOfRuin typeOfRuin) {
        setHasRuin(true);
        this.typeOfRuin = typeOfRuin;
    }

    public TypeOfRuin getTypeOfRuin (){
        return typeOfRuin ;
    }
}
