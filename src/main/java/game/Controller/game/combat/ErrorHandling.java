package game.Controller.game.combat;

import game.Controller.game.CivilizationController;
import game.Controller.game.CombatUnitController;
import game.Controller.game.GameController;
import game.Controller.game.TerrainController;
import game.Model.City;
import game.Model.Location;
import game.Model.Terrain;
import game.Model.Unit;
import game.Enum.CombatType;
import game.Enum.UnitStatus;
import java.util.ArrayList;

public class ErrorHandling {

    public static String findAttackCityError(Unit unit, City city, GameController gameController) {
        String error;
        if (city == null) return "there is no city in that location";
        if (gameController.getCurrentCivilization().getCities().contains(city)) return "you cant attack your own city!";
        if ((error = CombatUnitController.checkUnit(unit, gameController)) != null) return error;
        if (unit.getTimesMovedThisTurn() >= 2) return "unit is out of move!";
        if (!canAttack(unit, city.getTerrains().get(0).getLocation()))
            return "you can't attack city from here";
        if (CombatUnitController.isRangedUnit(unit) && unit.getUnitStatus() != UnitStatus.RANGED_UNIT_IS_READY)
            return "ranged unit is not set-up!";
        return null;
    }

    public static boolean canAttack(Unit unit, Location location) {
        if (unit.getTypeOfUnit().getCombatType() == CombatType.MELEE) {
            ArrayList<Terrain> neighbourTerrains = CivilizationController.getNeighbourTerrainsByRadius1(
                    unit.getLocation(), GameController.getInstance().getMap(), GameController.getInstance().getMapWidth()
                    , GameController.getInstance().getMapHeight()
            );
            return neighbourTerrains.contains(TerrainController.getTerrainByLocation(location));

        } else {
            ArrayList<Terrain> neighbourTerrains = CivilizationController.getNeighbourTerrainsByRadius1(
                    unit.getLocation() , GameController.getInstance().getMap() , GameController.getInstance().getMapWidth()
                    , GameController.getInstance().getMapHeight()
            );
            ArrayList<Terrain> allTerrainsInRange = new ArrayList<>(neighbourTerrains);
            for (Terrain terrain : neighbourTerrains) {
                ArrayList<Terrain> newTerrains = CivilizationController.getNeighbourTerrainsByRadius1(
                        terrain.getLocation() , GameController.getInstance().getMap() ,
                        GameController.getInstance().getMapWidth() , GameController.getInstance().getMapHeight()
                );
                for (Terrain newTerrain : newTerrains) {
                    allTerrainsInRange.add(newTerrain);
                }
            }
            return neighbourTerrains.contains(TerrainController.getTerrainByLocation(location));
        }
    }
}
