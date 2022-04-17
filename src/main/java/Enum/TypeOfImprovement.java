package Enum;

public enum TypeOfImprovement {
    CAMP("camp", 0, 0, 0, TypeOfTechnology.TRAPPING, 1111,
            new String[]{"forest","tundra", "plain", "hill"}),
    FARM("farm", 0, 1, 0, TypeOfTechnology.AGRICULTURE, 1111,
            new String[]{"plain", "desert", "grassland"}),
    LUMBER_MILL("lumberMill", 0, 0, 1, TypeOfTechnology.CONSTRUCTION ,1111,
            new String[]{"forest"}),
    MINE("mine", 0, 0, 1, TypeOfTechnology.MINING, 1111,
            new String[]{"plain", "desert", "grassland", "tundra", "snow", "hill", "forest", "jungle", "marsh"}),
    PASTURE("pasture", 0, 0, 0 , TypeOfTechnology.ANIMAL_HUSBANDRY, 1111,
            new String[]{"plain", "desert", "grassland", "tundra", "hill"}),
    PLANTATION("plantation", 0, 0, 0, TypeOfTechnology.CALENDAR, 1111,
            new String[]{"plain", "desert", "grassland", "forest", "jungle", "marsh", "floodplains"}),
    QUARRY("quarry", 0, 0, 0, TypeOfTechnology.MASONRY, 1111,
            new String[]{"plain", "desert", "grassland", "tundra", "hill"}),
    TRADING_POST("tradingPost", 1, 0, 0, TypeOfTechnology.TRAPPING, 1111,
            new String[]{"plain", "desert", "grassland", "tundra"}),
    MANUFACTORY("manufactory", 0, 0, 2, TypeOfTechnology.ENGINEERING, 1111,
            new String[]{"plain", "desert", "grassland", "tundra", "snow"});
    // turns needed

    private String name;
    private int gold;
    private int food;
    private int production;
    private TypeOfTechnology typeOfTechnology;
    private int turnsNeeded;
    private String[] canBeFoundOn;

    TypeOfImprovement(String name, int gold, int food, int production, TypeOfTechnology typeOfTechnology,
                      int turnsNeeded, String[] canBeFoundOn) {
        this.name = name;
        this.gold = gold;
        this.food = food;
        this.production = production;
        this.typeOfTechnology = typeOfTechnology;
        this.turnsNeeded = turnsNeeded;
        this.canBeFoundOn = canBeFoundOn;
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

    public Object getTypeOfTechnology() {
        return typeOfTechnology;
    }

    public void setTypeOfTechnology(TypeOfTechnology typeOfTechnology) {
        this.typeOfTechnology = typeOfTechnology;
    }

    public int getTurnsNeeded() {
        return turnsNeeded;
    }

    public void setTurnsNeeded(int turnsNeeded) {
        this.turnsNeeded = turnsNeeded;
    }

    public String[] getCanBeFoundOn() {
        return canBeFoundOn;
    }

    public void setCanBeFoundOn(String[] canBeFoundOn) {
        this.canBeFoundOn = canBeFoundOn;
    }
}
