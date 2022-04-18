package Enum;

public enum TypeOfTerrain {
    // TODO changeOfCombatType
    DESERT("desert", 0, 0, 0, 1,-33/100, new String[]{"oasis", "floodplains"},
            new String[]{"iron", "oil", "aluminium", "uranium", "gold", "silver", "gem", "marble", "cotton", "aloe", "sheep"}),
    GRASSLAND("grassland", 0, 2, 0, 1, -33/100, new String[]{"forest", "marsh"},
            new String[]{"iron", "horse", "coal", "uranium", "cow", "gold", "gem", "marble", "cotton", "wine", "sheep"}),
    HILL("hill", 0,0, 2, 2, 25/100, new String[]{"forest", "jungle"},
            new String[]{"iron", "coal", "aluminium", "uranium", "gazelle", "gold", "silver", "gem", "marble", "sheep"}),
    MOUNTAIN("mountain", 0, 0, 0, 10000, 25/100, null,
            null),
    OCEAN("ocean", 0, 0, 0, 10000, 25/100, null,
            null),
    PLAIN("plain", 0, 1, 1, 1, -33/100, new String[]{"forest", "jungle"},
            new String[]{"iron", "horse", "coal", "aluminium", "uranium", "wheat", "gold", "gem", "marble", "ivory", "cotton",
            "wine", "aloe", "sheep"}),
    SNOW("snow", 0, 0, 0, 1, -33/100, null,
            new String[]{"iron", "oil", "uranium"}),
    TUNDRA("tundra", 0, 1, 0, 1, -33/100, new String[]{"forest"},
            new String[]{"iron", "horse", "oil", "aluminium", "uranium", "gazelle", "silver", "gem", "marble", "wool"});

    private String name;
    private int gold;
    private int food;
    private int production;
    private int mpNeeded;
    private float changeOfCombat;
    private String[] possibleFeatures;
    private String[] possibleResources;

    TypeOfTerrain(String name, int gold, int food, int production, int mpNeeded, float changeOfCombat,
                  String[] possibleFeatures, String[] possibleResources) {
        this.name = name;
        this.gold = gold;
        this.food = food;
        this.production = production;
        this.mpNeeded = mpNeeded;
        this.changeOfCombat = changeOfCombat;
        this.possibleFeatures = possibleFeatures;
        this.possibleResources = possibleResources;
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

    public float getChangeOfCombat() {
        return changeOfCombat;
    }

    public void setChangeOfCombat(float changeOfCombat) {
        this.changeOfCombat = changeOfCombat;
    }

    public String[] getPossibleFeatures() {
        return possibleFeatures;
    }

    public void setPossibleFeatures(String[] possibleFeatures) {
        this.possibleFeatures = possibleFeatures;
    }

    public String[] getPossibleResources() {
        return possibleResources;
    }

    public void setPossibleResources(String[] possibleResources) {
        this.possibleResources = possibleResources;
    }
}
