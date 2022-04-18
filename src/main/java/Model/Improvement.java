package Model;

import Enum.TypeOfImprovement;

public class Improvement {
    private TypeOfImprovement improvement;
    private int turn;

    public Improvement(TypeOfImprovement improvement, int turn) {
        this.improvement = improvement;
        this.turn = turn;
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
}
