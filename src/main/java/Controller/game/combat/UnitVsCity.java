package Controller.game.combat;

import Controller.game.CombatUnitController;
import Controller.game.GameController;
import Controller.game.TerrainController;
import Controller.game.update.UpdateHappiness;
import Model.City;
import Model.Terrain;
import Model.Unit;
import View.CityCaptureMenu;

public class UnitVsCity {

    public static void attack(Unit unit, City city, GameController gameController) {
        damageUnit(unit , city);
        damageCity(unit , city);
        unitDeath(unit);
        cityIsDefeated(unit , city , gameController);
    }

    private static void cityIsDefeated(Unit unit, City city, GameController gameController) {
        if (city.getHp() >= 0) return;
        if (CombatUnitController.isRangedUnit(unit)) {
            city.setHp(1);
            return;
        }
        int command = new CityCaptureMenu().getCommand();
        switch (command) {
            case 1 -> captureCity(unit, city , gameController);
            case 2 -> ruinCity(city);
        }
    }

    private static void captureCity(Unit unit, City city , GameController gameController) {
        city.getOwnership().getCities().remove(city);
        unit.getCivilization().getCities().add(city);
        gameController.getCurrentCivilization().setHappiness(gameController.getCurrentCivilization().getHappiness() - 5);
    }

    private static void ruinCity(City city) {
        for (Terrain terrain : city.getTerrains()) {
            terrain.setPillaged(true);
        }
        city.getOwnership().getCities().remove(city);
    }

    private static void damageCity(Unit unit, City city) {
        double changeOfCombat = TerrainController.getChangeOfCombat(city.getTerrains().get(0));
        int currentHp = city.getHp();
        int strength;
        strength = Math.max(unit.getTypeOfUnit().getCombatStrength() , unit.getTypeOfUnit().getRangedCombatStrength());
        int finalHp = (int) (currentHp - ((double) unit.getHp() / 10) * strength * (1 + changeOfCombat));
        city.setHp(finalHp);
    }

    private static void unitDeath(Unit unit) {
        if (unit.getHp() <= 0) unit.getCivilization().getUnits().remove(unit);
    }

    private static void damageUnit(Unit unit, City city) {
        if (CombatUnitController.isRangedUnit(unit)) return;
        double changeOfCombat = TerrainController.getChangeOfCombat(TerrainController.getTerrainByLocation(unit.getLocation()));
        int currentHp = unit.getHp();
        int finalHp = (int) (currentHp - city.getDefencePower() * (1 + changeOfCombat));
        unit.setHp(finalHp);
    }
}
