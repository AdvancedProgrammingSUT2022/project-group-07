package Enum;

public enum TypeOfImprovement {
//    CAMP,
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
    // technology
    private int turnsNeeded;
    // canBeFoundOn<TypeOfTerrain>

    TypeOfImprovement(String name, int gold, int food, int production, int turnsNeeded) {
        this.name = name;
        this.gold = gold;
        this.food = food;
        this.production = production;
        this.turnsNeeded = turnsNeeded;
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

    public int getTurnsNeeded() {
        return turnsNeeded;
    }

    public void setTurnsNeeded(int turnsNeeded) {
        this.turnsNeeded = turnsNeeded;
    }
}
