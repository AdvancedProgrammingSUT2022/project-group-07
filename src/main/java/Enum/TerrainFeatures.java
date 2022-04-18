package Enum;

public enum TerrainFeatures {
    // TODO type of changeOfCombat
    // TODO check mp for RIVER
    FLOODPLAINS("floodplains", 2, 0, 0, (float) -33/100, 1, new String[]{"wheat", "sugar"}),
    FOREST("forest", 1, 1, 0, (float) 25/100, 2, new String[]{"uranium", "gazelle", "wool", "color", "silk"}),
    ICE("ice", 0,0,0, 0, 10000, null),
    JUNGLE("jungle", 1, -1, 0, (float) 25/100, 2, new String[]{"oil", "uranium", "banana", "gem", "color", "spices"}),
    MARSH("marsh", -1, 0, 0, (float) -33/100, 2, new String[]{"oil", "uranium", "sugar"}),
    OASIS("oasis", 3, 0, 1, (float) -33/100, 1, null),
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

    public int getFood() {
        return food;
    }

    public int getGold() {
        return gold;
    }

    public int getProduction() {
        return production;
    }

    public int getMp() {
        return mp;
    }

    public float getChangeOfCombat() {
        return changeOfCombat;
    }

    public String[] getPossibleResources() {
        return possibleResources;
    }
}
