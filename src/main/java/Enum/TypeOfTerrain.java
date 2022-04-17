package Enum;

public enum TypeOfTerrain {
    DESERT("desert", 0, 0, 0, 1,-33/100),
    GRASSLAND("grassland", 0, 2, 0, 1, -33/100),
    HILL("hill", 0,0, 2, 2, 25/100),
    MOUNTAIN("mountain", 0, 0, 0, 10000, 25/100),
    OCEAN("ocean", 0, 0, 0, 10000, 25/100),
    PLAIN("plain", 0, 1, 1, 1, -33/100),
    SNOW("snow", 0, 0, 0, 1, -33/100),
    TUNDRA("tundra", 0, 1, 0, 1, -33/100);

    private String name;
    private int gold;
    private int food;
    private int production;
    private int mpNeeded;
    private int changeOfCombat;

    TypeOfTerrain(String name, int gold, int food, int production, int mpNeeded, int changeOfCombat) {
        this.name = name;
        this.gold = gold;
        this.food = food;
        this.production = production;
        this.mpNeeded = mpNeeded;
        this.changeOfCombat = changeOfCombat;
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

    public int getMpNeeded() {
        return mpNeeded;
    }

    public void setMpNeeded(int mpNeeded) {
        this.mpNeeded = mpNeeded;
    }

    public int getChangeOfCombat() {
        return changeOfCombat;
    }

    public void setChangeOfCombat(int changeOfCombat) {
        this.changeOfCombat = changeOfCombat;
    }
}
