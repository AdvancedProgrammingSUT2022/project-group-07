package Model;

import Enum.TypeOfUnit;
import Enum.UnitStatus;

public class Workers extends Unit{
    private Improvement improvementAboutToBeCreated;
    private Route routeAboutToBeBuilt;
    private int repairTurns;

    public Workers(TypeOfUnit typeOfUnit, UnitStatus unitStatus, Location location, int hp, Civilization civilization, int turn,
                   Improvement improvementAboutToBeCreated, Route routeAboutToBeBuilt, int repairTurns) {
        super(typeOfUnit, unitStatus, location, hp, civilization, turn);
        this.improvementAboutToBeCreated = improvementAboutToBeCreated;
        this.routeAboutToBeBuilt = routeAboutToBeBuilt;
        this.repairTurns = repairTurns;
    }

    @Override
    public Improvement getImprovementAboutToBeCreated() {
        return improvementAboutToBeCreated;
    }

    @Override
    public void setImprovementAboutToBeCreated(Improvement improvementAboutToBeCreated) {
        this.improvementAboutToBeCreated = improvementAboutToBeCreated;
    }

    @Override
    public Route getRouteAboutToBeBuilt() {
        return routeAboutToBeBuilt;
    }

    @Override
    public void setRouteAboutToBeBuilt(Route routeAboutToBeBuilt) {
        this.routeAboutToBeBuilt = routeAboutToBeBuilt;
    }

    @Override
    public int getRepairTurns() {
        return repairTurns;
    }

    @Override
    public void setRepairTurns(int repairTurns) {
        this.repairTurns = repairTurns;
    }
}
