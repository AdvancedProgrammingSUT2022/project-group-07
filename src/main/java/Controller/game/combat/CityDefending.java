package Controller.game.combat;

import Controller.game.CombatUnitController;
import Controller.game.GameController;
import Controller.game.TerrainController;
import Controller.game.UnitController;
import Model.City;
import Model.Civilization;
import Model.Terrain;
import Model.Unit;

public class CityDefending {

    public static void DefendFromPossibleTrespassers(City city , GameController gameController) {
        Unit unit = getTrespassingUnit(city , gameController);
        if (unit == null) return;
        Terrain terrain = TerrainController.getTerrainByLocation(unit.getLocation());
        double changeOfCombat = TerrainController.getChangeOfCombat(terrain);
        int currentHp = unit.getHp();
        int finalHp = (int) (currentHp - city.getDefencePower() * (1 + changeOfCombat));
        unit.setHp(finalHp);
        if (unit.getHp() <= 0) unit.getCivilization().getUnits().remove(unit);
    }

    private static Unit getTrespassingUnit(City city , GameController gameController) {
        for (Terrain terrain : city.getTerrains()) {
            Unit unit;
            if ((unit = enemyUnitIsHere(terrain , gameController)) != null)
                return unit;
        }
        return null;
    }

    private static Unit enemyUnitIsHere(Terrain terrain, GameController gameController) {
        for (Civilization civilization : gameController.getCivilizations()) {
            if (civilization.equals(gameController.getCurrentCivilization())) continue;
            for (Unit unit : civilization.getUnits()) {
                if (!CombatUnitController.isMilitary(unit)) continue;
                if (unit.getLocation().equals(terrain.getLocation()))
                    return unit;
            }
        }
        return null;
    }
}
