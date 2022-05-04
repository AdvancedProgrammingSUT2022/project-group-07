package Model;

import Enum.TypeOfImprovement;

public class Improvement {
    private TypeOfImprovement TypeOfImprovement;
    private int turn;
    private Terrain terrain;

    public Improvement(TypeOfImprovement TypeOfImprovement , int turn, Terrain terrain) {
        this.TypeOfImprovement = TypeOfImprovement ;
        this.turn = turn;
        this.terrain = terrain;
    }

    public TypeOfImprovement getTypeOfImprovement () {
        return TypeOfImprovement ;
    }

    public void setTypeOfImprovement (TypeOfImprovement TypeOfImprovement ) {
        this.TypeOfImprovement  = TypeOfImprovement ;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Terrain getTerrain() {
        return terrain;
    }
}
