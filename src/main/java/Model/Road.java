package Model;

public class Road {
    private int turnsNeeded;
    private Location location;

    public Road(int turnsNeeded, Location location) {
        this.turnsNeeded = turnsNeeded;
        this.location = location;
    }

    public int getTurnsNeeded() {
        return turnsNeeded;
    }

    public Location getLocation() {
        return location;
    }

    public void setTurnsNeeded(int turnsNeeded) {
        this.turnsNeeded += turnsNeeded;
    }
}
