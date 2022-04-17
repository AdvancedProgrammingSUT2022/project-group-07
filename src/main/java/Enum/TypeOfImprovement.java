package Enum;

import java.util.ArrayList;

public enum TypeOfImprovement {
//    CAMP(),
//    FARM,
//    LUMBERMILL,
//    MINE,
//    PASTURE,
//    PLANTATION,
//    QUARRY,
//    TRADINGPOST,
//    MANUFACTORY
    ;

    private String name;
    private int gold;
    private int food;
    private int production;
    // typeOfTechnology
//    private TypeOfTechnology typeOfTechnology;
    private int turnsNeeded;
    private ArrayList<TypeOfTerrain> canBeFoundOn = new ArrayList<>();

    TypeOfImprovement(String name, int gold, int food, int production,
                      TypeOfTechnology typeOfTechnology, int turnsNeeded,
                      TypeOfTerrain typeOfTerrain) {
        this.name = name;
        this.gold = gold;
        this.food = food;
        this.production = production;
//        this.typeOfTechnology = typeOfTechnology;
        this.turnsNeeded = turnsNeeded;
        this.canBeFoundOn.add(typeOfTerrain);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getProduction() {
        return production;
    }

    public void setProduction(int production) {
        this.production = production;
    }

    // typeOfTechnology
//    public TypeOfTechnology getTypeOfTechnology() {
//        return typeOfTechnology;
//    }
//
//    public void setTypeOfTechnology(TypeOfTechnology typeOfTechnology) {
//        this.typeOfTechnology = typeOfTechnology;
//    }

    public int getTurnsNeeded() {
        return turnsNeeded;
    }

    public void setTurnsNeeded(int turnsNeeded) {
        this.turnsNeeded = turnsNeeded;
    }

    public ArrayList<TypeOfTerrain> getCanBeFoundOn() {
        return canBeFoundOn;
    }

    public void setCanBeFoundOn(ArrayList<TypeOfTerrain> canBeFoundOn) {
        this.canBeFoundOn = canBeFoundOn;
    }
}
