package Model;

public class Citizen {
    private Terrain terrain ;

    /**
     * a function to assign citizen to terrain
     * @param terrain
     */
    public void assignWork(Terrain terrain){
        this.terrain = terrain ;
    }

    /**
     * a function to remove a citizen from working on a terrain
     * @param terrain
     */
    public void removeWork(Terrain terrain){
        this.terrain = null ;
    }
}
