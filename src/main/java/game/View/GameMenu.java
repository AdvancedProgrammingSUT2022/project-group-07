package game.View;

import game.Controller.cheat.Cheat;
import game.Controller.game.*;
import game.Controller.game.units.Settler;
import game.Controller.game.units.Worker;
import game.Controller.menu.GameMenuController;
import game.Enum.MenuName;
import game.Enum.regexes.GameMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu extends Menu{
    private final GameMenuController gameMenuController;
    private GameController gameController;
    public GameMenu(Scanner scanner, GameMenuController gameMenuController, GameController gameController){
        super(scanner);
        this.gameMenuController = gameMenuController;
        this.gameController = gameController;
    }

    public void run() {
        String input;
        Matcher matcher;
        while (MenuName.getCurrentMenu() == MenuName.GAME_MENU) {
            MapController.printMap(GameController.getInstance().getMap() , gameController.getCurrentCivilization() , gameController.getCivilizations());
            input = scanner.nextLine();
            // menu commands ::::
            if (GameMenuCommands.getMatcher(input , GameMenuCommands.EXIT) != null) {
                String result = gameMenuController.exit();
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.MENU_NAVIGATION)) != null){

            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.SHOW_CURRENT_MENU)) != null){

            }
            // info commands :::

            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INFO_RESEARCH)) != null){
                Information.researchInfo(gameController.getCurrentCivilization());
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INFO_UNITS)) != null){
                Information.unitsInformation(gameController.getCurrentCivilization());
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INFO_CITIES)) != null){
                Information.citiesInformation(gameController.getCurrentCivilization());
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INFO_DIPLOMACY)) != null){

            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INFO_VICTORY)) != null) {

            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INFO_DEMOGRAPHICS)) != null){
                Information.demographicsInformation(gameController.getCivilizations() , gameController.getCurrentCivilization());
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INFO_NOTIFICATIONS)) != null){
                Information.notificationHistory(gameController.getTurn() , gameController.getCurrentCivilization());
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INFO_MILITARY)) != null) {
                Information.militaryInformation(gameController.getCurrentCivilization());
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INFO_ECONOMIC)) != null){

            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INFO_DIPLOMATIC)) != null){

            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INFO_DEALS)) != null){

            }
            // selection commands :::::

            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.SELECT_UNIT_COMBAT)) != null){
                String result = SelectController.selectCombatUnit(matcher, gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.SELECT_UNIT_NONCOMBAT)) != null){
                String result = SelectController.selectNonCombatUnit(matcher , gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.SELECT_CITY_BY_LOCATION)) != null){
                String result = SelectController.selectCityByLocation(matcher , gameController.getCivilizations()) ;
                System.out.println(result);
                if (result.startsWith("city selected successfully")) new CityMenu(scanner , gameController).run();
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.SELECT_CITY_BY_NAME)) != null){
                String result = SelectController.selectCityByName(matcher , gameController.getCivilizations()) ;
                System.out.println(result);
                if (result.startsWith("city selected successfully")) new CityMenu(scanner , gameController).run();
            }

            // unit commands ::
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_MOVE_TO)) != null){
                String result = UnitController.moveUnit(matcher , gameController , SelectController.selectedUnit);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_SLEEP)) != null){
                String result = UnitController.sleep(gameController);
                System.out.println(result);
            }
