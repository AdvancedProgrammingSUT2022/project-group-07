package Controller.game;

import Model.City;
import Model.Location;
import Model.Unit;

import java.util.ArrayList;


public class CombatUnitController {

    private boolean isMilitary(Unit unit) {
        return true;
    }

    private String alert(Unit unit) {
        return "";
    }

    private String fortify(Unit unit) {
        return "";
    }

//    private City getCurrentCity(Unit unit, ArrayList<City> cities) {
//
//    }

    private String garrison(Unit Unit, City City) {
        return "";
    }

    private boolean isSiegeUnit(Unit unit) {
        return true;
    }

    private String setUpUnit(Unit unit) {
        return "";
    }

    private boolean canAttack(Unit unit, Location location) {
        return true;
    }

//    private City getCityByLocation(Location location) {
//
//    }
//
//    private Unit getUnitByLocation(Location location) {
//
//    }

    private String attack(Unit unit, City city) {
        return "";
    }

    private String attack(Unit unit, Unit enemyUnit) {
        return "";
    }
}
