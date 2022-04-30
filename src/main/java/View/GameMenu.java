package View;

import Controller.game.*;
import Controller.game.units.Settler;
import Controller.game.units.Worker;
import Controller.menu.GameMenuController;
import Enum.MenuName;
import Enum.regexes.GameMenuCommands;

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
            MapController.printMap(GameController.getMap() , gameController.getCurrentCivilization() , gameController.getCivilizations());
            input = scanner.nextLine();
            // menu commands ::::
            if (GameMenuCommands.getMatcher(input , GameMenuCommands.EXIT) != null) {

            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.MENU_NAVIGATION)) != null){

            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.SHOW_CURRENT_MENU)) != null){

            }
            // info commands :::

            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INFO_RESEARCH)) != null){

            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INFO_UNITS)) != null){

            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INFO_CITIES)) != null){

            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INFO_DIPLOMACY)) != null){

            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INFO_VICTORY)) != null) {

            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INFO_DEMOGRAPHICS)) != null){

            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INFO_NOTIFICATIONS)) != null){

            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INFO_MILITARY)) != null) {

            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.INFO_MILITARY)) != null){

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
                String result = CityController.selectCityByLocation(matcher , gameController.getCivilizations()) ;
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.SELECT_CITY_BY_NAME)) != null){

            }

            // city command ::
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.CITY_SHOW_TILES_OWNED)) != null){
                String result = CityController.showTilesOwned() ;
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.CITY_SHOW_TILES_AVAILABLE)) != null){
                String result = CityController.showTilesAvailable(GameController.getMap() , GameController.getMapWidth() , GameController.getMapHeight()) ;
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.CITY_BUY_TILE)) != null){
                String result = CityController.buyTile(matcher , GameController.getMap() , GameController.getMapWidth() , GameController.getMapHeight()) ;
                System.out.println(result);
            }


            // unit commands ::
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.CREATE_UNIT)) != null){
                String result = UnitController.checkRequiredTechsAndResourcesToCreateUnit(matcher, gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_MOVE_TO)) != null){
                String result = UnitController.moveUnit(matcher , gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_SLEEP)) != null){
                String result = UnitController.sleep(gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_ALERT)) != null){

            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_FORTIFY)) != null){
                String result = UnitController.fortifyUnit(gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_GARRISON)) != null){

            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_SETUP_RANGED)) != null){

            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_ATTACK))!=null){

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
                String result = Worker.removeJungle(matcher, gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_REMOVE_ROUTE)) != null){
                String result = Worker.removeRoute(matcher, gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_REPAIR)) != null){
                String result = Worker.repair(matcher, gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_BUILD_ROAD)) != null){
                String result = Worker.buildRoad(matcher, gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_BUILD_RAILROAD)) != null){
                String result = Worker.buildRailRoad(matcher, gameController);
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.UNIT_REMOVE_MARSH)) != null){
                String result = Worker.removeMarsh(matcher, gameController);
                System.out.println(result);
            }

            // research commands :::
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.RESEARCH_SHOW_CURRENT)) != null){
                String result = ResearchController.showCurrentResearch(gameController.getCurrentCivilization());
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.RESEARCH_SHOW_AVAILABLE)) != null) {
                String result = ResearchController.showAvailableResearch(gameController.getCurrentCivilization());
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.RESEARCH_SHOW_OWNED)) != null) {
                String result = ResearchController.showOwnedResearch(gameController.getCurrentCivilization());
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.RESEARCH_TECHNOLOGY)) != null) {
                String result = ResearchController.researchTechnology(matcher , gameController.getCurrentCivilization());
                System.out.println(result);
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
            else System.out.println("invalid command ayoub");
        }

    }
}
