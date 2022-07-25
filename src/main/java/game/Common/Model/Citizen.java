package game.Common.Model;

public class Citizen {
    private int number;
    private Terrain terrain ;

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public Citizen(int number) {
        this.number = number;
    }

    /**
     * a function to assign citizen to terrain
     * @param terrain
     */
    public void assignWork(Terrain terrain){
        this.terrain = terrain ;
    }

    public int getNumber() {
        return number;
    }

    public void removeWork(){
        this.terrain = null ;
    }
}
