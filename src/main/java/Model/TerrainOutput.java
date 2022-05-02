package Model;

public class TerrainOutput {
    private int food;
    private int production;
    private int gold;

    public TerrainOutput() {
        this.food = 0;
        this.gold = 0;
        this.production = 0;
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

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