//            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_SIEGE)) != null){
//                String result = CombatUnitController.siegeUnits();
//                System.out.println(result);
//            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_RANGED_SETUP)) != null){
                String result = CombatUnitController.setUpUnit(matcher);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_PILLAGE)) != null){
                String result = CombatUnitController.pillage();
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_ALERT)) != null){
                String result = CombatUnitController.alert(gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_FORTIFY)) != null){
                String result = CombatUnitController.fortify(gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_GARRISON)) != null){
                String result = CombatUnitController.garrison(gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_SETUP_RANGED)) != null){

            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_ATTACK))!=null){

            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.ATTACK_CITY)) != null) {
                String result = CombatUnitController.attackCity(matcher , gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_FOUND_CITY)) != null){
                String result = Settler.foundCity(matcher , gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_CANCEL_MISSION))!= null){
                String result = UnitController.cancelMission(gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_WAKE))!= null){
                String result = UnitController.wake(gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_DELETE)) != null){
                String result = UnitController.deleteUnit(gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_BUILD_IMPROVEMENT)) != null){
                String result = Worker.buildImprovement(matcher, gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_REMOVE_JUNGLE)) != null){
                String result = Worker.removeJungleOrForestOrMarsh(matcher, gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_REMOVE_FOREST)) != null){
                String result = Worker.removeJungleOrForestOrMarsh(matcher, gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_REMOVE_MARSH)) != null){
                String result = Worker.removeJungleOrForestOrMarsh(matcher, gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_REMOVE_ROUTE)) != null){
                String result = Worker.removeRoute(gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_REPAIR)) != null){
                String result = Worker.checkToRepair(gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_BUILD_ROAD)) != null){
                String result = Worker.checkToBuildRoute(matcher, gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_BUILD_RAILROAD)) != null){
                String result = Worker.checkToBuildRoute(matcher, gameController);
                System.out.println(result);
            }

            // research menu :::
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.RESEARCH_MENU)) != null){
                System.out.println("entered research menu!");
                new ResearchMenu(scanner , gameController).run();
            }

            // map commands :::
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.MAP_SHOW_LOCATION)) != null){
                String result = MapController.showMapOnLocation(matcher) ;
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.MAP_SHOW_CITY)) != null){
                String result = MapController.showMapOnCity(matcher , gameController.getCivilizations()) ;
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.MAP_MOVE)) != null){
                String result = MapController.moveMap(matcher);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.NEXT_TURN)) != null) {
                String result = gameMenuController.nextTurn(gameController);
                System.out.println(result);
            }
            // cheats :::
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INCREASE_TURN)) != null) {
                String result = Cheat.turnCheat(matcher , gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INCREASE_GOLD)) != null) {
                String result = Cheat.goldCheat(matcher , gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INCREASE_FOOD)) != null) {
                String result = Cheat.foodCheat(matcher , gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INCREASE_SCIENCE)) != null) {
                String result = Cheat.scienceCheat(matcher , gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INCREASE_PRODUCTION)) != null) {
                String result = Cheat.productionCheat(matcher , gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INCREASE_HAPPINESS)) != null) {
                String result = Cheat.happinessCheat(matcher , gameController);
                System.out.println(result);
            }
            else if ((GameMenuCommands.getMatcher(input , GameMenuCommands.HEAL_UNIT)) != null) {
                String result = Cheat.healUnit();
                System.out.println(result);
            }
            else if (GameMenuCommands.getMatcher(input , GameMenuCommands.HEAL_CITY) != null) {
                String result = Cheat.healCity();
                System.out.println(result);
            }
            else if (GameMenuCommands.getMatcher(input , GameMenuCommands.ADD_ENEMY_CITY_TO_YOURS) != null) {
                String result = Cheat.addEnemyCity(gameController);
                System.out.println(result);
            }
            else if (GameMenuCommands.getMatcher(input , GameMenuCommands.DISABLE_ENEMY_UNIT) != null) {
                String result = Cheat.disableEnemyUnit(gameController);
                System.out.println(result);
            }
            else if (GameMenuCommands.getMatcher(input , GameMenuCommands.BOMBING_ENEMY_CITY) != null){
                String result = Cheat.plantingBombsOnEnemyCity(gameController);
                System.out.println(result);
            }
            else if (GameMenuCommands.getMatcher(input , GameMenuCommands.SET_TIMES_MOVED_TO_ZERO) != null) {
                String result = Cheat.setTimesMovedZero(gameController);
                System.out.println(result);
            }
            else System.out.println("invalid command ayoub");
        }
    }
}
