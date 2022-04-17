package Enum;

public enum TerrainFeatures {
    FLOODPLAINS("floodplains", 2, 0, 0, -33/100, 1, null),
    FOREST("forest", 1, 1, 0, 25/100, 2, null),
    ICE("ice", 0,0,0, 0, 10000, null),
    JUNGLE("jungle", 1, -1, 0, 25/100, 2, null),
    MARSH("marsh", -1, 0, 0, -33/100, 2, null),
    OASIS("oasis", 3, 0, 1, -33/100, 1, null),
    RIVER("river", 0, 0, 1, 0, 9999, null);

    private String name;
    private int food;
    private int gold;
    private int production;
    private int mp;
    private float changeOfCombat;
    private String[] possibleResources;

    TerrainFeatures(String name, int food, int production, int gold, float changeOfCombat, int mp, String[] possibleResources) {
        this.name = name;
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.changeOfCombat = changeOfCombat;
        this.mp = mp;
        this.possibleResources = possibleResources;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getProduction() {
        return production;
    }

    public void setProduction(int production) {
        this.production = production;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getChangeOfCombat() {
        return changeOfCombat;
    }

    public void setChangeOfCombat(int changeOfCombat) {
        this.changeOfCombat = changeOfCombat;
    }

    public String[] getPossibleResources() {
        return possibleResources;
    }

    public void setPossibleResources(String[] possibleResources) {
        this.possibleResources = possibleResources;
    }
}
