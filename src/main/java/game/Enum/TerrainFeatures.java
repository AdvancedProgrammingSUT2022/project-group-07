package game.Enum;

public enum TerrainFeatures {
    // TODO check mp for RIVER --> all the remaining points
    FLOODPLAINS("floodplains", 2, 0, 0, -0.33, 1,
            new Resources[]{Resources.WHEAT, Resources.SUGAR}),
    FOREST("forest", 1, 1, 0, 0.25, 2,
            new Resources[]{Resources.GAZELLE, Resources.COLOR, Resources.SILK}),
    ICE("ice", 0,0,0, 0, Integer.MAX_VALUE, null),
    JUNGLE("jungle", 1, -1, 0, 0.25, 2,
            new Resources[]{Resources.BANANA,Resources.COLOR}),
    MARSH("marsh", -1, 0, 0, -0.33, 2,
            new Resources[]{Resources.SUGAR}),
    OASIS("oasis", 3, 0, 1, -0.33, 1, null),
    RIVER("river", 0, 0, 1, 0, 10, null);

    private String name;
    private int food;
    private int gold;
    private int production;
    private int mp;
    private double changeOfCombat;
    private Resources[] possibleResources;

    TerrainFeatures(String name, int food, int production, int gold, double changeOfCombat, int mp, Resources[] possibleResources) {
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

    public double getChangeOfCombat() {
        return changeOfCombat;
    }

    public Resources[] getPossibleResources() {
        return possibleResources;
    }
}
