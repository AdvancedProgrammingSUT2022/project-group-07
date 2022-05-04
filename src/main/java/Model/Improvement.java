package Model;

import Enum.TypeOfImprovement;

public class Improvement {
    private TypeOfImprovement improvement;
    private int turn;
    private Location location;

    public Improvement(TypeOfImprovement improvement, int turn, Location location) {
        this.improvement = improvement;
        this.turn = turn;
        this.location = location;
    }

    public TypeOfImprovement getImprovement() {
        return improvement;
    }

    public void setImprovement(TypeOfImprovement improvement) {
        this.improvement = improvement;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Location getLocation() {
        return location;
    }
}
