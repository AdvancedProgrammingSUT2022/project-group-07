package game.Model;
import game.Enum.TypeOfUnit;
import game.Enum.UnitStatus;

import java.util.ArrayList;

public class Unit {
    private TypeOfUnit typeOfUnit;
    private UnitStatus unitStatus;
    private Location location;
    private int hp;
    private transient Civilization civilization;
    private int turn;
    private int mp;
    private ArrayList<Terrain> pathToGo;
    private int timesMovedThisTurn;
    private Improvement improvementAboutToBeCreated;
    private Route routeAboutToBeBuilt;
    private int repairTurns;

    public UnitStatus getUnitStatus() {
        return unitStatus;
    }

    public Unit(TypeOfUnit typeOfUnit, UnitStatus unitStatus, Location location, int hp, Civilization civilization, int turn) {
        this.typeOfUnit = typeOfUnit;
        this.unitStatus = unitStatus;
        this.location = location;
        this.hp = hp;
        this.civilization = civilization;
        this.turn = turn;
        this.mp = typeOfUnit.getMp();
        this.timesMovedThisTurn = 0;
        this.pathToGo = new ArrayList<>();
    }

    public TypeOfUnit getTypeOfUnit() {
        return typeOfUnit;
    }

    public int getTurn() {
        return turn;
    }

    public void setUnitStatus(UnitStatus unitStatus) {
        this.unitStatus = unitStatus;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public Civilization getCivilization() {
        return civilization;
    }

    public void setCivilization(Civilization civilization) {
        this.civilization = civilization;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public ArrayList<Terrain> getPathToGo() {
        return pathToGo;
    }

    public void setPathToGo(ArrayList<Terrain> pathToGo) {
        this.pathToGo = pathToGo;
    }
    public int getTimesMovedThisTurn() {
        return timesMovedThisTurn;
    }

    public void setTimesMovedThisTurn(int timesMovedThisTurn) {
        this.timesMovedThisTurn = timesMovedThisTurn;
    }

    public Improvement getImprovementAboutToBeCreated() {
        return improvementAboutToBeCreated;
    }

    public void setImprovementAboutToBeCreated(Improvement improvementAboutToBeCreated) {
        this.improvementAboutToBeCreated = improvementAboutToBeCreated;
    }

    public Route getRouteAboutToBeBuilt() {
        return routeAboutToBeBuilt;
    }

    public void setRouteAboutToBeBuilt(Route routeAboutToBeBuilt) {
        this.routeAboutToBeBuilt = routeAboutToBeBuilt;
    }

    public int getRepairTurns() {
        return repairTurns;
    }

    public void setRepairTurns(int repairTurns) {
        this.repairTurns = repairTurns;
    }
}
