package Model;

import java.util.ArrayList;
import Enum.Building;
import Enum.TypeOfUnit;

public class City {

    private ArrayList<Terrain> terrains;
    private ArrayList<Citizen> citizens;
    private String name;
    private int food;
    private int production;
    private int hp;
    private Civilization ownership;
    private ArrayList<Building> buildings;
    private ArrayList<TypeOfUnit> wantedUnits;
    private int turnsTillGrowth ;
    private int defencePower;
    private Unit garrison;

    public City (String name , Civilization ownership){
        this.terrains = new ArrayList<Terrain>();
        this.citizens = new ArrayList<Citizen>();
        this.name = name;
        this.food = 0;
        this.production = 0;
        this.hp = 30;
        this.ownership = ownership;
        this.buildings = new ArrayList<>();
        turnsTillGrowth = 6 ;
        this.defencePower = 10;
        this.wantedUnits = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public int getFood() {
        return food;
    }
    public ArrayList<Citizen> getCitizens() {
        return citizens;
    }
    public ArrayList<Terrain> getTerrains() {
        return terrains;
    }
    public Civilization getOwnership() {
        return ownership;
    }
    public int getHp() {
        return hp;
    }

    public void addBuilding(Building building) {
        this.buildings.add(building);
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    public ArrayList<Building> getBuildings() {
        return this.buildings;
    }

    public void addCitizen(Citizen citizen) {
        citizens.add(citizen);
    }

    public void addTerrain(Terrain terrain) {
        if (!this.terrains.contains(terrain))
            this.terrains.add(terrain);
    }

    public void setFood(int food) {
        this.food = food;
    }

    public void setProduction(int production) {
        this.production = production;
    }

    public int getProduction() {
        return production;
    }

    public ArrayList<TypeOfUnit> getWantedUnits() {
        return wantedUnits;
    }

    public void addWantedUnit(TypeOfUnit unit){
        this.wantedUnits.add(unit) ;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getTurnsTillGrowth() {
        return this.turnsTillGrowth;
    }

    public void setTurnsTillGrowth(int turnsTillGrowth) {
        this.turnsTillGrowth = turnsTillGrowth;
    }

    public int getDefencePower() {
        return defencePower;
    }

    public void setDefencePower(int defencePower) {
        this.defencePower = defencePower;
    }

    public Unit getGarrison() {
        return garrison;
    }

    public void setGarrison(Unit garrison) {
        this.garrison = garrison;
    }
}
