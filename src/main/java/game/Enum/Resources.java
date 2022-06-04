package game.Enum;

public enum Resources {
    BANANA("banana", 1, 0, 0, new String[]{"forest"}, null, TypeOfImprovement.PLANTATION, TypeOfResource.BONUS),
    COW("cow", 1, 0, 0, new String[]{"grassland"}, null, TypeOfImprovement.PASTURE, TypeOfResource.BONUS),
    GAZELLE("gazelle", 1, 0, 0, new String[]{"forest", "tundra", "hill"}, null, TypeOfImprovement.CAMP, TypeOfResource.BONUS),
    SHEEP("sheep", 2, 0, 0, new String[]{"hill", "grassland", "", "desert"}, null, TypeOfImprovement.PASTURE, TypeOfResource.BONUS),
    WHEAT("wheat", 1, 0, 0, new String[]{"plain", "floodplains"}, null, TypeOfImprovement.FARM, TypeOfResource.BONUS),
    COAL("coal", 0, 1, 0, new String[]{"plain", "hill", "grassland"}, TypeOfTechnology.SCIENTIFIC_THEORY, TypeOfImprovement.MINE, TypeOfResource.STRATEGIC),
    HORSE("horse", 0, 1, 0, new String[]{"tundra", "plain", "grassland"}, TypeOfTechnology.ANIMAL_HUSBANDRY, TypeOfImprovement.PASTURE, TypeOfResource.STRATEGIC),
    IRON("iron", 0, 1, 0, new String[]{"tundra", "plain", "desert", "hill", "grassland", "snow"}, TypeOfTechnology.IRON_WORKING, TypeOfImprovement.MINE, TypeOfResource.STRATEGIC),
    COTTON("cotton", 0, 0, 2, new String[]{"desert", "plain", "grassland"}, null, TypeOfImprovement.PLANTATION, TypeOfResource.LUXURY),
    COLOR("color", 0, 0, 2, new String[]{"jungle", "forest"}, null, TypeOfImprovement.PLANTATION, TypeOfResource.LUXURY),
    FUR("fur", 0, 0, 2, new String[]{"forest", "tundra"}, null, TypeOfImprovement.CAMP, TypeOfResource.LUXURY),
    PRECIOUS_STONE("preciousStone", 0, 0, 3, new String[]{"jungle", "tundra", "plain", "desert", "grassland", "hill"}, null, TypeOfImprovement.MINE, TypeOfResource.LUXURY),
    GOLD("gold", 0, 0, 2, new String[]{"desert", "plain", "hill", "grassland"}, null, TypeOfImprovement.MINE, TypeOfResource.LUXURY),
    ALOE("aloe", 0, 0, 2, new String[]{"plain", "desert"}, null, TypeOfImprovement.PLANTATION, TypeOfResource.LUXURY),
    IVORY("ivory", 0, 0, 2, new String[]{"plain"}, null, TypeOfImprovement.CAMP, TypeOfResource.LUXURY),
    MARBLE("marble", 0, 0, 2, new String[]{"tundra", "plain", "desert", "grassland", "hill"}, null, TypeOfImprovement.QUARRY, TypeOfResource.LUXURY),
    SILK("silk", 0, 0, 2, new String[]{"forest"}, null, TypeOfImprovement.PLANTATION, TypeOfResource.LUXURY),
    SILVER("silver", 0, 0, 2, new String[]{"tundra", "desert", "hill"}, null, TypeOfImprovement.MINE, TypeOfResource.LUXURY),
    SUGAR("sugar", 0, 0, 2, new String[]{"floodplains", "marsh"}, null, TypeOfImprovement.PLANTATION, TypeOfResource.LUXURY);


    private String name;
    private int food;
    private int production;
    private int gold;
    private String[] canBeFoundOn;
    private TypeOfTechnology technologyNeeded;
    private TypeOfImprovement improvementNeeded;
    private TypeOfResource typeOfResource;


    Resources(String name, int food, int production, int gold, String[] canBeFoundOn,
              TypeOfTechnology technologyNeeded, TypeOfImprovement improvementNeeded, TypeOfResource typeOfResource) {
        this.name = name;
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.canBeFoundOn = canBeFoundOn;
        this.technologyNeeded = technologyNeeded;
        this.improvementNeeded = improvementNeeded;
        this.typeOfResource = typeOfResource;
    }

    public String getName() {
        return name;
    }

    public int getFood() {
        return food;
    }

    public int getProduction() {
        return production;
    }

    public int getGold() {
        return gold;
    }

    public String[] getCanBeFoundOn() {
        return canBeFoundOn;
    }

    public TypeOfTechnology getTechnologyNeeded() {
        return technologyNeeded;
    }

    public TypeOfImprovement getImprovementNeeded() {
        return improvementNeeded;
    }

    public TypeOfResource getTypeOfResource() {
        return typeOfResource;
    }
}
