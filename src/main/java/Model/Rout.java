package Model;

public class Rout {
    // TODO terrain instead of location
    private String name;
    private int turnsNeeded;
    private Location location;

    public Rout(String name, int turnsNeeded, Location location) {
        this.name = name;
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
        this.turnsNeeded = turnsNeeded;
    }

    public String getName() {
        return name;
    }
}
