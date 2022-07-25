package game.Common.Model;

import game.Common.Enum.Building;

public class UnderConstructionBuilding {

    private Building building ;
    private int remainingTurns ;

    public UnderConstructionBuilding (Building building , int remainingTurns){
        this.building = building ;
        this.remainingTurns = remainingTurns ;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public int getRemainingTurns() {
        return remainingTurns;
    }

    public void setRemainingTurns(int remainingTurns) {
        this.remainingTurns = remainingTurns;
    }
}
